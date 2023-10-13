package com.wlwq.web.controller.web;

import com.wlwq.api.domain.FourRelationships;
import com.wlwq.api.service.IFourRelationshipsService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 四类关系Controller
 *
 * @author dxy
 */
@Controller
@RequestMapping("/web/fourRelationships")
public class FourRelationshipsController extends BaseController {

    private String prefix = "web/fourRelationships";

    @Autowired
    private IFourRelationshipsService fourRelationshipsService;

    @RequiresPermissions("web:fourRelationships:view")
    @GetMapping()
    public String fourRelationships() {
        return prefix + "/fourRelationships";
    }

    /**
     * 查询四类关系列表
     */
    @RequiresPermissions("web:fourRelationships:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FourRelationships fourRelationships) {
        startPage();
        if(ShiroUtils.getUserId()!=1){
            fourRelationships.setCompanyId(ShiroUtils.getSysUser().getDeptId());
        }
        List<FourRelationships> list = fourRelationshipsService.selectWebFourRelationshipsList(fourRelationships);
        return getDataTable(list);
    }

    /**
     * 导出四类关系列表
     */
    @RequiresPermissions("web:fourRelationships:export")
    @Log(title = "四类关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FourRelationships fourRelationships) {
        List<FourRelationships> list = fourRelationshipsService.selectFourRelationshipsList(fourRelationships);
        ExcelUtil<FourRelationships> util = new ExcelUtil<FourRelationships>(FourRelationships.class);
        return util.exportExcel(list, "fourRelationships");
    }

    /**
     * 新增四类关系
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存四类关系
     */
    @RequiresPermissions("web:fourRelationships:add")
    @Log(title = "四类关系", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FourRelationships fourRelationships) {
        return toAjax(fourRelationshipsService.insertFourRelationships(fourRelationships));
    }

    /**
     * 修改四类关系
     */
    @GetMapping("/edit/{fourRelationshipsId}")
    public String edit(@PathVariable("fourRelationshipsId") String fourRelationshipsId, ModelMap mmap) {
        FourRelationships fourRelationships = fourRelationshipsService.selectFourRelationshipsById(fourRelationshipsId);
        mmap.put("fourRelationships", fourRelationships);
        return prefix + "/edit";
    }

    /**
     * 修改保存四类关系
     */
    @RequiresPermissions("web:fourRelationships:edit")
    @Log(title = "四类关系", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FourRelationships fourRelationships) {
        return toAjax(fourRelationshipsService.updateFourRelationships(fourRelationships));
    }

    /**
     * 删除四类关系
     */
    @RequiresPermissions("web:fourRelationships:remove")
    @Log(title = "四类关系", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fourRelationshipsService.deleteFourRelationshipsByIds(ids));
    }
}
