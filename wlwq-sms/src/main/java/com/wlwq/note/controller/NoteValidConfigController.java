package com.wlwq.note.controller;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.note.domain.NoteValidConfig;
import com.wlwq.note.service.INoteValidConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人机验证配置Controller
 * 
 * @author Rick wlwq
 * @date 2021-07-08
 */
@Controller
@RequestMapping("/note/noteValidConfig")
public class NoteValidConfigController extends BaseController
{
    private String prefix = "note/noteValidConfig";

    @Autowired
    private INoteValidConfigService noteValidConfigService;

    @RequiresPermissions("note:noteValidConfig:view")
    @GetMapping()
    public String noteValidConfig(String noteConfigId,ModelMap modelMap)
    {
        modelMap.put("noteConfigId",noteConfigId);
        return prefix + "/noteValidConfig";
    }

    /**
     * 查询人机验证配置列表
     */
    @RequiresPermissions("note:noteValidConfig:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoteValidConfig noteValidConfig)
    {
        startPage();
        List<NoteValidConfig> list = noteValidConfigService.selectNoteValidConfigList(noteValidConfig);
        return getDataTable(list);
    }

    /**
     * 导出人机验证配置列表
     */
    @RequiresPermissions("note:noteValidConfig:export")
    @Log(title = "人机验证配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NoteValidConfig noteValidConfig)
    {
        List<NoteValidConfig> list = noteValidConfigService.selectNoteValidConfigList(noteValidConfig);
        ExcelUtil<NoteValidConfig> util = new ExcelUtil<NoteValidConfig>(NoteValidConfig.class);
        return util.exportExcel(list, "noteValidConfig");
    }

    /**
     * 新增人机验证配置
     */
    @GetMapping("/add")
    public String add(String noteConfigId,ModelMap modelMap)
    {
        modelMap.put("noteConfigId",noteConfigId);
        return prefix + "/add";
    }

    /**
     * 新增保存人机验证配置
     */
    @RequiresPermissions("note:noteValidConfig:add")
    @Log(title = "人机验证配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NoteValidConfig noteValidConfig)
    {
        noteValidConfig.setNoteValidConfigId(IdUtil.getSnowflake(0,0).nextIdStr());
        return toAjax(noteValidConfigService.insertNoteValidConfig(noteValidConfig));
    }

    /**
     * 修改人机验证配置
     */
    @GetMapping("/edit/{noteValidConfigId}")
    public String edit(@PathVariable("noteValidConfigId") String noteValidConfigId, ModelMap mmap)
    {
        NoteValidConfig noteValidConfig = noteValidConfigService.selectNoteValidConfigById(noteValidConfigId);
        mmap.put("noteValidConfig", noteValidConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存人机验证配置
     */
    @RequiresPermissions("note:noteValidConfig:edit")
    @Log(title = "人机验证配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NoteValidConfig noteValidConfig)
    {
        return toAjax(noteValidConfigService.updateNoteValidConfig(noteValidConfig));
    }

    /**
     * 删除人机验证配置
     */
    @RequiresPermissions("note:noteValidConfig:remove")
    @Log(title = "人机验证配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noteValidConfigService.deleteNoteValidConfigByIds(ids));
    }
}
