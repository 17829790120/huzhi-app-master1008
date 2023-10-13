package com.wlwq.web.controller.web;

import com.wlwq.api.domain.FourRelationshipsReadRecord;
import com.wlwq.api.service.IFourRelationshipsReadRecordService;
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
 * 四类关系已读记录Controller
 *
 * @author dxy
 */
@Controller
@RequestMapping("/web/FourRelationshipsReadRecord")
public class FourRelationshipsReadRecordController extends BaseController {

    private String prefix = "web/FourRelationshipsReadRecord";

    @Autowired
    private IFourRelationshipsReadRecordService fourRelationshipsReadRecordService;

    @RequiresPermissions("web:FourRelationshipsReadRecord:view")
    @GetMapping()
    public String FourRelationshipsReadRecord() {
        return prefix + "/FourRelationshipsReadRecord";
    }

            /**
         * 查询四类关系已读记录列表
         */
        @RequiresPermissions("web:FourRelationshipsReadRecord:list")
        @PostMapping("/list")
        @ResponseBody
        public TableDataInfo list(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
            startPage();
            List<FourRelationshipsReadRecord> list = fourRelationshipsReadRecordService.selectFourRelationshipsReadRecordList(fourRelationshipsReadRecord);
            return getDataTable(list);
        }
    
    /**
     * 导出四类关系已读记录列表
     */
    @RequiresPermissions("web:FourRelationshipsReadRecord:export")
    @Log(title = "六大架构已读记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        List<FourRelationshipsReadRecord> list = fourRelationshipsReadRecordService.selectFourRelationshipsReadRecordList(fourRelationshipsReadRecord);
        ExcelUtil<FourRelationshipsReadRecord> util = new ExcelUtil<FourRelationshipsReadRecord>(FourRelationshipsReadRecord.class);
        return util.exportExcel(list, "FourRelationshipsReadRecord");
    }

    /**
     * 新增四类关系已读记录
     */
    @GetMapping("/add")
    public String add() {
            return prefix + "/add";
        }
    
    /**
     * 新增保存四类关系已读记录
     */
    @RequiresPermissions("web:FourRelationshipsReadRecord:add")
    @Log(title = "四类关系已读记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        return toAjax(fourRelationshipsReadRecordService.insertFourRelationshipsReadRecord(fourRelationshipsReadRecord));
    }

    /**
     * 修改四类关系已读记录
     */
    @GetMapping("/edit/{fourRelationshipsReadRecordId}")
    public String edit(@PathVariable("fourRelationshipsReadRecordId") String fourRelationshipsReadRecordId, ModelMap mmap) {
        FourRelationshipsReadRecord fourRelationshipsReadRecord = fourRelationshipsReadRecordService.selectFourRelationshipsReadRecordById(fourRelationshipsReadRecordId);
        mmap.put("fourRelationshipsReadRecord", fourRelationshipsReadRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存四类关系已读记录
     */
    @RequiresPermissions("web:FourRelationshipsReadRecord:edit")
    @Log(title = "四类关系已读记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FourRelationshipsReadRecord fourRelationshipsReadRecord) {
        return toAjax(fourRelationshipsReadRecordService.updateFourRelationshipsReadRecord(fourRelationshipsReadRecord));
    }

    /**
     * 删除四类关系已读记录
     */
    @RequiresPermissions("web:FourRelationshipsReadRecord:remove")
    @Log(title = "四类关系已读记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(fourRelationshipsReadRecordService.deleteFourRelationshipsReadRecordByIds(ids));
    }
}
