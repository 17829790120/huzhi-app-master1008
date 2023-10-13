package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.AccountMedal;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IAccountMedalService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.taskService.TaskScoreService;
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
import com.wlwq.api.domain.AccountMedalRecord;
import com.wlwq.api.service.IAccountMedalRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 用户勋章记录Controller
 *
 * @author gaoce
 * @date 2023-06-09
 */
@Controller
@RequestMapping("/web/accountMedalRecord")
public class AccountMedalRecordController extends BaseController {

    private String prefix = "web/accountMedalRecord";

    @Autowired
    private IAccountMedalRecordService accountMedalRecordService;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private IAccountMedalService accountMedalService;

    @Autowired
    private TaskScoreService taskScoreService;

    @RequiresPermissions("web:accountMedalRecord:view")
    @GetMapping()
    public String accountMedalRecord() {
        return prefix + "/accountMedalRecord";
    }

    /**
     * 查询用户勋章记录列表
     */
    @RequiresPermissions("web:accountMedalRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountMedalRecord accountMedalRecord) {
        startPage();
        List<AccountMedalRecord> list = accountMedalRecordService.selectAccountMedalRecordList(accountMedalRecord);
        return getDataTable(list);
    }

    /**
     * 导出用户勋章记录列表
     */
    @RequiresPermissions("web:accountMedalRecord:export")
    @Log(title = "用户勋章记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountMedalRecord accountMedalRecord) {
        List<AccountMedalRecord> list = accountMedalRecordService.selectAccountMedalRecordList(accountMedalRecord);
        ExcelUtil<AccountMedalRecord> util = new ExcelUtil<AccountMedalRecord>(AccountMedalRecord.class);
        return util.exportExcel(list, "accountMedalRecord");
    }

    /**
     * 新增用户勋章记录
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        // 查询用户列表
        List<ApiAccount> accountList = accountService.selectApiAccountList(ApiAccount.builder().build());
        modelMap.put("accountList",accountList);
        // 查询二级勋章
        List<AccountMedal> medalList = accountMedalService.selectApiAccountNoMedalList(AccountMedal.builder().showStatus(1).build());
        modelMap.put("medalList",medalList);
        return prefix + "/add";
    }

    /**
     * 新增保存用户勋章记录
     */
    @RequiresPermissions("web:accountMedalRecord:add")
    @Log(title = "用户勋章记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountMedalRecord accountMedalRecord) {
        AccountMedal accountMedal = accountMedalService.selectAccountMedalById(accountMedalRecord.getAccountMedalId());
        if(accountMedal == null){
            return error("要赠送的勋章不存在！");
        }
        accountMedalRecord.setScore(accountMedal.getScore());
        // 赠送积分，异步操作
        AsyncManager.me().execute(taskScoreService.managerMedal(accountMedalRecord,accountMedal));
        return toAjax(1);
    }

    /**
     * 修改用户勋章记录
     */
    @GetMapping("/edit/{accountMedalRecordId}")
    public String edit(@PathVariable("accountMedalRecordId") String accountMedalRecordId, ModelMap mmap) {
        AccountMedalRecord accountMedalRecord = accountMedalRecordService.selectAccountMedalRecordById(accountMedalRecordId);
        mmap.put("accountMedalRecord", accountMedalRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户勋章记录
     */
    @RequiresPermissions("web:accountMedalRecord:edit")
    @Log(title = "用户勋章记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountMedalRecord accountMedalRecord) {
        return toAjax(accountMedalRecordService.updateAccountMedalRecord(accountMedalRecord));
    }

    /**
     * 删除用户勋章记录
     */
    @RequiresPermissions("web:accountMedalRecord:remove")
    @Log(title = "用户勋章记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountMedalRecordService.deleteAccountMedalRecordByIds(ids));
    }
}
