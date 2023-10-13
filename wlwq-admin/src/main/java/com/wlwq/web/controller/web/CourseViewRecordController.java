package com.wlwq.web.controller.web;

import java.util.List;

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
import com.wlwq.api.domain.CourseViewRecord;
import com.wlwq.api.service.ICourseViewRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 章节观看记录Controller
 *
 * @author wwb
 * @date 2023-04-24
 */
@Controller
@RequestMapping("/web/courseViewRecord")
public class CourseViewRecordController extends BaseController {

    private String prefix = "web/courseViewRecord";

    @Autowired
    private ICourseViewRecordService courseViewRecordService;

    @RequiresPermissions("web:courseViewRecord:view")
    @GetMapping()
    public String courseViewRecord() {
        return prefix + "/courseViewRecord";
    }

    /**
     * 查询章节观看记录列表
     */
    @RequiresPermissions("web:courseViewRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CourseViewRecord courseViewRecord) {
        startPage();
        List<CourseViewRecord> list = courseViewRecordService.selectCourseViewRecordList(courseViewRecord);
        return getDataTable(list);
    }

    /**
     * 导出章节观看记录列表
     */
    @RequiresPermissions("web:courseViewRecord:export")
    @Log(title = "章节观看记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CourseViewRecord courseViewRecord) {
        List<CourseViewRecord> list = courseViewRecordService.selectCourseViewRecordList(courseViewRecord);
        ExcelUtil<CourseViewRecord> util = new ExcelUtil<CourseViewRecord>(CourseViewRecord.class);
        return util.exportExcel(list, "courseViewRecord");
    }

    /**
     * 新增章节观看记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存章节观看记录
     */
    @RequiresPermissions("web:courseViewRecord:add")
    @Log(title = "章节观看记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseViewRecord courseViewRecord) {
        return toAjax(courseViewRecordService.insertCourseViewRecord(courseViewRecord));
    }

    /**
     * 修改章节观看记录
     */
    @GetMapping("/edit/{courseViewRecordId}")
    public String edit(@PathVariable("courseViewRecordId") String courseViewRecordId, ModelMap mmap) {
        CourseViewRecord courseViewRecord = courseViewRecordService.selectCourseViewRecordById(courseViewRecordId);
        mmap.put("courseViewRecord", courseViewRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存章节观看记录
     */
    @RequiresPermissions("web:courseViewRecord:edit")
    @Log(title = "章节观看记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseViewRecord courseViewRecord) {
        return toAjax(courseViewRecordService.updateCourseViewRecord(courseViewRecord));
    }

    /**
     * 删除章节观看记录
     */
    @RequiresPermissions("web:courseViewRecord:remove")
    @Log(title = "章节观看记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(courseViewRecordService.deleteCourseViewRecordByIds(ids));
    }
}
