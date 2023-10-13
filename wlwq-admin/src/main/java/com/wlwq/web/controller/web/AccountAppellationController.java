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
import com.wlwq.api.domain.AccountAppellation;
import com.wlwq.api.service.IAccountAppellationService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 用户称谓Controller
 *
 * @author gaoce
 * @date 2023-06-08
 */
@Controller
@RequestMapping("/web/accountAppellation")
public class AccountAppellationController extends BaseController {

    private String prefix = "web/accountAppellation";

    @Autowired
    private IAccountAppellationService accountAppellationService;

    @RequiresPermissions("web:accountAppellation:view")
    @GetMapping()
    public String accountAppellation() {
        return prefix + "/accountAppellation";
    }

    /**
     * 查询用户称谓列表
     */
    @RequiresPermissions("web:accountAppellation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountAppellation accountAppellation) {
        startPage();
        List<AccountAppellation> list = accountAppellationService.selectAccountAppellationList(accountAppellation);
        return getDataTable(list);
    }

    /**
     * 导出用户称谓列表
     */
    @RequiresPermissions("web:accountAppellation:export")
    @Log(title = "用户称谓", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountAppellation accountAppellation) {
        List<AccountAppellation> list = accountAppellationService.selectAccountAppellationList(accountAppellation);
        ExcelUtil<AccountAppellation> util = new ExcelUtil<AccountAppellation>(AccountAppellation.class);
        return util.exportExcel(list, "accountAppellation");
    }

    /**
     * 新增用户称谓
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户称谓
     */
    @RequiresPermissions("web:accountAppellation:add")
    @Log(title = "用户称谓", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountAppellation accountAppellation) {
        return toAjax(accountAppellationService.insertAccountAppellation(accountAppellation));
    }

    /**
     * 修改用户称谓
     */
    @GetMapping("/edit/{accountAppellationId}")
    public String edit(@PathVariable("accountAppellationId") String accountAppellationId, ModelMap mmap) {
        AccountAppellation accountAppellation = accountAppellationService.selectAccountAppellationById(accountAppellationId);
        mmap.put("accountAppellation", accountAppellation);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户称谓
     */
    @RequiresPermissions("web:accountAppellation:edit")
    @Log(title = "用户称谓", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountAppellation accountAppellation) {
        return toAjax(accountAppellationService.updateAccountAppellation(accountAppellation));
    }

    /**
     * 删除用户称谓
     */
    @RequiresPermissions("web:accountAppellation:remove")
    @Log(title = "用户称谓", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountAppellationService.deleteAccountAppellationByIds(ids));
    }
}
