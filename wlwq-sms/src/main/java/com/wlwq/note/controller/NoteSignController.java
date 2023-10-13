package com.wlwq.note.controller;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.note.domain.NoteSign;
import com.wlwq.note.service.INoteSignService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信签名Controller
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Controller
@RequestMapping("/note/noteSign")
public class NoteSignController extends BaseController
{
    private String prefix = "note/noteSign";

    @Autowired
    private INoteSignService noteSignService;

    @RequiresPermissions("note:noteSign:view")
    @GetMapping()
    public String noteSign(String noteConfigId,ModelMap modelMap)
    {
        modelMap.put("noteConfigId",noteConfigId);
        return prefix + "/noteSign";
    }

    /**
     * 查询短信签名列表
     */
    @RequiresPermissions("note:noteSign:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoteSign noteSign)
    {
        startPage();
        List<NoteSign> list = noteSignService.selectNoteSignList(noteSign);
        return getDataTable(list);
    }

    /**
     * 导出短信签名列表
     */
    @RequiresPermissions("note:noteSign:export")
    @Log(title = "短信签名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NoteSign noteSign)
    {
        List<NoteSign> list = noteSignService.selectNoteSignList(noteSign);
        ExcelUtil<NoteSign> util = new ExcelUtil<NoteSign>(NoteSign.class);
        return util.exportExcel(list, "noteSign");
    }

    /**
     * 新增短信签名
     */
    @GetMapping("/add")
    public String add(String noteConfigId,ModelMap modelMap)
    {
        modelMap.put("noteConfigId",noteConfigId);
        return prefix + "/add";
    }

    /**
     * 新增保存短信签名
     */
    @RequiresPermissions("note:noteSign:add")
    @Log(title = "短信签名", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NoteSign noteSign)
    {
        noteSign.setNoteSignId(IdUtil.getSnowflake(0,0).nextIdStr());
        return toAjax(noteSignService.insertNoteSign(noteSign));
    }

    /**
     * 修改短信签名
     */
    @GetMapping("/edit/{noteSignId}")
    public String edit(@PathVariable("noteSignId") String noteSignId, ModelMap mmap)
    {
        NoteSign noteSign = noteSignService.selectNoteSignById(noteSignId);
        mmap.put("noteSign", noteSign);
        return prefix + "/edit";
    }

    /**
     * 修改保存短信签名
     */
    @RequiresPermissions("note:noteSign:edit")
    @Log(title = "短信签名", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NoteSign noteSign)
    {
        return toAjax(noteSignService.updateNoteSign(noteSign));
    }

    /**
     * 删除短信签名
     */
    @RequiresPermissions("note:noteSign:remove")
    @Log(title = "短信签名", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noteSignService.deleteNoteSignByIds(ids));
    }
}
