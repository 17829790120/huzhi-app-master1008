package com.wlwq.web.controller.web;

import com.wlwq.api.domain.HotUpdateWgt;
import com.wlwq.api.service.IHotUpdateWgtService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 热更新Controller
 *
 * @author lk
 * @date 2022-10-26
 */
@Controller
@RequestMapping("/web/hotUpdateWgt")
public class HotUpdateWgtWebController extends BaseController {

    private String prefix = "web/hotUpdateWgt";

    @Autowired
    private IHotUpdateWgtService hotUpdateWgtService;

    @RequiresPermissions("web:hotUpdateWgt:view")
    @GetMapping()
    public String hotUpdateWgt() {
        return prefix + "/hotUpdateWgt";
    }

    @PostMapping("/changeOpenStatus")
    @ResponseBody
    public AjaxResult changeOpenStatus(HotUpdateWgt hotUpdateWgt) {
        return toAjax(hotUpdateWgtService.updateHotUpdateWgt(hotUpdateWgt));
    }

    /**
     * 查询热更新列表
     */
    @RequiresPermissions("web:hotUpdateWgt:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HotUpdateWgt hotUpdateWgt) {
        startPage();
        List<HotUpdateWgt> list = hotUpdateWgtService.selectHotUpdateWgtList(hotUpdateWgt);
        return getDataTable(list);
    }

    /**
     * 导出热更新列表
     */
    @RequiresPermissions("web:hotUpdateWgt:export")
    @Log(title = "热更新", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HotUpdateWgt hotUpdateWgt) {
        List<HotUpdateWgt> list = hotUpdateWgtService.selectHotUpdateWgtList(hotUpdateWgt);
        ExcelUtil<HotUpdateWgt> util = new ExcelUtil<HotUpdateWgt>(HotUpdateWgt.class);
        return util.exportExcel(list, "hotUpdateWgt");
    }

    /**
     * 新增热更新
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存热更新
     */
    @RequiresPermissions("web:hotUpdateWgt:add")
    @Log(title = "热更新", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HotUpdateWgt hotUpdateWgt) {
        return toAjax(hotUpdateWgtService.insertHotUpdateWgt(hotUpdateWgt));
    }

    /**
     * 修改热更新
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        HotUpdateWgt hotUpdateWgt = hotUpdateWgtService.selectHotUpdateWgtById(id);
        mmap.put("hotUpdateWgt", hotUpdateWgt);
        return prefix + "/edit";
    }

    /**
     * 修改保存热更新
     */
    @RequiresPermissions("web:hotUpdateWgt:edit")
    @Log(title = "热更新", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HotUpdateWgt hotUpdateWgt) {
        return toAjax(hotUpdateWgtService.updateHotUpdateWgt(hotUpdateWgt));
    }

    /**
     * 删除热更新
     */
    @RequiresPermissions("web:hotUpdateWgt:remove")
    @Log(title = "热更新", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(hotUpdateWgtService.deleteHotUpdateWgtByIds(ids));
    }
}