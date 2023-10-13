package com.wlwq.controller.sms;

import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.request.SendNoticeRequest;
import com.wlwq.service.SmsService;
import com.wlwq.utils.ClientIpUtil;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @ClassName SmsController
 * @Description 短信
 * @Date 2021/1/6 16:01
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/api/sms")
@AllArgsConstructor
public class SmsController extends ApiController {

    private final SmsService smsService;

    /**
    *  @Description 发送验证码
    *  @author Rick wlwq
    *  @Date   2021/1/6 18:16
    */
    @PassToken
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    public ApiResult sendSms(@Validated SendNoticeRequest params, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        // 获取clientIP
        params.setClientIp(ClientIpUtil.getClientIp(request));
        return ok(smsService.sendSms(params));
    }

}
