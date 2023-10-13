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
import com.wlwq.api.domain.AccountSignRecord;
import com.wlwq.api.service.IAccountSignRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 用户签到记录Controller
 *
 * @author gaoce
 * @date 2023-07-03
 */
@Controller
@RequestMapping("/web/accountSignRecord")
public class AccountSignRecordController extends BaseController {

    private String prefix = "web/accountSignRecord";

    @Autowired
    private IAccountSignRecordService accountSignRecordService;

    @RequiresPermissions("web:accountSignRecord:view")
    @GetMapping()
    public String accountSignRecord() {
        return prefix + "/accountSignRecord";
    }

    /**
     * 查询用户签到记录列表
     */
    @RequiresPermissions("web:accountSignRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountSignRecord accountSignRecord) {
        startPage();
        List<AccountSignRecord> list = accountSignRecordService.selectAccountSignRecordList(accountSignRecord);
        return getDataTable(list);
    }

    /**
     * 导出用户签到记录列表
     */
    @RequiresPermissions("web:accountSignRecord:export")
    @Log(title = "用户签到记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountSignRecord accountSignRecord) {
        List<AccountSignRecord> list = accountSignRecordService.selectAccountSignRecordList(accountSignRecord);
        ExcelUtil<AccountSignRecord> util = new ExcelUtil<AccountSignRecord>(AccountSignRecord.class);
        return util.exportExcel(list, "accountSignRecord");
    }

    /**
     * 新增用户签到记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户签到记录
     */
    @RequiresPermissions("web:accountSignRecord:add")
    @Log(title = "用户签到记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountSignRecord accountSignRecord) {
        return toAjax(accountSignRecordService.insertAccountSignRecord(accountSignRecord));
    }

    /**
     * 修改用户签到记录
     */
    @GetMapping("/edit/{accountSignRecordId}")
    public String edit(@PathVariable("accountSignRecordId") String accountSignRecordId, ModelMap mmap) {
        AccountSignRecord accountSignRecord = accountSignRecordService.selectAccountSignRecordById(accountSignRecordId);
        mmap.put("accountSignRecord", accountSignRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户签到记录
     */
    @RequiresPermissions("web:accountSignRecord:edit")
    @Log(title = "用户签到记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountSignRecord accountSignRecord) {
        return toAjax(accountSignRecordService.updateAccountSignRecord(accountSignRecord));
    }

    /**
     * 删除用户签到记录
     */
    @RequiresPermissions("web:accountSignRecord:remove")
    @Log(title = "用户签到记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountSignRecordService.deleteAccountSignRecordByIds(ids));
    }
}
