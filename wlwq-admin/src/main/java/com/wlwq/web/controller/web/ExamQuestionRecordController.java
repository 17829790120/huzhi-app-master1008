package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.exception.BusinessException;
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
import com.wlwq.api.domain.ExamQuestionRecord;
import com.wlwq.api.service.IExamQuestionRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 考试答题记录Controller
 *
 * @author wwb
 * @date 2023-04-24
 */
@Controller
@RequestMapping("/web/examQuestionRecord")
public class ExamQuestionRecordController extends BaseController {

    private String prefix = "web/examQuestionRecord";

    @Autowired
    private IExamQuestionRecordService examQuestionRecordService;

/*
    @RequiresPermissions("web:examQuestionRecord:view")
    @GetMapping()
    public String examQuestionRecord() {
        return prefix + "/examQuestionRecord";
    }
*/

    //@RequiresPermissions("web:examQuestionRecord:view")
    @GetMapping()
    public String examQuestionRecord(String examRecordId, ModelMap modelMap)
    {
        if (examRecordId ==null){
            throw new BusinessException("考试记录为空！");
        }
        modelMap.put("examRecordId",examRecordId);
        return prefix + "/examQuestionRecord";
    }

    /**
     * 查询考试答题记录列表
     */
    @RequiresPermissions("web:examQuestionRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExamQuestionRecord examQuestionRecord) {
        startPage();
        List<ExamQuestionRecord> list = examQuestionRecordService.selectExamQuestionRecordList(examQuestionRecord);
        return getDataTable(list);
    }

    /**
     * 导出考试答题记录列表
     */
    @RequiresPermissions("web:examQuestionRecord:export")
    @Log(title = "考试答题记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamQuestionRecord examQuestionRecord) {
        List<ExamQuestionRecord> list = examQuestionRecordService.selectExamQuestionRecordList(examQuestionRecord);
        ExcelUtil<ExamQuestionRecord> util = new ExcelUtil<ExamQuestionRecord>(ExamQuestionRecord.class);
        return util.exportExcel(list, "examQuestionRecord");
    }

    /**
     * 新增考试答题记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存考试答题记录
     */
    @RequiresPermissions("web:examQuestionRecord:add")
    @Log(title = "考试答题记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamQuestionRecord examQuestionRecord) {
        return toAjax(examQuestionRecordService.insertExamQuestionRecord(examQuestionRecord));
    }

    /**
     * 修改考试答题记录
     */
    @GetMapping("/edit/{examQuestionRecordId}")
    public String edit(@PathVariable("examQuestionRecordId") String examQuestionRecordId, ModelMap mmap) {
        ExamQuestionRecord examQuestionRecord = examQuestionRecordService.selectExamQuestionRecordById(examQuestionRecordId);
        mmap.put("examQuestionRecord", examQuestionRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存考试答题记录
     */
    @RequiresPermissions("web:examQuestionRecord:edit")
    @Log(title = "考试答题记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamQuestionRecord examQuestionRecord) {
        ExamQuestionRecord examQuestionRecordNew = examQuestionRecordService.selectExamQuestionRecordById(examQuestionRecord.getExamQuestionRecordId());
        if(examQuestionRecordNew == null){
            return AjaxResult.error("系统没有此记录。");
        }
        Double score = examQuestionRecordNew.getScore();
        Double myScore = examQuestionRecord.getMyScore();
        if(score!=null && myScore!=null){
            if(myScore<0 || myScore>score){
                return AjaxResult.error("得分不能大于给定分数或小于0");
            }
        }
        return toAjax(examQuestionRecordService.updateExamQuestionRecord(examQuestionRecord));
    }

    /**
     * 删除考试答题记录
     */
    @RequiresPermissions("web:examQuestionRecord:remove")
    @Log(title = "考试答题记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(examQuestionRecordService.deleteExamQuestionRecordByIds(ids));
    }
}
