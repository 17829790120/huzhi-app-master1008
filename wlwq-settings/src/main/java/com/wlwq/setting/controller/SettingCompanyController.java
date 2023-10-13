package com.wlwq.setting.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.setting.domain.SettingCompany;
import com.wlwq.setting.service.ISettingCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司信息Controller
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Controller
@RequestMapping("/setting/settingCompany")
public class SettingCompanyController extends BaseController
{
    private String prefix = "setting/settingCompany";

    @Autowired
    private ISettingCompanyService settingCompanyService;

    @RequiresPermissions("setting:settingCompany:view")
    @GetMapping()
    public String settingCompany()
    {
        return prefix + "/settingCompany";
    }

    /**
     * 查询公司信息列表
     */
    @RequiresPermissions("setting:settingCompany:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SettingCompany settingCompany)
    {
        startPage();
        List<SettingCompany> list = settingCompanyService.selectSettingCompanyList(settingCompany);
        return getDataTable(list);
    }

    /**
     * 导出公司信息列表
     */
    @RequiresPermissions("setting:settingCompany:export")
    @Log(title = "公司信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SettingCompany settingCompany)
    {
        List<SettingCompany> list = settingCompanyService.selectSettingCompanyList(settingCompany);
        ExcelUtil<SettingCompany> util = new ExcelUtil<SettingCompany>(SettingCompany.class);
        return util.exportExcel(list, "settingCompany");
    }

    /**
     * 新增公司信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存公司信息
     */
    @RequiresPermissions("setting:settingCompany:add")
    @Log(title = "公司信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SettingCompany settingCompany)
    {
        return toAjax(settingCompanyService.insertSettingCompany(settingCompany));
    }

    /**
     * 修改公司信息
     */
    @GetMapping("/edit/{companyId}")
    public String edit(@PathVariable("companyId") Long companyId, ModelMap mmap)
    {
        SettingCompany settingCompany = settingCompanyService.selectSettingCompanyById(companyId);
        mmap.put("settingCompany", settingCompany);
        return prefix + "/edit";
    }

    /**
     * 修改保存公司信息
     */
    @RequiresPermissions("setting:settingCompany:edit")
    @Log(title = "公司信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SettingCompany settingCompany)
    {
        return toAjax(settingCompanyService.updateSettingCompany(settingCompany));
    }

    /**
     * 删除公司信息
     */
    @RequiresPermissions("setting:settingCompany:remove")
    @Log(title = "公司信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(settingCompanyService.deleteSettingCompanyByIds(ids));
    }
}
