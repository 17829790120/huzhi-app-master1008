package com.wlwq.web.controller.web;

import java.util.ArrayList;
import java.util.List;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.StringUtils;
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
import com.wlwq.api.domain.StudyInformationCategory;
import com.wlwq.api.service.IStudyInformationCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;

/**
 * 学习模块--自己与组织的关系--资讯分类Controller
 *
 * @author web
 * @date 2023-05-16
 */
@Controller
@RequestMapping("/web/studyInformationCategory")
public class StudyInformationCategoryController extends BaseController {

    private String prefix = "web/studyInformationCategory";

    @Autowired
    private IStudyInformationCategoryService studyInformationCategoryService;

    @RequiresPermissions("web:studyInformationCategory:view")
    @GetMapping()
    public String studyInformationCategory() {
        return prefix + "/studyInformationCategory";
    }

    /**
     * 查询资讯分类列表
     */
    @RequiresPermissions("web:studyInformationCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public List<StudyInformationCategory> list(StudyInformationCategory studyInformationCategory) {
        studyInformationCategory.setDelStatus(0);
        return studyInformationCategoryService.selectStudyInformationCategoryList(studyInformationCategory);
    }

    /**
     * 导出资讯分类列表
     */
    @RequiresPermissions("web:studyInformationCategory:export")
    @Log(title = "资讯分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StudyInformationCategory studyInformationCategory) {
        List<StudyInformationCategory> list = studyInformationCategoryService.selectStudyInformationCategoryList(studyInformationCategory);
        ExcelUtil<StudyInformationCategory> util = new ExcelUtil<StudyInformationCategory>(StudyInformationCategory.class);
        return util.exportExcel(list, "studyInformationCategory");
    }

    /**
     * 新增资讯分类
     */
    @GetMapping(value = { "/add/{studyInformationCategoryId}", "/add/" })
    public String add(@PathVariable(value = "studyInformationCategoryId", required = false) Long studyInformationCategoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(studyInformationCategoryId))
        {
            mmap.put("studyInformationCategory", studyInformationCategoryService.selectStudyInformationCategoryById(studyInformationCategoryId));
        }
        return prefix + "/add";
    }


    /**
     * 新增保存资讯分类
     */
    @RequiresPermissions("web:studyInformationCategory:add")
    @Log(title = "资讯分类", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StudyInformationCategory studyInformationCategory) {
        studyInformationCategory.setUserId(ShiroUtils.getUserId());
        studyInformationCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return toAjax(studyInformationCategoryService.insertStudyInformationCategory(studyInformationCategory));
    }

    /**
     * 修改资讯分类
     */
    @GetMapping("/edit/{studyInformationCategoryId}")
    public String edit(@PathVariable("studyInformationCategoryId") Long studyInformationCategoryId, ModelMap mmap) {
        StudyInformationCategory studyInformationCategory = studyInformationCategoryService.selectStudyInformationCategoryById(studyInformationCategoryId);
        mmap.put("studyInformationCategory", studyInformationCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯分类
     */
    @RequiresPermissions("web:studyInformationCategory:edit")
    @Log(title = "资讯分类", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StudyInformationCategory studyInformationCategory) {
        return toAjax(studyInformationCategoryService.updateStudyInformationCategory(studyInformationCategory));
    }

    /**
     * 删除资讯分类
     */
    @RequiresPermissions("web:studyInformationCategory:remove")
    @Log(title = "资讯分类", businessType = BusinessType.DELETE)
    @PostMapping("/removeAll")
    @ResponseBody
    public AjaxResult removeAll(String ids) {
        return toAjax(studyInformationCategoryService.deleteStudyInformationCategoryByIds(ids));
    }
    /**
     * 删除资讯分类
     */
    @RequiresPermissions("web:studyInformationCategory:remove")
    @Log(title = "资讯分类", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{studyInformationCategoryId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable(value = "studyInformationCategoryId") Long studyInformationCategoryId) {
        return toAjax(studyInformationCategoryService.deleteStudyInformationCategoryById(studyInformationCategoryId));
    }

    /**
     * 选择资讯分类树
     */
    @GetMapping(value = { "/selectInformationCategoryTree/{studyInformationCategoryId}", "/selectInformationCategoryTree/" })
    public String selectInformationCategoryTree(@PathVariable(value = "studyInformationCategoryId", required = false) Long studyInformationCategoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(studyInformationCategoryId))
        {
            mmap.put("studyInformationCategoryId", studyInformationCategoryService.selectStudyInformationCategoryById(studyInformationCategoryId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载资讯分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<StudyInformationCategory> informationCategoryList = studyInformationCategoryService.selectStudyInformationCategoryList(StudyInformationCategory.builder().delStatus(0).build());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (StudyInformationCategory informationCategory : informationCategoryList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(informationCategory.getStudyInformationCategoryId());
            ztree.setpId(informationCategory.getParentId());
            ztree.setName(informationCategory.getInformationCategoryTitle());
            ztree.setTitle(informationCategory.getInformationCategoryTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
