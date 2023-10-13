package com.wlwq.information.controller;

import java.util.ArrayList;
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
import com.wlwq.information.domain.InformationCategory;
import com.wlwq.information.service.IInformationCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 资讯分类Controller
 *
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Controller
@RequestMapping("/information/informationCategory")
public class InformationCategoryWebController extends BaseController {
    private String prefix = "information/informationCategory";

    @Autowired
    private IInformationCategoryService informationCategoryService;

    @RequiresPermissions("information:informationCategory:view")
    @GetMapping()
    public String informationCategory() {
        return prefix + "/informationCategory";
    }

    /**
     * 查询资讯分类树列表
     */
    @RequiresPermissions("information:informationCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public List<InformationCategory> list(InformationCategory informationCategory) {
        return informationCategoryService.selectInformationCategoryList(informationCategory);
    }

    /**
     * 导出资讯分类列表
     */
    @RequiresPermissions("information:informationCategory:export")
    @Log(title = "资讯分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InformationCategory informationCategory) {
        List<InformationCategory> list = informationCategoryService.selectInformationCategoryList(informationCategory);
        ExcelUtil<InformationCategory> util = new ExcelUtil<InformationCategory>(InformationCategory.class);
        return util.exportExcel(list, "informationCategory");
    }

    /**
     * 新增资讯分类
     */
    @GetMapping(value = {"/add/{informationCategoryId}", "/add/"})
    public String add(@PathVariable(value = "informationCategoryId", required = false) Long informationCategoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(informationCategoryId)) {
            mmap.put("informationCategory", informationCategoryService.selectInformationCategoryById(informationCategoryId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存资讯分类
     */
    @RequiresPermissions("information:informationCategory:add")
    @Log(title = "资讯分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InformationCategory informationCategory) {
        informationCategory.setUserId(ShiroUtils.getUserId());
        informationCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return toAjax(informationCategoryService.insertInformationCategory(informationCategory));
    }

    /**
     * 修改资讯分类
     */
    @GetMapping("/edit/{informationCategoryId}")
    public String edit(@PathVariable("informationCategoryId") Long informationCategoryId, ModelMap mmap) {
        InformationCategory informationCategory = informationCategoryService.selectInformationCategoryById(informationCategoryId);
        mmap.put("informationCategory", informationCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯分类
     */
    @RequiresPermissions("information:informationCategory:edit")
    @Log(title = "资讯分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InformationCategory informationCategory) {
        return toAjax(informationCategoryService.updateInformationCategory(informationCategory));
    }

    /**
     * 删除
     */
    @RequiresPermissions("information:informationCategory:remove")
    @Log(title = "资讯分类", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{informationCategoryId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("informationCategoryId") Long informationCategoryId) {
        return toAjax(informationCategoryService.deleteInformationCategoryById(informationCategoryId));
    }

    /**
     * 选择资讯分类树
     */
    @GetMapping(value = {"/selectInformationCategoryTree/{informationCategoryId}", "/selectInformationCategoryTree/"})
    public String selectInformationCategoryTree(@PathVariable(value = "informationCategoryId", required = false) Long informationCategoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(informationCategoryId)) {
            mmap.put("informationCategory", informationCategoryService.selectInformationCategoryById(informationCategoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载资讯分类树列表
     */
/*    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = informationCategoryService.selectInformationCategoryTree();
        return ztrees;
    }*/

    /**
     * 加载资讯分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<InformationCategory> informationCategoryList = informationCategoryService.selectInformationCategoryList(InformationCategory.builder().delStatus(0).build());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (InformationCategory informationCategory : informationCategoryList) {
            Ztree ztree = new Ztree();
            ztree.setId(informationCategory.getInformationCategoryId());
            ztree.setpId(informationCategory.getParentId());
            ztree.setName(informationCategory.getInformationCategoryTitle());
            ztree.setTitle(informationCategory.getInformationCategoryTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }

}
