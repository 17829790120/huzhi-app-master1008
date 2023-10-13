package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.CourseCustomizationCategory;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.system.service.ISysDeptService;
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
import com.wlwq.api.domain.ResourceFileCategory;
import com.wlwq.api.service.IResourceFileCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 资源文件类别Controller
 *
 * @author wwb
 * @date 2023-05-23
 */
@Controller
@RequestMapping("/web/resourceFileCategory")
public class ResourceFileCategoryController extends BaseController {

    private String prefix = "web/resourceFileCategory";

    @Autowired
    private IResourceFileCategoryService resourceFileCategoryService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:resourceFileCategory:view")
    @GetMapping()
    public String resourceFileCategory() {
        return prefix + "/resourceFileCategory";
    }

    /**
     * 查询资源文件类别树列表
     */
    @RequiresPermissions("web:resourceFileCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public List<ResourceFileCategory> list(ResourceFileCategory resourceFileCategory) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                resourceFileCategory.setCompanyId(dept.getDeptId());
            }
        }
        resourceFileCategory.setTag(2);
        List<ResourceFileCategory> list = resourceFileCategoryService.selectResourceFileCategoryList(resourceFileCategory);
        return list;
    }

    /**
     * 导出资源文件类别列表
     */
    @RequiresPermissions("web:resourceFileCategory:export")
    @Log(title = "资源文件类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ResourceFileCategory resourceFileCategory) {
        List<ResourceFileCategory> list = resourceFileCategoryService.selectResourceFileCategoryList(resourceFileCategory);
        ExcelUtil<ResourceFileCategory> util = new ExcelUtil<ResourceFileCategory>(ResourceFileCategory.class);
        return util.exportExcel(list, "resourceFileCategory");
    }

    /**
     * 新增资源文件类别
     */
    @GetMapping(value = {"/add/{resourceFileCategoryId}", "/add/"})
    public String add(@PathVariable(value = "resourceFileCategoryId", required = false) Long resourceFileCategoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(resourceFileCategoryId)) {
            mmap.put("resourceFileCategory", resourceFileCategoryService.selectResourceFileCategoryById(resourceFileCategoryId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存资源文件类别
     */
    @RequiresPermissions("web:resourceFileCategory:add")
    @Log(title = "资源文件类别", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ResourceFileCategory resourceFileCategory) {
        resourceFileCategory.setUserId(ShiroUtils.getUserId());
        resourceFileCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        if (resourceFileCategory.getParentId() == null ||resourceFileCategory.getParentId() == 0) {
            resourceFileCategory.setAncestors("0");
            resourceFileCategory.setLevel(1);
            resourceFileCategory.setParentId(0L);
        } else {
            ResourceFileCategory category = resourceFileCategoryService.selectResourceFileCategoryById(resourceFileCategory.getParentId());
            resourceFileCategory.setAncestors(category.getAncestors() + "," + resourceFileCategory.getParentId());
            resourceFileCategory.setLevel(Convert.toStrArray(resourceFileCategory.getAncestors()).length);
        }
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                resourceFileCategory.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(resourceFileCategoryService.insertResourceFileCategory(resourceFileCategory));
    }

    /**
     * 修改资源文件类别
     */
    @GetMapping("/edit/{resourceFileCategoryId}")
    public String edit(@PathVariable("resourceFileCategoryId") Long resourceFileCategoryId, ModelMap mmap) {
        ResourceFileCategory resourceFileCategory = resourceFileCategoryService.selectResourceFileCategoryById(resourceFileCategoryId);
        mmap.put("resourceFileCategory", resourceFileCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存资源文件类别
     */
    @RequiresPermissions("web:resourceFileCategory:edit")
    @Log(title = "资源文件类别", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ResourceFileCategory resourceFileCategory) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                resourceFileCategory.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(resourceFileCategoryService.updateResourceFileCategory(resourceFileCategory));
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:resourceFileCategory:remove")
    @Log(title = "资源文件类别", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{resourceFileCategoryId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("resourceFileCategoryId") Long resourceFileCategoryId) {
        return toAjax(resourceFileCategoryService.deleteResourceFileCategoryById(resourceFileCategoryId));
    }

    /**
     * 选择资源文件类别树
     */
    @GetMapping(value = {"/selectResourceFileCategoryTree/{resourceFileCategoryId}", "/selectResourceFileCategoryTree/"})
    public String selectResourceFileCategoryTree(@PathVariable(value = "resourceFileCategoryId", required = false) Long resourceFileCategoryId, ModelMap mmap) {
        if (StringUtils.isNotNull(resourceFileCategoryId)) {
            mmap.put("resourceFileCategory", resourceFileCategoryService.selectResourceFileCategoryById(resourceFileCategoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载资源文件类别树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = resourceFileCategoryService.selectResourceFileCategoryTree();
        return ztrees;
    }
}
