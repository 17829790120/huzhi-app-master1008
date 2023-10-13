package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.ShiroUtils;
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
import com.wlwq.api.domain.QuestionnaireRecordAnswer;
import com.wlwq.api.service.IQuestionnaireRecordAnswerService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 问卷记录答案Controller
 *
 * @author wwb
 * @date 2023-05-09
 */
@Controller
@RequestMapping("/web/questionnaireRecordAnswer")
public class QuestionnaireRecordAnswerController extends BaseController {

    private String prefix = "web/questionnaireRecordAnswer";

    @Autowired
    private IQuestionnaireRecordAnswerService questionnaireRecordAnswerService;

    //@RequiresPermissions("web:questionnaireRecordAnswer:view")
    @GetMapping()
    public String questionnaireRecordAnswer(String questionnaireRecordId, ModelMap modelMap) {
        if (questionnaireRecordId == null) {
            throw new BusinessException("题目标识为空！");
        }
        modelMap.put("questionnaireRecordId", questionnaireRecordId);
        return prefix + "/questionnaireRecordAnswer";
    }

    /**
     * 查询问卷记录答案列表
     */
    @RequiresPermissions("web:questionnaireRecordAnswer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        startPage();
        List<QuestionnaireRecordAnswer> list = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerList(questionnaireRecordAnswer);
        return getDataTable(list);
    }

    /**
     * 查询问卷记录答案列表
     */
    @RequiresPermissions("web:questionnaireRecordAnswer:list")
    @PostMapping("/listNew")
    @ResponseBody
    public TableDataInfo listNew(String questionnaireRecordId) {
        startPage();
        List<QuestionnaireRecordAnswer> list = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerList(QuestionnaireRecordAnswer
                .builder()
                .questionnaireRecordId(questionnaireRecordId)
                .build());
        return getDataTable(list);
    }

    /**
     * 导出问卷记录答案列表
     */
    @RequiresPermissions("web:questionnaireRecordAnswer:export")
    @Log(title = "问卷记录答案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        List<QuestionnaireRecordAnswer> list = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerList(questionnaireRecordAnswer);
        ExcelUtil<QuestionnaireRecordAnswer> util = new ExcelUtil<QuestionnaireRecordAnswer>(QuestionnaireRecordAnswer.class);
        return util.exportExcel(list, "questionnaireRecordAnswer");
    }

    /**
     * 新增问卷记录答案
     */
    @GetMapping("/add")
    public String add(String questionnaireRecordId, ModelMap mmap) {
        mmap.put("questionnaireRecordId", questionnaireRecordId);
        return prefix + "/add";
    }

    /**
     * 新增保存问卷记录答案
     */
    @RequiresPermissions("web:questionnaireRecordAnswer:add")
    @Log(title = "问卷记录答案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        questionnaireRecordAnswer.setCreatorDeptId(ShiroUtils.getSysUser().getDeptId());
        questionnaireRecordAnswer.setCreatorId(ShiroUtils.getUserId());
        questionnaireRecordAnswer.setCreatorName(ShiroUtils.getSysUser().getUserName());
        return toAjax(questionnaireRecordAnswerService.insertQuestionnaireRecordAnswer(questionnaireRecordAnswer));
    }

    /**
     * 修改问卷记录答案
     */
    @GetMapping("/edit/{questionnaireRecordAnswerId}")
    public String edit(@PathVariable("questionnaireRecordAnswerId") String questionnaireRecordAnswerId, ModelMap mmap) {
        QuestionnaireRecordAnswer questionnaireRecordAnswer = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerById(questionnaireRecordAnswerId);
        mmap.put("questionnaireRecordAnswer", questionnaireRecordAnswer);
        return prefix + "/edit";
    }

    /**
     * 修改保存问卷记录答案
     */
    @RequiresPermissions("web:questionnaireRecordAnswer:edit")
    @Log(title = "问卷记录答案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionnaireRecordAnswer questionnaireRecordAnswer) {
        return toAjax(questionnaireRecordAnswerService.updateQuestionnaireRecordAnswer(questionnaireRecordAnswer));
    }

    /**
     * 删除问卷记录答案
     */
    @RequiresPermissions("web:questionnaireRecordAnswer:remove")
    @Log(title = "问卷记录答案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireRecordAnswerService.deleteQuestionnaireRecordAnswerByIds(ids));
    }
}
