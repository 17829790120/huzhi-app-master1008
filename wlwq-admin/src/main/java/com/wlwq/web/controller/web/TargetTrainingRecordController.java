package com.wlwq.web.controller.web;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.api.domain.TargetTrainingRecord;
import com.wlwq.api.service.ITargetTrainingRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 目标训练记录Controller
 *
 * @author wwb
 * @date 2023-06-05
 */
@Controller
@RequestMapping("/web/targetTrainingRecord")
public class TargetTrainingRecordController extends BaseController {

    private String prefix = "web/targetTrainingRecord";

    @Autowired
    private ITargetTrainingRecordService targetTrainingRecordService;

    @RequiresPermissions("web:targetTrainingRecord:view")
    @GetMapping()
    public String targetTrainingRecord() {
        return prefix + "/targetTrainingRecord";
    }

    /**
     * 查询目标训练记录列表
     */
    @RequiresPermissions("web:targetTrainingRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TargetTrainingRecord targetTrainingRecord) {
        startPage();
        List<TargetTrainingRecord> list = targetTrainingRecordService.selectTargetTrainingRecordList(targetTrainingRecord);
        return getDataTable(list);
    }

    /**
     * 导出目标训练记录列表
     */
    @RequiresPermissions("web:targetTrainingRecord:export")
    @Log(title = "目标训练记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TargetTrainingRecord targetTrainingRecord) {
        List<TargetTrainingRecord> list = targetTrainingRecordService.selectTargetTrainingRecordList(targetTrainingRecord);
        ExcelUtil<TargetTrainingRecord> util = new ExcelUtil<TargetTrainingRecord>(TargetTrainingRecord.class);
        return util.exportExcel(list, "targetTrainingRecord");
    }

    /**
     * 新增目标训练记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存目标训练记录
     */
    @RequiresPermissions("web:targetTrainingRecord:add")
    @Log(title = "目标训练记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TargetTrainingRecord targetTrainingRecord) {
        return toAjax(targetTrainingRecordService.insertTargetTrainingRecord(targetTrainingRecord));
    }

    /**
     * 修改目标训练记录
     */
    @GetMapping("/edit/{targetTrainingRecordId}")
    public String edit(@PathVariable("targetTrainingRecordId") String targetTrainingRecordId, ModelMap mmap) {
        TargetTrainingRecord targetTrainingRecord = targetTrainingRecordService.selectTargetTrainingRecordById(targetTrainingRecordId);
        mmap.put("targetTrainingRecord", targetTrainingRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存目标训练记录
     */
    @RequiresPermissions("web:targetTrainingRecord:edit")
    @Log(title = "目标训练记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TargetTrainingRecord targetTrainingRecord) {
        return toAjax(targetTrainingRecordService.updateTargetTrainingRecord(targetTrainingRecord));
    }

    /**
     * 删除目标训练记录
     */
    @RequiresPermissions("web:targetTrainingRecord:remove")
    @Log(title = "目标训练记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(targetTrainingRecordService.deleteTargetTrainingRecordByIds(ids));
    }
}
