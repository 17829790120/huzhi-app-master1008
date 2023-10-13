package com.wlwq.privatePhone.controller;

import java.util.List;

import com.wlwq.privatePhone.axService.AXService;
import com.wlwq.privatePhone.domain.VirtualNumber;
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
import com.wlwq.privatePhone.service.IVirtualNumberService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 虚拟号码Controller
 * 
 * @author Rick wlwq
 * @date 2021-04-01
 */
@Controller
@RequestMapping("/privatePhone/virtualNumber")
public class VirtualNumberController extends BaseController
{
    private String prefix = "privatePhone/virtualNumber";

    @Autowired
    private IVirtualNumberService virtualNumberService;

    @RequiresPermissions("privatePhone:virtualNumber:view")
    @GetMapping()
    public String virtualNumber()
    {
        return prefix + "/virtualNumber";
    }

    /**
     * 查询虚拟号码列表
     */
    @RequiresPermissions("privatePhone:virtualNumber:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VirtualNumber virtualNumber)
    {
        startPage();
        List<VirtualNumber> list = virtualNumberService.selectVirtualNumberList(virtualNumber);
        return getDataTable(list);
    }

    /**
     * 导出虚拟号码列表
     */
    @RequiresPermissions("privatePhone:virtualNumber:export")
    @Log(title = "虚拟号码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VirtualNumber virtualNumber)
    {
        List<VirtualNumber> list = virtualNumberService.selectVirtualNumberList(virtualNumber);
        ExcelUtil<VirtualNumber> util = new ExcelUtil<VirtualNumber>(VirtualNumber.class);
        return util.exportExcel(list, "virtualNumber");
    }

    /**
     * 新增虚拟号码
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存虚拟号码
     */
    @RequiresPermissions("privatePhone:virtualNumber:add")
    @Log(title = "虚拟号码", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VirtualNumber virtualNumber)
    {
        return toAjax(virtualNumberService.insertVirtualNumber(virtualNumber));
    }

    /**
     * 修改虚拟号码
     */
    @GetMapping("/edit/{virtualNumberId}")
    public String edit(@PathVariable("virtualNumberId") Long virtualNumberId, ModelMap mmap)
    {
        VirtualNumber virtualNumber = virtualNumberService.selectVirtualNumberById(virtualNumberId);
        mmap.put("virtualNumber", virtualNumber);
        return prefix + "/edit";
    }

    /**
     * 修改保存虚拟号码
     */
    @RequiresPermissions("privatePhone:virtualNumber:edit")
    @Log(title = "虚拟号码", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VirtualNumber virtualNumber)
    {
        return toAjax(virtualNumberService.updateVirtualNumber(virtualNumber));
    }

    @Autowired
    private AXService axService;

    @RequiresPermissions("privatePhone:virtualNumber:edit")
    @Log(title = "虚拟号码", businessType = BusinessType.UPDATE)
    @PostMapping("/unBindNumber")
    @ResponseBody
    public AjaxResult unBindNumber(Long virtualNumberId)
    {
        VirtualNumber virtualNumber = virtualNumberService.selectVirtualNumberById(virtualNumberId);
        // 解绑
        axService.axUnbindNumber(virtualNumber.getVirtualNumber(), virtualNumber.getSubId());
        return toAjax(virtualNumberService.updateVirtualNumber(VirtualNumber.builder()
                .virtualNumberId(virtualNumberId)
                .realNumber("0")
                .subId("0")
                .status(0)
                .build()));
    }

    /**
     * 删除虚拟号码
     */
    @RequiresPermissions("privatePhone:virtualNumber:remove")
    @Log(title = "虚拟号码", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(virtualNumberService.deleteVirtualNumberByIds(ids));
    }
}
