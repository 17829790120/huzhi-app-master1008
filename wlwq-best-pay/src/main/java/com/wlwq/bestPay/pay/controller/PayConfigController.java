package com.wlwq.bestPay.pay.controller;

import cn.hutool.core.util.IdUtil;
import com.wlwq.bestPay.pay.domain.PayConfig;
import com.wlwq.bestPay.pay.service.IPayConfigService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付配置2.0版Controller
 * 
 * @author Rick wlwq
 * @date 2021-07-02
 */
@Controller
@RequestMapping("/pay/payConfig")
public class PayConfigController extends BaseController
{
    private String prefix = "pay/payConfig";

    @Autowired
    private IPayConfigService payConfigService;

    @RequiresPermissions("pay:payConfig:view")
    @GetMapping()
    public String payConfig()
    {
        return prefix + "/payConfig";
    }

    /**
     * 查询支付配置2.0版列表
     */
    @RequiresPermissions("pay:payConfig:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PayConfig payConfig)
    {
        startPage();
        List<PayConfig> list = payConfigService.selectPayConfigList(payConfig);
        return getDataTable(list);
    }

    /**
     * 导出支付配置2.0版列表
     */
    @RequiresPermissions("pay:payConfig:export")
    @Log(title = "支付配置2.0版", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PayConfig payConfig)
    {
        List<PayConfig> list = payConfigService.selectPayConfigList(payConfig);
        ExcelUtil<PayConfig> util = new ExcelUtil<PayConfig>(PayConfig.class);
        return util.exportExcel(list, "payConfig");
    }

    /**
     * 新增支付配置2.0版
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存支付配置2.0版
     */
    @RequiresPermissions("pay:payConfig:add")
    @Log(title = "支付配置2.0版", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PayConfig payConfig)
    {
        payConfig.setPayConfigId(IdUtil.getSnowflake(0,0).nextIdStr());
        return toAjax(payConfigService.insertPayConfig(payConfig));
    }

    /**
     * 修改支付配置2.0版
     */
    @GetMapping("/edit/{payConfigId}")
    public String edit(@PathVariable("payConfigId") String payConfigId, ModelMap mmap)
    {
        PayConfig payConfig = payConfigService.selectPayConfigById(payConfigId);
        mmap.put("payConfig", payConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存支付配置2.0版
     */
    @RequiresPermissions("pay:payConfig:edit")
    @Log(title = "支付配置2.0版", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PayConfig payConfig)
    {
        return toAjax(payConfigService.updatePayConfig(payConfig));
    }

    /**
     * 删除支付配置2.0版
     */
    @RequiresPermissions("pay:payConfig:remove")
    @Log(title = "支付配置2.0版", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(payConfigService.deletePayConfigByIds(ids));
    }
}
