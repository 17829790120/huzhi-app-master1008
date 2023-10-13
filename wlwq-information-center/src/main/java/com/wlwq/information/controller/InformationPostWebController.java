package com.wlwq.information.controller;

import java.util.List;

import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.information.domain.InformationCategory;
import com.wlwq.information.service.IInformationCategoryService;
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
import com.wlwq.information.domain.InformationPost;
import com.wlwq.information.service.IInformationPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 资讯文章Controller
 *
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Controller
@RequestMapping("/information/informationPost")
public class InformationPostWebController extends BaseController {
    private String prefix = "information/informationPost";

    @Autowired
    private IInformationPostService informationPostService;
    @Autowired
    private IInformationCategoryService informationCategoryService;

    @RequiresPermissions("information:informationPost:view")
    @GetMapping()
    public String informationPost() {
        return prefix + "/informationPost";
    }

    /**
     * 查询资讯文章列表
     */
    @RequiresPermissions("information:informationPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(InformationPost informationPost) {
        startPage();
        List<InformationPost> list = informationPostService.selectInformationPostList(informationPost);
        return getDataTable(list);
    }

    /**
     * 导出资讯文章列表
     */
    @RequiresPermissions("information:informationPost:export")
    @Log(title = "资讯文章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InformationPost informationPost) {
        List<InformationPost> list = informationPostService.selectInformationPostList(informationPost);
        ExcelUtil<InformationPost> util = new ExcelUtil<InformationPost>(InformationPost.class);
        return util.exportExcel(list, "informationPost");
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
    @RequiresPermissions("information:informationPost:add")
    @Log(title = "资讯文章", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InformationPost informationPost) {
        informationPost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        informationPost.setUserId(ShiroUtils.getUserId());
        Long informationCategoryId = informationPost.getInformationCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = informationCategoryService.selectAncestorsById(informationCategoryId, buf);
            informationPost.setAncestors(buf.toString());
        }
        return toAjax(informationPostService.insertInformationPost(informationPost));
    }

    /**
     * 修改资讯文章
     */
    @GetMapping("/edit/{informationPostId}")
    public String edit(@PathVariable("informationPostId") Long informationPostId, ModelMap mmap) {
        InformationPost informationPost = informationPostService.selectInformationPostById(informationPostId);
        mmap.put("informationPost", informationPost);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯文章
     */
    @RequiresPermissions("information:informationPost:edit")
    @Log(title = "资讯文章", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InformationPost informationPost) {
        Long informationCategoryId = informationPost.getInformationCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = informationCategoryService.selectAncestorsById(informationCategoryId, buf);
            informationPost.setAncestors(buf.toString());
        }
        return toAjax(informationPostService.updateInformationPost(informationPost));
    }

    /**
     * 删除资讯文章(删除单个)
     */
    @RequiresPermissions("information:informationPost:remove")
    @Log(title = "资讯文章", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Long ids) {
        return toAjax(informationPostService.deleteInformationPostById(ids));
    }

    /**
     * 删除资讯文章
     */
    @RequiresPermissions("information:informationPost:remove")
    @Log(title = "资讯文章", businessType = BusinessType.DELETE)
    @PostMapping("/removeAll")
    @ResponseBody
    public AjaxResult removeAll(String ids) {
        return toAjax(informationPostService.deleteInformationPostByIds(ids));
    }
}
