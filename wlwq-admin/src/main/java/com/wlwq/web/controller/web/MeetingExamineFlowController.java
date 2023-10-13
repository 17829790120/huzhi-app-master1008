package com.wlwq.web.controller.web;

import java.util.List;
import com.wlwq.api.domain.ExamineModule;
import com.wlwq.api.service.IExamineModuleService;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.system.service.ISysPostService;
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
import com.wlwq.api.domain.MeetingExamineFlow;
import com.wlwq.api.service.IMeetingExamineFlowService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 会议审批流程Controller
 *
 * @author wlwq
 * @date 2023-05-29
 */
@Controller
@RequestMapping("/web/meetingExamineFlow")
public class MeetingExamineFlowController extends BaseController {

    private String prefix = "web/meetingExamineFlow";

    @Autowired
    private IMeetingExamineFlowService meetingExamineFlowService;

    @Autowired
    private IExamineModuleService examineModuleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:meetingExamineFlow:view")
    @GetMapping()
    public String meetingExamineFlow(ModelMap modelMap) {
        // 审批模板
        modelMap.put("moduleList",examineModuleService.selectMeetingExamineModuleList(ExamineModule.builder().tag(0).showStatus(1).build()));
        return prefix + "/meetingExamineFlow";
    }

    /**
     * 查询会议审批流程列表
     */
    @RequiresPermissions("web:meetingExamineFlow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingExamineFlow meetingExamineFlow) {
        startPage();
        List<MeetingExamineFlow> list = meetingExamineFlowService.selectMeetingExamineFlowList(meetingExamineFlow);
        return getDataTable(list);
    }

    /**
     * 导出会议审批流程列表
     */
    @RequiresPermissions("web:meetingExamineFlow:export")
    @Log(title = "会议审批流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MeetingExamineFlow meetingExamineFlow) {
        List<MeetingExamineFlow> list = meetingExamineFlowService.selectMeetingExamineFlowList(meetingExamineFlow);
        ExcelUtil<MeetingExamineFlow> util = new ExcelUtil<MeetingExamineFlow>(MeetingExamineFlow.class);
        return util.exportExcel(list, "meetingExamineFlow");
    }

    /**
     * 新增会议审批流程
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        // 审批模板
        modelMap.put("moduleList",examineModuleService.selectMeetingExamineModuleList(ExamineModule.builder().tag(0).showStatus(1).build()));
        return prefix + "/add";
    }

    /**
     * 新增保存会议审批流程
     */
    @RequiresPermissions("web:meetingExamineFlow:add")
    @Log(title = "会议审批流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MeetingExamineFlow meetingExamineFlow) {
        // 查询所属的公司
        SysDept dept = deptService.selectDeptById(meetingExamineFlow.getDeptId());
        meetingExamineFlow.setCompanyId(dept == null ? 0L : dept.getParentId());
        if(meetingExamineFlow.getExamineModuleId() !=null && meetingExamineFlow.getExamineModuleId()==25){
            List<MeetingExamineFlow> list = meetingExamineFlowService.selectMeetingExamineFlowList(MeetingExamineFlow
                    .builder()
                    .examineModuleId(25L)
                    .companyId(meetingExamineFlow.getCompanyId())
                    .build());
            if(list != null && list.size()>0){
                return AjaxResult.error("领导评价只能添加一级评价。");
            }
        }
        return toAjax(meetingExamineFlowService.insertMeetingExamineFlow(meetingExamineFlow));
    }

    /**
     * 修改会议审批流程
     */
    @GetMapping("/edit/{meetingExamineFlowId}")
    public String edit(@PathVariable("meetingExamineFlowId") String meetingExamineFlowId, ModelMap mmap) {
        MeetingExamineFlow meetingExamineFlow = meetingExamineFlowService.selectMeetingExamineFlowById(meetingExamineFlowId);
        mmap.put("meetingExamineFlow", meetingExamineFlow);
        // 审批模板
        mmap.put("moduleList",examineModuleService.selectMeetingExamineModuleList(ExamineModule.builder().tag(0).showStatus(1).build()));
        return prefix + "/edit";
    }

    /**
     * 修改保存会议审批流程
     */
    @RequiresPermissions("web:meetingExamineFlow:edit")
    @Log(title = "会议审批流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MeetingExamineFlow meetingExamineFlow) {
        // 查询所属的公司
        SysDept dept = deptService.selectDeptById(meetingExamineFlow.getDeptId());
        meetingExamineFlow.setCompanyId(dept == null ? 0L : dept.getParentId());

/*        if(meetingExamineFlow.getExamineModuleId() !=null && meetingExamineFlow.getExamineModuleId()==25){
            List<MeetingExamineFlow> list = meetingExamineFlowService.selectMeetingExamineFlowList(MeetingExamineFlow
                    .builder()
                    .examineModuleId(25L)
                    .companyId(meetingExamineFlow.getCompanyId())
                    .build());
            if(list != null && list.size()>0){
                return AjaxResult.error("领导评价只能添加一级评价。");
            }
        }*/
        return toAjax(meetingExamineFlowService.updateMeetingExamineFlow(meetingExamineFlow));
    }

    /**
     * 删除会议审批流程
     */
    @RequiresPermissions("web:meetingExamineFlow:remove")
    @Log(title = "会议审批流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(meetingExamineFlowService.deleteMeetingExamineFlowByIds(ids));
    }
}
