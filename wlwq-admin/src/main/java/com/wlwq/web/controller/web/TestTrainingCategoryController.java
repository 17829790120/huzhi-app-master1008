package com.wlwq.web.controller.web;

import java.util.List;

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
import com.wlwq.api.domain.TestTrainingCategory;
import com.wlwq.api.service.ITestTrainingCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 测试训练类别Controller
 *
 * @author wwb
 * @date 2023-05-26
 */
@Controller
@RequestMapping("/web/testTrainingCategory")
public class TestTrainingCategoryController extends BaseController {

    private String prefix = "web/testTrainingCategory";

    @Autowired
    private ITestTrainingCategoryService testTrainingCategoryService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:testTrainingCategory:view")
    @GetMapping()
    public String testTrainingCategory() {
        return prefix + "/testTrainingCategory";
    }

    /**
     * 查询测试训练类别列表
     */
    @RequiresPermissions("web:testTrainingCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestTrainingCategory testTrainingCategory) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                testTrainingCategory.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<TestTrainingCategory> list = testTrainingCategoryService.selectTestTrainingCategoryList(testTrainingCategory);
        return getDataTable(list);
    }

    /**
     * 导出测试训练类别列表
     */
    @RequiresPermissions("web:testTrainingCategory:export")
    @Log(title = "测试训练类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestTrainingCategory testTrainingCategory) {
        List<TestTrainingCategory> list = testTrainingCategoryService.selectTestTrainingCategoryList(testTrainingCategory);
        ExcelUtil<TestTrainingCategory> util = new ExcelUtil<TestTrainingCategory>(TestTrainingCategory.class);
        return util.exportExcel(list, "testTrainingCategory");
    }

    /**
     * 新增测试训练类别
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存测试训练类别
     */
    @RequiresPermissions("web:testTrainingCategory:add")
    @Log(title = "测试训练类别", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestTrainingCategory testTrainingCategory) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                testTrainingCategory.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(testTrainingCategoryService.insertTestTrainingCategory(testTrainingCategory));
    }

    /**
     * 修改测试训练类别
     */
    @GetMapping("/edit/{testTrainingCategoryId}")
    public String edit(@PathVariable("testTrainingCategoryId") String testTrainingCategoryId, ModelMap mmap) {
        TestTrainingCategory testTrainingCategory = testTrainingCategoryService.selectTestTrainingCategoryById(testTrainingCategoryId);
        mmap.put("testTrainingCategory", testTrainingCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存测试训练类别
     */
    @RequiresPermissions("web:testTrainingCategory:edit")
    @Log(title = "测试训练类别", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestTrainingCategory testTrainingCategory) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                testTrainingCategory.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(testTrainingCategoryService.updateTestTrainingCategory(testTrainingCategory));
    }

    /**
     * 删除测试训练类别
     */
    @RequiresPermissions("web:testTrainingCategory:remove")
    @Log(title = "测试训练类别", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(testTrainingCategoryService.deleteTestTrainingCategoryByIds(ids));
    }
}
