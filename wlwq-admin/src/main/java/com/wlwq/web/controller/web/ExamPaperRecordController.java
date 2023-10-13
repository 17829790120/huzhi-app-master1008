package com.wlwq.web.controller.web;

import java.util.List;
import com.wlwq.api.domain.TestTrainingCategory;
import com.wlwq.api.service.ITestTrainingCategoryService;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.system.service.ISysDeptService;
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
import com.wlwq.api.domain.ExamPaperRecord;
import com.wlwq.api.service.IExamPaperRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 考试试卷记录Controller
 *
 * @author wwb
 * @date 2023-05-26
 */
@Controller
@RequestMapping("/web/examPaperRecord")
public class ExamPaperRecordController extends BaseController {

    private String prefix = "web/examPaperRecord";

    @Autowired
    private IExamPaperRecordService examPaperRecordService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ITestTrainingCategoryService testTrainingCategoryService;

    @RequiresPermissions("web:examPaperRecord:view")
    @GetMapping()
    public String examPaperRecord(ModelMap modelMap) {
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
        return prefix + "/examPaperRecord";
    }

    /**
     * 查询考试试卷记录列表
     */
    @RequiresPermissions("web:examPaperRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExamPaperRecord examPaperRecord) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                examPaperRecord.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<ExamPaperRecord> list = examPaperRecordService.selectExamPaperRecordList(examPaperRecord);
        return getDataTable(list);
    }

    /**
     * 导出考试试卷记录列表
     */
    @RequiresPermissions("web:examPaperRecord:export")
    @Log(title = "考试试卷记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamPaperRecord examPaperRecord) {
        List<ExamPaperRecord> list = examPaperRecordService.selectExamPaperRecordList(examPaperRecord);
        ExcelUtil<ExamPaperRecord> util = new ExcelUtil<ExamPaperRecord>(ExamPaperRecord.class);
        return util.exportExcel(list, "examPaperRecord");
    }

    /**
     * 新增考试试卷记录
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
     * 新增保存考试试卷记录
     */
    @RequiresPermissions("web:examPaperRecord:add")
    @Log(title = "考试试卷记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamPaperRecord examPaperRecord) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                examPaperRecord.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(examPaperRecordService.insertExamPaperRecord(examPaperRecord));
    }

    /**
     * 修改考试试卷记录
     */
    @GetMapping("/edit/{examPaperRecordId}")
    public String edit(@PathVariable("examPaperRecordId") String examPaperRecordId, ModelMap mmap) {
        ExamPaperRecord examPaperRecord = examPaperRecordService.selectExamPaperRecordById(examPaperRecordId);
        mmap.put("examPaperRecord", examPaperRecord);
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
     * 修改保存考试试卷记录
     */
    @RequiresPermissions("web:examPaperRecord:edit")
    @Log(title = "考试试卷记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamPaperRecord examPaperRecord) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                examPaperRecord.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(examPaperRecordService.updateExamPaperRecord(examPaperRecord));
    }

    /**
     * 删除考试试卷记录
     */
    @RequiresPermissions("web:examPaperRecord:remove")
    @Log(title = "考试试卷记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(examPaperRecordService.deleteExamPaperRecordByIds(ids));
    }
}
