package com.wlwq.web.controller.web;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.CustomExamineRecord;
import com.wlwq.api.service.ICustomExamineRecordService;
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
 * 客户成交Controller
 *
 * @author wlwq
 * @date 2023-06-10
 */
@Controller
@RequestMapping("/api/record")
public class CustomExamineRecordController extends BaseController {

    private String prefix = "api/record";

    @Autowired
    private ICustomExamineRecordService customExamineRecordService;

    @RequiresPermissions("api:record:view")
    @GetMapping()
    public String record() {
        return prefix + "/record";
    }

    /**
     * 查询客户成交列表
     */
    @RequiresPermissions("api:record:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CustomExamineRecord customExamineRecord) {
        startPage();
        List<CustomExamineRecord> list = customExamineRecordService.selectCustomExamineRecordList(customExamineRecord);
        return getDataTable(list);
    }

    /**
     * 导出客户成交列表
     */
    @RequiresPermissions("api:record:export")
    @Log(title = "客户成交", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomExamineRecord customExamineRecord) {
        List<CustomExamineRecord> list = customExamineRecordService.selectCustomExamineRecordList(customExamineRecord);
        ExcelUtil<CustomExamineRecord> util = new ExcelUtil<CustomExamineRecord>(CustomExamineRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 新增客户成交
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客户成交
     */
    @RequiresPermissions("api:record:add")
    @Log(title = "客户成交", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomExamineRecord customExamineRecord) {
        String l = IdUtil.getSnowflake(1, 1).nextIdStr();
        customExamineRecord.setCustomExamineRecordId(l);
        return toAjax(customExamineRecordService.insertCustomExamineRecord(customExamineRecord));
    }

    /**
     * 修改客户成交
     */
    @GetMapping("/edit/{customExamineRecordId}")
    public String edit(@PathVariable("customExamineRecordId") String customExamineRecordId, ModelMap mmap) {
        CustomExamineRecord customExamineRecord = customExamineRecordService.selectCustomExamineRecordById(customExamineRecordId);
        mmap.put("customExamineRecord", customExamineRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户成交
     */
    @RequiresPermissions("api:record:edit")
    @Log(title = "客户成交", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomExamineRecord customExamineRecord) {
        return toAjax(customExamineRecordService.updateCustomExamineRecord(customExamineRecord));
    }

    /**
     * 删除客户成交
     */
    @RequiresPermissions("api:record:remove")
    @Log(title = "客户成交", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customExamineRecordService.deleteCustomExamineRecordByIds(ids));
    }
}
