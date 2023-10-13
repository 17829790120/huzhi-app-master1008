package com.wlwq.web.controller.web;

import com.alibaba.fastjson.JSONObject;
import com.wlwq.api.domain.CourseChapter;
import com.wlwq.api.service.ICourseChapterService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 课程章节Controller
 *
 * @author Rick Jen
 * @date 2021-01-18
 */
@Controller
@RequestMapping("/web/chapter")
public class CourseChapterWebController extends BaseController
{
    private String prefix = "web/chapter";

    @Autowired
    private ICourseChapterService courseChapterService;

    @RequiresPermissions("web:chapter:view")
    @GetMapping()
    public String chapter(Long courseId, ModelMap modelMap)
    {
        if (courseId ==null){
            throw new BusinessException("课程标识为空！");
        }
        modelMap.put("courseId",courseId);
        return prefix + "/chapter";
    }

    /**
     * 查询课程章节树列表
     */
    @RequiresPermissions("web:chapter:list")
    @PostMapping("/list")
    @ResponseBody
    public List<CourseChapter> list(CourseChapter courseChapter)
    {
        List<CourseChapter> list = courseChapterService.selectCourseChapterList(courseChapter);
        return list;
    }

    /**
     * 导出课程章节列表
     */
    @RequiresPermissions("web:chapter:export")
    @Log(title = "课程章节", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseChapter courseChapter)
    {
        List<CourseChapter> list = courseChapterService.selectCourseChapterList(courseChapter);
        ExcelUtil<CourseChapter> util = new ExcelUtil<CourseChapter>(CourseChapter.class);
        return util.exportExcel(list, "chapter");
    }

    /**
     * 新增课程章节
     */
    @GetMapping(value = { "/add" })
    public String add(Long chapterId, Long courseId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(chapterId))
        {
            mmap.put("courseChapter", courseChapterService.selectCourseChapterById(chapterId));
        }
        if (StringUtils.isNull(courseId))
        {
            throw new BusinessException("课程标识为空！");
        }
        mmap.put("courseId", courseId);
        return prefix + "/add";
    }

    /**
     * 新增保存课程章节
     */
    @RequiresPermissions("web:chapter:add")
    @Log(title = "课程章节", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseChapter courseChapter) throws Exception
    {
        if(StringUtils.isNotBlank(courseChapter.getVideoUrl())){
            Map<String, Object> map = fileSize(courseChapter.getVideoUrl());
            courseChapter.setFileSize(Double.valueOf(map.get("size").toString()));
            if("video/mp4".equals(map.get("type").toString())){
                JSONObject jsonObject = getFileInfor(courseChapter.getVideoUrl()+"?avinfo");
                courseChapter.setFileDuration(jsonObject == null ? 0 : Double.valueOf(jsonObject.get("duration").toString()));
            }
        }
        int count = courseChapterService.insertCourseChapter(courseChapter);
        if(count <= 0){
            return AjaxResult.error("添加失败。");
        }
        return AjaxResult.success("添加成功。");
    }

    /**
     * 修改课程章节
     */
    @GetMapping("/edit/{chapterId}")
    public String edit(@PathVariable("chapterId") Long chapterId, ModelMap mmap)
    {
        CourseChapter courseChapter = courseChapterService.selectCourseChapterById(chapterId);
        mmap.put("courseChapter", courseChapter);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程章节
     */
    @RequiresPermissions("web:chapter:edit")
    @Log(title = "课程章节", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseChapter courseChapter) throws Exception
    {
        if(StringUtils.isNotBlank(courseChapter.getVideoUrl())){
            Map<String, Object> map = fileSize(courseChapter.getVideoUrl());
            courseChapter.setFileSize(Double.valueOf(map.get("size").toString()));
            if("video/mp4".equals(map.get("type").toString())){
                JSONObject jsonObject = getFileInfor(courseChapter.getVideoUrl()+"?avinfo");
                courseChapter.setFileDuration(jsonObject == null ? 0 : Double.valueOf(jsonObject.get("duration").toString()));
            }else{
                courseChapter.setFileDuration(0.0);
            }
        }
        int count = courseChapterService.updateCourseChapter(courseChapter);
        if(count <= 0){
            return AjaxResult.error("修改失败。");
        }
        return AjaxResult.success("修改成功。");
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:chapter:remove")
    @Log(title = "课程章节", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{chapterId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("chapterId") Long chapterId)
    {
        return toAjax(courseChapterService.deleteCourseChapterById(chapterId));
    }

    /**
     * 选择课程章节树
     */
    @GetMapping(value = { "/selectChapterTree" })
    public String selectChapterTree( Long chapterId,Long courseId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(chapterId))
        {
            mmap.put("courseChapter", courseChapterService.selectCourseChapterById(chapterId));
        }
        if (StringUtils.isNull(courseId))
        {
            throw new BusinessException("课程标识为空！");
        }
        mmap.put("courseId", courseId);
        return prefix + "/tree";
    }

    /**
     * 加载课程章节树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData(CourseChapter courseChapter)
    {
        List<Ztree> ztrees = courseChapterService.selectCourseChapterTree(courseChapter);
        return ztrees;
    }
}
