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
import com.wlwq.api.domain.QuestionnaireAccountAnswerResult;
import com.wlwq.api.service.IQuestionnaireAccountAnswerResultService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 问卷记录答案Controller
 *
 * @author wlwq
 * @date 2023-05-11
 */
@Controller
@RequestMapping("/web/questionAccountAnswerResult")
public class QuestionnaireAccountAnswerResultController extends BaseController {

    private String prefix = "web/questionAccountAnswerResult";

    @Autowired
    private IQuestionnaireAccountAnswerResultService questionnaireAccountAnswerResultService;

    @RequiresPermissions("web:questionAccountAnswerResult:view")
    @GetMapping()
    public String questionAccountAnswerResult() {
        return prefix + "/questionAccountAnswerResult";
    }

    /**
     * 查询问卷记录答案列表
     */
    @RequiresPermissions("web:questionAccountAnswerResult:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        startPage();
        List<QuestionnaireAccountAnswerResult> list = questionnaireAccountAnswerResultService.selectQuestionnaireAccountAnswerResultList(questionnaireAccountAnswerResult);
        return getDataTable(list);
    }

    /**
     * 导出问卷记录答案列表
     */
    @RequiresPermissions("web:questionAccountAnswerResult:export")
    @Log(title = "问卷记录答案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        List<QuestionnaireAccountAnswerResult> list = questionnaireAccountAnswerResultService.selectQuestionnaireAccountAnswerResultList(questionnaireAccountAnswerResult);
        ExcelUtil<QuestionnaireAccountAnswerResult> util = new ExcelUtil<QuestionnaireAccountAnswerResult>(QuestionnaireAccountAnswerResult.class);
        return util.exportExcel(list, "questionAccountAnswerResult");
    }

    /**
     * 新增问卷记录答案
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存问卷记录答案
     */
    @RequiresPermissions("web:questionAccountAnswerResult:add")
    @Log(title = "问卷记录答案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        return toAjax(questionnaireAccountAnswerResultService.insertQuestionnaireAccountAnswerResult(questionnaireAccountAnswerResult));
    }

    /**
     * 修改问卷记录答案
     */
    @GetMapping("/edit/{questionnaireAccountAnswerResultId}")
    public String edit(@PathVariable("questionnaireAccountAnswerResultId") String questionnaireAccountAnswerResultId, ModelMap mmap) {
        QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult = questionnaireAccountAnswerResultService.selectQuestionnaireAccountAnswerResultById(questionnaireAccountAnswerResultId);
        mmap.put("questionnaireAccountAnswerResult", questionnaireAccountAnswerResult);
        return prefix + "/edit";
    }

    /**
     * 修改保存问卷记录答案
     */
    @RequiresPermissions("web:questionAccountAnswerResult:edit")
    @Log(title = "问卷记录答案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult) {
        return toAjax(questionnaireAccountAnswerResultService.updateQuestionnaireAccountAnswerResult(questionnaireAccountAnswerResult));
    }

    /**
     * 删除问卷记录答案
     */
    @RequiresPermissions("web:questionAccountAnswerResult:remove")
    @Log(title = "问卷记录答案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireAccountAnswerResultService.deleteQuestionnaireAccountAnswerResultByIds(ids));
    }
}
