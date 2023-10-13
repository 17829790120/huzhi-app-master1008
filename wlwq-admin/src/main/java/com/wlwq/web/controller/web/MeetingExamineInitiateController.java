package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.service.IMeetingExamineFlowAccountService;
import com.wlwq.common.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.api.domain.MeetingExamineInitiate;
import com.wlwq.api.service.IMeetingExamineInitiateService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 会议审批发起Controller
 *
 * @author wwb
 * @date 2023-05-29
 */
@Controller
@RequestMapping("/web/meetingExamineInitiate")
public class MeetingExamineInitiateController extends BaseController {

    private String prefix = "web/meetingExamineInitiate";

    @Autowired
    private IMeetingExamineInitiateService meetingExamineInitiateService;

    @Autowired
    private IMeetingExamineFlowAccountService flowAccountService;

    @RequiresPermissions("web:meetingExamineInitiate:view")
    @GetMapping()
    public String meetingExamineInitiate() {
        return prefix + "/meetingExamineInitiate";
    }

    /**
     * 查询会议审批发起列表
     */
    @RequiresPermissions("web:meetingExamineInitiate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingExamineInitiate meetingExamineInitiate) {
        startPage();
        List<MeetingExamineInitiate> list = meetingExamineInitiateService.selectMeetingExamineInitiateList(meetingExamineInitiate);
        return getDataTable(list);
    }

    /**
     * 导出会议审批发起列表
     */
    @RequiresPermissions("web:meetingExamineInitiate:export")
    @Log(title = "会议审批发起", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MeetingExamineInitiate meetingExamineInitiate) {
        List<MeetingExamineInitiate> list = meetingExamineInitiateService.selectMeetingExamineInitiateList(meetingExamineInitiate);
        ExcelUtil<MeetingExamineInitiate> util = new ExcelUtil<MeetingExamineInitiate>(MeetingExamineInitiate.class);
        return util.exportExcel(list, "meetingExamineInitiate");
    }

    /**
     * 新增会议审批发起
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存会议审批发起
     */
    @RequiresPermissions("web:meetingExamineInitiate:add")
    @Log(title = "会议审批发起", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MeetingExamineInitiate meetingExamineInitiate) {
        return toAjax(meetingExamineInitiateService.insertMeetingExamineInitiate(meetingExamineInitiate));
    }

    /**
     * 修改会议审批发起
     */
    @GetMapping("/edit/{examineInitiateId}")
    public String edit(@PathVariable("examineInitiateId") String examineInitiateId, ModelMap mmap) {
        MeetingExamineInitiate meetingExamineInitiate = meetingExamineInitiateService.selectMeetingExamineInitiateById(examineInitiateId);
        mmap.put("meetingExamineInitiate", meetingExamineInitiate);
        return prefix + "/edit";
    }

    /**
     * 修改保存会议审批发起
     */
    @RequiresPermissions("web:meetingExamineInitiate:edit")
    @Log(title = "会议审批发起", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MeetingExamineInitiate meetingExamineInitiate) {
        return toAjax(meetingExamineInitiateService.updateMeetingExamineInitiate(meetingExamineInitiate));
    }

    /**
     * 取消会议
     */
    //@Log(title = "取消会议", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult cancel(MeetingExamineInitiate meetingExamineInitiate) {
        MeetingExamineInitiate initiate = meetingExamineInitiateService.selectMeetingExamineInitiateById(meetingExamineInitiate.getExamineInitiateId());
        if(initiate == null){
            return AjaxResult.error("会议取消失败");
        }
        long nowTime = DateUtils.getNowDate().getTime();
        if(nowTime < initiate.getMeetintBeginTime().getTime()){
            meetingExamineInitiate.setExamineStatus(6);
            int count = meetingExamineInitiateService.updateMeetingExamineInitiate(meetingExamineInitiate);
            if (count <= 0) {
                return AjaxResult.error("会议取消失败");
            }
            count = flowAccountService.deleteMeetingExamineFlowAccountByInitiateId(meetingExamineInitiate.getExamineInitiateId());
            if (count <= 0) {
                return AjaxResult.error("会议取消失败");
            }
            return AjaxResult.success("会议取消成功");
        }
        return AjaxResult.error("会议取消失败，未开始的会议可以取消。");
    }

    /**
     * 删除会议审批发起
     */
    @RequiresPermissions("web:meetingExamineInitiate:remove")
    @Log(title = "会议审批发起", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(meetingExamineInitiateService.deleteMeetingExamineInitiateByIds(ids));
    }
}
