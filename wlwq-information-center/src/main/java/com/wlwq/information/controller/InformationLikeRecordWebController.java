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
import com.wlwq.information.domain.InformationLikeRecord;
import com.wlwq.information.service.IInformationLikeRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 点赞记录Controller
 * 
 * @author Rick wlwq
 * @date 2021-04-20
 */
@Controller
@RequestMapping("/information/informationLikeRecord")
public class InformationLikeRecordWebController extends BaseController
{
    private String prefix = "information/informationLikeRecord";

    @Autowired
    private IInformationLikeRecordService informationLikeRecordService;

    @RequiresPermissions("information:informationLikeRecord:view")
    @GetMapping()
    public String informationLikeRecord(Integer likeType,Long primaryId,ModelMap modelMap)
    {
        modelMap.put("primaryId",primaryId);
        modelMap.put("likeType",likeType);
        return prefix + "/informationLikeRecord";
    }

    /**
     * 查询点赞记录列表
     */
    @RequiresPermissions("information:informationLikeRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InformationLikeRecord informationLikeRecord)
    {
        startPage();
        List<InformationLikeRecord> list = informationLikeRecordService.selectInformationLikeRecordList(informationLikeRecord);
        return getDataTable(list);
    }

    /**
     * 导出点赞记录列表
     */
    @RequiresPermissions("information:informationLikeRecord:export")
    @Log(title = "点赞记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InformationLikeRecord informationLikeRecord)
    {
        List<InformationLikeRecord> list = informationLikeRecordService.selectInformationLikeRecordList(informationLikeRecord);
        ExcelUtil<InformationLikeRecord> util = new ExcelUtil<InformationLikeRecord>(InformationLikeRecord.class);
        return util.exportExcel(list, "informationLikeRecord");
    }

    /**
     * 新增点赞记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存点赞记录
     */
    @RequiresPermissions("information:informationLikeRecord:add")
    @Log(title = "点赞记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InformationLikeRecord informationLikeRecord)
    {
        return toAjax(informationLikeRecordService.insertInformationLikeRecord(informationLikeRecord));
    }

    /**
     * 修改点赞记录
     */
    @GetMapping("/edit/{informationLikeRecordId}")
    public String edit(@PathVariable("informationLikeRecordId") Long informationLikeRecordId, ModelMap mmap)
    {
        InformationLikeRecord informationLikeRecord = informationLikeRecordService.selectInformationLikeRecordById(informationLikeRecordId);
        mmap.put("informationLikeRecord", informationLikeRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存点赞记录
     */
    @RequiresPermissions("information:informationLikeRecord:edit")
    @Log(title = "点赞记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InformationLikeRecord informationLikeRecord)
    {
        return toAjax(informationLikeRecordService.updateInformationLikeRecord(informationLikeRecord));
    }

    /**
     * 删除点赞记录
     */
    @RequiresPermissions("information:informationLikeRecord:remove")
    @Log(title = "点赞记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(informationLikeRecordService.deleteInformationLikeRecordByIds(ids));
    }
}
