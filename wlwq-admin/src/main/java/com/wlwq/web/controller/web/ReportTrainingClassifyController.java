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
import com.wlwq.api.domain.ReportTrainingClassify;
import com.wlwq.api.service.IReportTrainingClassifyService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 汇报训练分类Controller
 *
 * @author gaoce
 * @date 2023-06-01
 */
@Controller
@RequestMapping("/web/reportTrainingClassify")
public class ReportTrainingClassifyController extends BaseController {

    private String prefix = "web/reportTrainingClassify";

    @Autowired
    private IReportTrainingClassifyService reportTrainingClassifyService;

    @RequiresPermissions("web:reportTrainingClassify:view")
    @GetMapping()
    public String reportTrainingClassify() {
        return prefix + "/reportTrainingClassify";
    }

    /**
     * 查询汇报训练分类列表
     */
    @RequiresPermissions("web:reportTrainingClassify:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReportTrainingClassify reportTrainingClassify) {
        startPage();
        List<ReportTrainingClassify> list = reportTrainingClassifyService.selectReportTrainingClassifyList(reportTrainingClassify);
        return getDataTable(list);
    }

    /**
     * 导出汇报训练分类列表
     */
    @RequiresPermissions("web:reportTrainingClassify:export")
    @Log(title = "汇报训练分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReportTrainingClassify reportTrainingClassify) {
        List<ReportTrainingClassify> list = reportTrainingClassifyService.selectReportTrainingClassifyList(reportTrainingClassify);
        ExcelUtil<ReportTrainingClassify> util = new ExcelUtil<ReportTrainingClassify>(ReportTrainingClassify.class);
        return util.exportExcel(list, "reportTrainingClassify");
    }

    /**
     * 新增汇报训练分类
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存汇报训练分类
     */
    @RequiresPermissions("web:reportTrainingClassify:add")
    @Log(title = "汇报训练分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ReportTrainingClassify reportTrainingClassify) {
        return toAjax(reportTrainingClassifyService.insertReportTrainingClassify(reportTrainingClassify));
    }

    /**
     * 修改汇报训练分类
     */
    @GetMapping("/edit/{reportTrainingClassifyId}")
    public String edit(@PathVariable("reportTrainingClassifyId") Long reportTrainingClassifyId, ModelMap mmap) {
        ReportTrainingClassify reportTrainingClassify = reportTrainingClassifyService.selectReportTrainingClassifyById(reportTrainingClassifyId);
        mmap.put("reportTrainingClassify", reportTrainingClassify);
        return prefix + "/edit";
    }

    /**
     * 修改保存汇报训练分类
     */
    @RequiresPermissions("web:reportTrainingClassify:edit")
    @Log(title = "汇报训练分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ReportTrainingClassify reportTrainingClassify) {
        return toAjax(reportTrainingClassifyService.updateReportTrainingClassify(reportTrainingClassify));
    }

    /**
     * 删除汇报训练分类
     */
    @RequiresPermissions("web:reportTrainingClassify:remove")
    @Log(title = "汇报训练分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(reportTrainingClassifyService.deleteReportTrainingClassifyByIds(ids));
    }
}
