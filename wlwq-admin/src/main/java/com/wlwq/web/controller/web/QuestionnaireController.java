package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.InsideSurveyCategory;
import com.wlwq.api.service.IInsideSurveyCategoryService;
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
import com.wlwq.api.domain.Questionnaire;
import com.wlwq.api.service.IQuestionnaireService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 内部调研问卷Controller
 *
 * @author web
 * @date 2023-05-09
 */
@Controller
@RequestMapping("/web/questionnaire")
public class QuestionnaireController extends BaseController {

    private String prefix = "web/questionnaire";

    @Autowired
    private IQuestionnaireService questionnaireService;

    @Autowired
    private IInsideSurveyCategoryService insideSurveyCategoryService;

    @RequiresPermissions("web:questionnaire:view")
    @GetMapping()
    public String questionnaire(ModelMap mmap) {
        List<InsideSurveyCategory> list = insideSurveyCategoryService.selectInsideSurveyCategoryList(InsideSurveyCategory
                .builder()
                .deptId(ShiroUtils.getSysUser().getDeptId())
                .build());
        mmap.put("list", list);
        return prefix + "/questionnaire";
    }

    /**
     * 查询内部调研问卷列表
     */
    @RequiresPermissions("web:questionnaire:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Questionnaire questionnaire) {
        questionnaire.setDeptId(ShiroUtils.getSysUser().getDeptId());
        startPage();
        List<Questionnaire> list = questionnaireService.selectQuestionnaireList(questionnaire);
        return getDataTable(list);
    }

    /**
     * 导出内部调研问卷列表
     */
    @RequiresPermissions("web:questionnaire:export")
    @Log(title = "内部调研问卷", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Questionnaire questionnaire) {
        List<Questionnaire> list = questionnaireService.selectQuestionnaireList(questionnaire);
        ExcelUtil<Questionnaire> util = new ExcelUtil<Questionnaire>(Questionnaire.class);
        return util.exportExcel(list, "questionnaire");
    }

    /**
     * 新增内部调研问卷
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<InsideSurveyCategory> list = insideSurveyCategoryService.selectInsideSurveyCategoryList(InsideSurveyCategory
                .builder()
                .deptId(ShiroUtils.getSysUser().getDeptId())
                .build());
        mmap.put("list", list);
        return prefix + "/add";
    }

    /**
     * 新增保存内部调研问卷
     */
    @RequiresPermissions("web:questionnaire:add")
    @Log(title = "内部调研问卷", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Questionnaire questionnaire) {
        questionnaire.setDeptId(ShiroUtils.getSysUser().getDeptId());
        questionnaire.setCreatorDeptId(ShiroUtils.getSysUser().getDeptId());
        questionnaire.setCreatorId(ShiroUtils.getUserId());
        questionnaire.setCreatorName(ShiroUtils.getSysUser().getUserName());
        return toAjax(questionnaireService.insertQuestionnaire(questionnaire));
    }

    /**
     * 修改内部调研问卷
     */
    @GetMapping("/edit/{questionnaireId}")
    public String edit(@PathVariable("questionnaireId") String questionnaireId, ModelMap mmap) {
        Questionnaire questionnaire = questionnaireService.selectQuestionnaireById(questionnaireId);
        mmap.put("questionnaire", questionnaire);
        List<InsideSurveyCategory> list = insideSurveyCategoryService.selectInsideSurveyCategoryList(InsideSurveyCategory
                .builder()
                .deptId(ShiroUtils.getSysUser().getDeptId())
                .build());
        mmap.put("list", list);
        return prefix + "/edit";
    }

    /**
     * 修改保存内部调研问卷
     */
    @RequiresPermissions("web:questionnaire:edit")
    @Log(title = "内部调研问卷", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Questionnaire questionnaire) {
        return toAjax(questionnaireService.updateQuestionnaire(questionnaire));
    }

    /**
     * 删除内部调研问卷
     */
    @RequiresPermissions("web:questionnaire:remove")
    @Log(title = "内部调研问卷", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireService.deleteQuestionnaireByIds(ids));
    }
}
