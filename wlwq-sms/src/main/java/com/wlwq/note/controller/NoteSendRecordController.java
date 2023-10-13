package com.wlwq.note.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.note.domain.NoteSendRecord;
import com.wlwq.note.service.INoteSendRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信发送记录Controller
 * 
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Controller
@RequestMapping("/note/noteSendRecord")
public class NoteSendRecordController extends BaseController
{
    private String prefix = "note/noteSendRecord";

    @Autowired
    private INoteSendRecordService noteSendRecordService;

    @RequiresPermissions("note:noteSendRecord:view")
    @GetMapping()
    public String noteSendRecord()
    {
        return prefix + "/noteSendRecord";
    }

    /**
     * 查询短信发送记录列表
     */
    @RequiresPermissions("note:noteSendRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoteSendRecord noteSendRecord)
    {
        startPage();
        List<NoteSendRecord> list = noteSendRecordService.selectNoteSendRecordList(noteSendRecord);
        return getDataTable(list);
    }

    /**
     * 导出短信发送记录列表
     */
    @RequiresPermissions("note:noteSendRecord:export")
    @Log(title = "短信发送记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NoteSendRecord noteSendRecord)
    {
        List<NoteSendRecord> list = noteSendRecordService.selectNoteSendRecordList(noteSendRecord);
        ExcelUtil<NoteSendRecord> util = new ExcelUtil<NoteSendRecord>(NoteSendRecord.class);
        return util.exportExcel(list, "noteSendRecord");
    }

    /**
     * 新增短信发送记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存短信发送记录
     */
    @RequiresPermissions("note:noteSendRecord:add")
    @Log(title = "短信发送记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NoteSendRecord noteSendRecord)
    {
        return toAjax(noteSendRecordService.insertNoteSendRecord(noteSendRecord));
    }

    /**
     * 修改短信发送记录
     */
    @GetMapping("/edit/{noteSendRecordId}")
    public String edit(@PathVariable("noteSendRecordId") Long noteSendRecordId, ModelMap mmap)
    {
        NoteSendRecord noteSendRecord = noteSendRecordService.selectNoteSendRecordById(noteSendRecordId);
        mmap.put("noteSendRecord", noteSendRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存短信发送记录
     */
    @RequiresPermissions("note:noteSendRecord:edit")
    @Log(title = "短信发送记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NoteSendRecord noteSendRecord)
    {
        return toAjax(noteSendRecordService.updateNoteSendRecord(noteSendRecord));
    }

    /**
     * 删除短信发送记录
     */
    @RequiresPermissions("note:noteSendRecord:remove")
    @Log(title = "短信发送记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noteSendRecordService.deleteNoteSendRecordByIds(ids));
    }
}
