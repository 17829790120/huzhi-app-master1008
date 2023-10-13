package com.wlwq.setting.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.service.TokenService;
import com.wlwq.setting.domain.SettingFeedback;
import com.wlwq.setting.params.FeedbackParams;
import com.wlwq.setting.service.ISettingFeedbackService;
import com.wlwq.setting.service.ISettingFeedbackTypeService;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @ClassName FeedbackController
 * @Description 意见反馈
 * @Date 2021/6/7 17:50
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/api/feedback")
@AllArgsConstructor
public class FeedbackController extends ApiController {

    private IApiAccountService accountService;

    /**
    *  @Description 意见反馈类型
    *  @author Rick wlwq
    *  @Date   2021/6/7 18:12
    */
    @RequestMapping(value = "/feedbackTypeList",method = RequestMethod.GET)
    @PassToken
    public ApiResult feedbackTypeList(){
        return ok(typeService.selectTypeList());
    }

    /**
    *  @Description 意见反馈
    *  @author Rick wlwq
    *  @Date   2021/6/7 18:05
    */
    @RequestMapping(value = "/subFeedback",method = RequestMethod.POST)
    public ApiResult subFeedback(HttpServletRequest request, @Validated FeedbackParams params, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 查询用户
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return fail("该账号不存在！");
        }
        int flag = feedbackService.insertSettingFeedback(SettingFeedback.builder()
                .accountId(tokenService.getAccountId(request))
                .accountHead(apiAccount.getHeadPortrait())
                .accountName(apiAccount.getNickName())
                .accountPhone(apiAccount.getPhone())
                .feedbackType(params.getFeedbackType())
                .feedbackContent(params.getContent())
                .feedbackImages(params.getImages())
                .contactWay(params.getContactWay())
                .build());
        if (flag < 1){
            return fail("反馈异常！");
        }
        return ok("反馈成功！");
    }

    private final TokenService tokenService;

    private final ISettingFeedbackService feedbackService;

    private final ISettingFeedbackTypeService typeService;
}
