package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.HotUpdateWgt;
import com.wlwq.api.service.IHotUpdateWgtService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热更新Controller
 *
 * @author lk
 * @date 2022-10-26
 */
@RestController
@RequestMapping("/api/hotUpdateWgt")
public class HotUpdateWgtController extends ApiController {

    @Autowired
    private IHotUpdateWgtService hotUpdateWgtService;

    /**
     * 查询可以热更新的版本内容
     *
     * @param versionCode 版本号
     * @param type        1101：安卓   1102：ios
     * @return 热更新
     */
    @PassToken
    @RequestMapping(value = "/getHotUpdateUrl", method = RequestMethod.GET)
    public ApiResult getHotUpdateUrl(Long versionCode, String type) {
        HotUpdateWgt wgt = hotUpdateWgtService.getHotUpdateUrl(versionCode, type);
        if (wgt != null) {
            return ApiResult.ok(wgt);
        } else {
            return ApiResult.fail("现在就是最新版本");
        }
    }
}