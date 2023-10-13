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
import com.wlwq.api.domain.ExamineModule;
import com.wlwq.api.service.IExamineModuleService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 审批类型Controller
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Controller
@RequestMapping("/web/examineModule")
public class ExamineModuleController extends BaseController {

    private String prefix = "web/examineModule";

    @Autowired
    private IExamineModuleService examineModuleService;

    @RequiresPermissions("web:examineModule:view")
    @GetMapping()
    public String examineModule() {
        return prefix + "/examineModule";
    }

    /**
     * 查询审批类型树列表
     */
    @RequiresPermissions("web:examineModule:list")
    @PostMapping("/list")
    @ResponseBody
    public List<ExamineModule> list(ExamineModule examineModule) {
        List<ExamineModule> list = examineModuleService.selectExamineModuleList(examineModule);
        return list;
    }

    /**
     * 导出审批类型列表
     */
    @RequiresPermissions("web:examineModule:export")
    @Log(title = "审批类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExamineModule examineModule) {
        List<ExamineModule> list = examineModuleService.selectExamineModuleList(examineModule);
        ExcelUtil<ExamineModule> util = new ExcelUtil<ExamineModule>(ExamineModule.class);
        return util.exportExcel(list, "examineModule");
    }

    /**
     * 新增审批类型
     */
    @GetMapping(value = {"/add/{examineModuleId}", "/add/"})
    public String add(@PathVariable(value = "examineModuleId", required = false) Long examineModuleId, ModelMap mmap) {
        if (StringUtils.isNotNull(examineModuleId)) {
            mmap.put("examineModule", examineModuleService.selectExamineModuleById(examineModuleId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存审批类型
     */
    @RequiresPermissions("web:examineModule:add")
    @Log(title = "审批类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExamineModule examineModule) {
        return toAjax(examineModuleService.insertExamineModule(examineModule));
    }

    /**
     * 修改审批类型
     */
    @GetMapping("/edit/{examineModuleId}")
    public String edit(@PathVariable("examineModuleId") Long examineModuleId, ModelMap mmap) {
        ExamineModule examineModule = examineModuleService.selectExamineModuleById(examineModuleId);
        mmap.put("examineModule", examineModule);
        return prefix + "/edit";
    }

    /**
     * 修改保存审批类型
     */
    @RequiresPermissions("web:examineModule:edit")
    @Log(title = "审批类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExamineModule examineModule) {
        return toAjax(examineModuleService.updateExamineModule(examineModule));
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:examineModule:remove")
    @Log(title = "审批类型", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{examineModuleId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("examineModuleId") Long examineModuleId) {
        return toAjax(examineModuleService.deleteExamineModuleById(examineModuleId));
    }

    /**
     * 选择审批类型树
     */
    @GetMapping(value = {"/selectExamineModuleTree/{examineModuleId}", "/selectExamineModuleTree/"})
    public String selectExamineModuleTree(@PathVariable(value = "examineModuleId", required = false) Long examineModuleId, ModelMap mmap) {
        if (StringUtils.isNotNull(examineModuleId)) {
            mmap.put("examineModule", examineModuleService.selectExamineModuleById(examineModuleId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载审批类型树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = examineModuleService.selectExamineModuleTree();
        return ztrees;
    }
}
