package com.wlwq.note.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.note.domain.NoteConfig;
import com.wlwq.note.service.INoteConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信配置Controller
 *
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Controller
@RequestMapping("/note/noteConfig")
public class NoteConfigController extends BaseController
{
    private String prefix = "note/noteConfig";

    @Autowired
    private INoteConfigService noteConfigService;

    @RequiresPermissions("note:noteConfig:view")
    @GetMapping()
    public String noteConfig()
    {
        return prefix + "/noteConfig";
    }

    /**
     * 查询短信配置列表
     */
    @RequiresPermissions("note:noteConfig:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoteConfig noteConfig)
    {
        startPage();
        List<NoteConfig> list = noteConfigService.selectNoteConfigList(noteConfig);
        return getDataTable(list);
    }

    /**
     * 导出短信配置列表
     */
    @RequiresPermissions("note:noteConfig:export")
    @Log(title = "短信配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NoteConfig noteConfig)
    {
        List<NoteConfig> list = noteConfigService.selectNoteConfigList(noteConfig);
        ExcelUtil<NoteConfig> util = new ExcelUtil<NoteConfig>(NoteConfig.class);
        return util.exportExcel(list, "noteConfig");
    }

    /**
     * 新增短信配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存短信配置
     */
    @RequiresPermissions("note:noteConfig:add")
    @Log(title = "短信配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NoteConfig noteConfig)
    {
        return toAjax(noteConfigService.insertNoteConfig(noteConfig));
    }

    /**
     * 修改短信配置
     */
    @GetMapping("/edit/{noteConfigId}")
    public String edit(@PathVariable("noteConfigId") String noteConfigId, ModelMap mmap)
    {
        NoteConfig noteConfig = noteConfigService.selectNoteConfigById(noteConfigId);
        mmap.put("noteConfig", noteConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存短信配置
     */
    @RequiresPermissions("note:noteConfig:edit")
    @Log(title = "短信配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NoteConfig noteConfig)
    {
        return toAjax(noteConfigService.updateNoteConfig(noteConfig));
    }

    /**
     * 删除短信配置
     */
    @RequiresPermissions("note:noteConfig:remove")
    @Log(title = "短信配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noteConfigService.deleteNoteConfigByIds(ids));
    }
}
