package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.exception.BusinessException;
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
import com.wlwq.api.domain.TargetContent;
import com.wlwq.api.service.ITargetContentService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 目标训练内容Controller
 *
 * @author wwb
 * @date 2023-06-02
 */
@Controller
@RequestMapping("/web/targetContent")
public class TargetContentController extends BaseController {

    private String prefix = "web/targetContent";

    @Autowired
    private ITargetContentService targetContentService;

    @RequiresPermissions("web:targetContent:view")
    @GetMapping()
    public String targetContent() {
        return prefix + "/targetContent";
    }

    @RequiresPermissions("web:targetContent:view")
    @GetMapping("/targetContentNew")
    public String targetContentNew(String targetTrainingId,ModelMap modelMap) {
        if (targetTrainingId ==null){
            throw new BusinessException("请选择目标年份！");
        }
        modelMap.put("targetTrainingId",targetTrainingId);
        return prefix + "/targetContent";
    }



    /**
     * 查询目标训练内容树列表
     */
    @RequiresPermissions("web:targetContent:list")
    @PostMapping("/list")
    @ResponseBody
    public List<TargetContent> list(TargetContent targetContent) {
        List<TargetContent> list = targetContentService.selectTargetContentList(targetContent);
        return list;
    }

    /**
     * 导出目标训练内容列表
     */
    @RequiresPermissions("web:targetContent:export")
    @Log(title = "目标训练内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TargetContent targetContent) {
        List<TargetContent> list = targetContentService.selectTargetContentList(targetContent);
        ExcelUtil<TargetContent> util = new ExcelUtil<TargetContent>(TargetContent.class);
        return util.exportExcel(list, "targetContent");
    }

    /**
     * 新增目标训练内容
     */
    @GetMapping(value = {"/add/{id}/{targetTrainingId}", "/add/{targetTrainingId}"})
    public String add(@PathVariable(value = "id", required = false) Long id,
                      @PathVariable(value = "targetTrainingId", required = false) String targetTrainingId, ModelMap mmap) {
        if (StringUtils.isNotNull(id)) {
            mmap.put("targetContent", targetContentService.selectTargetContentById(id));
        }
        mmap.put("targetTrainingId", targetTrainingId);
        return prefix + "/add";
    }

    /**
     * 新增保存目标训练内容
     */
    @RequiresPermissions("web:targetContent:add")
    @Log(title = "目标训练内容", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TargetContent targetContent) {
        return toAjax(targetContentService.insertTargetContent(targetContent));
    }

    /**
     * 修改目标训练内容
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        TargetContent targetContent = targetContentService.selectTargetContentById(id);
        mmap.put("targetContent", targetContent);
        return prefix + "/edit";
    }

    /**
     * 修改保存目标训练内容
     */
    @RequiresPermissions("web:targetContent:edit")
    @Log(title = "目标训练内容", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TargetContent targetContent) {
        return toAjax(targetContentService.updateTargetContent(targetContent));
    }

    /**
     * 删除
     */
    @RequiresPermissions("web:targetContent:remove")
    @Log(title = "目标训练内容", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id) {
        return toAjax(targetContentService.deleteTargetContentById(id));
    }

    /**
     * 选择目标训练内容树
     */
    @GetMapping(value = {"/selectTargetContentTree/{id}", "/selectTargetContentTree/"})
    public String selectTargetContentTree(@PathVariable(value = "id", required = false) Long id, ModelMap mmap) {
        if (StringUtils.isNotNull(id)) {
            mmap.put("targetContent", targetContentService.selectTargetContentById(id));
        }
        return prefix + "/tree";
    }

    /**
     * 加载目标训练内容树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = targetContentService.selectTargetContentTree();
        return ztrees;
    }
}
