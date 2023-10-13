package com.wlwq.web.controller.web;

import java.util.List;

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
import com.wlwq.api.domain.ResourceFilePost;
import com.wlwq.api.service.IResourceFilePostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 资源文件Controller
 *
 * @author wwb
 * @date 2023-05-24
 */
@Controller
@RequestMapping("/web/resourceFilePost")
public class ResourceFilePostController extends BaseController {

    private String prefix = "web/resourceFilePost";

    @Autowired
    private IResourceFilePostService resourceFilePostService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:resourceFilePost:view")
    @GetMapping()
    public String resourceFilePost() {
        return prefix + "/resourceFilePost";
    }

    /**
     * 查询资源文件列表
     */
    @RequiresPermissions("web:resourceFilePost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ResourceFilePost resourceFilePost) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                resourceFilePost.setCompanyId(dept.getDeptId());
            }
        }
        resourceFilePost.setTag(2);
        startPage();
        List<ResourceFilePost> list = resourceFilePostService.selectResourceFilePostList(resourceFilePost);
        return getDataTable(list);
    }

    /**
     * 导出资源文件列表
     */
    @RequiresPermissions("web:resourceFilePost:export")
    @Log(title = "资源文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ResourceFilePost resourceFilePost) {
        List<ResourceFilePost> list = resourceFilePostService.selectResourceFilePostList(resourceFilePost);
        ExcelUtil<ResourceFilePost> util = new ExcelUtil<ResourceFilePost>(ResourceFilePost.class);
        return util.exportExcel(list, "resourceFilePost");
    }

    /**
     * 新增资源文件
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存资源文件
     */
    @RequiresPermissions("web:resourceFilePost:add")
    @Log(title = "资源文件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ResourceFilePost resourceFilePost) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                resourceFilePost.setCompanyId(dept.getDeptId());
            }
        }
        resourceFilePost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        resourceFilePost.setUserId(ShiroUtils.getUserId());
        Long informationCategoryId = resourceFilePost.getResourceFileCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = resourceFilePostService.selectAncestorsById(informationCategoryId, buf);
            resourceFilePost.setAncestors(buf.toString());
        }
        return toAjax(resourceFilePostService.insertResourceFilePost(resourceFilePost));
    }

    /**
     * 修改资源文件
     */
    @GetMapping("/edit/{resourceFilePostId}")
    public String edit(@PathVariable("resourceFilePostId") String resourceFilePostId, ModelMap mmap) {
        ResourceFilePost resourceFilePost = resourceFilePostService.selectResourceFilePostById(resourceFilePostId);
        mmap.put("resourceFilePost", resourceFilePost);
        return prefix + "/edit";
    }

    /**
     * 修改保存资源文件
     */
    @RequiresPermissions("web:resourceFilePost:edit")
    @Log(title = "资源文件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ResourceFilePost resourceFilePost) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                resourceFilePost.setCompanyId(dept.getDeptId());
            }
        }
        Long informationCategoryId = resourceFilePost.getResourceFileCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = resourceFilePostService.selectAncestorsById(informationCategoryId, buf);
            resourceFilePost.setAncestors(buf.toString());
        }
        return toAjax(resourceFilePostService.updateResourceFilePost(resourceFilePost));
    }

    /**
     * 删除资源文件
     */
    @RequiresPermissions("web:resourceFilePost:remove")
    @Log(title = "资源文件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(resourceFilePostService.deleteResourceFilePostByIds(ids));
    }
}
