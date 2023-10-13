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
import com.wlwq.api.domain.SixStructuresReadRecord;
import com.wlwq.api.service.ISixStructuresReadRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 六大架构已读记录Controller
 *
 * @author wlwq
 * @date 2023-08-28
 */
@Controller
@RequestMapping("/web/SixStructuresReadRecord")
public class SixStructuresReadRecordController extends BaseController {

    private String prefix = "web/SixStructuresReadRecord";

    @Autowired
    private ISixStructuresReadRecordService sixStructuresReadRecordService;

    @RequiresPermissions("web:SixStructuresReadRecord:view")
    @GetMapping()
    public String SixStructuresReadRecord() {
        return prefix + "/SixStructuresReadRecord";
    }

            /**
         * 查询六大架构已读记录列表
         */
        @RequiresPermissions("web:SixStructuresReadRecord:list")
        @PostMapping("/list")
        @ResponseBody
        public TableDataInfo list(SixStructuresReadRecord sixStructuresReadRecord) {
            startPage();
            List<SixStructuresReadRecord> list = sixStructuresReadRecordService.selectSixStructuresReadRecordList(sixStructuresReadRecord);
            return getDataTable(list);
        }
    
    /**
     * 导出六大架构已读记录列表
     */
    @RequiresPermissions("web:SixStructuresReadRecord:export")
    @Log(title = "六大架构已读记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SixStructuresReadRecord sixStructuresReadRecord) {
        List<SixStructuresReadRecord> list = sixStructuresReadRecordService.selectSixStructuresReadRecordList(sixStructuresReadRecord);
        ExcelUtil<SixStructuresReadRecord> util = new ExcelUtil<SixStructuresReadRecord>(SixStructuresReadRecord.class);
        return util.exportExcel(list, "SixStructuresReadRecord");
    }

            /**
         * 新增六大架构已读记录
         */
        @GetMapping("/add")
        public String add() {
            return prefix + "/add";
        }
    
    /**
     * 新增保存六大架构已读记录
     */
    @RequiresPermissions("web:SixStructuresReadRecord:add")
    @Log(title = "六大架构已读记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SixStructuresReadRecord sixStructuresReadRecord) {
        return toAjax(sixStructuresReadRecordService.insertSixStructuresReadRecord(sixStructuresReadRecord));
    }

    /**
     * 修改六大架构已读记录
     */
    @GetMapping("/edit/{sixStructuresReadRecordId}")
    public String edit(@PathVariable("sixStructuresReadRecordId") String sixStructuresReadRecordId, ModelMap mmap) {
        SixStructuresReadRecord sixStructuresReadRecord = sixStructuresReadRecordService.selectSixStructuresReadRecordById(sixStructuresReadRecordId);
        mmap.put("sixStructuresReadRecord", sixStructuresReadRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存六大架构已读记录
     */
    @RequiresPermissions("web:SixStructuresReadRecord:edit")
    @Log(title = "六大架构已读记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SixStructuresReadRecord sixStructuresReadRecord) {
        return toAjax(sixStructuresReadRecordService.updateSixStructuresReadRecord(sixStructuresReadRecord));
    }

            /**
         * 删除六大架构已读记录
         */
        @RequiresPermissions("web:SixStructuresReadRecord:remove")
        @Log(title = "六大架构已读记录", businessType = BusinessType.DELETE)
        @PostMapping( "/remove")
        @ResponseBody
        public AjaxResult remove(String ids) {
            return toAjax(sixStructuresReadRecordService.deleteSixStructuresReadRecordByIds(ids));
        }
        }
