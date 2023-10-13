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
import com.wlwq.api.domain.LiveRegistration;
import com.wlwq.api.service.ILiveRegistrationService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 直播报名Controller
 *
 * @author wwb
 * @date 2023-05-15
 */
@Controller
@RequestMapping("/web/liveRegistration")
public class LiveRegistrationController extends BaseController {

    private String prefix = "web/liveRegistration";

    @Autowired
    private ILiveRegistrationService liveRegistrationService;

    @RequiresPermissions("web:liveRegistration:view")
    @GetMapping()
    public String liveRegistration() {
        return prefix + "/liveRegistration";
    }

    /**
     * 查询直播报名列表
     */
    @RequiresPermissions("web:liveRegistration:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(LiveRegistration liveRegistration) {
        startPage();
        List<LiveRegistration> list = liveRegistrationService.selectLiveRegistrationList(liveRegistration);
        return getDataTable(list);
    }

    /**
     * 导出直播报名列表
     */
    @RequiresPermissions("web:liveRegistration:export")
    @Log(title = "直播报名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(LiveRegistration liveRegistration) {
        List<LiveRegistration> list = liveRegistrationService.selectLiveRegistrationList(liveRegistration);
        ExcelUtil<LiveRegistration> util = new ExcelUtil<LiveRegistration>(LiveRegistration.class);
        return util.exportExcel(list, "liveRegistration");
    }

    /**
     * 新增直播报名
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存直播报名
     */
    @RequiresPermissions("web:liveRegistration:add")
    @Log(title = "直播报名", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(LiveRegistration liveRegistration) {
        return toAjax(liveRegistrationService.insertLiveRegistration(liveRegistration));
    }

    /**
     * 修改直播报名
     */
    @GetMapping("/edit/{liveRegistrationId}")
    public String edit(@PathVariable("liveRegistrationId") String liveRegistrationId, ModelMap mmap) {
        LiveRegistration liveRegistration = liveRegistrationService.selectLiveRegistrationById(liveRegistrationId);
        mmap.put("liveRegistration", liveRegistration);
        return prefix + "/edit";
    }

    /**
     * 修改保存直播报名
     */
    @RequiresPermissions("web:liveRegistration:edit")
    @Log(title = "直播报名", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(LiveRegistration liveRegistration) {
        return toAjax(liveRegistrationService.updateLiveRegistration(liveRegistration));
    }

    /**
     * 删除直播报名
     */
    @RequiresPermissions("web:liveRegistration:remove")
    @Log(title = "直播报名", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(liveRegistrationService.deleteLiveRegistrationByIds(ids));
    }
}
