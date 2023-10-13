package com.wlwq.web.controller.web;

import com.wlwq.api.domain.Categorys;
import com.wlwq.api.domain.TrainingModule;
import com.wlwq.api.service.ICategoryService;
import com.wlwq.api.service.ITrainingModuleService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考试类目Controller
 *
 * @author Renbowen
 * @date 2021-01-07
 */
@Controller
@RequestMapping("/web/categorys")
public class CategoryWebController extends BaseController {
    private String prefix = "web/categorys";

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ITrainingModuleService moduleService;

    @RequiresPermissions("web:categorys:view")
    @GetMapping()
    public String category(ModelMap mmap) {
        // 查询模块
        List<TrainingModule> moduleList = moduleService.selectTrainingModuleList(TrainingModule.builder().showStatus(1).build());
        mmap.put("moduleList",moduleList);
        return prefix + "/categorys";
    }

    /**
     * 查询考试类目树列表
     */
    @RequiresPermissions("web:categorys:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Categorys> list(Categorys categorys) {
        List<Categorys> list = categoryService.selectCategoryList(categorys);
        return list;
    }

    /**
     * 导出考试类目列表
     */
    @RequiresPermissions("web:categorys:export")
    @Log(title = "考试类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Categorys categorys) {
        List<Categorys> list = categoryService.selectCategoryList(categorys);
        ExcelUtil<Categorys> util = new ExcelUtil<Categorys>(Categorys.class);
        return util.exportExcel(list, "categorys");
    }

    /**
     * 新增考试类目
     */
    @GetMapping(value = {"/add/{categoryId}", "/add/"})
    public String add(@PathVariable(value = "categoryId", required = false) Long categoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(categoryId)) {
            mmap.put("categorys", categoryService.selectCategoryById(categoryId));
        }
        // 查询模块
        List<TrainingModule> moduleList = moduleService.selectTrainingModuleList(TrainingModule.builder().showStatus(1).build());
        mmap.put("moduleList",moduleList);
        return prefix + "/add";
    }

    /**
     * 新增保存考试类目
     */
    @RequiresPermissions("web:categorys:add")
    @Log(title = "考试类目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Categorys categorys) {
        if(categorys.getParentId() == null || categorys.getParentId() == 0){
            categorys.setLevel(1);
        }else{
            Categorys categorys1 = categoryService.selectCategoryById(categorys.getParentId());
            if(categorys1 != null && categorys1.getParentId() == 0){
                categorys.setLevel(2);
            }else{
                categorys.setLevel(3);
            }
        }

        return toAjax(categoryService.insertCategory(categorys));
    }

    /**
     * 修改考试类目
     */
    @GetMapping("/edit/{categoryId}")
    public String edit(@PathVariable("categoryId") Long categoryId, ModelMap mmap) {
        Categorys categorys = categoryService.selectCategoryById(categoryId);
        mmap.put("category", categorys);
        // 查询模块
        List<TrainingModule> moduleList = moduleService.selectTrainingModuleList(TrainingModule.builder().showStatus(1).build());
        mmap.put("moduleList",moduleList);
        return prefix + "/edit";
    }

    /**
     * 修改保存考试类目
     */
    @RequiresPermissions("web:categorys:edit")
    @Log(title = "考试类目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Categorys categorys) {
        return toAjax(categoryService.updateCategory(categorys));
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:categorys:remove")
    @Log(title = "考试类目", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{categoryId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("categoryId") Long categoryId) {
        return toAjax(categoryService.deleteCategoryById(categoryId));
    }

    /**
     * 选择考试类目树
     */
    @GetMapping(value = {"/selectCategoryTree/{categoryId}", "/selectCategoryTree/"})
    public String selectCategoryTree(@PathVariable(value = "categoryId", required = false) Long categoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(categoryId)) {
            mmap.put("category", categoryService.selectCategoryById(categoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载考试类目树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = categoryService.selectCategoryTree();
        return ztrees;
    }

    /**
     * 根据一级分类查询二级分类
     *
     * @return
     */
    @PostMapping("/selectCategorysListByOneCategoryId")
    @ResponseBody
    public List<Categorys> selectCategorysListByOneCategoryId(String oneCategoryId) {
        List<Categorys> categorysList = categoryService.selectCategoryListByParentId(oneCategoryId);
        return categorysList;
    }

}
