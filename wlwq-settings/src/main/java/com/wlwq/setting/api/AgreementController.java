package com.wlwq.setting.api;

import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.setting.service.ISettingAgreementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AgreementController
 * @Description 协议
 * @Date 2021/6/7 16:38
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/set/agreement")
@AllArgsConstructor
public class AgreementController extends ApiController {

    /**
    *  @Description 根据协议ID获取协议内容
    *  @author Rick wlwq
    *  @Date   2021/6/7 17:13
    */
    @RequestMapping(value = "/agreementById",method = RequestMethod.GET)
    public ApiResult agreementById(String agreementId){
        if (StringUtils.isBlank(agreementId)){
            return fail("协议标识为空！");
        }
        return ok(agreementService.selectSettingAgreementById(agreementId));
    }

    private final ISettingAgreementService agreementService;

}
