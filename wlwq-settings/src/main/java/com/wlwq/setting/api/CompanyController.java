package com.wlwq.setting.api;

import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.setting.service.ISettingCompanyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CompanyController
 * @Description 公司简介
 * @Date 2021/6/7 17:35
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/set/company")
@AllArgsConstructor
public class CompanyController extends ApiController {

    /**
    *  @Description 获取版本号
    *  @author Rick wlwq
    *  @Date   2021/6/7 17:45
    */
    @RequestMapping(value = "/getAppVersion",method = RequestMethod.GET)
    public String getAppVersion(){
        return companyService.selectVersion();
    }

    /**
     * @Description 获取公司详情
     * @author Rick wlwq
     * @Date 2021/6/7 17:42
     */
    @RequestMapping(value = "/companyDetail", method = RequestMethod.GET)
    public ApiResult companyDetail() {
        return ok(companyService.selectCompanyDetail());
    }

    private final ISettingCompanyService companyService;

}
