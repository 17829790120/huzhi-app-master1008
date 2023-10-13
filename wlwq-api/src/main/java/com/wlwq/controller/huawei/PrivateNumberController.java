package com.wlwq.controller.huawei;

import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.privatePhone.axService.AXService;
import com.wlwq.privatePhone.params.AxBindParams;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PrivateNumberController
 * @Description 华为隐私保护通话
 * @Date 2021/4/6 14:58
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/api/privateNumber")
@AllArgsConstructor
public class PrivateNumberController extends ApiController {

    private final AXService axService;

    @PassToken
    @RequestMapping(value = "/getRecordVideo")
    public ApiResult getRecordVideo(){
        return ok(axService.axGetRecordDownloadLink("ostorQH.huawei.com","21071207281312050575210.wav"));
    }

    @PassToken
    @RequestMapping(value = "/axBind")
    public ApiResult axBind(){
        return ok(axService.axBind(AxBindParams.builder()
                .accountPhone("19913145893")
                .calleeNumDisplay("0")
                .callDirection("0")
                .build()));
    }

    @PassToken
    @RequestMapping(value = "/axUnbindNumber")
    public ApiResult axUnbindNumber(String privateNumber){
        axService.axUnbindNumber("16512292687",null);
        return ok("");
    }
}
