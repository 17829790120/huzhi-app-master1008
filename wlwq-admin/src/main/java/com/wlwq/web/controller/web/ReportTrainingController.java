package com.wlwq.web.controller.web;

import java.util.*;

import cn.hutool.core.convert.Convert;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wlwq.api.domain.ReportTrainingReadRecord;
import com.wlwq.api.domain.Template;
import com.wlwq.api.paramsVO.TemplateFieldParamVO;
import com.wlwq.api.service.IReportTrainingReadRecordService;
import com.wlwq.api.service.ITemplateService;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
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
import com.wlwq.api.domain.ReportTraining;
import com.wlwq.api.service.IReportTrainingService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 汇报训练Controller
 *
 * @author gaoce
 * @date 2023-06-05
 */
@Controller
@RequestMapping("/web/reportTraining")
public class ReportTrainingController extends BaseController {

    private String prefix = "web/reportTraining";

    @Autowired
    private IReportTrainingService reportTrainingService;
    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IReportTrainingReadRecordService readRecordService;


    /**
     * templateType 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     * @param modelMap
     * @param templateType
     * @return
     */
    @RequiresPermissions("web:reportTraining:view")
    @GetMapping()
    public String reportTraining(ModelMap modelMap,Integer templateType) {
        modelMap.put("templateType",templateType);
        return prefix + "/reportTraining";
    }

    /**
     * 查询汇报训练列表
     */
    @RequiresPermissions("web:reportTraining:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReportTraining reportTraining) {
        Template template = templateService.selectTemplate(Template.builder().templateType(reportTraining.getTemplateType()).build());
        List<TemplateFieldParamVO> paramVOList = new Gson().fromJson(template == null ? "" : template.getTemplateContent(), new TypeToken<List<TemplateFieldParamVO>>() {

        }.getType());
        startPage();
        List<Map<String,Object>> list = reportTrainingService.selectWebReportTrainingList(reportTraining);
        list.forEach(param-> {
            List<TemplateFieldParamVO> trainingList = new Gson().fromJson(Convert.toStr(param.get("content")), new TypeToken<List<TemplateFieldParamVO>>() {

            }.getType());
            trainingList.forEach(training-> {
                paramVOList.forEach(e-> {
                    if(training.getFieldEnglishName().equals(e.getFieldEnglishName())){
                        param.put(e.getFieldEnglishName(),training.getValue());
                    }
                });
            });
        });
        return getDataTable(list);
    }

    /**
     * 导出汇报训练列表
     */
    @RequiresPermissions("web:reportTraining:export")
    @Log(title = "汇报训练", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReportTraining reportTraining) {
        List<ReportTraining> list = reportTrainingService.selectReportTrainingList(reportTraining);
        ExcelUtil<ReportTraining> util = new ExcelUtil<>(ReportTraining.class);
        return util.exportExcel(list, "reportTraining");
    }

    /**
     * 新增汇报训练
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存汇报训练
     */
    @RequiresPermissions("web:reportTraining:add")
    @Log(title = "汇报训练", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ReportTraining reportTraining) {
        return toAjax(reportTrainingService.insertReportTraining(reportTraining));
    }

    /**
     * 修改汇报训练
     */
    @GetMapping("/edit/{reportTrainingId}")
    public String edit(@PathVariable("reportTrainingId") String reportTrainingId, ModelMap mmap) {
        ReportTraining reportTraining = reportTrainingService.selectReportTrainingById(reportTrainingId);
        mmap.put("reportTraining", reportTraining);
        reportTraining.setTemplateFieldList(new Gson().fromJson(reportTraining.getContent(), new TypeToken<List<TemplateFieldParamVO>>() {
        }.getType()));
        return prefix + "/edit";
    }

    /**
     * 修改保存汇报训练
     */
    @RequiresPermissions("web:reportTraining:edit")
    @Log(title = "汇报训练", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ReportTraining reportTraining) {
        return toAjax(reportTrainingService.updateReportTraining(reportTraining));
    }

    /**
     * 删除汇报训练
     */
    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions("web:reportTraining:remove")
    @Log(title = "汇报训练", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if(StringUtils.isBlank(ids)){
            return error("请选择要删除的信息！");
        }
        // 删除已读的记录
        readRecordService.delReportTrainingReadRecord(ReportTrainingReadRecord.builder().reportTrainingId(ids).build());
        return toAjax(reportTrainingService.deleteReportTrainingByIds(ids));
    }

    /**
     * 动态获取列(周汇报)
     * templateType 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     */
    @PostMapping("/ajaxColumns")
    @ResponseBody
    public AjaxResult ajaxColumns(Integer templateType) {
        Template template = templateService.selectTemplate(Template.builder().templateType(templateType).build());
        if(template == null){
            return error("暂未查询到模板！");
        }
        List<TemplateFieldParamVO> paramVOList = new Gson().fromJson(template.getTemplateContent(), new TypeToken<List<TemplateFieldParamVO>>() {
        }.getType());
        return AjaxResult.success(paramVOList);
    }
}
