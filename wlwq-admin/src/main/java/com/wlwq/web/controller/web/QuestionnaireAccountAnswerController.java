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
import com.wlwq.api.domain.QuestionnaireAccountAnswer;
import com.wlwq.api.service.IQuestionnaireAccountAnswerService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 问卷记录答题Controller
 *
 * @author wwb
 * @date 2023-05-11
 */
@Controller
@RequestMapping("/web/questionnaireAccountAnswer")
public class QuestionnaireAccountAnswerController extends BaseController {

    private String prefix = "web/questionnaireAccountAnswer";

    @Autowired
    private IQuestionnaireAccountAnswerService questionnaireAccountAnswerService;

    @RequiresPermissions("web:questionnaireAccountAnswer:view")
    @GetMapping()
    public String questionnaireAccountAnswer() {
        return prefix + "/questionnaireAccountAnswer";
    }

    /**
     * 查询问卷记录答题列表
     */
    @RequiresPermissions("web:questionnaireAccountAnswer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        startPage();
        List<QuestionnaireAccountAnswer> list = questionnaireAccountAnswerService.selectQuestionnaireAccountAnswerList(questionnaireAccountAnswer);
        return getDataTable(list);
    }

    /**
     * 导出问卷记录答题列表
     */
    @RequiresPermissions("web:questionnaireAccountAnswer:export")
    @Log(title = "问卷记录答题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        List<QuestionnaireAccountAnswer> list = questionnaireAccountAnswerService.selectQuestionnaireAccountAnswerList(questionnaireAccountAnswer);
        ExcelUtil<QuestionnaireAccountAnswer> util = new ExcelUtil<QuestionnaireAccountAnswer>(QuestionnaireAccountAnswer.class);
        return util.exportExcel(list, "questionnaireAccountAnswer");
    }

    /**
     * 新增问卷记录答题
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存问卷记录答题
     */
    @RequiresPermissions("web:questionnaireAccountAnswer:add")
    @Log(title = "问卷记录答题", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        return toAjax(questionnaireAccountAnswerService.insertQuestionnaireAccountAnswer(questionnaireAccountAnswer));
    }

    /**
     * 修改问卷记录答题
     */
    @GetMapping("/edit/{questionnaireAccountAnswerId}")
    public String edit(@PathVariable("questionnaireAccountAnswerId") String questionnaireAccountAnswerId, ModelMap mmap) {
        QuestionnaireAccountAnswer questionnaireAccountAnswer = questionnaireAccountAnswerService.selectQuestionnaireAccountAnswerById(questionnaireAccountAnswerId);
        mmap.put("questionnaireAccountAnswer", questionnaireAccountAnswer);
        return prefix + "/edit";
    }

    /**
     * 修改保存问卷记录答题
     */
    @RequiresPermissions("web:questionnaireAccountAnswer:edit")
    @Log(title = "问卷记录答题", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionnaireAccountAnswer questionnaireAccountAnswer) {
        return toAjax(questionnaireAccountAnswerService.updateQuestionnaireAccountAnswer(questionnaireAccountAnswer));
    }

    /**
     * 删除问卷记录答题
     */
    @RequiresPermissions("web:questionnaireAccountAnswer:remove")
    @Log(title = "问卷记录答题", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireAccountAnswerService.deleteQuestionnaireAccountAnswerByIds(ids));
    }
}
