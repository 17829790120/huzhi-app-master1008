package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.InsideSurveyCategory;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IInsideSurveyCategoryService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wwb
 * 内部调研类别
 */
@RestController
@RequestMapping(value = "/api/insideSurveyCategory")
@AllArgsConstructor
public class InsideSurveyCategoryApiController extends BaseController {

    private final IInsideSurveyCategoryService insideSurveyCategoryService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 内部调研类别列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(Long deptId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if(account == null){
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if(deptId == null){
            return ApiResult.fail("请选择所在公司");
        }
        List<InsideSurveyCategory> list = insideSurveyCategoryService.selectInsideSurveyCategoryList(InsideSurveyCategory
                .builder()
                .deptId(deptId)
                .delStatus(0)
                .build());
        return ApiResult.ok(list);
    }
}
