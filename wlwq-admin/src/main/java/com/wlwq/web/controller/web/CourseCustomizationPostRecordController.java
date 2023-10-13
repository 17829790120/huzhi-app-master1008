package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.exception.BusinessException;
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
import com.wlwq.api.domain.CourseCustomizationPostRecord;
import com.wlwq.api.service.ICourseCustomizationPostRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 预约记录Controller
 *
 * @author wwb
 * @date 2023-05-22
 */
@Controller
@RequestMapping("/web/courseCustomizationPostRecord")
public class CourseCustomizationPostRecordController extends BaseController {

    private String prefix = "web/courseCustomizationPostRecord";

    @Autowired
    private ICourseCustomizationPostRecordService courseCustomizationPostRecordService;

/*    @RequiresPermissions("web:courseCustomizationPostRecord:view")
    @GetMapping()
    public String courseCustomizationPostRecord() {
        return prefix + "/courseCustomizationPostRecord";
    }*/

    @RequiresPermissions("web:courseCustomizationPostRecord:view")
    @GetMapping()
    public String courseCustomizationPostRecord(String courseCustomizationPostId, ModelMap modelMap) {
        if (courseCustomizationPostId == null) {
            throw new BusinessException("预约的课程为空！");
        }
        modelMap.put("courseCustomizationPostId", courseCustomizationPostId);
        return prefix + "/courseCustomizationPostRecord";
    }

    /**
     * 查询预约记录列表
     */
    @RequiresPermissions("web:courseCustomizationPostRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        startPage();
        List<CourseCustomizationPostRecord> list = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordList(courseCustomizationPostRecord);
        return getDataTable(list);
    }

    /**
     * 导出预约记录列表
     */
    @RequiresPermissions("web:courseCustomizationPostRecord:export")
    @Log(title = "预约记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        List<CourseCustomizationPostRecord> list = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordList(courseCustomizationPostRecord);
        ExcelUtil<CourseCustomizationPostRecord> util = new ExcelUtil<CourseCustomizationPostRecord>(CourseCustomizationPostRecord.class);
        return util.exportExcel(list, "courseCustomizationPostRecord");
    }

    /**
     * 新增预约记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预约记录
     */
    @RequiresPermissions("web:courseCustomizationPostRecord:add")
    @Log(title = "预约记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        return toAjax(courseCustomizationPostRecordService.insertCourseCustomizationPostRecord(courseCustomizationPostRecord));
    }

    /**
     * 修改预约记录
     */
    @GetMapping("/edit/{courseCustomizationPostRecordId}")
    public String edit(@PathVariable("courseCustomizationPostRecordId") String courseCustomizationPostRecordId, ModelMap mmap) {
        CourseCustomizationPostRecord courseCustomizationPostRecord = courseCustomizationPostRecordService.selectCourseCustomizationPostRecordById(courseCustomizationPostRecordId);
        mmap.put("courseCustomizationPostRecord", courseCustomizationPostRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存预约记录
     */
    @RequiresPermissions("web:courseCustomizationPostRecord:edit")
    @Log(title = "预约记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        return toAjax(courseCustomizationPostRecordService.updateCourseCustomizationPostRecord(courseCustomizationPostRecord));
    }

    /**
     * 删除预约记录
     */
    @RequiresPermissions("web:courseCustomizationPostRecord:remove")
    @Log(title = "预约记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseCustomizationPostRecordService.deleteCourseCustomizationPostRecordByIds(ids));
    }
}
