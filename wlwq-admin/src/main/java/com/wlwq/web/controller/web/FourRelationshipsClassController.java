package com.wlwq.web.controller.web;

import com.wlwq.api.domain.FourRelationshipsClass;
import com.wlwq.api.service.IFourRelationshipsClassService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 四类关系分类Controller
 *
 * @author dxy
 */
@Controller
@RequestMapping("/web/fourRelationshipsClass")
public class FourRelationshipsClassController extends BaseController {

    private String prefix = "web/fourRelationshipsClass";

    @Autowired
    private IFourRelationshipsClassService fourRelationshipsClassService;

    @RequiresPermissions("web:fourRelationshipsClass:view")
    @GetMapping()
    public String fourRelationshipsClass() {
        return prefix + "/fourRelationshipsClass";
    }

    /**
     * 查询四类关系分类列表
     */
    @RequiresPermissions("web:fourRelationshipsClass:list")
    @PostMapping("/list")
    @ResponseBody
    public List<FourRelationshipsClass> list(FourRelationshipsClass fourRelationshipsClass) {
        if(ShiroUtils.getUserId()!=1){
            fourRelationshipsClass.setUserId(ShiroUtils.getUserId());
            fourRelationshipsClass.setDeptId(ShiroUtils.getSysUser().getDeptId());
        }
        List<FourRelationshipsClass> list = fourRelationshipsClassService.selectFourRelationshipsClassList(fourRelationshipsClass);
        return list;
    }

    /**
     * 导出四类关系分类列表
     */
    @RequiresPermissions("web:fourRelationshipsClass:export")
    @Log(title = "四类关系分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FourRelationshipsClass fourRelationshipsClass) {
        List<FourRelationshipsClass> list = fourRelationshipsClassService.selectFourRelationshipsClassList(fourRelationshipsClass);
        ExcelUtil<FourRelationshipsClass> util = new ExcelUtil<FourRelationshipsClass>(FourRelationshipsClass.class);
        return util.exportExcel(list, "fourRelationshipsClass");
    }

    @GetMapping(value = {"/add/{fourRelationshipsClassId}", "/add/"})
    public String add(@PathVariable(value = "fourRelationshipsClassId", required = false) Long fourRelationshipsClassId, ModelMap mmap) {
        if (StringUtils.isNotNull(fourRelationshipsClassId)) {
            FourRelationshipsClass fourRelationshipsClass = fourRelationshipsClassService.selectFourRelationshipsClassById(fourRelationshipsClassId);
            mmap.put("storeClass", fourRelationshipsClass);
        } else {
            // 主目录
            mmap.put("storeClass", FourRelationshipsClass.builder().fourRelationshipsClassId(0L).className("主目录").build());
        }
        return prefix + "/add";
    }

    /**
     * 新增保存四类关系分类
     */
    @RequiresPermissions("web:fourRelationshipsClass:add")
    @Log(title = "四类关系分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FourRelationshipsClass fourRelationshipsClass) {
        fourRelationshipsClass.setUserId(ShiroUtils.getUserId());
        fourRelationshipsClass.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return toAjax(fourRelationshipsClassService.insertFourRelationshipsClass(fourRelationshipsClass));
    }

    /**
     * 修改四类关系分类
     */
    @GetMapping("/edit/{fourRelationshipsClassId}")
    public String edit(@PathVariable("fourRelationshipsClassId") Long  fourRelationshipsClassId, ModelMap mmap) {
        FourRelationshipsClass fourRelationshipsClass = fourRelationshipsClassService.selectFourRelationshipsClassById(fourRelationshipsClassId);
        // 查询父类

        FourRelationshipsClass parentClass = fourRelationshipsClassService.selectFourRelationshipsClassById(fourRelationshipsClass.getParentId());
        fourRelationshipsClass.setParentName(parentClass != null ? parentClass.getClassName() : "主目录");
        mmap.put("fourRelationshipsClass", fourRelationshipsClass);
        return prefix + "/edit";
    }

    /**
     * 修改保存四类关系分类
     */
    @RequiresPermissions("web:fourRelationshipsClass:edit")
    @Log(title = "四类关系分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FourRelationshipsClass fourRelationshipsClass) {
        return toAjax(fourRelationshipsClassService.updateFourRelationshipsClass(fourRelationshipsClass));
    }

    /**
     * 删除店铺分类
     */
    @RequiresPermissions("web:storeClass:remove")
    @Log(title = "店铺分类", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{fourRelationshipsClassId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("fourRelationshipsClassId") Long fourRelationshipsClassId) {
        List<FourRelationshipsClass> fourRelationshipsClassList = fourRelationshipsClassService.selectFourRelationshipsClassList(FourRelationshipsClass.builder().parentId(fourRelationshipsClassId).build());
        if (fourRelationshipsClassList.size() > 0) {
            return AjaxResult.warn("存在子菜单,不允许删除");
        }
        return toAjax(fourRelationshipsClassService.deleteFourRelationshipsClassById(fourRelationshipsClassId));
    }

    /**
     * 删除四类关系分类
     */
    @RequiresPermissions("web:fourRelationshipsClass:remove")
    @Log(title = "四类关系分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fourRelationshipsClassService.deleteFourRelationshipsClassByIds(ids));
    }

    /**
     * 选择服务分类树
     */
    @GetMapping(value = {"/selectStoreClassTree/{fourRelationshipsClassId}", "/selectStoreClassTree/"})
    public String selectStoreClassTree(@PathVariable(value = "fourRelationshipsClassId", required = false) Long fourRelationshipsClassId, ModelMap mmap) {
        if (StringUtils.isNotNull(fourRelationshipsClassId)) {
            mmap.put("storeClass", fourRelationshipsClassService.selectFourRelationshipsClassById(fourRelationshipsClassId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载服务分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = fourRelationshipsClassService.selectStoreClassTree();
        return ztrees;
    }
}
