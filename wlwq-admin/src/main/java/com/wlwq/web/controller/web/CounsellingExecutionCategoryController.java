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
import com.wlwq.api.domain.CounsellingExecutionCategory;
import com.wlwq.api.service.ICounsellingExecutionCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 辅导实施类目Controller
 *
 * @author wwb
 * @date 2023-05-18
 */
@Controller
@RequestMapping("/web/counsellingExecutionCategory")
public class CounsellingExecutionCategoryController extends BaseController {

    private String prefix = "web/counsellingExecutionCategory";

    @Autowired
    private ICounsellingExecutionCategoryService counsellingExecutionCategoryService;

    @RequiresPermissions("web:counsellingExecutionCategory:view")
    @GetMapping()
    public String counsellingExecutionCategory() {
        return prefix + "/counsellingExecutionCategory";
    }

    /**
     * 查询辅导实施类目列表
     */
    @RequiresPermissions("web:counsellingExecutionCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CounsellingExecutionCategory counsellingExecutionCategory) {
        startPage();
        List<CounsellingExecutionCategory> list = counsellingExecutionCategoryService.selectCounsellingExecutionCategoryList(counsellingExecutionCategory);
        return getDataTable(list);
    }

    /**
     * 导出辅导实施类目列表
     */
    @RequiresPermissions("web:counsellingExecutionCategory:export")
    @Log(title = "辅导实施类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CounsellingExecutionCategory counsellingExecutionCategory) {
        List<CounsellingExecutionCategory> list = counsellingExecutionCategoryService.selectCounsellingExecutionCategoryList(counsellingExecutionCategory);
        ExcelUtil<CounsellingExecutionCategory> util = new ExcelUtil<CounsellingExecutionCategory>(CounsellingExecutionCategory.class);
        return util.exportExcel(list, "counsellingExecutionCategory");
    }

    /**
     * 新增辅导实施类目
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存辅导实施类目
     */
    @RequiresPermissions("web:counsellingExecutionCategory:add")
    @Log(title = "辅导实施类目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CounsellingExecutionCategory counsellingExecutionCategory) {
        return toAjax(counsellingExecutionCategoryService.insertCounsellingExecutionCategory(counsellingExecutionCategory));
    }

    /**
     * 修改辅导实施类目
     */
    @GetMapping("/edit/{counsellingExecutionCategoryId}")
    public String edit(@PathVariable("counsellingExecutionCategoryId") String counsellingExecutionCategoryId, ModelMap mmap) {
        CounsellingExecutionCategory counsellingExecutionCategory = counsellingExecutionCategoryService.selectCounsellingExecutionCategoryById(counsellingExecutionCategoryId);
        mmap.put("counsellingExecutionCategory", counsellingExecutionCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存辅导实施类目
     */
    @RequiresPermissions("web:counsellingExecutionCategory:edit")
    @Log(title = "辅导实施类目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CounsellingExecutionCategory counsellingExecutionCategory) {
        return toAjax(counsellingExecutionCategoryService.updateCounsellingExecutionCategory(counsellingExecutionCategory));
    }

    /**
     * 删除辅导实施类目
     */
    @RequiresPermissions("web:counsellingExecutionCategory:remove")
    @Log(title = "辅导实施类目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(counsellingExecutionCategoryService.deleteCounsellingExecutionCategoryByIds(ids));
    }
}
