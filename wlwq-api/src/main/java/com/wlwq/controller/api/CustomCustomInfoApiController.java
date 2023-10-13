package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.resultVO.CustomFindVO;
import com.wlwq.api.resultVO.CustomVO;
import com.wlwq.api.resultVO.MyCustomVO;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICustomCustomInfoService;
import com.wlwq.api.service.ICustomUserClaimService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.entity.SysUser;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/custominfo")
@AllArgsConstructor
public class CustomCustomInfoApiController extends ApiController {


    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ICustomCustomInfoService customCustomInfoService;

    private final ICustomUserClaimService customUserClaimService;

    @PostMapping("/list")
    public ApiResult list(HttpServletRequest request, CustomCustomInfo customCustomInfo) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customCustomInfo.setCompanyId(account.getCompanyId());
        List<CustomCustomInfo> list = customCustomInfoService.selectCustomCustomInfoList(customCustomInfo);
        return ApiResult.ok(list);
    }

    @PostMapping("/add")
    public ApiResult addSave(HttpServletRequest request, @RequestBody CustomCustomInfo customCustomInfo) throws ParseException {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customCustomInfo.setAddSource(1L);
        customCustomInfo.setCreateBy(account.getAccountId());
        customCustomInfo.setDeptId(account.getDeptId());
        customCustomInfo.setCompanyId(account.getCompanyId());
        return ApiResult.ok(customCustomInfoService.insertCustomCustomInfo(customCustomInfo));
    }

    @PostMapping("/edit")
    public ApiResult edit(@RequestBody CustomCustomInfo customCustomInfo) {
        return ApiResult.ok(customCustomInfoService.updateCustomCustomInfo(customCustomInfo));
    }

    /**
     * 删除客户
     */
    @PostMapping("/remove")
    @ResponseBody
    public ApiResult remove(String ids) {
        return ApiResult.ok(customCustomInfoService.deleteCustomCustomInfoByIds(ids));
    }

    @PostMapping("/editcliam")
    @ResponseBody
    public AjaxResult editcustom(CustomCustomInfo customCustomInfo) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser == null) {
            return AjaxResult.error("该登录用户不存在！");
        }
        int i = customCustomInfoService.updateCustomCustomInfo(customCustomInfo);
        if (i > 0) {
            Long userId = sysUser.getUserId();
            CustomUserClaim customUserClaim = new CustomUserClaim();
            customUserClaim.setCreateBy(userId.toString());
            customUserClaim.setCustomInfoId(customCustomInfo.getCustomId());
            customUserClaim.setCustomUserId(userId.toString());
            customUserClaim.setStatus("0");
            customUserClaim.setClaimTime(new Date());

            customUserClaimService.insertCustomUserClaim(customUserClaim);
        }
        return AjaxResult.success(i);
    }

    /**
     * 查询可认领客户
     *
     * @param customSource
     * @return
     */
    @GetMapping("/findCustomInfo")
    public ApiResult findCustomInfo(PageParam pageParam, HttpServletRequest request,
                                    @RequestParam(required = false) String customSource
            , @RequestParam(required = false) String customName, @RequestParam(required = false) String type) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CustomVO> customInfo = customCustomInfoService.findCustomInfo(customName, customSource, account.getCompanyId(), type);
        PageInfo<CustomVO> pageInfo = new PageInfo<>(customInfo);
        return ok(pageInfo);
    }

    /**
     * 查询客户详细信息及跟进状态
     */
    @GetMapping("/getCustomInfoVO")
    public ApiResult getCustomInfoVO(HttpServletRequest request, @RequestParam String customId,@RequestParam(required = false) String type) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        String accountId=null;
        if(StringUtils.isEmpty(type)){
            accountId=account.getAccountId();
        }
        return customCustomInfoService.getCustomInfoVO(customId,accountId);
    }

    /**
     * 认领客户
     */
    @GetMapping("/claimCustom")
    public ApiResult claimCustom(HttpServletRequest request, @RequestParam String customId) throws ParseException {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        return ApiResult.ok(customCustomInfoService.claimCustom(Long.parseLong(account.getAccountId()),account.getDeptId().toString(),account.getCompanyId().toString(), customId));
    }

    /**
     * 查询客户
     */
    @GetMapping("/findMyCustomVO")
    public ApiResult findMyCustomVO(PageParam pageParam, HttpServletRequest request, CustomFindVO customVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (customVO.getPositionType() == null) {
            return ApiResult.fail("用户级别不能为空！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        if (customVO.getPositionType().equals(NumberUtils.INTEGER_ONE.toString())) {
            customVO.setDeptId(account.getDeptId().toString());
        } else if (customVO.getPositionType().equals(NumberUtils.INTEGER_TWO.toString())) {
            customVO.setCompanyId(account.getCompanyId().toString());
        } else {
            customVO.setUserId(account.getAccountId());
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<MyCustomVO> myCustomVO = customCustomInfoService.findMyCustomVO(customVO);
        PageInfo<MyCustomVO> pageInfo = new PageInfo<>(myCustomVO);
        return ApiResult.ok(pageInfo);
    }


    /**
     * 查询我认领的客户
     */
    @GetMapping("/findMyCustom")
    public ApiResult findMyCustom(PageParam pageParam, HttpServletRequest request,String customName) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<MyCustomVO> myCustom = customCustomInfoService.findMyCustom(Long.parseLong(account.getAccountId()), customName);
        PageInfo<MyCustomVO> pageInfo = new PageInfo<>(myCustom);
        return ApiResult.ok(pageInfo);
    }
    /**
     * 释放客户
     */
    @GetMapping("/releaseCustom")
    public ApiResult releaseCustom(@RequestParam("customId") String customId, @RequestParam("customClaimId") String customClaimId) {
        return ApiResult.ok(customCustomInfoService.releaseCustom(customId, customClaimId));
    }

    /**
     * 查询成交
     */
    @GetMapping("/getCustomCountVO")
    public ApiResult getCustomCountVO(HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        return ApiResult.ok(customCustomInfoService.getCustomCountVO(account.getAccountId()));
    }

    /**
     * 查询数据字典
     */
    @GetMapping("/findCustomDictVO")
    public ApiResult findCustomDictVO(@RequestParam("dictType") String dictType) {
        return ApiResult.ok(customCustomInfoService.findCustomDictVO(dictType));
    }

    /**
     * 修改客户标签
     */
    @GetMapping("/updateCustomLabel")
    public ApiResult updateCustomLabel(@RequestParam("customId") String customId, @RequestParam(required = false) String customLabel) {
        return ApiResult.ok(customCustomInfoService.updateCustomLabel(customId, customLabel));
    }

    /**
     * 查询排行榜
     */
    @GetMapping("/getCustomRankingVO")
    public ApiResult getCustomRankingVO(PageParam pageParam, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        return ApiResult.ok(customCustomInfoService.getCustomRankingVO(pageParam, account.getAccountId(), account.getCompanyId().toString()));
    }
}
