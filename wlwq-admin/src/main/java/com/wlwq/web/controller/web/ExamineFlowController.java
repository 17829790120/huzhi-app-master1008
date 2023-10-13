package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.ExamineModule;
import com.wlwq.api.service.IExamineModuleService;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.system.domain.SysPost;
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
import com.wlwq.api.domain.ExamineFlow;
import com.wlwq.api.service.IExamineFlowService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 审批流程Controller
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Controller
@RequestMapping("/web/examineFlow")
public class ExamineFlowController extends BaseController {

    private String prefix = "web/examineFlow";

    @Autowired
    private IExamineFlowService examineFlowService;

    @Autowired
    private IExamineModuleService examineModuleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:examineFlow:view")
    @GetMapping()
    public String examineFlow(ModelMap modelMap) {
        // 审批模板
        modelMap.put("moduleList",examineModuleService.selectExamineModuleList(ExamineModule.builder().tag(0).showStatus(1).build()));
        // 审批岗位
        SysPost sysPost = new SysPost();
        sysPost.setStatus("0");
        modelMap.put("postList",postService.selectPostList(sysPost));
        return prefix + "/examineFlow";
    }

    /**
     * 查询审批流程列表
     */
    @RequiresPermissions("web:examineFlow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExamineFlow examineFlow) {
        startPage();
        List<ExamineFlow> list = examineFlowService.selectExamineFlowList(examineFlow);
        return getDataTable(list);
    }

    /**
     * 导出审批流程列表
     */
    @RequiresPermissions("web:examineFlow:export")
    @Log(title = "审批流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamineFlow examineFlow) {
        List<ExamineFlow> list = examineFlowService.selectExamineFlowList(examineFlow);
        ExcelUtil<ExamineFlow> util = new ExcelUtil<ExamineFlow>(ExamineFlow.class);
        return util.exportExcel(list, "examineFlow");
    }

    /**
     * 新增审批流程
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        // 审批模板
        modelMap.put("moduleList",examineModuleService.selectExamineModuleList(ExamineModule.builder().tag(0).showStatus(1).build()));
        return prefix + "/add";
    }

    /**
     * 新增保存审批流程
     */
    @RequiresPermissions("web:examineFlow:add")
    @Log(title = "审批流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamineFlow examineFlow) {
        // 查询所属的公司
        SysDept dept = deptService.selectDeptById(examineFlow.getDeptId());
        examineFlow.setCompanyId(dept == null ? 0L : dept.getParentId());
        return toAjax(examineFlowService.insertExamineFlow(examineFlow));
    }

    /**
     * 修改审批流程
     */
    @GetMapping("/edit/{examineFlowId}")
    public String edit(@PathVariable("examineFlowId") String examineFlowId, ModelMap mmap) {
        ExamineFlow examineFlow = examineFlowService.selectExamineFlowById(examineFlowId);
        mmap.put("examineFlow", examineFlow);
        // 审批模板
        mmap.put("moduleList",examineModuleService.selectExamineModuleList(ExamineModule.builder().tag(0).showStatus(1).build()));
        return prefix + "/edit";
    }

    /**
     * 修改保存审批流程
     */
    @RequiresPermissions("web:examineFlow:edit")
    @Log(title = "审批流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamineFlow examineFlow) {
        // 查询所属的公司
        SysDept dept = deptService.selectDeptById(examineFlow.getDeptId());
        examineFlow.setCompanyId(dept == null ? 0L : dept.getParentId());
        return toAjax(examineFlowService.updateExamineFlow(examineFlow));
    }

    /**
     * 删除审批流程
     */
    @RequiresPermissions("web:examineFlow:remove")
    @Log(title = "审批流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(examineFlowService.deleteExamineFlowByIds(ids));
    }
}
