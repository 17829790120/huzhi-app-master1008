package com.wlwq.information.controller;

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
import com.wlwq.information.domain.InformationSetting;
import com.wlwq.information.service.IInformationSettingService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 资讯设置Controller
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Controller
@RequestMapping("/information/informationSetting")
public class InformationSettingWebController extends BaseController
{
    private String prefix = "information/informationSetting";

    @Autowired
    private IInformationSettingService informationSettingService;

    @RequiresPermissions("information:informationSetting:view")
    @GetMapping()
    public String informationSetting()
    {
        return prefix + "/informationSetting";
    }

    /**
     * 查询资讯设置列表
     */
    @RequiresPermissions("information:informationSetting:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InformationSetting informationSetting)
    {
        startPage();
        List<InformationSetting> list = informationSettingService.selectInformationSettingList(informationSetting);
        return getDataTable(list);
    }

    /**
     * 导出资讯设置列表
     */
    @RequiresPermissions("information:informationSetting:export")
    @Log(title = "资讯设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InformationSetting informationSetting)
    {
        List<InformationSetting> list = informationSettingService.selectInformationSettingList(informationSetting);
        ExcelUtil<InformationSetting> util = new ExcelUtil<InformationSetting>(InformationSetting.class);
        return util.exportExcel(list, "informationSetting");
    }

    /**
     * 新增资讯设置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存资讯设置
     */
    @RequiresPermissions("information:informationSetting:add")
    @Log(title = "资讯设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InformationSetting informationSetting)
    {
        return toAjax(informationSettingService.insertInformationSetting(informationSetting));
    }

    /**
     * 修改资讯设置
     */
    @GetMapping("/edit/{informationSettingId}")
    public String edit(@PathVariable("informationSettingId") Long informationSettingId, ModelMap mmap)
    {
        InformationSetting informationSetting = informationSettingService.selectInformationSettingById(informationSettingId);
        mmap.put("informationSetting", informationSetting);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯设置
     */
    @RequiresPermissions("information:informationSetting:edit")
    @Log(title = "资讯设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InformationSetting informationSetting)
    {
        return toAjax(informationSettingService.updateInformationSetting(informationSetting));
    }

    /**
     * 删除资讯设置
     */
    @RequiresPermissions("information:informationSetting:remove")
    @Log(title = "资讯设置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(informationSettingService.deleteInformationSettingByIds(ids));
    }
}
