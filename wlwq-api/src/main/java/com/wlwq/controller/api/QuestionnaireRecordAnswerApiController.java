package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.QuestionnaireRecordAnswer;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IQuestionnaireRecordAnswerService;
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
 * 问卷记录答案
 */
@RestController
@RequestMapping(value = "/api/questionnaireRecordAnswer")
@AllArgsConstructor
public class QuestionnaireRecordAnswerApiController extends BaseController {

    private final IQuestionnaireRecordAnswerService questionnaireRecordAnswerService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 问卷记录答案列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(String questionnaireRecordId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if(account == null){
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if(StringUtils.isEmpty(questionnaireRecordId)){
            return ApiResult.fail("请选择试题题目");
        }
        List<QuestionnaireRecordAnswer> list = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerList(QuestionnaireRecordAnswer
                .builder()
                .questionnaireRecordId(questionnaireRecordId)
                .delStatus(0)
                .build());
        return ApiResult.ok(list);
    }
}
