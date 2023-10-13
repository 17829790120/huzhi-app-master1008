package com.wlwq.web.controller.system;

import java.util.Iterator;
import java.util.List;

import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.system.service.ISysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.constant.UserConstants;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.system.domain.SysPost;
import com.wlwq.system.service.ISysPostService;

/**
 * 岗位信息操作处理
 *
 * @author wlwq
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    private String prefix = "system/post";

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String operlog(@RequestParam(defaultValue = "0") Long deptId, ModelMap modelMap) {
        modelMap.put("deptId", deptId);
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        return util.exportExcel(list, "岗位数据");
    }

    /**
     * 根据公司ID查询岗位信息
     *
     * @return
     */
    @PostMapping("/selectPostsById")
    @ResponseBody
    public List<SysPost> selectPostsById(SysPost post) {
        post.setStatus("0");
        post.setAccountId(StringUtils.isBlank(post.getAccountId()) ? "0" :post.getAccountId());
        List<SysPost> postList = postService.selectWebPostList(post);
        return postList;
    }

    /**
     * 根据部门ID查询岗位信息
     *
     * @return
     */
    @PostMapping("/selectPostsByDeptId")
    @ResponseBody
    public List<SysPost> selectPostsByDeptId(Long deptId) {
        SysDept sysDept = deptService.selectDeptById(deptId);
        SysPost post = new SysPost();
        post.setStatus("0");
        post.setPostType(2);
        post.setCompanyId(sysDept == null ? -1 : sysDept.getParentId());
        List<SysPost> postList = postService.selectPostList(post);
        return postList;
    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(postService.deletePostByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 新增岗位
     */
    @GetMapping("/add")
    public String add(Long companyId, ModelMap modelMap) {
        modelMap.put("companyId", companyId);
        return prefix + "/add";
    }

    /**
     * 新增保存岗位
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysPost post) {
        if (UserConstants.POST_NAME_NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
        mmap.put("post", postService.selectPostById(postId));
        return prefix + "/edit";
    }

    /**
     * 修改保存岗位
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysPost post) {
        if (UserConstants.POST_NAME_NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 校验岗位名称
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public String checkPostNameUnique(SysPost post) {
        return postService.checkPostNameUnique(post);
    }

    /**
     * 校验岗位编码
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public String checkPostCodeUnique(SysPost post) {
        return postService.checkPostCodeUnique(post);
    }
}
