package com.wlwq.setting.controller;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.setting.domain.SettingAgreement;
import com.wlwq.setting.service.ISettingAgreementService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 协议Controller
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Controller
@RequestMapping("/setting/settingAgreement")
public class SettingAgreementController extends BaseController
{
    private String prefix = "setting/settingAgreement";

    @Autowired
    private ISettingAgreementService settingAgreementService;

    @RequiresPermissions("setting:settingAgreement:view")
    @GetMapping()
    public String settingAgreement()
    {
        return prefix + "/settingAgreement";
    }

    /**
     * 查询协议列表
     */
    @RequiresPermissions("setting:settingAgreement:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SettingAgreement settingAgreement)
    {
        settingAgreement.setDelStatus(0);
        startPage();
        List<SettingAgreement> list = settingAgreementService.selectSettingAgreementList(settingAgreement);
        return getDataTable(list);
    }

    /**
     * 导出协议列表
     */
    @RequiresPermissions("setting:settingAgreement:export")
    @Log(title = "协议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SettingAgreement settingAgreement)
    {
        List<SettingAgreement> list = settingAgreementService.selectSettingAgreementList(settingAgreement);
        ExcelUtil<SettingAgreement> util = new ExcelUtil<SettingAgreement>(SettingAgreement.class);
        return util.exportExcel(list, "settingAgreement");
    }

    /**
     * 新增协议
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存协议
     */
    @RequiresPermissions("setting:settingAgreement:add")
    @Log(title = "协议", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SettingAgreement settingAgreement)
    {
        settingAgreement.setAgreementId(IdUtil.getSnowflake(0,0).nextIdStr());
        return toAjax(settingAgreementService.insertSettingAgreement(settingAgreement));
    }

    /**
     * 修改协议
     */
    @GetMapping("/edit/{agreementId}")
    public String edit(@PathVariable("agreementId") String agreementId, ModelMap mmap)
    {
        SettingAgreement settingAgreement = settingAgreementService.selectSettingAgreementById(agreementId);
        mmap.put("settingAgreement", settingAgreement);
        return prefix + "/edit";
    }

    /**
     * 修改保存协议
     */
    @RequiresPermissions("setting:settingAgreement:edit")
    @Log(title = "协议", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SettingAgreement settingAgreement)
    {
        return toAjax(settingAgreementService.updateSettingAgreement(settingAgreement));
    }

    /**
     * 删除协议
     */
    @RequiresPermissions("setting:settingAgreement:remove")
    @Log(title = "协议", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(settingAgreementService.deleteSettingAgreementByIds(ids));
    }
}
