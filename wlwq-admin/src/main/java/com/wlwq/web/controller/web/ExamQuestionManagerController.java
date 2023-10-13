package com.wlwq.web.controller.web;

import java.util.List;
import cn.hutool.core.bean.BeanUtil;
import com.wlwq.api.domain.QuestionManager;
import com.wlwq.api.service.IQuestionManagerService;
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
import com.wlwq.api.domain.ExamQuestionManager;
import com.wlwq.api.service.IExamQuestionManagerService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 考试记录试题Controller
 *
 * @author wwb
 * @date 2023-05-26
 */
@Controller
@RequestMapping("/web/examQuestionManager")
public class ExamQuestionManagerController extends BaseController {

    private String prefix = "web/examQuestionManager";

    @Autowired
    private IExamQuestionManagerService examQuestionManagerService;

    @Autowired
    private IQuestionManagerService questionManagerService;

    @RequiresPermissions("web:examQuestionManager:view")
    @GetMapping()
    public String examQuestionManager(ModelMap modelMap,String examPaperRecordId) {
        modelMap.put("examPaperRecordId",examPaperRecordId);
        return prefix + "/examQuestionManager";
    }

    //@RequiresPermissions("web:examQuestionManager:view")
    @GetMapping("/allQuestionsManager")
    public String allQuestionsManager(ModelMap modelMap,String examPaperRecordId) {
        modelMap.put("examPaperRecordId",examPaperRecordId);
        return prefix + "/examQuestionManager";
    }
    //@RequiresPermissions("web:examQuestionManager:view")
    @GetMapping("/allAccount")
    public String allAccount(ModelMap modelMap,String examPaperRecordId) {
        modelMap.put("examPaperRecordId",examPaperRecordId);
        return prefix + "/accountDept";
    }
    /**
     * 查询考试记录试题列表
     */
    @RequiresPermissions("web:examQuestionManager:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExamQuestionManager examQuestionManager) {
        startPage();
        List<ExamQuestionManager> list = examQuestionManagerService.selectExamQuestionManagerList(examQuestionManager);
        return getDataTable(list);
    }

    /**
     * 导出考试记录试题列表
     */
    @RequiresPermissions("web:examQuestionManager:export")
    @Log(title = "考试记录试题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamQuestionManager examQuestionManager) {
        List<ExamQuestionManager> list = examQuestionManagerService.selectExamQuestionManagerList(examQuestionManager);
        ExcelUtil<ExamQuestionManager> util = new ExcelUtil<ExamQuestionManager>(ExamQuestionManager.class);
        return util.exportExcel(list, "examQuestionManager");
    }

    /**
     * 新增考试记录试题
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存考试记录试题
     */
    @RequiresPermissions("web:examQuestionManager:add")
    @Log(title = "考试记录试题", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamQuestionManager examQuestionManager) {
        return toAjax(examQuestionManagerService.insertExamQuestionManager(examQuestionManager));
    }

    /**
     * 新增保存考试试卷记录（从题库给试卷添加试题）
     */
    @RequiresPermissions("web:examPaperRecord:add")
    @Log(title = "考试试卷记录", businessType = BusinessType.INSERT)
    @PostMapping("/addNew")
    @ResponseBody
    public AjaxResult addNew(ExamQuestionManager examQuestionManager) {
        QuestionManager questionManager = questionManagerService.selectQuestionManagerById(examQuestionManager.getQuestionManagerId());
        if(questionManager == null ){
            return AjaxResult.error("没有此试题信息。");
        }
        BeanUtil.copyProperties(questionManager,examQuestionManager,"examPaperRecordId");
        return toAjax(examQuestionManagerService.insertExamQuestionManager(examQuestionManager));
    }

    /**
     *
     * 查询未添加到试卷的试题
     *
     * SELECT * FROM `question_manager` WHERE question_manager_id NOT IN (
     * SELECT question_manager_id FROM `exam_question_manager` WHERE exam_paper_record_id=1662015106088833024
     * )
     *
     */

    /**
     * 修改考试记录试题
     */
    @GetMapping("/edit/{examQuestionManagerId}")
    public String edit(@PathVariable("examQuestionManagerId") String examQuestionManagerId, ModelMap mmap) {
        ExamQuestionManager examQuestionManager = examQuestionManagerService.selectExamQuestionManagerById(examQuestionManagerId);
        mmap.put("examQuestionManager", examQuestionManager);
        return prefix + "/edit";
    }

    /**
     * 修改保存考试记录试题
     */
    @RequiresPermissions("web:examQuestionManager:edit")
    @Log(title = "考试记录试题", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamQuestionManager examQuestionManager) {
        return toAjax(examQuestionManagerService.updateExamQuestionManager(examQuestionManager));
    }

    /**
     * 删除考试记录试题
     */
    @RequiresPermissions("web:examQuestionManager:remove")
    @Log(title = "考试记录试题", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(examQuestionManagerService.deleteExamQuestionManagerByIds(ids));
    }
}
