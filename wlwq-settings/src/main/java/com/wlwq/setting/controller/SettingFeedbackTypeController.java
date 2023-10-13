package com.wlwq.setting.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.setting.domain.SettingFeedbackType;
import com.wlwq.setting.service.ISettingFeedbackTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 意见反馈类型Controller
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Controller
@RequestMapping("/setting/settingFeedbackType")
public class SettingFeedbackTypeController extends BaseController
{
    private String prefix = "setting/settingFeedbackType";

    @Autowired
    private ISettingFeedbackTypeService settingFeedbackTypeService;

    @RequiresPermissions("setting:settingFeedbackType:view")
    @GetMapping()
    public String settingFeedbackType()
    {
        return prefix + "/settingFeedbackType";
    }

    /**
     * 查询意见反馈类型列表
     */
    @RequiresPermissions("setting:settingFeedbackType:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SettingFeedbackType settingFeedbackType)
    {
        settingFeedbackType.setDelStatus(0);
        startPage();
        List<SettingFeedbackType> list = settingFeedbackTypeService.selectSettingFeedbackTypeList(settingFeedbackType);
        return getDataTable(list);
    }

    /**
     * 导出意见反馈类型列表
     */
    @RequiresPermissions("setting:settingFeedbackType:export")
    @Log(title = "意见反馈类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SettingFeedbackType settingFeedbackType)
    {
        List<SettingFeedbackType> list = settingFeedbackTypeService.selectSettingFeedbackTypeList(settingFeedbackType);
        ExcelUtil<SettingFeedbackType> util = new ExcelUtil<SettingFeedbackType>(SettingFeedbackType.class);
        return util.exportExcel(list, "settingFeedbackType");
    }

    /**
     * 新增意见反馈类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存意见反馈类型
     */
    @RequiresPermissions("setting:settingFeedbackType:add")
    @Log(title = "意见反馈类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SettingFeedbackType settingFeedbackType)
    {
        return toAjax(settingFeedbackTypeService.insertSettingFeedbackType(settingFeedbackType));
    }

    /**
     * 修改意见反馈类型
     */
    @GetMapping("/edit/{feedbackTypeId}")
    public String edit(@PathVariable("feedbackTypeId") Long feedbackTypeId, ModelMap mmap)
    {
        SettingFeedbackType settingFeedbackType = settingFeedbackTypeService.selectSettingFeedbackTypeById(feedbackTypeId);
        mmap.put("settingFeedbackType", settingFeedbackType);
        return prefix + "/edit";
    }

    /**
     * 修改保存意见反馈类型
     */
    @RequiresPermissions("setting:settingFeedbackType:edit")
    @Log(title = "意见反馈类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SettingFeedbackType settingFeedbackType)
    {
        return toAjax(settingFeedbackTypeService.updateSettingFeedbackType(settingFeedbackType));
    }

    /**
     * 删除意见反馈类型
     */
    @RequiresPermissions("setting:settingFeedbackType:remove")
    @Log(title = "意见反馈类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(settingFeedbackTypeService.deleteSettingFeedbackTypeByIds(ids));
    }
}
