package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.QuestionnaireRecord;
import com.wlwq.api.domain.QuestionnaireRecordAnswer;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IQuestionnaireRecordAnswerService;
import com.wlwq.api.service.IQuestionnaireRecordService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
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
 * 内部调研问卷记录
 */
@RestController
@RequestMapping(value = "/api/questionnaireRecord")
@AllArgsConstructor
public class QuestionnaireRecordApiController extends ApiController {

    private final IQuestionnaireRecordService questionnaireRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IQuestionnaireRecordAnswerService questionnaireRecordAnswerService;

    /**
     * 内部调研问卷记录列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(String questionnaireId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if(account == null){
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if(StringUtils.isEmpty(questionnaireId)){
            return ApiResult.fail("请选择试题");
        }
        List<QuestionnaireRecord> list = questionnaireRecordService.selectQuestionnaireRecordList(QuestionnaireRecord
                .builder()
                .questionnaireId(questionnaireId)
                .delStatus(0)
                .build());
        list.forEach(
                obj -> {
                    List<QuestionnaireRecordAnswer> questionnaireRecordAnswerList = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerList(QuestionnaireRecordAnswer
                            .builder()
                            .questionnaireRecordId(obj.getQuestionnaireRecordId())
                            .delStatus(0)
                            .build());
                    obj.setQuestionnaireRecordAnswerList(questionnaireRecordAnswerList);
                }
        );
        return ok(list);
    }

}
