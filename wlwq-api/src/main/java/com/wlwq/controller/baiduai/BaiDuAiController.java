package com.wlwq.controller.baiduai;

import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.constant.IdCardConstant;
import com.wlwq.params.BusinessLicenseParams;
import com.wlwq.params.DrivingLicenseParams;
import com.wlwq.params.IdCardParams;
import com.wlwq.params.VehicleLicenseParams;
import com.wlwq.service.AuthService;
import com.wlwq.service.TextRecognitionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BaiDuAiController
 * @Description 百度AI识别接口
 * @Date 2021/2/7 11:14
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/api/baidu")
@AllArgsConstructor
public class BaiDuAiController {

    /**
     * 百度AI识别服务
     */
    private final TextRecognitionService textService;

    private final AuthService authService;

    @RequestMapping(value = "/getAuth",method = RequestMethod.POST)
    @PassToken
    public String getAuth(){
        return authService.getAuth();
    }

    /**
    *  @Description 行驶证识别
    *  @author Rick wlwq
    *  @Date   `2021/7/19` 10:42
    */
    @RequestMapping(value = "/getVehicleLicenseInfo",method = RequestMethod.POST)
    @PassToken
    public ApiResult getVehicleLicenseInfo(String url,String side){
        return ApiResult.ok(textService.vehicleLicense(VehicleLicenseParams.builder()
                .imageUrl(url)
                .vehicleLicenseSide(side)
                .build()));
    }

    /**
    *  @Description 驾驶证识别
    *  @author Rick wlwq
    *  @Date   2021/7/19 10:34
    */
    @RequestMapping(value = "/getDrivingLicenseInfo",method = RequestMethod.POST)
    @PassToken
    public ApiResult getDrivingLicenseInfo(String url,String side){
        return ApiResult.ok(textService.drivingLicense(DrivingLicenseParams.builder()
                .imageUrl(url)
                .drivingLicenseSide(side)
                .build()));
    }

    /**
    *  @Description 营业执照识别
    *  @author Rick wlwq
    *  @Date   2021/7/19 10:27
    */
    @RequestMapping(value = "/getBusinessLicenseInfo",method = RequestMethod.POST)
    @PassToken
    public ApiResult getBusinessLicenseInfo(String url){
        return ApiResult.ok(textService.businessLicense(BusinessLicenseParams.builder()
                .imageUrl(url)
                .build()));
    }


    @RequestMapping(value = "/getIdCardInfo",method = RequestMethod.POST)
    @PassToken
    public ApiResult getIdCardInfo(String url,String side){
        return ApiResult.ok(textService.idCardInfo(IdCardParams.builder()
                .imageUrl(url)
                .idCardSide(side)
                .detectRisk(IdCardConstant.NEED_RESULT_PARAM)
                .detectPhoto(IdCardConstant.NEED_RESULT_PARAM)
                .build()));
    }


    @RequestMapping(value = "/getBankCardInfo",method = RequestMethod.POST)
    @PassToken
    public ApiResult getBankCardInfo(String imageBase64){
        return ApiResult.ok(textService.bankCardInfo(imageBase64));
    }

}
