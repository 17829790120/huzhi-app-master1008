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
import com.wlwq.api.domain.MeetingTrainingItem;
import com.wlwq.api.service.IMeetingTrainingItemService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 会议训练流程事项Controller
 *
 * @author wwb
 * @date 2023-05-29
 */
@Controller
@RequestMapping("/web/meetingTrainingItem")
public class MeetingTrainingItemController extends BaseController {

    private String prefix = "web/meetingTrainingItem";

    @Autowired
    private IMeetingTrainingItemService meetingTrainingItemService;

    @RequiresPermissions("web:meetingTrainingItem:view")
    @GetMapping()
    public String meetingTrainingItem() {
        return prefix + "/meetingTrainingItem";
    }

    /**
     * 查询会议训练流程事项列表
     */
    @RequiresPermissions("web:meetingTrainingItem:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingTrainingItem meetingTrainingItem) {
        startPage();
        List<MeetingTrainingItem> list = meetingTrainingItemService.selectMeetingTrainingItemList(meetingTrainingItem);
        return getDataTable(list);
    }

    /**
     * 查询会议训练流程事项列表
     * examPaperRecordId 考试试卷记录表id
     */
    @GetMapping("/listByMeetingTrainingId")
    public String listByMeetingTrainingId(ModelMap modelMap,String meetingTrainingId) {
        modelMap.put("meetingTrainingId",meetingTrainingId);
        return prefix + "/meetingTrainingItem";
    }

    /**
     * 导出会议训练流程事项列表
     */
    @RequiresPermissions("web:meetingTrainingItem:export")
    @Log(title = "会议训练流程事项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MeetingTrainingItem meetingTrainingItem) {
        List<MeetingTrainingItem> list = meetingTrainingItemService.selectMeetingTrainingItemList(meetingTrainingItem);
        ExcelUtil<MeetingTrainingItem> util = new ExcelUtil<MeetingTrainingItem>(MeetingTrainingItem.class);
        return util.exportExcel(list, "meetingTrainingItem");
    }

    /**
     * 新增会议训练流程事项
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存会议训练流程事项
     */
    @RequiresPermissions("web:meetingTrainingItem:add")
    @Log(title = "会议训练流程事项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MeetingTrainingItem meetingTrainingItem) {
        return toAjax(meetingTrainingItemService.insertMeetingTrainingItem(meetingTrainingItem));
    }

    /**
     * 修改会议训练流程事项
     */
    @GetMapping("/edit/{meetingTrainingItemId}")
    public String edit(@PathVariable("meetingTrainingItemId") String meetingTrainingItemId, ModelMap mmap) {
        MeetingTrainingItem meetingTrainingItem = meetingTrainingItemService.selectMeetingTrainingItemById(meetingTrainingItemId);
        mmap.put("meetingTrainingItem", meetingTrainingItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存会议训练流程事项
     */
    @RequiresPermissions("web:meetingTrainingItem:edit")
    @Log(title = "会议训练流程事项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MeetingTrainingItem meetingTrainingItem) {
        return toAjax(meetingTrainingItemService.updateMeetingTrainingItem(meetingTrainingItem));
    }

    /**
     * 删除会议训练流程事项
     */
    @RequiresPermissions("web:meetingTrainingItem:remove")
    @Log(title = "会议训练流程事项", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(meetingTrainingItemService.deleteMeetingTrainingItemByIds(ids));
    }
}
