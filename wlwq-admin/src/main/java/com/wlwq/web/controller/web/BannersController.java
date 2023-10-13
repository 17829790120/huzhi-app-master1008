package com.wlwq.web.controller.web;

import com.wlwq.api.domain.Banners;
import com.wlwq.api.service.IBannersService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * banner列表Controller
 *
 * @author gaoce
 * @date 2022-11-24
 */
@Controller
@RequestMapping("/web/banners")
public class BannersController extends BaseController {
    private String prefix = "web/banners";

    @Autowired
    private IBannersService bannersService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:banners:view")
    @GetMapping()
    public String banners(ModelMap modelMap) {
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        modelMap.put("deptList",deptList);
        return prefix + "/banners";
    }

    /**
     * 查询banner列表列表
     */
    @RequiresPermissions("web:banners:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Banners banners) {
        startPage();
        List<Banners> list = bannersService.selectBannersList(banners);
        return getDataTable(list);
    }

    /**
     * 导出banner列表列表
     */
    @RequiresPermissions("web:banners:export")
    @Log(title = "banner列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Banners banners) {
        List<Banners> list = bannersService.selectBannersList(banners);
        ExcelUtil<Banners> util = new ExcelUtil<Banners>(Banners.class);
        return util.exportExcel(list, "banners");
    }

    /**
     * 新增banner列表
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        modelMap.put("deptList",deptList);
        return prefix + "/add";
    }

    /**
     * 新增保存banner列表
     */
    @RequiresPermissions("web:banners:add")
    @Log(title = "banner列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Banners banners) {
        //学习中的banner图只能平台添加
        if("3".equals(banners.getBannerLocation()) && banners.getDeptId() != 100){
            return AjaxResult.error("学习中的banner图只能平台添加。");
        }else if("2".equals(banners.getBannerLocation()) && banners.getDeptId() == 100){
            //管理中的banner图只能分公司自己添加
            return AjaxResult.error("管理中的banner图只能分公司自己添加。");
        }
        return toAjax(bannersService.insertBanners(banners));
    }

    /**
     * 修改banner列表
     */
    @GetMapping("/edit/{bannerId}")
    public String edit(@PathVariable("bannerId") String bannerId, ModelMap mmap) {
        Banners banners = bannersService.selectBannersById(bannerId);
        mmap.put("banners", banners);
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        mmap.put("deptList",deptList);
        return prefix + "/edit";
    }

    /**
     * 修改保存banner列表
     */
    @RequiresPermissions("web:banners:edit")
    @Log(title = "banner列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Banners banners) {
        //学习中的banner图只能平台添加
        if("3".equals(banners.getBannerLocation()) && banners.getDeptId() != 100){
            return AjaxResult.error("学习中的banner图只能平台添加。");
        }else if("2".equals(banners.getBannerLocation()) && banners.getDeptId() == 100){
            //管理中的banner图只能分公司自己添加
            return AjaxResult.error("管理中的banner图只能分公司自己添加。");
        }
        return toAjax(bannersService.updateBanners(banners));
    }

    /**
     * 删除banner列表
     */
    @RequiresPermissions("web:banners:remove")
    @Log(title = "banner列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bannersService.deleteBannersByIds(ids));
    }

    /**
     * 服务广告链接
     */
    @GetMapping("/addServeUrl")
    public String addServeUrl(ModelMap modelMap,String jumpType,Long deptId) {
        modelMap.put("jumpType",jumpType);
        modelMap.put("deptId",deptId);
        return prefix + "/addServeUrl";
    }


    /**
     * 商城广告链接
     */
    @GetMapping("/addGoodsUrl")
    public String addGoodsUrl(ModelMap modelMap,String jumpType) {
        modelMap.put("jumpType",jumpType);
        return prefix + "/addGoodsUrl";
    }
}
