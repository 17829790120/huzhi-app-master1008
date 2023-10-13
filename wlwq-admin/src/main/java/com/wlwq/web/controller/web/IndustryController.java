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
import com.wlwq.api.domain.Industry;
import com.wlwq.api.service.IIndustryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 行业信息Controller
 *
 * @author wwb
 * @date 2023-05-23
 */
@Controller
@RequestMapping("/web/industry")
public class IndustryController extends BaseController {

    private String prefix = "web/industry";

    @Autowired
    private IIndustryService industryService;

    @RequiresPermissions("web:industry:view")
    @GetMapping()
    public String industry() {
        return prefix + "/industry";
    }

    /**
     * 查询行业信息树列表
     */
    @RequiresPermissions("web:industry:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Industry> list(Industry industry) {
        List<Industry> list = industryService.selectIndustryList(industry);
        return list;
    }

    /**
     * 导出行业信息列表
     */
    @RequiresPermissions("web:industry:export")
    @Log(title = "行业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Industry industry) {
        List<Industry> list = industryService.selectIndustryList(industry);
        ExcelUtil<Industry> util = new ExcelUtil<Industry>(Industry.class);
        return util.exportExcel(list, "industry");
    }

    /**
     * 新增行业信息
     */
    @GetMapping(value = {"/add/{id}", "/add/"})
    public String add(@PathVariable(value = "id", required = false) Long id, ModelMap mmap) {
        if (StringUtils.isNotNull(id)) {
            mmap.put("industry", industryService.selectIndustryById(id));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存行业信息
     */
    @RequiresPermissions("web:industry:add")
    @Log(title = "行业信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Industry industry) {
        return toAjax(industryService.insertIndustry(industry));
    }

    /**
     * 修改行业信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Industry industry = industryService.selectIndustryById(id);
        mmap.put("industry", industry);
        return prefix + "/edit";
    }

    /**
     * 修改保存行业信息
     */
    @RequiresPermissions("web:industry:edit")
    @Log(title = "行业信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Industry industry) {
        return toAjax(industryService.updateIndustry(industry));
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:industry:remove")
    @Log(title = "行业信息", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id) {
        return toAjax(industryService.deleteIndustryById(id));
    }

    /**
     * 选择行业信息树
     */
    @GetMapping(value = {"/selectIndustryTree/{id}", "/selectIndustryTree/"})
    public String selectIndustryTree(@PathVariable(value = "id", required = false) Long id, ModelMap mmap) {
        if (StringUtils.isNotNull(id)) {
            mmap.put("industry", industryService.selectIndustryById(id));
        }
        return prefix + "/tree";
    }

    /**
     * 加载行业信息树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = industryService.selectIndustryTree();
        return ztrees;
    }
}
