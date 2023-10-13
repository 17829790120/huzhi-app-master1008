package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICustomUserClaimService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customclaim")
@AllArgsConstructor
public class CustomUserClaimApiController {

    private final TokenService tokenService;

    private final ICustomUserClaimService customUserClaimService;

    private final IApiAccountService accountService;

    @PostMapping("/list")
    @ResponseBody
    public ApiResult list(CustomUserClaim customUserClaim) {
        List<CustomUserClaim> list = customUserClaimService.selectCustomUserClaimList(customUserClaim);
        return ApiResult.ok(list);
    }

    @PostMapping("/add")
    @ResponseBody
    public ApiResult addSave(HttpServletRequest request, CustomUserClaim customUserClaim) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customUserClaim.setCreateBy(account.getAccountId());
        return ApiResult.ok(customUserClaimService.insertCustomUserClaim(customUserClaim));
    }

    @PostMapping
    public ApiResult edit(HttpServletRequest request,@RequestBody CustomUserClaim customUserClaim){
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customUserClaim.setCreateBy(account.getAccountId());
        return ApiResult.ok(customUserClaimService.updateCustomUserClaim(customUserClaim));
    }

    @PostMapping( "/remove")
    @ResponseBody
    public ApiResult remove(String ids) {
        return ApiResult.ok(customUserClaimService.deleteCustomUserClaimByIds(ids));
    }
}
