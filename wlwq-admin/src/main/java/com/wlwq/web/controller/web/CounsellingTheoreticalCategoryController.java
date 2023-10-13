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
import com.wlwq.api.domain.CounsellingTheoreticalCategory;
import com.wlwq.api.service.ICounsellingTheoreticalCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 理论体系类目Controller
 *
 * @author wwb
 * @date 2023-05-18
 */
@Controller
@RequestMapping("/web/counsellingTheoreticalCategory")
public class CounsellingTheoreticalCategoryController extends BaseController {

    private String prefix = "web/counsellingTheoreticalCategory";

    @Autowired
    private ICounsellingTheoreticalCategoryService counsellingTheoreticalCategoryService;

    @RequiresPermissions("web:counsellingTheoreticalCategory:view")
    @GetMapping()
    public String counsellingTheoreticalCategory() {
        return prefix + "/counsellingTheoreticalCategory";
    }

    /**
     * 查询理论体系类目列表
     */
    @RequiresPermissions("web:counsellingTheoreticalCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        startPage();
        List<CounsellingTheoreticalCategory> list = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryList(counsellingTheoreticalCategory);
        return getDataTable(list);
    }

    /**
     * 导出理论体系类目列表
     */
    @RequiresPermissions("web:counsellingTheoreticalCategory:export")
    @Log(title = "理论体系类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        List<CounsellingTheoreticalCategory> list = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryList(counsellingTheoreticalCategory);
        ExcelUtil<CounsellingTheoreticalCategory> util = new ExcelUtil<CounsellingTheoreticalCategory>(CounsellingTheoreticalCategory.class);
        return util.exportExcel(list, "counsellingTheoreticalCategory");
    }

    /**
     * 新增理论体系类目
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存理论体系类目
     */
    @RequiresPermissions("web:counsellingTheoreticalCategory:add")
    @Log(title = "理论体系类目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        return toAjax(counsellingTheoreticalCategoryService.insertCounsellingTheoreticalCategory(counsellingTheoreticalCategory));
    }

    /**
     * 修改理论体系类目
     */
    @GetMapping("/edit/{counsellingTheoreticalCategoryId}")
    public String edit(@PathVariable("counsellingTheoreticalCategoryId") String counsellingTheoreticalCategoryId, ModelMap mmap) {
        CounsellingTheoreticalCategory counsellingTheoreticalCategory = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryById(counsellingTheoreticalCategoryId);
        mmap.put("counsellingTheoreticalCategory", counsellingTheoreticalCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存理论体系类目
     */
    @RequiresPermissions("web:counsellingTheoreticalCategory:edit")
    @Log(title = "理论体系类目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        return toAjax(counsellingTheoreticalCategoryService.updateCounsellingTheoreticalCategory(counsellingTheoreticalCategory));
    }

    /**
     * 删除理论体系类目
     */
    @RequiresPermissions("web:counsellingTheoreticalCategory:remove")
    @Log(title = "理论体系类目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(counsellingTheoreticalCategoryService.deleteCounsellingTheoreticalCategoryByIds(ids));
    }
}
