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
import com.wlwq.api.domain.TrainingModule;
import com.wlwq.api.service.ITrainingModuleService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 人员训练模块Controller
 *
 * @author gaoce
 * @date 2023-07-31
 */
@Controller
@RequestMapping("/web/trainingModule")
public class TrainingModuleController extends BaseController {

    private String prefix = "web/trainingModule";

    @Autowired
    private ITrainingModuleService trainingModuleService;

    @RequiresPermissions("web:trainingModule:view")
    @GetMapping()
    public String trainingModule() {
        return prefix + "/trainingModule";
    }

    /**
     * 查询人员训练模块列表
     */
    @RequiresPermissions("web:trainingModule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TrainingModule trainingModule) {
        startPage();
        List<TrainingModule> list = trainingModuleService.selectTrainingModuleList(trainingModule);
        return getDataTable(list);
    }

    /**
     * 导出人员训练模块列表
     */
    @RequiresPermissions("web:trainingModule:export")
    @Log(title = "人员训练模块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TrainingModule trainingModule) {
        List<TrainingModule> list = trainingModuleService.selectTrainingModuleList(trainingModule);
        ExcelUtil<TrainingModule> util = new ExcelUtil<TrainingModule>(TrainingModule.class);
        return util.exportExcel(list, "trainingModule");
    }

    /**
     * 新增人员训练模块
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存人员训练模块
     */
    @RequiresPermissions("web:trainingModule:add")
    @Log(title = "人员训练模块", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TrainingModule trainingModule) {
        return toAjax(trainingModuleService.insertTrainingModule(trainingModule));
    }

    /**
     * 修改人员训练模块
     */
    @GetMapping("/edit/{trainingModuleId}")
    public String edit(@PathVariable("trainingModuleId") String trainingModuleId, ModelMap mmap) {
        TrainingModule trainingModule = trainingModuleService.selectTrainingModuleById(trainingModuleId);
        mmap.put("trainingModule", trainingModule);
        return prefix + "/edit";
    }

    /**
     * 修改保存人员训练模块
     */
    @RequiresPermissions("web:trainingModule:edit")
    @Log(title = "人员训练模块", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TrainingModule trainingModule) {
        return toAjax(trainingModuleService.updateTrainingModule(trainingModule));
    }

    /**
     * 删除人员训练模块
     */
    @RequiresPermissions("web:trainingModule:remove")
    @Log(title = "人员训练模块", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(trainingModuleService.deleteTrainingModuleByIds(ids));
    }
}
