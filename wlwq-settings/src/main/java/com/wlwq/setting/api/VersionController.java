package com.wlwq.setting.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.setting.domain.SettingAppVersion;
import com.wlwq.setting.service.ISettingAppVersionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName VersionController
 * @Description 版本控制
 * @Date 2022/4/13 14:28
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/set/agreement")
@AllArgsConstructor
public class VersionController extends ApiController {

    /**
    *  @Description 获取最新的版本信息
    *  @author Rick wlwq
    *  @Date   2022/4/13 14:29
     *  resourceType 资源类型 1101:Android   1102:IOS
    */
    @PassToken
    @RequestMapping(value = "/selectNewVersion",method = RequestMethod.GET)
    public ApiResult selectNewVersion(String resourceType){
        SettingAppVersion version = versionService.selectNewVersion(resourceType);
        return ok(version);
    }

    private final ISettingAppVersionService versionService;

}
