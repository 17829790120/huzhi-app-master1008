package com.wlwq.controller.api;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.AccountBinding;
import com.wlwq.api.domain.AccountMedalRecord;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IAccountBindingService;
import com.wlwq.api.service.IAccountMedalRecordService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.login.AccountPasswordLoginParamsVO;
import com.wlwq.params.login.EditPasswordParamsVO;
import com.wlwq.params.login.LoginParamsVO;
import com.wlwq.params.sms.VerifySmsParamsVO;
import com.wlwq.service.SmsService;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.vo.LoginAccount;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * 登录相关
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/login")
@AllArgsConstructor
public class LoginController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 验证码服务
     */
    private final SmsService smsService;

    private final ISysDeptService deptService;

    private final IAccountMedalRecordService medalRecordService;

    private final IAccountBindingService accountBindingService;

    /**
     * 验证码登录/注册
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PassToken
    public ApiResult login(@Validated LoginParamsVO paramsVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 校验验证码
        if (!smsService.checkCode(paramsVO.getUuid(), paramsVO.getCode(), paramsVO.getPhone())) {
            return fail("验证码错误！");
        }
        Map<String, String> map = new HashMap<String, String>(2);
        // 查询该账号是否存在
        ApiAccount account = accountService.selectApiAccountByPhone(paramsVO.getPhone());
        if (account == null) {
            //新增用户信息
            ApiResult apiResult = accountService.addExperienceAccount(paramsVO.getPhone());
            if(apiResult.getCode()==ApiCode.SUCCESS.getCode()){
                account = accountService.selectApiAccountByPhone(paramsVO.getPhone());
            }else{
                return fail("账号信息错误！");
            }
        }
        if (account.getForbiddenStatus() == 1) {
            return fail("账号已被管理员禁用，请联系管理员！");
        }
        //是否为体验账号并判断是否过期
        if(account.getIsExperience().equals(NumberUtils.INTEGER_ONE.toString())){
            if(!accountService.isExpire(paramsVO.getPhone())){
                return fail("您当前账号体验时长已过期");
            }
        }
        // 如果微信授权登录，传过来的值不为空的情况下，库里边没有openid不为空的情况下
        if (StringUtils.isEmpty(account.getWxOpenid()) && StringUtils.isNotEmpty(paramsVO.getWxOpenId())) {
            accountService.updateApiAccount(ApiAccount.builder().accountId(account.getAccountId()).wxOpenid(paramsVO.getWxOpenId()).build());
        }
        map.put("token", tokenService.createToken(LoginAccount.builder().accountId(account.getAccountId()).build()));
        // 查询已经绑定的账号和添加的账号是否存在
        if(StringUtils.isNotBlank(paramsVO.getAccountId())){
            accountBindingService.insertAccountBinding(AccountBinding.builder().accountId(paramsVO.getAccountId()).accountIds(account.getAccountId()).build());
        }
        return ApiResult.ok(map);
    }


    /**
     * 微信授权登录
     *
     * @return
     */
    @RequestMapping(value = "/checkBindWeChat", method = RequestMethod.POST)
    @PassToken
    public ApiResult checkBindWeChat(String wxOpenId) {
        if (StringUtils.isNull(wxOpenId)) {
            return fail("微信唯一标识不能为空！");
        }
        ApiAccount apiAccount = accountService.selectApiAccountByWxOpenId(wxOpenId);
        if (apiAccount == null) {
            return ApiResult.result(ApiCode.DONT_BIND_WECHAT);
        }
        return ApiResult.ok(tokenService.createToken(LoginAccount.builder().accountId(apiAccount.getAccountId()).build()));
    }

    /**
     * 根据账号ID查询token
     * accountId  账号ID
     * @return
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    public ApiResult checkToken(@RequestParam(defaultValue = "0") String accountId) {
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return ApiResult.fail("该账号不存在！");
        }
        return ApiResult.ok(tokenService.createToken(LoginAccount.builder().accountId(apiAccount.getAccountId()).build()));
    }

    /***
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPhoneByToken")
    public ApiResult getPhoneByToken(HttpServletRequest request) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount != null) {
            // 查询已领取的一级勋章数量
            apiAccount.setMedalCount(medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder()
                    .accountId(accountId)
                    .tag(0)
                    .build()));
        }
        return ok(apiAccount);
    }

    /***
     * 修改用户信息
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public ApiResult updateAccount(HttpServletRequest request,
                                   @Valid ApiAccount apiAccount,
                                   BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String accountId = tokenService.getAccountId(request);
        ApiAccount account = accountService.selectApiAccountById(accountId);
        if (account == null) {
            return fail("查询用户信息失败！");
        }
        if (apiAccount.getWxOpenid() != null) {
            List<ApiAccount> apiAccounts = accountService.selectApiAccountList(ApiAccount.builder().wxOpenid(apiAccount.getWxOpenid()).build());
            if (apiAccounts.size() != 0) {
                return fail("该微信已绑定其他账号");
            }
        }
        apiAccount.setAccountId(accountId);
        int count = accountService.updateApiAccount(apiAccount);
        if (count <= 0) {
            throw new ApiException("修改失败！");
        }
        return okMsg("修改成功！");
    }

    /**
     * 校验验证码是否正确
     *
     * @param params
     * @return
     */
    @PassToken
    @PostMapping(value = "/verifySms")
    public ApiResult verifySms(@Validated VerifySmsParamsVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 校验验证码
        if (!smsService.checkCode(params.getUuid(), params.getCode(), params.getPhone())) {
            return fail("验证码错误！");
        }
        return ApiResult.okMsg("成功！");
    }


    /**
     * 更换手机号提交
     *
     * @return
     */
    @RequestMapping(value = "/editPhoneSub", method = RequestMethod.POST)
    @PassToken
    public ApiResult editPhoneSub(HttpServletRequest request, @Validated LoginParamsVO paramsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 校验验证码
        if (!smsService.checkCode(paramsVO.getUuid(), paramsVO.getCode(), paramsVO.getPhone())) {
            return fail("验证码错误！");
        }
        ApiAccount apiAccount = accountService.selectApiAccountByPhone(paramsVO.getPhone());
        if (apiAccount != null) {
            return fail("该手机号已存在！");
        }
        String accountId = tokenService.getAccountId(request);
        ApiAccount account = accountService.selectApiAccountById(accountId);
        if (account == null) {
            return fail("查询用户信息失败！");
        }
        // 更换手机号
        account.setPhone(paramsVO.getPhone());
        int count = accountService.updateApiAccount(account);
        if (count <= 0) {
            return fail("更换手机号失败！");
        }
        return ok(tokenService.createToken(LoginAccount.builder().accountId(accountId).build()));
    }

    /**
     * 微信解绑
     *
     * @return
     */
    @RequestMapping(value = "/wxUntie", method = RequestMethod.POST)
    public ApiResult wxUntie(HttpServletRequest request) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount account = accountService.selectApiAccountById(accountId);
        if (account == null) {
            return fail("查询用户信息失败！");
        }
        return ok(accountService.wxUntie(accountId));
    }


    /**
     * 用户注销
     *
     * @return
     */
    @RequestMapping(value = "/accountLogout", method = RequestMethod.POST)
    public ApiResult accountLogout(HttpServletRequest request, @Validated LoginParamsVO
            userLoginParamsVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 校验验证码
        if (!smsService.checkCode(userLoginParamsVO.getUuid(), userLoginParamsVO.getCode(), userLoginParamsVO.getPhone())) {
            return fail("验证码错误！");
        }
        String accountId = tokenService.getAccountId(request);
        ApiAccount account = accountService.selectApiAccountById(accountId);
        if (account == null) {
            return fail("查询用户信息失败！");
        }
        // 查询有没有数据，没有的话才能去注销
        return ok(accountService.deleteApiAccountById(accountId));
    }

    /**
     * 设置密码/修改密码/忘记密码
     *
     * @return
     */
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    @PassToken
    public ApiResult editPassword(@Validated EditPasswordParamsVO
                                          editPasswordParamsVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 查询该账号是否存在
        ApiAccount account = accountService.selectApiAccountByPhone(editPasswordParamsVO.getPhone());
        if (account == null) {
            return fail("查询用户信息失败！");
        }
        // 校验验证码
        if (!smsService.checkCode(editPasswordParamsVO.getUuid(), editPasswordParamsVO.getCode(), editPasswordParamsVO.getPhone())) {
            return fail("验证码错误！");
        }
        // 前端密码传输加密，需在此解密
        String password = Base64.decodeStr(editPasswordParamsVO.getPassword());
        int count = accountService.updateApiAccount(ApiAccount.builder().accountId(account.getAccountId()).password(SmUtil.sm3(password)).build());
        if (count <= 0) {
            return fail("设置密码失败！");
        }
        return ok(tokenService.createToken(LoginAccount.builder().accountId(account.getAccountId()).build()));
    }

    /**
     * 用户端 账号密码登录
     *
     * @return
     */
    @RequestMapping(value = "/passwordLogin", method = RequestMethod.POST)
    @PassToken
    public ApiResult passwordLogin(@Validated AccountPasswordLoginParamsVO
                                           accountPasswordLoginParamsVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 查询该账号是否存在
        ApiAccount account = accountService.selectApiAccountByPhone(accountPasswordLoginParamsVO.getPhone());
        if (account == null) {
            return fail("该手机号不存在！");
        }

        if (StringUtils.isEmpty(account.getPassword())) {
            return fail("请先用账号、验证码登录，登录成功之后在个人中心设置密码！");
        }
        // 前端密码传输加密，需在此解密
        String password = Base64.decodeStr(accountPasswordLoginParamsVO.getPassword());
        if (!SmUtil.sm3(password).equals(account.getPassword())) {
            return fail("密码不正确！");
        }
        if (account.getForbiddenStatus() == 1) {
            return fail("账号已被管理员禁用，请联系管理员！");
        }
        // 查询已经绑定的账号和添加的账号是否存在
        if(StringUtils.isNotBlank(accountPasswordLoginParamsVO.getAccountId())){
            accountBindingService.insertAccountBinding(AccountBinding.builder().accountId(accountPasswordLoginParamsVO.getAccountId()).accountIds(account.getAccountId()).build());
        }
        return ApiResult.ok(tokenService.createToken(LoginAccount.builder().accountId(account.getAccountId()).build()));
    }

    /***
     * 根据token获取用户手机号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPhone")
    public ApiResult getPhone(HttpServletRequest request) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return fail("该账号不存在！");
        }
        return ok(apiAccount.getPhone());
    }

    /**
     * 个人资料中绑定微信
     *
     * @param wxOpenId 微信openId
     * @param request
     * @return
     */
    @PostMapping(value = "/bindWeChat")
    public ApiResult bindWeChat(String wxOpenId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该账号不存在！");
        }
        // 查询同一类型openId是否存在
        ApiAccount openIdAccount = accountService.selectApiAccountByWxOpenId(wxOpenId);
        if (openIdAccount != null) {
            return ApiResult.fail("该微信标识已存在，请勿重复绑定！");
        }
        // 更新用户信息
        int count = accountService.updateApiAccount(ApiAccount.builder().accountId(account.getAccountId()).wxOpenid(wxOpenId).build());
        if (count <= 0) {
            return ApiResult.fail("绑定失败！");
        }
        return ApiResult.okMsg("绑定成功！");
    }

    /***
     * 查询部门及部门下人数统计
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDept")
    public ApiResult getDept(HttpServletRequest request) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (apiAccount.getCompanyId() == null) {
            return fail("此用户未安排部门。");
        }
        List<HashMap<String, Object>> map = deptService.selectDeptMap(apiAccount.getCompanyId());
        return ok(map);
    }

    /***
     * 查询部门下的人
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAccountByDept")
    public ApiResult getAccountByDept(HttpServletRequest request, Long deptId, Long companyId) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (deptId == null) {
            return fail("请选择需要查询的部门。");
        }
        if (companyId == null) {
            return fail("请选择需要查询的公司。");
        }
        List<HashMap<String, Object>> list = accountService.selectAccountMap(deptId, companyId, null);
        return ok(list);
    }

    /***
     * 查询部门及部门下的人
     * @param request
     * @param keyword 关键字搜索
     * @return
     */
    @RequestMapping(value = "/getDeptAndAccount")
    public ApiResult getDeptAndAccount(HttpServletRequest request, String keyword) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (apiAccount.getCompanyId() == null) {
            return fail("此用户未安排部门。");
        }
        List<HashMap<String, Object>> list = deptService.selectDeptAndAccountMap(apiAccount.getCompanyId());
        list.forEach(
                obj -> {
                    List<HashMap<String, Object>> accountList = accountService.selectAccountMap(Long.valueOf(obj.get("deptId").toString()), apiAccount.getCompanyId(), keyword);
                    obj.put("accountList", accountList);
                }
        );
        return ok(list);
    }

}
