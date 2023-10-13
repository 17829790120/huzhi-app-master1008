package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.service.IQuestionnaireService;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
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
import com.wlwq.api.domain.QuestionnaireRecord;
import com.wlwq.api.service.IQuestionnaireRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 内部调研问卷记录Controller
 *
 * @author wwb
 * @date 2023-05-09
 */
@Controller
@RequestMapping("/web/questionnaireRecord")
public class QuestionnaireRecordController extends BaseController {

    private String prefix = "web/questionnaireRecord";

    @Autowired
    private IQuestionnaireRecordService questionnaireRecordService;

    @Autowired
    private IQuestionnaireService questionnaireService;

    //@RequiresPermissions("web:questionnaireRecord:view")
    @GetMapping()
    public String questionnaireRecord(String questionnaireId, ModelMap modelMap)
    {
        if (questionnaireId ==null){
            throw new BusinessException("请选择问卷！");
        }
        modelMap.put("questionnaireId",questionnaireId);
        return prefix + "/questionnaireRecord";
    }

    /**
     * 查询内部调研问卷记录列表
     */
    @RequiresPermissions("web:questionnaireRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionnaireRecord questionnaireRecord) {
        startPage();
        List<QuestionnaireRecord> list = questionnaireRecordService.selectQuestionnaireRecordList(questionnaireRecord);
        return getDataTable(list);
    }

    /**
     * 导出内部调研问卷记录列表
     */
    @RequiresPermissions("web:questionnaireRecord:export")
    @Log(title = "内部调研问卷记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionnaireRecord questionnaireRecord) {
        List<QuestionnaireRecord> list = questionnaireRecordService.selectQuestionnaireRecordList(questionnaireRecord);
        ExcelUtil<QuestionnaireRecord> util = new ExcelUtil<QuestionnaireRecord>(QuestionnaireRecord.class);
        return util.exportExcel(list, "questionnaireRecord");
    }

    /**
     * 新增内部调研问卷记录
     */
    @GetMapping("/add")
    public String add(String questionnaireId,ModelMap mmap) {
        if (StringUtils.isNotNull(questionnaireId))
        {
            mmap.put("questionnaire", questionnaireService.selectQuestionnaireById(questionnaireId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存内部调研问卷记录
     */
    @RequiresPermissions("web:questionnaireRecord:add")
    @Log(title = "内部调研问卷记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionnaireRecord questionnaireRecord) {
        questionnaireRecord.setCreatorDeptId(ShiroUtils.getSysUser().getDeptId());
        questionnaireRecord.setCreatorId(ShiroUtils.getUserId());
        questionnaireRecord.setCreatorName(ShiroUtils.getSysUser().getUserName());
        return toAjax(questionnaireRecordService.insertQuestionnaireRecord(questionnaireRecord));
    }

    /**
     * 修改内部调研问卷记录
     */
    @GetMapping("/edit/{questionnaireRecordId}/{questionnaireId}")
    public String edit(@PathVariable("questionnaireRecordId") String questionnaireRecordId,
                       @PathVariable("questionnaireId") String questionnaireId, ModelMap mmap) {
        QuestionnaireRecord questionnaireRecord = questionnaireRecordService.selectQuestionnaireRecordById(questionnaireRecordId);
        mmap.put("questionnaireRecord", questionnaireRecord);
        mmap.put("questionnaire", questionnaireService.selectQuestionnaireById(questionnaireId));
        return prefix + "/edit";
    }

    /**
     * 修改保存内部调研问卷记录
     */
    @RequiresPermissions("web:questionnaireRecord:edit")
    @Log(title = "内部调研问卷记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionnaireRecord questionnaireRecord) {
        return toAjax(questionnaireRecordService.updateQuestionnaireRecord(questionnaireRecord));
    }

    /**
     * 删除内部调研问卷记录
     */
    @RequiresPermissions("web:questionnaireRecord:remove")
    @Log(title = "内部调研问卷记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireRecordService.deleteQuestionnaireRecordByIds(ids));
    }
}
