package com.wlwq.controller.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.utils.InvitationCodeUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.login.WxAppletLoginParamsVO;
import com.wlwq.service.TokenService;
import com.wlwq.vo.LoginAccount;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author gaoce
 * @ClassName WxAppletLoginController
 * @Description 微信小程序登录相关
 */
@RestController
@RequestMapping(value = "/api/wxAppletLogin")
public class WxAppletLoginController extends ApiController {

    @Autowired
    private  TokenService tokenService;

    @Autowired
    private  IApiAccountService accountService;


    /**
     * wxJava小程序操作API
     */
    @Autowired
    private  WxMaService wxService;



    /**
     * 微信小程序appid
     */
    @Value("${wx.miniapp.appid}")
    private String appid;


    /**
     * 微信小程序appid
     */
    @Value("${wx.miniapp.secret}")
    private String secret;


    /**
     * 通过code获取session
     * @param code
     * @return
     * @throws WxErrorException
     */
    @GetMapping(value = "/getSession")
    @PassToken
    public ApiResult biography(String code) throws WxErrorException {
        if (StringUtils.isBlank(code)){
            return ApiResult.fail("code为空！");
        }
        // 查询openID等相关信息
        WxMaJscode2SessionResult session = wxService.getUserService()
                .getSessionInfo(code);
        if(session == null){
            return ApiResult.fail("未获取到相关信息！");
        }
        Map<String,Object> map = new HashMap<>(5);
        map.put("session",session);
        // 查询openID是是否为空
        //绑定的话直接返回token  先查询用户角色
        ApiAccount account = accountService.selectApiAccount(ApiAccount.builder().wxAppletOpenid(session.getOpenid()).build());
        // 是否绑定手机号 0:需要绑定手机号 1:不需要绑定手机号
        String token = "";
        if(account != null && StringUtils.isNotBlank(account.getPhone())){
            token = tokenService.createToken(LoginAccount.builder().accountId(account.getAccountId()).build());
        }
        map.put("token",token);
        return ApiResult.ok(map);
    }

    /**
     * 微信小程序登录
     *
     * @param params
     * @return
     * @throws WxErrorException
     */
    @PassToken
    @PostMapping(value = "/login")
    @Transactional(rollbackFor = Exception.class)
    public ApiResult login(@Validated WxAppletLoginParamsVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //绑定的话直接返回token  先查询用户角色
        ApiAccount wxAppletApiAccount = accountService.selectApiAccountByWxAppletOpenId(params.getWxOpenId());
        if (StringUtils.isNull(wxAppletApiAccount)) {
            String phone = getPhone(params.getCode());
            if(StringUtils.isBlank(phone)){
                return fail("未获取到手机号！");
            }
            // 查询该账号是否存在
            ApiAccount apiAccount = accountService.selectApiAccountByPhone(phone);
            if (apiAccount == null) {
                // 判断邀请码是否填写，如果填写的话是否存在
                if(StringUtils.isNotBlank(params.getInvitationCode())){
                    ApiAccount invitationAccount = accountService.selectApiAccount(ApiAccount.builder().myInvitationCode(params.getInvitationCode()).build());
                    if(invitationAccount == null){
                        return fail("该邀请码不存在");
                    }
                }
                ApiAccount account = ApiAccount.builder()
                        .headPortrait(params.getHeadPortrait())
                        .nickName(params.getNickName())
                        .phone(phone)
                        .sex(params.getSex())
                        .invitationCode(params.getInvitationCode())
                        .myInvitationCode(InvitationCodeUtils.generateCode())
                        .wxAppletOpenid(params.getWxOpenId())
                        .build();
                int flag = accountService.insertApiAccount(account);
                if (flag < 1) {
                    return fail("注册失败！");
                }
                return ok(tokenService.createToken(LoginAccount.builder().accountId(account.getAccountId()).build()));
            }else{
                int flag = accountService.updateApiAccount(ApiAccount.builder().accountId(apiAccount.getAccountId()).wxAppletOpenid(params.getWxOpenId()).build());
                if (flag < 1) {
                    return fail("更新失败！");
                }
                return ok(tokenService.createToken(LoginAccount.builder().accountId(apiAccount.getAccountId()).build()));
            }

        }
        return ok(tokenService.createToken(LoginAccount.builder().accountId(wxAppletApiAccount.getAccountId()).build()));
    }


    /**
     * 获取手机号
     * @param code
     * @return
     */
    public String getPhone(String code) {
        String phoneNumber = "";
        //请求微信获取token
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret+"";
        String tokenObj = HttpUtil.get(url);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(tokenObj);
        String accessToken = jsonObject.getStr("access_token");
        if (StringUtils.isNotEmpty(accessToken)) {
            //使用token请求微信获取用户手机号
            cn.hutool.json.JSONObject body = JSONUtil.createObj();
            body.putOpt("code", code);
            String getUserPhoneNumberUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
            String phoneObj = HttpUtil.post(getUserPhoneNumberUrl, body.toString());
            if (StringUtils.isNotEmpty(phoneObj)) {
                cn.hutool.json.JSONObject phoneJsonObject = JSONUtil.parseObj(phoneObj);
                cn.hutool.json.JSONObject phoneInfo = phoneJsonObject.getJSONObject("phone_info");
                if (phoneInfo != null) {
                    phoneNumber = phoneInfo.getStr("phoneNumber");
                }
            }
        }
        return phoneNumber;
    }

}
