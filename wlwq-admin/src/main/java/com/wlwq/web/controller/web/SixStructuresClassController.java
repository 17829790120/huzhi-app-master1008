package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.SixStructures;
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
import com.wlwq.api.domain.SixStructuresClass;
import com.wlwq.api.service.ISixStructuresClassService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 六大架构分类Controller
 *
 * @author wlwq
 * @date 2023-08-28
 */
@Controller
@RequestMapping("/web/sixStructuresClass")
public class SixStructuresClassController extends BaseController {

    private String prefix = "web/sixStructuresClass";

    @Autowired
    private ISixStructuresClassService sixStructuresClassService;

    @RequiresPermissions("web:sixStructuresClass:view")
    @GetMapping()
    public String sixStructuresClass() {
        return prefix + "/sixStructuresClass";
    }

    /**
     * 查询六大架构分类列表
     */
    @RequiresPermissions("web:sixStructuresClass:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SixStructuresClass> list(SixStructuresClass sixStructuresClass) {
        if(ShiroUtils.getUserId()!=1){
            sixStructuresClass.setUserId(ShiroUtils.getUserId());
            sixStructuresClass.setDeptId(ShiroUtils.getSysUser().getDeptId());
        }
        List<SixStructuresClass> list = sixStructuresClassService.selectSixStructuresClassList(sixStructuresClass);
        return list;
    }

    /**
     * 导出六大架构分类列表
     */
    @RequiresPermissions("web:sixStructuresClass:export")
    @Log(title = "六大架构分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SixStructuresClass sixStructuresClass) {
        List<SixStructuresClass> list = sixStructuresClassService.selectSixStructuresClassList(sixStructuresClass);
        ExcelUtil<SixStructuresClass> util = new ExcelUtil<SixStructuresClass>(SixStructuresClass.class);
        return util.exportExcel(list, "sixStructuresClass");
    }

    @GetMapping(value = {"/add/{sixStructuresClassId}", "/add/"})
    public String add(@PathVariable(value = "sixStructuresClassId", required = false) Long sixStructuresClassId, ModelMap mmap) {
        if (StringUtils.isNotNull(sixStructuresClassId)) {
            SixStructuresClass sixStructuresClass = sixStructuresClassService.selectSixStructuresClassById(sixStructuresClassId);
            mmap.put("storeClass", sixStructuresClass);
        } else {
            // 主目录
            mmap.put("storeClass", SixStructuresClass.builder().sixStructuresClassId(0L).className("主目录").build());
        }
        return prefix + "/add";
    }

    /**
     * 新增保存六大架构分类
     */
    @RequiresPermissions("web:sixStructuresClass:add")
    @Log(title = "六大架构分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SixStructuresClass sixStructuresClass) {
        sixStructuresClass.setUserId(ShiroUtils.getUserId());
        sixStructuresClass.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return toAjax(sixStructuresClassService.insertSixStructuresClass(sixStructuresClass));
    }

    /**
     * 修改六大架构分类
     */
    @GetMapping("/edit/{sixStructuresClassId}")
    public String edit(@PathVariable("sixStructuresClassId") Long  sixStructuresClassId, ModelMap mmap) {
        SixStructuresClass sixStructuresClass = sixStructuresClassService.selectSixStructuresClassById(sixStructuresClassId);
        // 查询父类

        SixStructuresClass parentClass = sixStructuresClassService.selectSixStructuresClassById(sixStructuresClass.getParentId());
        sixStructuresClass.setParentName(parentClass != null ? parentClass.getClassName() : "主目录");
        mmap.put("sixStructuresClass", sixStructuresClass);
        return prefix + "/edit";
    }

    /**
     * 修改保存六大架构分类
     */
    @RequiresPermissions("web:sixStructuresClass:edit")
    @Log(title = "六大架构分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SixStructuresClass sixStructuresClass) {
        return toAjax(sixStructuresClassService.updateSixStructuresClass(sixStructuresClass));
    }

    /**
     * 删除店铺分类
     */
    @RequiresPermissions("web:storeClass:remove")
    @Log(title = "店铺分类", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{sixStructuresClassId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("sixStructuresClassId") Long sixStructuresClassId) {
        List<SixStructuresClass> sixStructuresClassList = sixStructuresClassService.selectSixStructuresClassList(SixStructuresClass.builder().parentId(sixStructuresClassId).build());
        if (sixStructuresClassList.size() > 0) {
            return AjaxResult.warn("存在子菜单,不允许删除");
        }
        return toAjax(sixStructuresClassService.deleteSixStructuresClassById(sixStructuresClassId));
    }

    /**
     * 删除六大架构分类
     */
    @RequiresPermissions("web:sixStructuresClass:remove")
    @Log(title = "六大架构分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sixStructuresClassService.deleteSixStructuresClassByIds(ids));
    }

    /**
     * 选择服务分类树
     */
    @GetMapping(value = {"/selectStoreClassTree/{sixStructuresClassId}", "/selectStoreClassTree/"})
    public String selectStoreClassTree(@PathVariable(value = "sixStructuresClassId", required = false) Long sixStructuresClassId, ModelMap mmap) {
        if (StringUtils.isNotNull(sixStructuresClassId)) {
            mmap.put("storeClass", sixStructuresClassService.selectSixStructuresClassById(sixStructuresClassId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载服务分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = sixStructuresClassService.selectStoreClassTree();
        return ztrees;
    }
}
