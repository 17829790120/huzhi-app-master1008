package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.ShiroUtils;
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
import com.wlwq.api.domain.WinningTrainingFollowUpRecord;
import com.wlwq.api.service.IWinningTrainingFollowUpRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 训练跟进记录Controller
 *
 * @author wwb
 * @date 2023-06-08
 */
@Controller
@RequestMapping("/web/winningTrainingFollowUpRecord")
public class WinningTrainingFollowUpRecordController extends BaseController {

    private String prefix = "web/winningTrainingFollowUpRecord";

    @Autowired
    private IWinningTrainingFollowUpRecordService winningTrainingFollowUpRecordService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:winningTrainingFollowUpRecord:view")
    @GetMapping()
    public String winningTrainingFollowUpRecord() {
        return prefix + "/winningTrainingFollowUpRecord";
    }

    /**
     * 查询训练跟进记录列表
     */
    @RequiresPermissions("web:winningTrainingFollowUpRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                winningTrainingFollowUpRecord.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<WinningTrainingFollowUpRecord> list = winningTrainingFollowUpRecordService.selectWinningTrainingFollowUpRecordList(winningTrainingFollowUpRecord);
        return getDataTable(list);
    }

    /**
     * 导出训练跟进记录列表
     */
    @RequiresPermissions("web:winningTrainingFollowUpRecord:export")
    @Log(title = "训练跟进记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        List<WinningTrainingFollowUpRecord> list = winningTrainingFollowUpRecordService.selectWinningTrainingFollowUpRecordList(winningTrainingFollowUpRecord);
        ExcelUtil<WinningTrainingFollowUpRecord> util = new ExcelUtil<WinningTrainingFollowUpRecord>(WinningTrainingFollowUpRecord.class);
        return util.exportExcel(list, "winningTrainingFollowUpRecord");
    }

    /**
     * 新增训练跟进记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存训练跟进记录
     */
    @RequiresPermissions("web:winningTrainingFollowUpRecord:add")
    @Log(title = "训练跟进记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        return toAjax(winningTrainingFollowUpRecordService.insertWinningTrainingFollowUpRecord(winningTrainingFollowUpRecord));
    }

    /**
     * 修改训练跟进记录
     */
    @GetMapping("/edit/{winningTrainingFollowUpRecordId}")
    public String edit(@PathVariable("winningTrainingFollowUpRecordId") String winningTrainingFollowUpRecordId, ModelMap mmap) {
        WinningTrainingFollowUpRecord winningTrainingFollowUpRecord = winningTrainingFollowUpRecordService.selectWinningTrainingFollowUpRecordById(winningTrainingFollowUpRecordId);
        mmap.put("winningTrainingFollowUpRecord", winningTrainingFollowUpRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存训练跟进记录
     */
    @RequiresPermissions("web:winningTrainingFollowUpRecord:edit")
    @Log(title = "训练跟进记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(WinningTrainingFollowUpRecord winningTrainingFollowUpRecord) {
        return toAjax(winningTrainingFollowUpRecordService.updateWinningTrainingFollowUpRecord(winningTrainingFollowUpRecord));
    }

    /**
     * 删除训练跟进记录
     */
    @RequiresPermissions("web:winningTrainingFollowUpRecord:remove")
    @Log(title = "训练跟进记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(winningTrainingFollowUpRecordService.deleteWinningTrainingFollowUpRecordByIds(ids));
    }
}
