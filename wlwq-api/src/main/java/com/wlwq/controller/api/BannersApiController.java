package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.Banners;
import com.wlwq.api.service.IBannersService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.system.service.ISysDeptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/banners")
@AllArgsConstructor
public class BannersApiController extends ApiController {

    private final IBannersService bannersService;

    private final ISysDeptService deptService;

    /**
     * banner列表
     * @param deptId 部门ID，默认总公司（100）
     * @param bannerLocation banner位置(1:首页 2:商城)
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "100") Long deptId,
                          @RequestParam(defaultValue = "1") String bannerLocation){
        SysDept dept = deptService.selectDeptById(deptId);
        if(dept != null){
            deptId = dept.getParentId();
            if(deptId == 100L || deptId == 0){
                deptId = dept.getDeptId();
            }
        }
        List<Banners> bannersList = bannersService.selectApiBannersList(Banners.builder()
                .showStatus(1)
                .tag(-1)
                .deptId(deptId)
                .bannerLocation(bannerLocation)
                .build());
        return ok(bannersList);
    }


    /**
     * banner详情
     * @param bannerId bannerID
     * @return
     */
    @PassToken
    @GetMapping(value = "/detail")
    public ApiResult detail(@RequestParam(defaultValue = "0") String bannerId){
        Banners banners = bannersService.selectBannersById(bannerId);
        if(banners == null){
            return ApiResult.result(ApiCode.NOT_FOUND);
        }
        return ok(banners.getContent());
    }


}
