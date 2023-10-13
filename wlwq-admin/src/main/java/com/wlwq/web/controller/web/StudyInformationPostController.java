package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.service.IStudyInformationCategoryService;
import com.wlwq.common.utils.ShiroUtils;
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
import com.wlwq.api.domain.StudyInformationPost;
import com.wlwq.api.service.IStudyInformationPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 资讯文章Controller
 *
 * @author web
 * @date 2023-05-16
 */
@Controller
@RequestMapping("/web/studyInformationPost")
public class StudyInformationPostController extends BaseController {

    private String prefix = "web/studyInformationPost";

    @Autowired
    private IStudyInformationPostService studyInformationPostService;

    @Autowired
    private IStudyInformationCategoryService studyInformationCategoryService;

    @RequiresPermissions("web:studyInformationPost:view")
    @GetMapping()
    public String studyInformationPost() {
        return prefix + "/studyInformationPost";
    }

    /**
     * 查询资讯文章列表
     */
    @RequiresPermissions("web:studyInformationPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StudyInformationPost studyInformationPost) {
        studyInformationPost.setDelStatus(0);
        startPage();
        List<StudyInformationPost> list = studyInformationPostService.selectStudyInformationPostList(studyInformationPost);
        return getDataTable(list);
    }

    /**
     * 导出资讯文章列表
     */
    @RequiresPermissions("web:studyInformationPost:export")
    @Log(title = "资讯文章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StudyInformationPost studyInformationPost) {
        List<StudyInformationPost> list = studyInformationPostService.selectStudyInformationPostList(studyInformationPost);
        ExcelUtil<StudyInformationPost> util = new ExcelUtil<StudyInformationPost>(StudyInformationPost.class);
        return util.exportExcel(list, "studyInformationPost");
    }

    /**
     * 新增资讯文章
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存资讯文章
     */
    @RequiresPermissions("web:studyInformationPost:add")
    @Log(title = "资讯文章", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StudyInformationPost studyInformationPost) {
        studyInformationPost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        studyInformationPost.setUserId(ShiroUtils.getUserId());
        Long studyInformationCategoryId = studyInformationPost.getStudyInformationCategoryId();
        if (studyInformationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = studyInformationCategoryService.selectAncestorsById(studyInformationCategoryId, buf);
            studyInformationPost.setAncestors(buf.toString());
        }
        return toAjax(studyInformationPostService.insertStudyInformationPost(studyInformationPost));
    }

    /**
     * 修改资讯文章
     */
    @GetMapping("/edit/{studyInformationPostId}")
    public String edit(@PathVariable("studyInformationPostId") Long studyInformationPostId, ModelMap mmap) {
        StudyInformationPost studyInformationPost = studyInformationPostService.selectStudyInformationPostById(studyInformationPostId);
        mmap.put("studyInformationPost", studyInformationPost);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯文章
     */
    @RequiresPermissions("web:studyInformationPost:edit")
    @Log(title = "资讯文章", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StudyInformationPost studyInformationPost) {
        Long studyInformationCategoryId = studyInformationPost.getStudyInformationCategoryId();
        if (studyInformationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = studyInformationCategoryService.selectAncestorsById(studyInformationCategoryId, buf);
            studyInformationPost.setAncestors(buf.toString());
        }
        return toAjax(studyInformationPostService.updateStudyInformationPost(studyInformationPost));
    }

    /**
     * 删除资讯文章
     */
    @RequiresPermissions("web:studyInformationPost:remove")
    @Log(title = "资讯文章", businessType = BusinessType.DELETE)
    @PostMapping("/removeAll")
    @ResponseBody
    public AjaxResult removeAll(String ids) {
        return toAjax(studyInformationPostService.deleteStudyInformationPostByIds(ids));
    }

    @RequiresPermissions("web:studyInformationCategory:remove")
    @Log(title = "资讯分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long ids) {
        return toAjax(studyInformationPostService.deleteStudyInformationPostById(ids));
    }

}
