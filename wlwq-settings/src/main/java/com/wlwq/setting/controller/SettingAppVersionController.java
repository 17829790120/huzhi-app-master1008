package com.wlwq.setting.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.setting.domain.SettingAppVersion;
import com.wlwq.setting.service.ISettingAppVersionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APP版本控制Controller
 * 
 * @author Rick wlwq
 * @date 2022-04-13
 */
@Controller
@RequestMapping("/setting/version")
public class SettingAppVersionController extends BaseController
{
    private String prefix = "setting/version";

    @Autowired
    private ISettingAppVersionService settingAppVersionService;

    @RequiresPermissions("setting:version:view")
    @GetMapping()
    public String version()
    {
        return prefix + "/version";
    }

    /**
     * 查询APP版本控制列表
     */
    @RequiresPermissions("setting:version:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SettingAppVersion settingAppVersion)
    {
        startPage();
        List<SettingAppVersion> list = settingAppVersionService.selectSettingAppVersionList(settingAppVersion);
        return getDataTable(list);
    }

    /**
     * 导出APP版本控制列表
     */
    @RequiresPermissions("setting:version:export")
    @Log(title = "APP版本控制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SettingAppVersion settingAppVersion)
    {
        List<SettingAppVersion> list = settingAppVersionService.selectSettingAppVersionList(settingAppVersion);
        ExcelUtil<SettingAppVersion> util = new ExcelUtil<SettingAppVersion>(SettingAppVersion.class);
        return util.exportExcel(list, "version");
    }

    /**
     * 新增APP版本控制
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存APP版本控制
     */
    @RequiresPermissions("setting:version:add")
    @Log(title = "APP版本控制", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SettingAppVersion settingAppVersion)
    {
        return toAjax(settingAppVersionService.insertSettingAppVersion(settingAppVersion));
    }

    /**
     * 修改APP版本控制
     */
    @GetMapping("/edit/{versionId}")
    public String edit(@PathVariable("versionId") Long versionId, ModelMap mmap)
    {
        SettingAppVersion settingAppVersion = settingAppVersionService.selectSettingAppVersionById(versionId);
        mmap.put("settingAppVersion", settingAppVersion);
        return prefix + "/edit";
    }

    /**
     * 修改保存APP版本控制
     */
    @RequiresPermissions("setting:version:edit")
    @Log(title = "APP版本控制", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SettingAppVersion settingAppVersion)
    {
        return toAjax(settingAppVersionService.updateSettingAppVersion(settingAppVersion));
    }

    /**
     * 删除APP版本控制
     */
    @RequiresPermissions("setting:version:remove")
    @Log(title = "APP版本控制", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(settingAppVersionService.deleteSettingAppVersionByIds(ids));
    }
}
