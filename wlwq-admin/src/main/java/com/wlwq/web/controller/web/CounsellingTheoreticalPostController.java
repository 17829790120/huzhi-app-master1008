package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.CounsellingTheoreticalCategory;
import com.wlwq.api.service.ICounsellingTheoreticalCategoryService;
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
import com.wlwq.api.domain.CounsellingTheoreticalPost;
import com.wlwq.api.service.ICounsellingTheoreticalPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 理论体系资讯Controller
 *
 * @author wwb
 * @date 2023-05-18
 */
@Controller
@RequestMapping("/web/counsellingTheoreticalPost")
public class CounsellingTheoreticalPostController extends BaseController {

    private String prefix = "web/counsellingTheoreticalPost";

    @Autowired
    private ICounsellingTheoreticalPostService counsellingTheoreticalPostService;

    @Autowired
    private ICounsellingTheoreticalCategoryService counsellingTheoreticalCategoryService;

/*    @RequiresPermissions("web:counsellingTheoreticalPost:view")
    @GetMapping()
    public String counsellingTheoreticalPost() {
        return prefix + "/counsellingTheoreticalPost";
    }*/

    @RequiresPermissions("web:counsellingTheoreticalPost:view")
    @GetMapping()
    public String counsellingTheoreticalPost(String counsellingTheoreticalCategoryId,ModelMap modelMap) {
        if (counsellingTheoreticalCategoryId ==null){
            throw new BusinessException("理论体系类别为空！");
        }
        modelMap.put("counsellingTheoreticalCategoryId",counsellingTheoreticalCategoryId);
        return prefix + "/counsellingTheoreticalPost";
    }

    /**
     * 查询理论体系资讯列表
     */
    @RequiresPermissions("web:counsellingTheoreticalPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        startPage();
        List<CounsellingTheoreticalPost> list = counsellingTheoreticalPostService.selectCounsellingTheoreticalPostList(counsellingTheoreticalPost);
        return getDataTable(list);
    }

    /**
     * 导出理论体系资讯列表
     */
    @RequiresPermissions("web:counsellingTheoreticalPost:export")
    @Log(title = "理论体系资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        List<CounsellingTheoreticalPost> list = counsellingTheoreticalPostService.selectCounsellingTheoreticalPostList(counsellingTheoreticalPost);
        ExcelUtil<CounsellingTheoreticalPost> util = new ExcelUtil<CounsellingTheoreticalPost>(CounsellingTheoreticalPost.class);
        return util.exportExcel(list, "counsellingTheoreticalPost");
    }

    /**
     * 新增理论体系资讯
     */
    @GetMapping("/addOld")
    public String addOld(ModelMap modelMap) {
        List<CounsellingTheoreticalCategory> theoreticalCategoryList = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryList(
                CounsellingTheoreticalCategory.builder().delStatus(0).build());
        modelMap.put("theoreticalCategoryList",theoreticalCategoryList);
        return prefix + "/add";
    }

    /**
     * 新增理论体系资讯
     */
    @GetMapping("/add")
    public String add(String counsellingTheoreticalCategoryId,ModelMap modelMap) {
        if (StringUtils.isNotNull(counsellingTheoreticalCategoryId))
        {
            modelMap.put("counsellingTheoreticalCategory", counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryById(counsellingTheoreticalCategoryId));
        }
        if (StringUtils.isNull(counsellingTheoreticalCategoryId))
        {
            throw new BusinessException("理论体系分类为空！");
        }

/*        List<CounsellingTheoreticalCategory> theoreticalCategoryList = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryList(
                CounsellingTheoreticalCategory.builder().delStatus(0).build());
        modelMap.put("theoreticalCategoryList",theoreticalCategoryList);*/
        modelMap.put("counsellingTheoreticalCategoryId",counsellingTheoreticalCategoryId);
        return prefix + "/add";
    }

    /**
     * 新增保存理论体系资讯
     */
    @RequiresPermissions("web:counsellingTheoreticalPost:add")
    @Log(title = "理论体系资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        return toAjax(counsellingTheoreticalPostService.insertCounsellingTheoreticalPost(counsellingTheoreticalPost));
    }

    /**
     * 修改理论体系资讯
     */
    @GetMapping("/edit/{counsellingTheoreticalPostId}")
    public String edit(@PathVariable("counsellingTheoreticalPostId") String counsellingTheoreticalPostId, ModelMap mmap) {
        CounsellingTheoreticalPost counsellingTheoreticalPost = counsellingTheoreticalPostService.selectCounsellingTheoreticalPostById(counsellingTheoreticalPostId);
        mmap.put("counsellingTheoreticalPost", counsellingTheoreticalPost);
        if (counsellingTheoreticalPost != null && StringUtils.isNotNull(counsellingTheoreticalPost.getCounsellingTheoreticalCategoryId()))
        {
            mmap.put("counsellingTheoreticalCategory", counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryById(counsellingTheoreticalPost.getCounsellingTheoreticalCategoryId()));
        }
/*        List<CounsellingTheoreticalCategory> theoreticalCategoryList = counsellingTheoreticalCategoryService.selectCounsellingTheoreticalCategoryList(
                CounsellingTheoreticalCategory.builder().delStatus(0).build());
        mmap.put("theoreticalCategoryList",theoreticalCategoryList);*/
        return prefix + "/edit";
    }

    /**
     * 修改保存理论体系资讯
     */
    @RequiresPermissions("web:counsellingTheoreticalPost:edit")
    @Log(title = "理论体系资讯", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        return toAjax(counsellingTheoreticalPostService.updateCounsellingTheoreticalPost(counsellingTheoreticalPost));
    }

    /**
     * 删除理论体系资讯
     */
    @RequiresPermissions("web:counsellingTheoreticalPost:remove")
    @Log(title = "理论体系资讯", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(counsellingTheoreticalPostService.deleteCounsellingTheoreticalPostByIds(ids));
    }
}
