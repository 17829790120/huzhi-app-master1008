package com.wlwq.web.controller.web;

import java.util.List;

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
import com.wlwq.api.domain.ExperienceSharing;
import com.wlwq.api.service.IExperienceSharingService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 心得分享Controller
 *
 * @author wwb
 * @date 2023-05-05
 */
@Controller
@RequestMapping("/web/experienceSharing")
public class ExperienceSharingController extends BaseController {

    private String prefix = "web/experienceSharing";

    @Autowired
    private IExperienceSharingService experienceSharingService;

    @RequiresPermissions("web:experienceSharing:view")
    @GetMapping()
    public String experienceSharing() {
        return prefix + "/experienceSharing";
    }

    /**
     * 查询心得分享列表
     */
    @RequiresPermissions("web:experienceSharing:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExperienceSharing experienceSharing) {
        startPage();
        List<ExperienceSharing> list = experienceSharingService.selectExperienceSharingList(experienceSharing);
        return getDataTable(list);
    }

    /**
     * 导出心得分享列表
     */
    @RequiresPermissions("web:experienceSharing:export")
    @Log(title = "心得分享", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExperienceSharing experienceSharing) {
        List<ExperienceSharing> list = experienceSharingService.selectExperienceSharingList(experienceSharing);
        ExcelUtil<ExperienceSharing> util = new ExcelUtil<ExperienceSharing>(ExperienceSharing.class);
        return util.exportExcel(list, "experienceSharing");
    }

    /**
     * 新增心得分享
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存心得分享
     */
    @RequiresPermissions("web:experienceSharing:add")
    @Log(title = "心得分享", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExperienceSharing experienceSharing) {
        return toAjax(experienceSharingService.insertExperienceSharing(experienceSharing));
    }

    /**
     * 修改心得分享
     */
    @GetMapping("/edit/{experienceSharingId}")
    public String edit(@PathVariable("experienceSharingId") String experienceSharingId, ModelMap mmap) {
        ExperienceSharing experienceSharing = experienceSharingService.selectExperienceSharingById(experienceSharingId);
        mmap.put("experienceSharing", experienceSharing);
        return prefix + "/edit";
    }

    /**
     * 修改保存心得分享
     */
    @RequiresPermissions("web:experienceSharing:edit")
    @Log(title = "心得分享", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExperienceSharing experienceSharing) {
        return toAjax(experienceSharingService.updateExperienceSharing(experienceSharing));
    }

    /**
     * 删除心得分享
     */
    @RequiresPermissions("web:experienceSharing:remove")
    @Log(title = "心得分享", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(experienceSharingService.deleteExperienceSharingByIds(ids));
    }
}
