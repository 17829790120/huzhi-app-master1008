package com.wlwq.web.controller.web;

import java.util.ArrayList;
import java.util.List;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
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
import com.wlwq.api.domain.NewsCenterCategory;
import com.wlwq.api.service.INewsCenterCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;

/**
 * 新闻中心Controller
 *
 * @author wwb
 * @date 2023-05-06
 */
@Controller
@RequestMapping("/web/newsCenterCategory")
public class NewsCenterCategoryController extends BaseController {

    private String prefix = "web/newsCenterCategory";

    @Autowired
    private INewsCenterCategoryService newsCenterCategoryService;

    @RequiresPermissions("web:newsCenterCategory:view")
    @GetMapping()
    public String newsCenterCategory() {
        return prefix + "/newsCenterCategory";
    }

    /**
     * 查询新闻中心列表
     */
    @RequiresPermissions("web:newsCenterCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public List<NewsCenterCategory> list(NewsCenterCategory newsCenterCategory) {
        List<NewsCenterCategory> list = newsCenterCategoryService.selectNewsCenterCategoryList(newsCenterCategory);
        return list;
    }

    /**
     * 导出新闻中心列表
     */
    @RequiresPermissions("web:newsCenterCategory:export")
    @Log(title = "新闻中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NewsCenterCategory newsCenterCategory) {
        List<NewsCenterCategory> list = newsCenterCategoryService.selectNewsCenterCategoryList(newsCenterCategory);
        ExcelUtil<NewsCenterCategory> util = new ExcelUtil<NewsCenterCategory>(NewsCenterCategory.class);
        return util.exportExcel(list, "newsCenterCategory");
    }

    /**
     * 新增新闻中心
     */
    @GetMapping(value = { "/add/{newsCenterCategoryId}", "/add/" })
    public String add(@PathVariable(value = "newsCenterCategoryId", required = false) Long newsCenterCategoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(newsCenterCategoryId))
        {
            mmap.put("newsCenterCategory", newsCenterCategoryService.selectNewsCenterCategoryById(newsCenterCategoryId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存新闻中心
     */
    @RequiresPermissions("web:newsCenterCategory:add")
    @Log(title = "新闻中心", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NewsCenterCategory newsCenterCategory) {
        newsCenterCategory.setUserId(ShiroUtils.getUserId());
        newsCenterCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return toAjax(newsCenterCategoryService.insertNewsCenterCategory(newsCenterCategory));
    }

    /**
     * 修改新闻中心
     */
    @GetMapping("/edit/{newsCenterCategoryId}")
    public String edit(@PathVariable("newsCenterCategoryId") Long newsCenterCategoryId, ModelMap mmap) {
        NewsCenterCategory newsCenterCategory = newsCenterCategoryService.selectNewsCenterCategoryById(newsCenterCategoryId);
        mmap.put("newsCenterCategory", newsCenterCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存新闻中心
     */
    @RequiresPermissions("web:newsCenterCategory:edit")
    @Log(title = "新闻中心", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NewsCenterCategory newsCenterCategory) {
        return toAjax(newsCenterCategoryService.updateNewsCenterCategory(newsCenterCategory));
    }

    /**
     * 删除新闻中心
     */
    @RequiresPermissions("web:newsCenterCategory:remove")
    @Log(title = "新闻中心", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(newsCenterCategoryService.deleteNewsCenterCategoryByIds(ids));
    }

    /**
     * 选择资讯分类树
     */
    @GetMapping(value = { "/selectNewsCenterCategoryTree/{newsCenterCategoryId}", "/selectNewsCenterCategoryTree/" })
    public String selectNewsCenterCategoryTree(@PathVariable(value = "newsCenterCategoryId", required = false) Long newsCenterCategoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(newsCenterCategoryId))
        {
            mmap.put("newsCenterCategory", newsCenterCategoryService.selectNewsCenterCategoryById(newsCenterCategoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载资讯分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<NewsCenterCategory> newsCenterCategoryList = newsCenterCategoryService.selectNewsCenterCategoryList(NewsCenterCategory.builder().delStatus(0).build());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (NewsCenterCategory newsCenterCategory : newsCenterCategoryList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(newsCenterCategory.getNewsCenterCategoryId());
            ztree.setpId(newsCenterCategory.getParentId());
            ztree.setName(newsCenterCategory.getNewsCenterCategoryTitle());
            ztree.setTitle(newsCenterCategory.getNewsCenterCategoryTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}