package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
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
@RequestMapping(value = "/api/index")
@AllArgsConstructor
public class IndexApiController extends ApiController {

    private final ISysDeptService deptService;

    /**
     * 首页城市（只显示一个）
     *
     * @param countyCoding 城市编码 029，默认西安的
     * @return
     */
    @PassToken
    @GetMapping(value = "/nearbyCity")
    public ApiResult nearbyCity(@RequestParam(defaultValue = "029") String countyCoding) {
        SysDept dept = new SysDept();
        dept.setCountyCoding(countyCoding);
        SysDept city = deptService.selectApiNearbyCity(dept);
        return ok(city);
    }

    /**
     * 城市列表
     *
     * @param keyword 搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/cityList")
    public ApiResult cityList(String keyword) {
        SysDept dept = new SysDept();
        dept.setDeptName(keyword);
        dept.setParentId(100L);
        List<SysDept> provinceList = deptService.selectApiDeptList(dept);
        provinceList.forEach((SysDept sysDept) -> {
            SysDept cityDept = new SysDept();
            cityDept.setParentId(sysDept.getDeptId());
            sysDept.setCityList(deptService.selectApiDeptList(cityDept));
        });
        return ok(provinceList);
    }

    /**
     * 部门列表
     *
     * @param deptId 部门id
     * @return
     */
    @PassToken
    @GetMapping(value = "/deptList")
    public ApiResult deptList(@RequestParam(defaultValue = "0") Long deptId) {
        SysDept deptNew = new SysDept();
        SysDept dept = deptService.selectDeptById(deptId);
        if (dept != null) {
            deptId = dept.getParentId();
            if (deptId == 100L || deptId == 0) {
                deptId = dept.getDeptId();
            }
            deptNew.setParentId(deptId);
        }else{
            deptNew.setParentId(-1L);
        }
        List<SysDept> provinceList = deptService.selectApiDeptList(deptNew);
        return ok(provinceList);
    }

}
