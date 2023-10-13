package com.wlwq.web.controller.web;

import com.wlwq.api.domain.QuestionManager;
import com.wlwq.api.domain.TestTrainingCategory;
import com.wlwq.api.service.IQuestionManagerService;
import com.wlwq.api.service.ITestTrainingCategoryService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试题列表Controller
 *
 * @author wwb
 * @date 2023-04-23
 */
@Controller
@RequestMapping("/web/questionManagerTestTraining")
public class QuestionManagerTestTrainingController extends BaseController {

    private String prefix = "web/questionManagerTestTraining";

    @Autowired
    private IQuestionManagerService questionManagerService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ITestTrainingCategoryService testTrainingCategoryService;

    @RequiresPermissions("web:questionManagerTestTraining:view")
    @GetMapping()
    public String questionManager(ModelMap modelMap) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                deptId = dept.getDeptId();
            }
        }
        List<TestTrainingCategory> testTrainingCategoryList = testTrainingCategoryService.selectTestTrainingCategoryList(TestTrainingCategory
                .builder().companyId(deptId).build());
        modelMap.put("testTrainingCategoryList",testTrainingCategoryList);
        return prefix + "/questionManagerTestTraining";
    }

    /**
     * 全部的题库列表
     *
     * @param modelMap
     * @return
     */
    @RequiresPermissions("web:questionManagerTestTraining:view")
    @GetMapping("/allQuestions")
    public String allQuestions(ModelMap modelMap,String examPaperRecordId,String examPaperTitle) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                deptId = dept.getDeptId();
            }
        }
        List<TestTrainingCategory> testTrainingCategoryList = testTrainingCategoryService.selectTestTrainingCategoryList(TestTrainingCategory
                .builder().companyId(deptId).build());
        modelMap.put("testTrainingCategoryList",testTrainingCategoryList);
        modelMap.put("examPaperRecordId",examPaperRecordId);
        modelMap.put("examPaperTitle",examPaperTitle);
        return prefix + "/questionManagerTestTrainingNew";
    }

    /**
     * 查询试题列表列表
     */
    @RequiresPermissions("web:questionManagerTestTraining:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionManager questionManager) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                questionManager.setCompanyId(dept.getDeptId());
            }
        }
        questionManager.setQuestionType(1);
        startPage();
        List<QuestionManager> list = questionManagerService.selectQuestionManagerList(questionManager);
        return getDataTable(list);
    }

    /**
     * 查询未添加到考卷的题库试题
     */
    @RequiresPermissions("web:questionManagerTestTraining:list")
    @PostMapping("/listNew")
    @ResponseBody
    public TableDataInfo listNew(QuestionManager questionManager) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                questionManager.setCompanyId(dept.getDeptId());
            }
        }
        questionManager.setQuestionType(1);
        startPage();
        List<QuestionManager> list = questionManagerService.selectNoQuestionManagerList(questionManager);
        return getDataTable(list);
    }


    /**
     * 导出试题列表列表
     */
    @RequiresPermissions("web:questionManagerTestTraining:export")
    @Log(title = "试题列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionManager questionManager) {
        List<QuestionManager> list = questionManagerService.selectQuestionManagerList(questionManager);
        ExcelUtil<QuestionManager> util = new ExcelUtil<QuestionManager>(QuestionManager.class);
        return util.exportExcel(list, "questionManagerTestTraining");
    }

    /**
     * 新增试题列表
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                deptId = dept.getDeptId();
            }
        }
        List<TestTrainingCategory> testTrainingCategoryList = testTrainingCategoryService.selectTestTrainingCategoryList(TestTrainingCategory
                .builder().companyId(deptId).build());
        mmap.put("testTrainingCategoryList",testTrainingCategoryList);
        return prefix + "/add";
    }

    /**
     * 新增保存试题列表
     */
    @RequiresPermissions("web:questionManagerTestTraining:add")
    @Log(title = "试题列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionManager questionManager) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                questionManager.setCompanyId(dept.getDeptId());
            }
        }
        questionManager.setQuestionType(1);
        return toAjax(questionManagerService.insertQuestionManager(questionManager));
    }

    /**
     * 修改试题列表
     */
    @GetMapping("/edit/{questionManagerId}")
    public String edit(@PathVariable("questionManagerId") String questionManagerId, ModelMap mmap) {
        QuestionManager questionManager = questionManagerService.selectQuestionManagerById(questionManagerId);
        mmap.put("questionManager", questionManager);
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                deptId = dept.getDeptId();
            }
        }
        List<TestTrainingCategory> testTrainingCategoryList = testTrainingCategoryService.selectTestTrainingCategoryList(TestTrainingCategory
                .builder().companyId(deptId).build());
        mmap.put("testTrainingCategoryList",testTrainingCategoryList);
        return prefix + "/edit";
    }

    /**
     * 修改保存试题列表
     */
    @RequiresPermissions("web:questionManagerTestTraining:edit")
    @Log(title = "试题列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionManager questionManager) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                questionManager.setCompanyId(dept.getDeptId());
            }
        }
        questionManager.setQuestionType(1);
        return toAjax(questionManagerService.updateQuestionManager(questionManager));
    }

    /**
     * 删除试题列表
     */
    @RequiresPermissions("web:questionManagerTestTraining:remove")
    @Log(title = "试题列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionManagerService.deleteQuestionManagerByIds(ids));
    }


    /**
     * 查询试题列表列表
     * chapterId 章节ID
     * courseId  课程ID
     */
    @GetMapping("/questionManagerList")
    public String questionManagerList(ModelMap modelMap,Long chapterId,Long courseId) {
        modelMap.put("chapterId",chapterId);
        modelMap.put("courseId",courseId);
        return prefix + "/questionManagerTestTraining";
    }
}
