package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数配置接口Controller
 *
 * @author wwb
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/system/config")
public class SysConfigApiController extends ApiController {
    private final ISysConfigService configService;

    /**
     * 查询系统中服务上架开关
     * @return
     */
    @PassToken
    @GetMapping("/serveShelfSwitch")
    public ApiResult serveShelfSwitch() {
        String shelfSwitch = configService.selectConfigByKey("serveShelfSwitch");
        if(StringUtils.isEmpty(shelfSwitch)){
            return ok(false);
        }else{
            return ok("1".equals(shelfSwitch) ? true:false);
        }
    }
}
