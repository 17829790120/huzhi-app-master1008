package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.Questionnaire;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IQuestionnaireService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wwb
 * 内部调研问卷
 */
@RestController
@RequestMapping(value = "/api/questionnaire")
@AllArgsConstructor
public class QuestionnaireApiController extends BaseController {

    private IQuestionnaireService questionnaireService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 内部调研问卷列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(String insideSurveyCategoryId, HttpServletRequest request,String keyword) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if(account == null){
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if(StringUtils.isEmpty(insideSurveyCategoryId)){
            return ApiResult.fail("请选择类别");
        }
        List<Questionnaire> list = questionnaireService.selectQuestionnaireList(Questionnaire
                .builder()
                .insideSurveyCategoryId(insideSurveyCategoryId)
                .delStatus(0)
                .title(keyword)
                .build());
        return ApiResult.ok(list);
    }

}
