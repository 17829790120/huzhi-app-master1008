package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.system.service.ISysDeptService;
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
import com.wlwq.api.domain.AccountClocking;
import com.wlwq.api.service.IAccountClockingService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 用户考勤Controller
 *
 * @author gaoce
 * @date 2023-06-16
 */
@Controller
@RequestMapping("/web/accountClocking")
public class AccountClockingController extends BaseController {

    private String prefix = "web/accountClocking";

    @Autowired
    private IAccountClockingService accountClockingService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:accountClocking:view")
    @GetMapping()
    public String accountClocking(ModelMap mmap) {
        // 查询公司信息
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        dept.setTag(1);
        List<SysDept> companyList = deptService.selectDeptList(dept);
        mmap.put("companyList", companyList);
        return prefix + "/accountClocking";
    }

    /**
     * 查询用户考勤列表
     */
    @RequiresPermissions("web:accountClocking:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountClocking accountClocking) {
        startPage();
        List<AccountClocking> list = accountClockingService.selectAccountClockingList(accountClocking);
        return getDataTable(list);
    }

    /**
     * 导出用户考勤列表
     */
    @RequiresPermissions("web:accountClocking:export")
    @Log(title = "用户考勤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountClocking accountClocking) {
        List<AccountClocking> list = accountClockingService.selectAccountClockingList(accountClocking);
        ExcelUtil<AccountClocking> util = new ExcelUtil<AccountClocking>(AccountClocking.class);
        return util.exportExcel(list, "accountClocking");
    }

    /**
     * 新增用户考勤
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户考勤
     */
    @RequiresPermissions("web:accountClocking:add")
    @Log(title = "用户考勤", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountClocking accountClocking) {
        return toAjax(accountClockingService.insertAccountClocking(accountClocking));
    }

    /**
     * 修改用户考勤
     */
    @GetMapping("/edit/{accountClockingId}")
    public String edit(@PathVariable("accountClockingId") String accountClockingId, ModelMap mmap) {
        AccountClocking accountClocking = accountClockingService.selectAccountClockingById(accountClockingId);
        mmap.put("accountClocking", accountClocking);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户考勤
     */
    @RequiresPermissions("web:accountClocking:edit")
    @Log(title = "用户考勤", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountClocking accountClocking) {
        return toAjax(accountClockingService.updateAccountClocking(accountClocking));
    }

    /**
     * 删除用户考勤
     */
    @RequiresPermissions("web:accountClocking:remove")
    @Log(title = "用户考勤", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountClockingService.deleteAccountClockingByIds(ids));
    }
}
