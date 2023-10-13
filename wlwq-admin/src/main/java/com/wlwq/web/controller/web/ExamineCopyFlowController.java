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
import com.wlwq.api.domain.ExamineCopyFlow;
import com.wlwq.api.service.IExamineCopyFlowService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 审批抄送流程Controller
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Controller
@RequestMapping("/web/examineCopyFlow")
public class ExamineCopyFlowController extends BaseController {

    private String prefix = "web/examineCopyFlow";

    @Autowired
    private IExamineCopyFlowService examineCopyFlowService;

    @Autowired
    private IExamineModuleService examineModuleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:examineCopyFlow:view")
    @GetMapping()
    public String examineCopyFlow(ModelMap modelMap) {
        // 审批模板
        modelMap.put("moduleList",examineModuleService.selectExamineModuleList(ExamineModule.builder().tag(1).showStatus(1).build()));
        return prefix + "/examineCopyFlow";
    }

    /**
     * 查询审批抄送流程列表
     */
    @RequiresPermissions("web:examineCopyFlow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExamineCopyFlow examineCopyFlow) {
        startPage();
        List<ExamineCopyFlow> list = examineCopyFlowService.selectExamineCopyFlowList(examineCopyFlow);
        return getDataTable(list);
    }

    /**
     * 导出审批抄送流程列表
     */
    @RequiresPermissions("web:examineCopyFlow:export")
    @Log(title = "审批抄送流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamineCopyFlow examineCopyFlow) {
        List<ExamineCopyFlow> list = examineCopyFlowService.selectExamineCopyFlowList(examineCopyFlow);
        ExcelUtil<ExamineCopyFlow> util = new ExcelUtil<ExamineCopyFlow>(ExamineCopyFlow.class);
        return util.exportExcel(list, "examineCopyFlow");
    }

    /**
     * 新增审批抄送流程
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        // 审批模板
        modelMap.put("moduleList",examineModuleService.selectExamineModuleList(ExamineModule.builder().tag(1).showStatus(1).build()));
        return prefix + "/add";
    }

    /**
     * 新增保存审批抄送流程
     */
    @RequiresPermissions("web:examineCopyFlow:add")
    @Log(title = "审批抄送流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamineCopyFlow examineCopyFlow) {
        // 查询所属的公司
        SysDept dept = deptService.selectDeptById(examineCopyFlow.getDeptId());
        examineCopyFlow.setCompanyId(dept == null ? 0L : dept.getParentId());
        return toAjax(examineCopyFlowService.insertExamineCopyFlow(examineCopyFlow));
    }

    /**
     * 修改审批抄送流程
     */
    @GetMapping("/edit/{examineCopyFlowId}")
    public String edit(@PathVariable("examineCopyFlowId") String examineCopyFlowId, ModelMap mmap) {
        ExamineCopyFlow examineCopyFlow = examineCopyFlowService.selectExamineCopyFlowById(examineCopyFlowId);
        mmap.put("examineCopyFlow", examineCopyFlow);
        // 审批模板
        mmap.put("moduleList",examineModuleService.selectExamineModuleList(ExamineModule.builder().tag(1).showStatus(1).build()));
        return prefix + "/edit";
    }

    /**
     * 修改保存审批抄送流程
     */
    @RequiresPermissions("web:examineCopyFlow:edit")
    @Log(title = "审批抄送流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamineCopyFlow examineCopyFlow) {
        // 查询所属的公司
        SysDept dept = deptService.selectDeptById(examineCopyFlow.getDeptId());
        examineCopyFlow.setCompanyId(dept == null ? 0L : dept.getParentId());
        return toAjax(examineCopyFlowService.updateExamineCopyFlow(examineCopyFlow));
    }

    /**
     * 删除审批抄送流程
     */
    @RequiresPermissions("web:examineCopyFlow:remove")
    @Log(title = "审批抄送流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(examineCopyFlowService.deleteExamineCopyFlowByIds(ids));
    }
}
