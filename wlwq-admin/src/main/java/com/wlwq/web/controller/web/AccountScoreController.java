package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.ApiAccount;
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
import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.service.IAccountScoreService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 用户积分Controller
 *
 * @author gaoce
 * @date 2023-06-06
 */
@Controller
@RequestMapping("/web/accountScore")
public class AccountScoreController extends BaseController {

    private String prefix = "web/accountScore";

    @Autowired
    private IAccountScoreService accountScoreService;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private TaskScoreService taskScoreService;

    @RequiresPermissions("web:accountScore:view")
    @GetMapping()
    public String accountScore() {
        return prefix + "/accountScore";
    }

    /**
     * 查询用户积分列表
     */
    @RequiresPermissions("web:accountScore:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountScore accountScore) {
        startPage();
        List<AccountScore> list = accountScoreService.selectAccountScoreList(accountScore);
        return getDataTable(list);
    }

    /**
     * 导出用户积分列表
     */
    @RequiresPermissions("web:accountScore:export")
    @Log(title = "用户积分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountScore accountScore) {
        List<AccountScore> list = accountScoreService.selectAccountScoreList(accountScore);
        ExcelUtil<AccountScore> util = new ExcelUtil<AccountScore>(AccountScore.class);
        return util.exportExcel(list, "accountScore");
    }

    /**
     * 新增用户积分
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        // 查询用户列表
        List<ApiAccount> accountList = accountService.selectApiAccountList(ApiAccount.builder().build());
        modelMap.put("accountList",accountList);
        return prefix + "/add";
    }

    /**
     * 新增保存用户积分
     */
    @RequiresPermissions("web:accountScore:add")
    @Log(title = "用户积分", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountScore accountScore) {
        // 赠送积分，异步操作
        AsyncManager.me().execute(taskScoreService.managerScore(accountScore));
        return toAjax(1);
    }

    /**
     * 修改用户积分
     */
    @GetMapping("/edit/{accountScoreId}")
    public String edit(@PathVariable("accountScoreId") String accountScoreId, ModelMap mmap) {
        AccountScore accountScore = accountScoreService.selectAccountScoreById(accountScoreId);
        mmap.put("accountScore", accountScore);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户积分
     */
    @RequiresPermissions("web:accountScore:edit")
    @Log(title = "用户积分", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountScore accountScore) {
        return toAjax(accountScoreService.updateAccountScore(accountScore));
    }

    /**
     * 删除用户积分
     */
    @RequiresPermissions("web:accountScore:remove")
    @Log(title = "用户积分", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountScoreService.deleteAccountScoreByIds(ids));
    }
}
