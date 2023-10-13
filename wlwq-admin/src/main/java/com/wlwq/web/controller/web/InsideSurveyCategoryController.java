package com.wlwq.web.controller.web;

import java.util.List;

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
import com.wlwq.api.domain.InsideSurveyCategory;
import com.wlwq.api.service.IInsideSurveyCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 内部调研类别Controller
 *
 * @author wlwq
 * @date 2023-05-08
 */
@Controller
@RequestMapping("/web/insideSurveyCategory")
public class InsideSurveyCategoryController extends BaseController {

    private String prefix = "web/insideSurveyCategory";

    @Autowired
    private IInsideSurveyCategoryService insideSurveyCategoryService;

    @RequiresPermissions("web:insideSurveyCategory:view")
    @GetMapping()
    public String insideSurveyCategory() {
        return prefix + "/insideSurveyCategory";
    }

    /**
     * 查询内部调研类别列表
     */
    @RequiresPermissions("web:insideSurveyCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InsideSurveyCategory insideSurveyCategory) {
        insideSurveyCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        startPage();
        List<InsideSurveyCategory> list = insideSurveyCategoryService.selectInsideSurveyCategoryList(insideSurveyCategory);
        return getDataTable(list);
    }

    /**
     * 导出内部调研类别列表
     */
    @RequiresPermissions("web:insideSurveyCategory:export")
    @Log(title = "内部调研类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InsideSurveyCategory insideSurveyCategory) {
        List<InsideSurveyCategory> list = insideSurveyCategoryService.selectInsideSurveyCategoryList(insideSurveyCategory);
        ExcelUtil<InsideSurveyCategory> util = new ExcelUtil<InsideSurveyCategory>(InsideSurveyCategory.class);
        return util.exportExcel(list, "insideSurveyCategory");
    }

    /**
     * 新增内部调研类别
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存内部调研类别
     */
    @RequiresPermissions("web:insideSurveyCategory:add")
    @Log(title = "内部调研类别", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InsideSurveyCategory insideSurveyCategory) {
        insideSurveyCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        insideSurveyCategory.setUserId(ShiroUtils.getUserId());
        return toAjax(insideSurveyCategoryService.insertInsideSurveyCategory(insideSurveyCategory));
    }

    /**
     * 修改内部调研类别
     */
    @GetMapping("/edit/{insideSurveyCategoryId}")
    public String edit(@PathVariable("insideSurveyCategoryId") String insideSurveyCategoryId, ModelMap mmap) {
        InsideSurveyCategory insideSurveyCategory = insideSurveyCategoryService.selectInsideSurveyCategoryById(insideSurveyCategoryId);
        mmap.put("insideSurveyCategory", insideSurveyCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存内部调研类别
     */
    @RequiresPermissions("web:insideSurveyCategory:edit")
    @Log(title = "内部调研类别", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InsideSurveyCategory insideSurveyCategory) {
        return toAjax(insideSurveyCategoryService.updateInsideSurveyCategory(insideSurveyCategory));
    }

    /**
     * 删除内部调研类别
     */
    @RequiresPermissions("web:insideSurveyCategory:remove")
    @Log(title = "内部调研类别", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(insideSurveyCategoryService.deleteInsideSurveyCategoryByIds(ids));
    }
}
