package com.wlwq.privatePhone.controller;

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
import com.wlwq.privatePhone.domain.AxServiceLog;
import com.wlwq.privatePhone.service.IAxServiceLogService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * AX号码绑定日志Controller
 * 
 * @author Rick wlwq
 * @date 2021-04-02
 */
@Controller
@RequestMapping("/privatePhone/axServiceLog")
public class AxServiceLogController extends BaseController
{
    private String prefix = "privatePhone/axServiceLog";

    @Autowired
    private IAxServiceLogService axServiceLogService;

    @RequiresPermissions("privatePhone:axServiceLog:view")
    @GetMapping()
    public String axServiceLog()
    {
        return prefix + "/axServiceLog";
    }

    /**
     * 查询AX号码绑定日志列表
     */
    @RequiresPermissions("privatePhone:axServiceLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AxServiceLog axServiceLog)
    {
        startPage();
        List<AxServiceLog> list = axServiceLogService.selectAxServiceLogList(axServiceLog);
        return getDataTable(list);
    }

    /**
     * 导出AX号码绑定日志列表
     */
    @RequiresPermissions("privatePhone:axServiceLog:export")
    @Log(title = "AX号码绑定日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AxServiceLog axServiceLog)
    {
        List<AxServiceLog> list = axServiceLogService.selectAxServiceLogList(axServiceLog);
        ExcelUtil<AxServiceLog> util = new ExcelUtil<AxServiceLog>(AxServiceLog.class);
        return util.exportExcel(list, "axServiceLog");
    }

    /**
     * 新增AX号码绑定日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存AX号码绑定日志
     */
    @RequiresPermissions("privatePhone:axServiceLog:add")
    @Log(title = "AX号码绑定日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AxServiceLog axServiceLog)
    {
        return toAjax(axServiceLogService.insertAxServiceLog(axServiceLog));
    }

    /**
     * 修改AX号码绑定日志
     */
    @GetMapping("/edit/{axServiceLogId}")
    public String edit(@PathVariable("axServiceLogId") Long axServiceLogId, ModelMap mmap)
    {
        AxServiceLog axServiceLog = axServiceLogService.selectAxServiceLogById(axServiceLogId);
        mmap.put("axServiceLog", axServiceLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存AX号码绑定日志
     */
    @RequiresPermissions("privatePhone:axServiceLog:edit")
    @Log(title = "AX号码绑定日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AxServiceLog axServiceLog)
    {
        return toAjax(axServiceLogService.updateAxServiceLog(axServiceLog));
    }

    /**
     * 删除AX号码绑定日志
     */
    @RequiresPermissions("privatePhone:axServiceLog:remove")
    @Log(title = "AX号码绑定日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(axServiceLogService.deleteAxServiceLogByIds(ids));
    }
}
