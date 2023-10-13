package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.service.INewsCenterCategoryService;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.system.service.ISysDeptService;
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
import com.wlwq.api.domain.NewsCenterPost;
import com.wlwq.api.service.INewsCenterPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 新闻中心资讯Controller
 *
 * @author wwb
 * @date 2023-05-06
 */
@Controller
@RequestMapping("/web/newsCenterPost")
public class NewsCenterPostController extends BaseController {

    private String prefix = "web/newsCenterPost";

    @Autowired
    private INewsCenterPostService newsCenterPostService;

    @Autowired
    private INewsCenterCategoryService newsCenterCategoryService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:newsCenterPost:view")
    @GetMapping()
    public String newsCenterPost(ModelMap map) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                map.put("companyId",dept.getDeptId());
            }
        }
        return prefix + "/newsCenterPost";
    }

    /**
     * 查询新闻中心资讯列表
     */
    @RequiresPermissions("web:newsCenterPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NewsCenterPost newsCenterPost) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                newsCenterPost.setDeptId(dept.getDeptId());
            }
        }
        startPage();
        List<NewsCenterPost> list = newsCenterPostService.selectNewsCenterPostList(newsCenterPost);
        return getDataTable(list);
    }

    /**
     * 导出新闻中心资讯列表
     */
    @RequiresPermissions("web:newsCenterPost:export")
    @Log(title = "新闻中心资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NewsCenterPost newsCenterPost) {
        List<NewsCenterPost> list = newsCenterPostService.selectNewsCenterPostList(newsCenterPost);
        ExcelUtil<NewsCenterPost> util = new ExcelUtil<NewsCenterPost>(NewsCenterPost.class);
        return util.exportExcel(list, "newsCenterPost");
    }

    /**
     * 新增新闻中心资讯
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存新闻中心资讯
     */
    @RequiresPermissions("web:newsCenterPost:add")
    @Log(title = "新闻中心资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NewsCenterPost newsCenterPost) {
        newsCenterPost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        newsCenterPost.setUserId(ShiroUtils.getUserId());
        Long informationCategoryId = newsCenterPost.getInformationCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = newsCenterCategoryService.selectAncestorsById(informationCategoryId, buf);
            newsCenterPost.setAncestors(buf.toString());
        }
        return toAjax(newsCenterPostService.insertNewsCenterPost(newsCenterPost));
    }

    /**
     * 修改新闻中心资讯
     */
    @GetMapping("/edit/{newsCenterPostId}")
    public String edit(@PathVariable("newsCenterPostId") Long newsCenterPostId, ModelMap mmap) {
        NewsCenterPost newsCenterPost = newsCenterPostService.selectNewsCenterPostById(newsCenterPostId);
        mmap.put("newsCenterPost", newsCenterPost);
        return prefix + "/edit";
    }

    /**
     * 修改保存新闻中心资讯
     */
    @RequiresPermissions("web:newsCenterPost:edit")
    @Log(title = "新闻中心资讯", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NewsCenterPost newsCenterPost) {
        Long informationCategoryId = newsCenterPost.getInformationCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = newsCenterCategoryService.selectAncestorsById(informationCategoryId, buf);
            newsCenterPost.setAncestors(buf.toString());
        }
        return toAjax(newsCenterPostService.updateNewsCenterPost(newsCenterPost));
    }

    /**
     * 删除新闻中心资讯
     */
    @RequiresPermissions("web:newsCenterPost:remove")
    @Log(title = "新闻中心资讯", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(newsCenterPostService.deleteNewsCenterPostByIds(ids));
    }
}