package com.wlwq.web.controller.web;

import com.wlwq.api.domain.AccountClocking;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.clocking.AccountClockingResultVO;
import com.wlwq.api.service.IAccountClockingService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author gaoce
 * 考勤统计
 */
@Controller
@RequestMapping("/web/accountClockingStatistics")
public class AccountClockingStatisticsController extends BaseController {

    private String prefix = "web/accountClockingStatistics";

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:accountClockingStatistics:view")
    @GetMapping()
    public String accountClockingStatistics(ModelMap mmap) {
        // 查询公司信息
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        dept.setTag(1);
        List<SysDept> companyList = deptService.selectDeptList(dept);
        mmap.put("companyList", companyList);
        return prefix + "/accountClockingStatistics";
    }

    /**
     * 查询用户考勤统计列表
     */
    @RequiresPermissions("web:accountClockingStatistics:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ApiAccount account) {
        startPage();
        List<AccountClockingResultVO> list = accountService.selectApiClockingAccountList(account);
        return getDataTable(list);
    }

    /**
     * 导出用户考勤统计列表
     */
    @RequiresPermissions("web:accountClockingStatistics:export")
    @Log(title = "用户考勤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ApiAccount account) {
        List<AccountClockingResultVO> list = accountService.selectApiClockingAccountList(account);
        ExcelUtil<AccountClockingResultVO> util = new ExcelUtil<AccountClockingResultVO>(AccountClockingResultVO.class);
        return util.exportExcel(list, "考勤统计");
    }
}
