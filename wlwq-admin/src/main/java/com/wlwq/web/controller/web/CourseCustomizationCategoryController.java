package com.wlwq.web.controller.web;

import java.util.ArrayList;
import java.util.List;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.core.text.Convert;
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
import com.wlwq.api.domain.CourseCustomizationCategory;
import com.wlwq.api.service.ICourseCustomizationCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;


/**
 * 课程定制类别Controller
 *
 * @author wwb
 * @date 2023-05-19
 */
@Controller
@RequestMapping("/web/courseCustomizationCategory")
public class CourseCustomizationCategoryController extends BaseController {

    private String prefix = "web/courseCustomizationCategory";

    @Autowired
    private ICourseCustomizationCategoryService courseCustomizationCategoryService;

    @RequiresPermissions("web:courseCustomizationCategory:view")
    @GetMapping()
    public String courseCustomizationCategory() {
        return prefix + "/courseCustomizationCategory";
    }

    /**
     * 查询课程定制类别列表
     */
    @RequiresPermissions("web:courseCustomizationCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public List<CourseCustomizationCategory> list(CourseCustomizationCategory courseCustomizationCategory) {
        List<CourseCustomizationCategory> list = courseCustomizationCategoryService.selectCourseCustomizationCategoryList(courseCustomizationCategory);
        return list;
    }

    /**
     * 导出课程定制类别列表
     */
    @RequiresPermissions("web:courseCustomizationCategory:export")
    @Log(title = "课程定制类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseCustomizationCategory courseCustomizationCategory) {
        List<CourseCustomizationCategory> list = courseCustomizationCategoryService.selectCourseCustomizationCategoryList(courseCustomizationCategory);
        ExcelUtil<CourseCustomizationCategory> util = new ExcelUtil<CourseCustomizationCategory>(CourseCustomizationCategory.class);
        return util.exportExcel(list, "courseCustomizationCategory");
    }

    /**
     * 新增课程定制类别
     */
    @GetMapping("/addOld")
    public String addOld() {
        return prefix + "/add";
    }

    /**
     * 新增课程定制类别
     */
    @GetMapping(value = {"/add/{courseCustomizationCategoryId}", "/add/"})
    public String add(@PathVariable(value = "courseCustomizationCategoryId", required = false) Long courseCustomizationCategoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(courseCustomizationCategoryId)) {
            mmap.put("courseCustomizationCategory", courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationCategoryId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存课程定制类别
     */
    @RequiresPermissions("web:courseCustomizationCategory:add")
    @Log(title = "课程定制类别", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseCustomizationCategory courseCustomizationCategory) {
        courseCustomizationCategory.setUserId(ShiroUtils.getUserId());
        courseCustomizationCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        if (courseCustomizationCategory.getParentId() == 0) {
            courseCustomizationCategory.setAncestors("0");
            courseCustomizationCategory.setLevel(1);
        } else {
            CourseCustomizationCategory category = courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationCategory.getParentId());
            courseCustomizationCategory.setAncestors(category.getAncestors() + "," + courseCustomizationCategory.getParentId());
            courseCustomizationCategory.setLevel(Convert.toStrArray(courseCustomizationCategory.getAncestors()).length);
        }
        return toAjax(courseCustomizationCategoryService.insertCourseCustomizationCategory(courseCustomizationCategory));
    }

    /**
     * 修改课程定制类别
     */
    @GetMapping("/edit/{courseCustomizationCategoryId}")
    public String edit(@PathVariable("courseCustomizationCategoryId") Long courseCustomizationCategoryId, ModelMap mmap) {
        CourseCustomizationCategory courseCustomizationCategory = courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationCategoryId);
        mmap.put("courseCustomizationCategory", courseCustomizationCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程定制类别
     */
    @RequiresPermissions("web:courseCustomizationCategory:edit")
    @Log(title = "课程定制类别", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseCustomizationCategory courseCustomizationCategory) {
        return toAjax(courseCustomizationCategoryService.updateCourseCustomizationCategory(courseCustomizationCategory));
    }

    /**
     * 删除课程定制类别
     */
    @RequiresPermissions("web:courseCustomizationCategory:remove")
    @Log(title = "课程定制类别", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{ids}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("ids") String ids) {
        return toAjax(courseCustomizationCategoryService.deleteCourseCustomizationCategoryByIds(ids));
    }

    /**
     * 选择资讯分类树
     */
    @GetMapping(value = {"/selectCategoryTree/{courseCustomizationCategoryId}", "/selectCategoryTree/"})
    public String selectCategoryTree(@PathVariable(value = "courseCustomizationCategoryId", required = false) Long courseCustomizationCategoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(courseCustomizationCategoryId)) {
            mmap.put("courseCustomizationCategory", courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationCategoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载资讯分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<CourseCustomizationCategory> list = courseCustomizationCategoryService.selectCourseCustomizationCategoryList(CourseCustomizationCategory.builder().delStatus(0).build());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (CourseCustomizationCategory category : list) {
            Ztree ztree = new Ztree();
            ztree.setId(category.getCourseCustomizationCategoryId());
            ztree.setpId(category.getParentId());
            ztree.setName(category.getCategoryTitle());
            ztree.setTitle(category.getCategoryTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
