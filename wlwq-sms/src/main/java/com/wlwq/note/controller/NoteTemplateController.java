package com.wlwq.note.controller;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.note.domain.NoteTemplate;
import com.wlwq.note.service.INoteTemplateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信模板Controller
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Controller
@RequestMapping("/note/noteTemplate")
public class NoteTemplateController extends BaseController
{
    private String prefix = "note/noteTemplate";

    @Autowired
    private INoteTemplateService noteTemplateService;

    @RequiresPermissions("note:noteTemplate:view")
    @GetMapping()
    public String noteTemplate(String noteConfigId,ModelMap modelMap)
    {
        modelMap.put("noteConfigId",noteConfigId);
        return prefix + "/noteTemplate";
    }

    /**
     * 查询短信模板列表
     */
    @RequiresPermissions("note:noteTemplate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoteTemplate noteTemplate)
    {
        startPage();
        List<NoteTemplate> list = noteTemplateService.selectNoteTemplateList(noteTemplate);
        return getDataTable(list);
    }

    /**
     * 导出短信模板列表
     */
    @RequiresPermissions("note:noteTemplate:export")
    @Log(title = "短信模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NoteTemplate noteTemplate)
    {
        List<NoteTemplate> list = noteTemplateService.selectNoteTemplateList(noteTemplate);
        ExcelUtil<NoteTemplate> util = new ExcelUtil<NoteTemplate>(NoteTemplate.class);
        return util.exportExcel(list, "noteTemplate");
    }

    /**
     * 新增短信模板
     */
    @GetMapping("/add")
    public String add(String noteConfigId,ModelMap modelMap)
    {
        modelMap.put("noteConfigId",noteConfigId);
        return prefix + "/add";
    }

    /**
     * 新增保存短信模板
     */
    @RequiresPermissions("note:noteTemplate:add")
    @Log(title = "短信模板", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NoteTemplate noteTemplate)
    {
        noteTemplate.setNoteTemplateId(IdUtil.getSnowflake(0,0).nextIdStr());
        return toAjax(noteTemplateService.insertNoteTemplate(noteTemplate));
    }

    /**
     * 修改短信模板
     */
    @GetMapping("/edit/{noteTemplateId}")
    public String edit(@PathVariable("noteTemplateId") String noteTemplateId, ModelMap mmap)
    {
        NoteTemplate noteTemplate = noteTemplateService.selectNoteTemplateById(noteTemplateId);
        mmap.put("noteTemplate", noteTemplate);
        return prefix + "/edit";
    }

    /**
     * 修改保存短信模板
     */
    @RequiresPermissions("note:noteTemplate:edit")
    @Log(title = "短信模板", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NoteTemplate noteTemplate)
    {
        return toAjax(noteTemplateService.updateNoteTemplate(noteTemplate));
    }

    /**
     * 删除短信模板
     */
    @RequiresPermissions("note:noteTemplate:remove")
    @Log(title = "短信模板", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noteTemplateService.deleteNoteTemplateByIds(ids));
    }
}
