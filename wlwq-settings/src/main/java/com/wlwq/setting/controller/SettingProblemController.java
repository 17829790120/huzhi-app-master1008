package com.wlwq.setting.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.setting.domain.SettingProblem;
import com.wlwq.setting.service.ISettingProblemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 常见问题Controller
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Controller
@RequestMapping("/setting/problem")
public class SettingProblemController extends BaseController
{
    private String prefix = "setting/problem";

    @Autowired
    private ISettingProblemService settingProblemService;

    @RequiresPermissions("setting:problem:view")
    @GetMapping()
    public String problem()
    {
        return prefix + "/problem";
    }

    /**
     * 查询常见问题列表
     */
    @RequiresPermissions("setting:problem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SettingProblem settingProblem)
    {
        settingProblem.setDelStatus(0);
        startPage();
        List<SettingProblem> list = settingProblemService.selectSettingProblemList(settingProblem);
        return getDataTable(list);
    }

    /**
     * 导出常见问题列表
     */
    @RequiresPermissions("setting:problem:export")
    @Log(title = "常见问题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SettingProblem settingProblem)
    {
        List<SettingProblem> list = settingProblemService.selectSettingProblemList(settingProblem);
        ExcelUtil<SettingProblem> util = new ExcelUtil<SettingProblem>(SettingProblem.class);
        return util.exportExcel(list, "problem");
    }

    /**
     * 新增常见问题
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存常见问题
     */
    @RequiresPermissions("setting:problem:add")
    @Log(title = "常见问题", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SettingProblem settingProblem)
    {
        return toAjax(settingProblemService.insertSettingProblem(settingProblem));
    }

    /**
     * 修改常见问题
     */
    @GetMapping("/edit/{problemId}")
    public String edit(@PathVariable("problemId") Long problemId, ModelMap mmap)
    {
        SettingProblem settingProblem = settingProblemService.selectSettingProblemById(problemId);
        mmap.put("settingProblem", settingProblem);
        return prefix + "/edit";
    }

    /**
     * 修改保存常见问题
     */
    @RequiresPermissions("setting:problem:edit")
    @Log(title = "常见问题", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SettingProblem settingProblem)
    {
        return toAjax(settingProblemService.updateSettingProblem(settingProblem));
    }

    /**
     * 删除常见问题
     */
    @RequiresPermissions("setting:problem:remove")
    @Log(title = "常见问题", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(settingProblemService.deleteSettingProblemByIds(ids));
    }
}
