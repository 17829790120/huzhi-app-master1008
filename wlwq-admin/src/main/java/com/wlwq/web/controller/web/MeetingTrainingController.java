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
import com.wlwq.api.domain.MeetingTraining;
import com.wlwq.api.service.IMeetingTrainingService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 会议训练Controller
 *
 * @author wwb
 * @date 2023-05-29
 */
@Controller
@RequestMapping("/web/meetingTraining")
public class MeetingTrainingController extends BaseController {

    private String prefix = "web/meetingTraining";

    @Autowired
    private IMeetingTrainingService meetingTrainingService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:meetingTraining:view")
    @GetMapping()
    public String meetingTraining() {
        return prefix + "/meetingTraining";
    }

    /**
     * 查询会议训练列表
     */
    @RequiresPermissions("web:meetingTraining:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MeetingTraining meetingTraining) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                deptId = dept.getDeptId();
                meetingTraining.setCompanyId(deptId);
            }
        }
        startPage();
        List<MeetingTraining> list = meetingTrainingService.selectMeetingTrainingList(meetingTraining);
        return getDataTable(list);
    }

    /**
     * 导出会议训练列表
     */
    @RequiresPermissions("web:meetingTraining:export")
    @Log(title = "会议训练", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MeetingTraining meetingTraining) {
        List<MeetingTraining> list = meetingTrainingService.selectMeetingTrainingList(meetingTraining);
        ExcelUtil<MeetingTraining> util = new ExcelUtil<MeetingTraining>(MeetingTraining.class);
        return util.exportExcel(list, "meetingTraining");
    }

    /**
     * 新增会议训练
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存会议训练
     */
    @RequiresPermissions("web:meetingTraining:add")
    @Log(title = "会议训练", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MeetingTraining meetingTraining) {
        return toAjax(meetingTrainingService.insertMeetingTraining(meetingTraining));
    }

    /**
     * 修改会议训练
     */
    @GetMapping("/edit/{meetingTrainingId}")
    public String edit(@PathVariable("meetingTrainingId") String meetingTrainingId, ModelMap mmap) {
        MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingById(meetingTrainingId);
        mmap.put("meetingTraining", meetingTraining);
        return prefix + "/edit";
    }

    /**
     * 修改保存会议训练
     */
    @RequiresPermissions("web:meetingTraining:edit")
    @Log(title = "会议训练", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MeetingTraining meetingTraining) {
        return toAjax(meetingTrainingService.updateMeetingTraining(meetingTraining));
    }

    /**
     * 删除会议训练
     */
    @RequiresPermissions("web:meetingTraining:remove")
    @Log(title = "会议训练", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(meetingTrainingService.deleteMeetingTrainingByIds(ids));
    }
}
