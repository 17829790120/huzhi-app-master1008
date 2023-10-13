package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICustomFollowService;
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
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customfollow")
@AllArgsConstructor
public class CustomFollowApiController {


    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ICustomFollowService customFollowService;

    @PostMapping("/list")
    @ResponseBody
    public ApiResult list(CustomFollow customFollow) {
        List<CustomFollow> list = customFollowService.selectCustomFollowList(customFollow);
        return ApiResult.ok(list);
    }

    /**
     * 新增保存客户跟进
     */
    @PostMapping("/add")
    public ApiResult addSave(HttpServletRequest request,@RequestBody CustomFollow customFollow) throws ParseException {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customFollow.setDeptId(account.getDeptId());
        customFollow.setCompanyId(account.getCompanyId());
        customFollow.setCreateBy(account.getAccountId());
        return ApiResult.ok(customFollowService.insertCustomFollow(customFollow));
    }

    @PostMapping
    public ApiResult edit(HttpServletRequest request,@RequestBody CustomFollow customFollow){
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customFollow.setCreateBy(account.getAccountId());
        return ApiResult.ok(customFollowService.updateCustomFollow(customFollow));
    }

    @PostMapping( "/remove")
    @ResponseBody
    public ApiResult remove(String ids) {
        return ApiResult.ok(customFollowService.deleteCustomFollowByIds(ids));
    }

}
