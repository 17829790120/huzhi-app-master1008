package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CustomLevelDays;
import com.wlwq.api.resultVO.CustomLevelVO;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICustomLevelDaysService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 客户等级回访日维护Controller
 *
 * @author wlwq
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/api/days")
@AllArgsConstructor
public class CustomLevelDaysApiController extends ApiController {
    @Autowired
    private ICustomLevelDaysService customLevelDaysService;
    @Autowired
    private IApiAccountService accountService;
    @Autowired
    private TokenService tokenService;
    /**
     * 查询客户等级回访日维护列表
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        List<CustomLevelVO> list = customLevelDaysService.findCustomLevelVO(account.getCompanyId().toString());
        return ApiResult.ok(list);
    }

}