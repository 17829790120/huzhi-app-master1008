package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CustomType;
import com.wlwq.api.resultVO.CustomLevelVO;
import com.wlwq.api.resultVO.CustomTypeVO;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICustomTypeService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
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
 * 客户意向Controller
 *
 * @author wlwq
 * @date 2023-06-10
 */
@RestController
@RequestMapping("/api/type")
@AllArgsConstructor
public class CustomTypeApiController extends ApiController {


    @Autowired
    private ICustomTypeService customTypeService;
    @Autowired
    private IApiAccountService accountService;
    @Autowired
    private TokenService tokenService;
    /**
     * 查询客户意向
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request,@RequestParam(required = false) String name) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        List<CustomTypeVO> list = customTypeService.findCustomTypeVO(account.getCompanyId().toString(),name);
        return ApiResult.ok(list);
    }
}