package com.wlwq.web.controller.web;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wlwq.api.paramsVO.TemplateFieldParamVO;
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
import com.wlwq.api.domain.Template;
import com.wlwq.api.service.ITemplateService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 自定义模版Controller
 *
 * @author gaoce
 * @date 2023-06-05
 */
@Controller
@RequestMapping("/web/template")
public class TemplateController extends BaseController {

    private String prefix = "web/template";

    @Autowired
    private ITemplateService templateService;

    @RequiresPermissions("web:template:view")
    @GetMapping()
    public String template() {
        return prefix + "/template";
    }

    /**
     * 查询自定义模版列表
     */
    @RequiresPermissions("web:template:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Template template) {
        startPage();
        List<Template> list = templateService.selectTemplateList(template);
        return getDataTable(list);
    }

    /**
     * 导出自定义模版列表
     */
    @RequiresPermissions("web:template:export")
    @Log(title = "自定义模版", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Template template) {
        List<Template> list = templateService.selectTemplateList(template);
        ExcelUtil<Template> util = new ExcelUtil<Template>(Template.class);
        return util.exportExcel(list, "template");
    }

    /**
     * 新增自定义模版
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存自定义模版
     */
    @RequiresPermissions("web:template:add")
    @Log(title = "自定义模版", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Template template) {
        List<TemplateFieldParamVO> templateFieldList = template.getTemplateFieldList();
        if (templateFieldList == null || templateFieldList.size() == 0) {
            return error("请添加字段！");
        }
        // 对list进行正序排列
        templateFieldList = templateFieldList.stream().sorted(Comparator.comparing(TemplateFieldParamVO::getSortNum))
                .collect(Collectors.toList());
        template.setTemplateContent(new Gson().toJson(templateFieldList));
        return toAjax(templateService.insertTemplate(template));
    }

    /**
     * 修改自定义模版
     */
    @GetMapping("/edit/{templateId}")
    public String edit(@PathVariable("templateId") String templateId, ModelMap mmap) {
        Template template = templateService.selectTemplateById(templateId);
        mmap.put("template", template);
        template.setTemplateFieldList(new Gson().fromJson(template.getTemplateContent(), new TypeToken<List<TemplateFieldParamVO>>() {
        }.getType()));
        return prefix + "/edit";
    }

    /**
     * 修改保存自定义模版
     */
    @RequiresPermissions("web:template:edit")
    @Log(title = "自定义模版", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Template template) {
        List<TemplateFieldParamVO> templateFieldList = template.getTemplateFieldList();
        if (templateFieldList == null || templateFieldList.size() == 0) {
            return error("请添加字段！");
        }
        // 对list进行正序排列
        templateFieldList = templateFieldList.stream().sorted(Comparator.comparing(TemplateFieldParamVO::getSortNum))
                .collect(Collectors.toList());
        template.setTemplateContent(new Gson().toJson(templateFieldList));
        return toAjax(templateService.updateTemplate(template));
    }

    /**
     * 删除自定义模版
     */
    @RequiresPermissions("web:template:remove")
    @Log(title = "自定义模版", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(templateService.deleteTemplateByIds(ids));
    }
}
