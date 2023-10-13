package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.service.ICounsellingExecutionCategoryService;
import com.wlwq.common.exception.BusinessException;
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
import com.wlwq.api.domain.CounsellingExecutionPost;
import com.wlwq.api.service.ICounsellingExecutionPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 辅导实施资讯Controller
 *
 * @author wwb
 * @date 2023-05-18
 */
@Controller
@RequestMapping("/web/counsellingExecutionPost")
public class CounsellingExecutionPostController extends BaseController {

    private String prefix = "web/counsellingExecutionPost";

    @Autowired
    private ICounsellingExecutionPostService counsellingExecutionPostService;

    @Autowired
    private ICounsellingExecutionCategoryService counsellingExecutionCategoryService;

/*    @RequiresPermissions("web:counsellingExecutionPost:view")
    @GetMapping()
    public String counsellingExecutionPost() {
        return prefix + "/counsellingExecutionPost";
    }*/

    @RequiresPermissions("web:counsellingExecutionPost:view")
    @GetMapping()
    public String counsellingExecutionPost(String counsellingExecutionCategoryId,ModelMap modelMap) {
        if (counsellingExecutionCategoryId ==null){
            throw new BusinessException("辅导实施类别为空！");
        }
        modelMap.put("counsellingExecutionCategoryId",counsellingExecutionCategoryId);
        return prefix + "/counsellingExecutionPost";
    }

    /**
     * 查询辅导实施资讯列表
     */
    @RequiresPermissions("web:counsellingExecutionPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CounsellingExecutionPost counsellingExecutionPost) {
        startPage();
        List<CounsellingExecutionPost> list = counsellingExecutionPostService.selectCounsellingExecutionPostList(counsellingExecutionPost);
        return getDataTable(list);
    }

    /**
     * 导出辅导实施资讯列表
     */
    @RequiresPermissions("web:counsellingExecutionPost:export")
    @Log(title = "辅导实施资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CounsellingExecutionPost counsellingExecutionPost) {
        List<CounsellingExecutionPost> list = counsellingExecutionPostService.selectCounsellingExecutionPostList(counsellingExecutionPost);
        ExcelUtil<CounsellingExecutionPost> util = new ExcelUtil<CounsellingExecutionPost>(CounsellingExecutionPost.class);
        return util.exportExcel(list, "counsellingExecutionPost");
    }

    /**
     * 新增辅导实施资讯
     */
    @GetMapping("/addOld")
    public String addOld() {
        return prefix + "/add";
    }

    /**
     * 新增辅导实施资讯
     */
    @GetMapping("/add")
    public String add(String counsellingExecutionCategoryId,ModelMap modelMap) {
        if (StringUtils.isNotNull(counsellingExecutionCategoryId))
        {
            modelMap.put("counsellingExecutionCategory", counsellingExecutionCategoryService.selectCounsellingExecutionCategoryById(counsellingExecutionCategoryId));
        }
        if (StringUtils.isNull(counsellingExecutionCategoryId))
        {
            throw new BusinessException("辅导实施类别为空！");
        }
        modelMap.put("counsellingExecutionCategoryId",counsellingExecutionCategoryId);
        return prefix + "/add";
    }

    /**
     * 新增保存辅导实施资讯
     */
    @RequiresPermissions("web:counsellingExecutionPost:add")
    @Log(title = "辅导实施资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CounsellingExecutionPost counsellingExecutionPost) {
        return toAjax(counsellingExecutionPostService.insertCounsellingExecutionPost(counsellingExecutionPost));
    }

    /**
     * 修改辅导实施资讯
     */
    @GetMapping("/edit/{counsellingExecutionPostId}")
    public String edit(@PathVariable("counsellingExecutionPostId") String counsellingExecutionPostId, ModelMap mmap) {
        CounsellingExecutionPost counsellingExecutionPost = counsellingExecutionPostService.selectCounsellingExecutionPostById(counsellingExecutionPostId);
        mmap.put("counsellingExecutionPost", counsellingExecutionPost);

        if (counsellingExecutionPost != null && StringUtils.isNotNull(counsellingExecutionPost.getCounsellingExecutionCategoryId()))
        {
            mmap.put("counsellingExecutionCategory", counsellingExecutionCategoryService.selectCounsellingExecutionCategoryById(counsellingExecutionPost.getCounsellingExecutionCategoryId()));
        }
        return prefix + "/edit";
    }

    /**
     * 修改保存辅导实施资讯
     */
    @RequiresPermissions("web:counsellingExecutionPost:edit")
    @Log(title = "辅导实施资讯", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CounsellingExecutionPost counsellingExecutionPost) {
        return toAjax(counsellingExecutionPostService.updateCounsellingExecutionPost(counsellingExecutionPost));
    }

    /**
     * 删除辅导实施资讯
     */
    @RequiresPermissions("web:counsellingExecutionPost:remove")
    @Log(title = "辅导实施资讯", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(counsellingExecutionPostService.deleteCounsellingExecutionPostByIds(ids));
    }
}
