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
import com.wlwq.api.domain.MeetingTrainingMinutes;
import com.wlwq.api.service.IMeetingTrainingMinutesService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 会议训练纪要Controller
 *
 * @author wwb
 * @date 2023-05-31
 */
@Controller
@RequestMapping("/web/meetingTrainingMinutes")
public class MeetingTrainingMinutesController extends BaseController {

    private String prefix = "web/meetingTrainingMinutes";

    @Autowired
    private IMeetingTrainingMinutesService meetingTrainingMinutesService;

    @RequiresPermissions("web:meetingTrainingMinutes:view")
    @GetMapping()
    public String meetingTrainingMinutes() {
        return prefix + "/meetingTrainingMinutes";
    }

    /**
     * 查询会议训练纪要列表
     */
    @RequiresPermissions("web:meetingTrainingMinutes:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingTrainingMinutes meetingTrainingMinutes) {
        startPage();
        List<MeetingTrainingMinutes> list = meetingTrainingMinutesService.selectMeetingTrainingMinutesList(meetingTrainingMinutes);
        return getDataTable(list);
    }

    /**
     * 导出会议训练纪要列表
     */
    @RequiresPermissions("web:meetingTrainingMinutes:export")
    @Log(title = "会议训练纪要", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MeetingTrainingMinutes meetingTrainingMinutes) {
        List<MeetingTrainingMinutes> list = meetingTrainingMinutesService.selectMeetingTrainingMinutesList(meetingTrainingMinutes);
        ExcelUtil<MeetingTrainingMinutes> util = new ExcelUtil<MeetingTrainingMinutes>(MeetingTrainingMinutes.class);
        return util.exportExcel(list, "meetingTrainingMinutes");
    }

    /**
     * 新增会议训练纪要
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存会议训练纪要
     */
    @RequiresPermissions("web:meetingTrainingMinutes:add")
    @Log(title = "会议训练纪要", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MeetingTrainingMinutes meetingTrainingMinutes) {
        return toAjax(meetingTrainingMinutesService.insertMeetingTrainingMinutes(meetingTrainingMinutes));
    }

    /**
     * 修改会议训练纪要
     */
    @GetMapping("/edit/{meetingTrainingMinutesId}")
    public String edit(@PathVariable("meetingTrainingMinutesId") String meetingTrainingMinutesId, ModelMap mmap) {
        MeetingTrainingMinutes meetingTrainingMinutes = meetingTrainingMinutesService.selectMeetingTrainingMinutesById(meetingTrainingMinutesId);
        mmap.put("meetingTrainingMinutes", meetingTrainingMinutes);
        return prefix + "/edit";
    }

    /**
     * 修改保存会议训练纪要
     */
    @RequiresPermissions("web:meetingTrainingMinutes:edit")
    @Log(title = "会议训练纪要", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MeetingTrainingMinutes meetingTrainingMinutes) {
        return toAjax(meetingTrainingMinutesService.updateMeetingTrainingMinutes(meetingTrainingMinutes));
    }

    /**
     * 删除会议训练纪要
     */
    @RequiresPermissions("web:meetingTrainingMinutes:remove")
    @Log(title = "会议训练纪要", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(meetingTrainingMinutesService.deleteMeetingTrainingMinutesByIds(ids));
    }
}
