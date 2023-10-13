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
import com.wlwq.api.domain.CustomLevelDays;
import com.wlwq.api.service.ICustomLevelDaysService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 客户等级回访日维护Controller
 *
 * @author wlwq
 * @date 2023-06-07
 */
@Controller
@RequestMapping("/web/days")
public class CustomLevelDaysController extends BaseController {

    private String prefix = "web/customLevelDays";

    @Autowired
    private ICustomLevelDaysService customLevelDaysService;
    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:days:view")
    @GetMapping()
    public String days() {
        return prefix + "/days";
    }

    /**
     * 查询客户等级回访日维护列表
     */
    @RequiresPermissions("web:days:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CustomLevelDays customLevelDays) {
        Long deptId =ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customLevelDays.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<CustomLevelDays> list = customLevelDaysService.selectCustomLevelDaysList(customLevelDays);
        return getDataTable(list);
    }

    /**
     * 导出客户等级回访日维护列表
     */
    @RequiresPermissions("web:days:export")
    @Log(title = "客户等级回访日维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomLevelDays customLevelDays) {
        List<CustomLevelDays> list = customLevelDaysService.selectCustomLevelDaysList(customLevelDays);
        ExcelUtil<CustomLevelDays> util = new ExcelUtil<CustomLevelDays>(CustomLevelDays.class);
        return util.exportExcel(list, "days");
    }

    /**
     * 新增客户等级回访日维护
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客户等级回访日维护
     */
    @RequiresPermissions("web:days:add")
    @Log(title = "客户等级回访日维护", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomLevelDays customLevelDays) {
        Long deptId =ShiroUtils.getSysUser().getDeptId();
        customLevelDays.setDeptId(deptId);
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customLevelDays.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(customLevelDaysService.insertCustomLevelDays(customLevelDays));
    }

    /**
     * 修改客户等级回访日维护
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        CustomLevelDays customLevelDays = customLevelDaysService.selectCustomLevelDaysById(id);
        mmap.put("customLevelDays", customLevelDays);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户等级回访日维护
     */
    @RequiresPermissions("web:days:edit")
    @Log(title = "客户等级回访日维护", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomLevelDays customLevelDays) {
        return toAjax(customLevelDaysService.updateCustomLevelDays(customLevelDays));
    }

    /**
     * 删除客户等级回访日维护
     */
    @RequiresPermissions("web:days:remove")
    @Log(title = "客户等级回访日维护", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customLevelDaysService.deleteCustomLevelDaysByIds(ids));
    }
}
