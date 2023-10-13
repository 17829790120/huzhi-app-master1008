package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.CourseCustomizationCategory;
import com.wlwq.api.service.ICourseCustomizationCategoryService;
import com.wlwq.common.core.text.Convert;
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
import com.wlwq.api.domain.CourseCustomizationPost;
import com.wlwq.api.service.ICourseCustomizationPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 课程定制资讯Controller
 *
 * @author wwb
 * @date 2023-05-19
 */
@Controller
@RequestMapping("/web/courseCustomizationPost")
public class CourseCustomizationPostController extends BaseController {

    private String prefix = "web/courseCustomizationPost";

    @Autowired
    private ICourseCustomizationPostService courseCustomizationPostService;

    @Autowired
    private ICourseCustomizationCategoryService courseCustomizationCategoryService;

    @RequiresPermissions("web:courseCustomizationPost:view")
    @GetMapping()
    public String courseCustomizationPost() {
        return prefix + "/courseCustomizationPost";
    }

    /**
     * 查询课程定制资讯列表
     */
    @RequiresPermissions("web:courseCustomizationPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CourseCustomizationPost courseCustomizationPost) {
        startPage();
        List<CourseCustomizationPost> list = courseCustomizationPostService.selectCourseCustomizationPostList(courseCustomizationPost);
        list.forEach(
                obj -> {
                    CourseCustomizationCategory category = courseCustomizationCategoryService.selectCourseCustomizationCategoryById(obj.getInformationCategoryId());
                    if(category != null){
                        obj.setCategoryTitle(category.getCategoryTitle());
                    }
                }
        );
        return getDataTable(list);
    }

    /**
     * 导出课程定制资讯列表
     */
    @RequiresPermissions("web:courseCustomizationPost:export")
    @Log(title = "课程定制资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseCustomizationPost courseCustomizationPost) {
        List<CourseCustomizationPost> list = courseCustomizationPostService.selectCourseCustomizationPostList(courseCustomizationPost);
        ExcelUtil<CourseCustomizationPost> util = new ExcelUtil<CourseCustomizationPost>(CourseCustomizationPost.class);
        return util.exportExcel(list, "courseCustomizationPost");
    }

    /**
     * 新增课程定制资讯
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存课程定制资讯
     */
    @RequiresPermissions("web:courseCustomizationPost:add")
    @Log(title = "课程定制资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseCustomizationPost courseCustomizationPost) {
        courseCustomizationPost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        courseCustomizationPost.setUserId(ShiroUtils.getUserId());
        CourseCustomizationCategory category = courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationPost.getInformationCategoryId());
        if (category.getLevel() != 3) {
            return AjaxResult.error("不能给上级目录添加咨询。");
        } else {
            courseCustomizationPost.setAncestors(category.getAncestors());
        }
        return toAjax(courseCustomizationPostService.insertCourseCustomizationPost(courseCustomizationPost));
    }

    /**
     * 修改课程定制资讯
     */
    @GetMapping("/edit/{courseCustomizationPostId}")
    public String edit(@PathVariable("courseCustomizationPostId") String courseCustomizationPostId, ModelMap mmap) {
        CourseCustomizationPost courseCustomizationPost = courseCustomizationPostService.selectCourseCustomizationPostById(courseCustomizationPostId);
        CourseCustomizationCategory category = courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationPost.getInformationCategoryId());
        if(category != null){
            courseCustomizationPost.setCategoryTitle(category.getCategoryTitle());
        }
        mmap.put("courseCustomizationPost", courseCustomizationPost);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程定制资讯
     */
    @RequiresPermissions("web:courseCustomizationPost:edit")
    @Log(title = "课程定制资讯", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseCustomizationPost courseCustomizationPost) {
        courseCustomizationPost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        courseCustomizationPost.setUserId(ShiroUtils.getUserId());
        CourseCustomizationCategory category = courseCustomizationCategoryService.selectCourseCustomizationCategoryById(courseCustomizationPost.getInformationCategoryId());
        if(category != null){
            if (category.getLevel() != 3) {
                return AjaxResult.error("不能给上级目录添加咨询。");
            } else {
                courseCustomizationPost.setAncestors(category.getAncestors());
            }
        }
        return toAjax(courseCustomizationPostService.updateCourseCustomizationPost(courseCustomizationPost));
    }

    /**
     * 删除课程定制资讯
     */
    @RequiresPermissions("web:courseCustomizationPost:remove")
    @Log(title = "课程定制资讯", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseCustomizationPostService.deleteCourseCustomizationPostByIds(ids));
    }
}
