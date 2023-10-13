package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.core.domain.entity.SysUser;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.taskService.TaskFlowerService;
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
import com.wlwq.api.domain.AccountFlowerRecord;
import com.wlwq.api.service.IAccountFlowerRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 红花记录Controller
 *
 * @author gaoce
 * @date 2023-05-26
 */
@Controller
@RequestMapping("/web/accountFlowerRecord")
public class AccountFlowerRecordController extends BaseController {

    private String prefix = "web/accountFlowerRecord";

    @Autowired
    private IAccountFlowerRecordService accountFlowerRecordService;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private TaskFlowerService taskFlowerService;



    @RequiresPermissions("web:accountFlowerRecord:view")
    @GetMapping()
    public String accountFlowerRecord() {
        return prefix + "/accountFlowerRecord";
    }

    /**
     * 查询红花记录列表
     */
    @RequiresPermissions("web:accountFlowerRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AccountFlowerRecord accountFlowerRecord) {
        startPage();
        List<AccountFlowerRecord> list = accountFlowerRecordService.selectAccountFlowerRecordList(accountFlowerRecord);
        return getDataTable(list);
    }

    /**
     * 导出红花记录列表
     */
    @RequiresPermissions("web:accountFlowerRecord:export")
    @Log(title = "红花记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccountFlowerRecord accountFlowerRecord) {
        List<AccountFlowerRecord> list = accountFlowerRecordService.selectAccountFlowerRecordList(accountFlowerRecord);
        ExcelUtil<AccountFlowerRecord> util = new ExcelUtil<AccountFlowerRecord>(AccountFlowerRecord.class);
        return util.exportExcel(list, "accountFlowerRecord");
    }

    /**
     * 新增红花记录
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        // 查询用户列表
        List<ApiAccount> accountList = accountService.selectApiAccountList(ApiAccount.builder().build());
        modelMap.put("accountList", accountList);
        return prefix + "/add";
    }

    /**
     * 新增保存红花记录
     */
    @RequiresPermissions("web:accountFlowerRecord:add")
    @Log(title = "红花记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AccountFlowerRecord accountFlowerRecord) {
        if(accountFlowerRecord.getRecordNum() == 0){
            return error("请输入大于0或小于0的整数！");
        }
        // 查询当前账号属于哪个公司
        SysUser user = ShiroUtils.getSysUser();
        if(user == null){
            return error("请重新登录！");
        }
        // 赠送红花，异步操作
        AsyncManager.me().execute(taskFlowerService.managerFlower(accountFlowerRecord,user.getDeptId()));
        return toAjax(1);
    }

    /**
     * 修改红花记录
     */
    @GetMapping("/edit/{accountFlowerRecordId}")
    public String edit(@PathVariable("accountFlowerRecordId") String accountFlowerRecordId, ModelMap mmap) {
        AccountFlowerRecord accountFlowerRecord = accountFlowerRecordService.selectAccountFlowerRecordById(accountFlowerRecordId);
        mmap.put("accountFlowerRecord", accountFlowerRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存红花记录
     */
    @RequiresPermissions("web:accountFlowerRecord:edit")
    @Log(title = "红花记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AccountFlowerRecord accountFlowerRecord) {
        return toAjax(accountFlowerRecordService.updateAccountFlowerRecord(accountFlowerRecord));
    }

    /**
     * 删除红花记录
     */
    @RequiresPermissions("web:accountFlowerRecord:remove")
    @Log(title = "红花记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(accountFlowerRecordService.deleteAccountFlowerRecordByIds(ids));
    }
}
