package com.wlwq.setting.controller;

import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.setting.domain.SettingFeedback;
import com.wlwq.setting.service.ISettingFeedbackService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 意见反馈Controller
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Controller
@RequestMapping("/setting/feedback")
public class SettingFeedbackController extends BaseController
{
    private String prefix = "setting/feedback";

    @Autowired
    private ISettingFeedbackService settingFeedbackService;

    @RequiresPermissions("setting:feedback:view")
    @GetMapping()
    public String feedback()
    {
        return prefix + "/feedback";
    }

    /**
     * 查询意见反馈列表
     */
    @RequiresPermissions("setting:feedback:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SettingFeedback settingFeedback)
    {
        startPage();
        List<SettingFeedback> list = settingFeedbackService.selectSettingFeedbackList(settingFeedback);
        for (SettingFeedback feedback : list) {
            if (StringUtils.isNotBlank(feedback.getFeedbackImages())){
                feedback.setFeedbackImagesStr(feedback.getFeedbackImages().split(","));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出意见反馈列表
     */
    @RequiresPermissions("setting:feedback:export")
    @Log(title = "意见反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SettingFeedback settingFeedback)
    {
        List<SettingFeedback> list = settingFeedbackService.selectSettingFeedbackList(settingFeedback);
        ExcelUtil<SettingFeedback> util = new ExcelUtil<SettingFeedback>(SettingFeedback.class);
        return util.exportExcel(list, "feedback");
    }

    /**
     * 新增意见反馈
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存意见反馈
     */
    @RequiresPermissions("setting:feedback:add")
    @Log(title = "意见反馈", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SettingFeedback settingFeedback)
    {
        return toAjax(settingFeedbackService.insertSettingFeedback(settingFeedback));
    }

    /**
     * 修改意见反馈
     */
    @GetMapping("/edit/{feedbackId}")
    public String edit(@PathVariable("feedbackId") Long feedbackId, ModelMap mmap)
    {
        SettingFeedback settingFeedback = settingFeedbackService.selectSettingFeedbackById(feedbackId);
        mmap.put("settingFeedback", settingFeedback);
        return prefix + "/edit";
    }

    /**
     * 修改保存意见反馈
     */
    @RequiresPermissions("setting:feedback:edit")
    @Log(title = "意见反馈", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SettingFeedback settingFeedback)
    {
        return toAjax(settingFeedbackService.updateSettingFeedback(settingFeedback));
    }

    /**
     * 删除意见反馈
     */
    @RequiresPermissions("setting:feedback:remove")
    @Log(title = "意见反馈", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(settingFeedbackService.deleteSettingFeedbackByIds(ids));
    }
}
