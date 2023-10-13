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
import com.wlwq.api.domain.QuestionnaireDistributeRecord;
import com.wlwq.api.service.IQuestionnaireDistributeRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 问卷发放记录Controller
 *
 * @author wwb
 * @date 2023-05-12
 */
@Controller
@RequestMapping("/web/questionnaireDistributeRecord")
public class QuestionnaireDistributeRecordController extends BaseController {

    private String prefix = "web/questionnaireDistributeRecord";

    @Autowired
    private IQuestionnaireDistributeRecordService questionnaireDistributeRecordService;

/*    @RequiresPermissions("web:questionnaireDistributeRecord:view")
    @GetMapping()
    public String questionnaireDistributeRecord() {
        return prefix + "/questionnaireDistributeRecord";
    }*/

    @RequiresPermissions("web:questionnaire:view")
    //@RequiresPermissions("web:questionnaireDistributeRecord:view")
    @GetMapping()
    public String questionnaireDistributeRecord(String questionnaireId, ModelMap modelMap) {
        if (questionnaireId == null) {
            throw new BusinessException("请选择问卷！");
        }
        modelMap.put("questionnaireId", questionnaireId);
        return prefix + "/questionnaireDistributeRecord";
    }

    /**
     * 查询问卷发放记录列表
     */
    @RequiresPermissions("web:questionnaireDistributeRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        startPage();
        List<QuestionnaireDistributeRecord> list = questionnaireDistributeRecordService.selectQuestionnaireDistributeRecordList(questionnaireDistributeRecord);
        return getDataTable(list);
    }

    /**
     * 导出问卷发放记录列表
     */
    @RequiresPermissions("web:questionnaireDistributeRecord:export")
    @Log(title = "问卷发放记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        List<QuestionnaireDistributeRecord> list = questionnaireDistributeRecordService.selectQuestionnaireDistributeRecordList(questionnaireDistributeRecord);
        ExcelUtil<QuestionnaireDistributeRecord> util = new ExcelUtil<QuestionnaireDistributeRecord>(QuestionnaireDistributeRecord.class);
        return util.exportExcel(list, "questionnaireDistributeRecord");
    }

    /**
     * 新增问卷发放记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存问卷发放记录
     */
    @RequiresPermissions("web:questionnaireDistributeRecord:add")
    @Log(title = "问卷发放记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        return toAjax(questionnaireDistributeRecordService.insertQuestionnaireDistributeRecord(questionnaireDistributeRecord));
    }

    /**
     * 修改问卷发放记录
     */
    @GetMapping("/edit/{questionnaireDistributeRecordId}")
    public String edit(@PathVariable("questionnaireDistributeRecordId") String questionnaireDistributeRecordId, ModelMap mmap) {
        QuestionnaireDistributeRecord questionnaireDistributeRecord = questionnaireDistributeRecordService.selectQuestionnaireDistributeRecordById(questionnaireDistributeRecordId);
        mmap.put("questionnaireDistributeRecord", questionnaireDistributeRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存问卷发放记录
     */
    @RequiresPermissions("web:questionnaireDistributeRecord:edit")
    @Log(title = "问卷发放记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionnaireDistributeRecord questionnaireDistributeRecord) {
        return toAjax(questionnaireDistributeRecordService.updateQuestionnaireDistributeRecord(questionnaireDistributeRecord));
    }

    /**
     * 删除问卷发放记录
     */
    @RequiresPermissions("web:questionnaireDistributeRecord:remove")
    @Log(title = "问卷发放记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireDistributeRecordService.deleteQuestionnaireDistributeRecordByIds(ids));
    }
}
