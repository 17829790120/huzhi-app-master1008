package com.wlwq.information.controller;

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
import com.wlwq.information.domain.InformationBrowseRecord;
import com.wlwq.information.service.IInformationBrowseRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 资讯浏览记录Controller
 *
 * @author Rick wlwq
 * @date 2021-04-20
 */
@Controller
@RequestMapping("/information/informationBrowseRecord")
public class InformationBrowseRecordWebController extends BaseController {
    private String prefix = "information/informationBrowseRecord";

    @Autowired
    private IInformationBrowseRecordService informationBrowseRecordService;

    @RequiresPermissions("information:informationBrowseRecord:view")
    @GetMapping()
    public String informationBrowseRecord(Long informationPostId, ModelMap modelMap) {
        modelMap.put("informationPostId", informationPostId);
        return prefix + "/informationBrowseRecord";
    }

    /**
     * 查询资讯浏览记录列表
     */
    @RequiresPermissions("information:informationBrowseRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InformationBrowseRecord informationBrowseRecord) {
        List<InformationBrowseRecord> list = null;
        if (informationBrowseRecord.getInformationType() == 1) {
            list = informationBrowseRecordService.selectInformationBrowseRecordList(informationBrowseRecord);
        } else if (informationBrowseRecord.getInformationType() == 2) {
            list = informationBrowseRecordService.selectInformationBrowseRecordNewsCenterList(informationBrowseRecord);
        }
        startPage();
        return getDataTable(list);
    }

    /**
     * 导出资讯浏览记录列表
     */
    @RequiresPermissions("information:informationBrowseRecord:export")
    @Log(title = "资讯浏览记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InformationBrowseRecord informationBrowseRecord) {
        List<InformationBrowseRecord> list = informationBrowseRecordService.selectInformationBrowseRecordList(informationBrowseRecord);
        ExcelUtil<InformationBrowseRecord> util = new ExcelUtil<InformationBrowseRecord>(InformationBrowseRecord.class);
        return util.exportExcel(list, "informationBrowseRecord");
    }

    /**
     * 新增资讯浏览记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存资讯浏览记录
     */
    @RequiresPermissions("information:informationBrowseRecord:add")
    @Log(title = "资讯浏览记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InformationBrowseRecord informationBrowseRecord) {
        return toAjax(informationBrowseRecordService.insertInformationBrowseRecord(informationBrowseRecord));
    }

    /**
     * 修改资讯浏览记录
     */
    @GetMapping("/edit/{informationBrowseRecordId}")
    public String edit(@PathVariable("informationBrowseRecordId") Long informationBrowseRecordId, ModelMap mmap) {
        InformationBrowseRecord informationBrowseRecord = informationBrowseRecordService.selectInformationBrowseRecordById(informationBrowseRecordId);
        mmap.put("informationBrowseRecord", informationBrowseRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯浏览记录
     */
    @RequiresPermissions("information:informationBrowseRecord:edit")
    @Log(title = "资讯浏览记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InformationBrowseRecord informationBrowseRecord) {
        return toAjax(informationBrowseRecordService.updateInformationBrowseRecord(informationBrowseRecord));
    }

    /**
     * 删除资讯浏览记录
     */
    @RequiresPermissions("information:informationBrowseRecord:remove")
    @Log(title = "资讯浏览记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(informationBrowseRecordService.deleteInformationBrowseRecordByIds(ids));
    }
}
