package com.wlwq.web.controller.web;

import com.wlwq.api.domain.Course;
import com.wlwq.api.service.ICourseService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程Controller
 *
 * @author Rick Jen
 * @date 2021-01-13
 */
@Controller
@RequestMapping("/course/course")
public class CourseWebController extends BaseController {
    private String prefix = "web/course";

    @Autowired
    private ICourseService courseService;

    @RequiresPermissions("course:course:view")
    @GetMapping()
    public String course() {
        return prefix + "/course";
    }

    /**
     * 查询课程列表
     */
    @RequiresPermissions("course:course:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Course course) {
        startPage();
        List<Course> list = courseService.selectCourseList(course);
        return getDataTable(list);
    }

    /**
     * 导出课程列表
     */
    @RequiresPermissions("course:course:export")
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Course course) {
        List<Course> list = courseService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        return util.exportExcel(list, "course");
    }

    /**
     * 新增课程
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.put("applePriceList", null);
        return prefix + "/add";
    }

    /**
     * 新增保存课程
     */
    @RequiresPermissions("course:course:add")
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Course course) {
        return toAjax(courseService.insertCourse(course));
    }

    /**
     * 修改课程
     */
    @GetMapping("/edit/{courseId}")
    public String edit(@PathVariable("courseId") Long courseId, ModelMap mmap) {
        Course course = courseService.selectCourseById(courseId);
        course.setCategoryId(course.getTwoCategoryId());
        mmap.put("course", course);
        mmap.put("applePriceList", null);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程
     */
    @RequiresPermissions("course:course:edit")
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Course course) {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课程
     */
    @RequiresPermissions("course:course:remove")
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseService.deleteCourseByIds(ids));
    }


    /**
     * 修改课程的推荐和不推荐
     */
    @PostMapping("/recommendEdit")
    @ResponseBody
    public AjaxResult recommendEdit(Long courseId, Integer recommendStatus) {
        return toAjax(courseService.recommendEdit(Course.builder().courseId(courseId).recommendStatus(recommendStatus).build()));
    }
}
