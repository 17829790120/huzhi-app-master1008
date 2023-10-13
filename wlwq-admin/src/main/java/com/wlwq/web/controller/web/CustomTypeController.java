package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.core.domain.entity.SysDept;
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
import com.wlwq.api.domain.CustomType;
import com.wlwq.api.service.ICustomTypeService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 客户意向Controller
 *
 * @author wlwq
 * @date 2023-06-10
 */
@Controller
@RequestMapping("/web/customType")
public class CustomTypeController extends BaseController {

    private String prefix = "web/customType";

    @Autowired
    private ICustomTypeService customTypeService;
    @Autowired
    private ISysDeptService deptService;
    @RequiresPermissions("web:customType:view")
    @GetMapping()
    public String type() {
        return prefix + "/type";
    }

    /**
     * 查询客户意向列表
     */
    @RequiresPermissions("web:customType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CustomType customType) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customType.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<CustomType> list = customTypeService.selectCustomTypeList(customType);
        return getDataTable(list);
    }

    /**
     * 导出客户意向列表
     */
    @RequiresPermissions("web:customType:export")
    @Log(title = "客户意向", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomType customType) {
        List<CustomType> list = customTypeService.selectCustomTypeList(customType);
        ExcelUtil<CustomType> util = new ExcelUtil<CustomType>(CustomType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 新增客户意向
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客户意向
     */
    @RequiresPermissions("web:customType:add")
    @Log(title = "客户意向", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomType customType) {
        Long deptId =ShiroUtils.getSysUser().getDeptId();
        customType.setDeptId(deptId.toString());
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customType.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(customTypeService.insertCustomType(customType));
    }

    /**
     * 修改客户意向
     */
    @GetMapping("/edit/{customTypeId}")
    public String edit(@PathVariable("customTypeId") String customTypeId, ModelMap mmap) {
        CustomType customType = customTypeService.selectCustomTypeById(customTypeId);
        mmap.put("customType", customType);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户意向
     */
    @RequiresPermissions("web:customType:edit")
    @Log(title = "客户意向", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomType customType) {
        return toAjax(customTypeService.updateCustomType(customType));
    }

    /**
     * 删除客户意向
     */
    @RequiresPermissions("web:customType:remove")
    @Log(title = "客户意向", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customTypeService.deleteCustomTypeByIds(ids));
    }
}
