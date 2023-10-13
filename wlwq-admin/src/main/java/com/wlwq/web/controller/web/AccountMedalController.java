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
import com.wlwq.api.domain.AccountMedal;
import com.wlwq.api.service.IAccountMedalService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 用户勋章Controller
 *
 * @author gaoce
 * @date 2023-06-08
 */
@Controller
@RequestMapping("/web/accountMedal")
public class AccountMedalController extends BaseController {

    private String prefix = "web/accountMedal";

    @Autowired
    private IAccountMedalService accountMedalService;

    @RequiresPermissions("web:accountMedal:view")
    @GetMapping()
    public String accountMedal() {
        return prefix + "/accountMedal";
    }

    /**
     * 查询用户勋章树列表
     */
    @RequiresPermissions("web:accountMedal:list")
    @PostMapping("/list")
    @ResponseBody
    public List<AccountMedal> list(AccountMedal accountMedal) {
        List<AccountMedal> list = accountMedalService.selectAccountMedalList(accountMedal);
        return list;
    }

    /**
     * 导出用户勋章列表
     */
    @RequiresPermissions("web:accountMedal:export")
    @Log(title = "用户勋章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountMedal accountMedal) {
        List<AccountMedal> list = accountMedalService.selectAccountMedalList(accountMedal);
        ExcelUtil<AccountMedal> util = new ExcelUtil<AccountMedal>(AccountMedal.class);
        return util.exportExcel(list, "accountMedal");
    }

    /**
     * 新增用户勋章
     */
    @GetMapping(value = {"/add/{accountMedalId}", "/add/"})
    public String add(@PathVariable(value = "accountMedalId", required = false) Long accountMedalId, ModelMap mmap) {
        if (StringUtils.isNotNull(accountMedalId)) {
            mmap.put("accountMedal", accountMedalService.selectAccountMedalById(accountMedalId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存用户勋章
     */
    @RequiresPermissions("web:accountMedal:add")
    @Log(title = "用户勋章", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountMedal accountMedal) {
        return toAjax(accountMedalService.insertAccountMedal(accountMedal));
    }

    /**
     * 修改用户勋章
     */
    @GetMapping("/edit/{accountMedalId}")
    public String edit(@PathVariable("accountMedalId") Long accountMedalId, ModelMap mmap) {
        AccountMedal accountMedal = accountMedalService.selectAccountMedalById(accountMedalId);
        mmap.put("accountMedal", accountMedal);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户勋章
     */
    @RequiresPermissions("web:accountMedal:edit")
    @Log(title = "用户勋章", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountMedal accountMedal) {
        return toAjax(accountMedalService.updateAccountMedal(accountMedal));
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:accountMedal:remove")
    @Log(title = "用户勋章", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{accountMedalId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("accountMedalId") Long accountMedalId) {
        return toAjax(accountMedalService.deleteAccountMedalById(accountMedalId));
    }

    /**
     * 选择用户勋章树
     */
    @GetMapping(value = {"/selectAccountMedalTree/{accountMedalId}", "/selectAccountMedalTree/"})
    public String selectAccountMedalTree(@PathVariable(value = "accountMedalId", required = false) Long accountMedalId, ModelMap mmap) {
        if (StringUtils.isNotNull(accountMedalId)) {
            mmap.put("accountMedal", accountMedalService.selectAccountMedalById(accountMedalId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载用户勋章树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = accountMedalService.selectAccountMedalTree();
        return ztrees;
    }
}
