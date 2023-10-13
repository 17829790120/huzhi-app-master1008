package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.HouseTrainingCategory;
import com.wlwq.api.service.IHouseTrainingCategoryService;
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
import com.wlwq.api.domain.HouseTrainingPost;
import com.wlwq.api.service.IHouseTrainingPostService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 家庭训练资讯Controller
 *
 * @author wwb
 * @date 2023-05-15
 */
@Controller
@RequestMapping("/web/houseTrainingPost")
public class HouseTrainingPostController extends BaseController {

    private String prefix = "web/houseTrainingPost";

    @Autowired
    private IHouseTrainingPostService houseTrainingPostService;
    @Autowired
    private IHouseTrainingCategoryService houseTrainingCategoryService;

    @RequiresPermissions("web:houseTrainingPost:view")
    @GetMapping()
    public String houseTrainingPost() {
        return prefix + "/houseTrainingPost";
    }

    /**
     * 查询家庭训练资讯列表
     */
    @RequiresPermissions("web:houseTrainingPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HouseTrainingPost houseTrainingPost) {
        startPage();
        List<HouseTrainingPost> list = houseTrainingPostService.selectHouseTrainingPostList(houseTrainingPost);
        return getDataTable(list);
    }

    /**
     * 导出家庭训练资讯列表
     */
    @RequiresPermissions("web:houseTrainingPost:export")
    @Log(title = "家庭训练资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HouseTrainingPost houseTrainingPost) {
        List<HouseTrainingPost> list = houseTrainingPostService.selectHouseTrainingPostList(houseTrainingPost);
        ExcelUtil<HouseTrainingPost> util = new ExcelUtil<HouseTrainingPost>(HouseTrainingPost.class);
        return util.exportExcel(list, "houseTrainingPost");
    }

    /**
     * 新增家庭训练资讯
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存家庭训练资讯
     */
    @RequiresPermissions("web:houseTrainingPost:add")
    @Log(title = "家庭训练资讯", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HouseTrainingPost houseTrainingPost) {
        houseTrainingPost.setDeptId(ShiroUtils.getSysUser().getDeptId());
        houseTrainingPost.setUserId(ShiroUtils.getUserId());

        Long informationCategoryId = houseTrainingPost.getInformationCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = houseTrainingCategoryService.selectHouseTrainingAncestorsById(informationCategoryId, buf);
            houseTrainingPost.setAncestors(buf.toString());
        }
        return toAjax(houseTrainingPostService.insertHouseTrainingPost(houseTrainingPost));
    }

    /**
     * 修改家庭训练资讯
     */
    @GetMapping("/edit/{houseTrainingPostId}")
    public String edit(@PathVariable("houseTrainingPostId") Long houseTrainingPostId, ModelMap mmap) {
        HouseTrainingPost houseTrainingPost = houseTrainingPostService.selectHouseTrainingPostById(houseTrainingPostId);
        mmap.put("houseTrainingPost", houseTrainingPost);
        return prefix + "/edit";
    }

    /**
     * 修改保存家庭训练资讯
     */
    @RequiresPermissions("web:houseTrainingPost:edit")
    @Log(title = "家庭训练资讯", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HouseTrainingPost houseTrainingPost) {
        Long informationCategoryId = houseTrainingPost.getInformationCategoryId();
        if (informationCategoryId != null) {
            StringBuffer buf = new StringBuffer();
            buf = houseTrainingCategoryService.selectHouseTrainingAncestorsById(informationCategoryId, buf);
            houseTrainingPost.setAncestors(buf.toString());
        }
        return toAjax(houseTrainingPostService.updateHouseTrainingPost(houseTrainingPost));
    }

    /**
     * 删除家庭训练资讯
     */
    @RequiresPermissions("web:houseTrainingPost:remove")
    @Log(title = "家庭训练资讯", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(houseTrainingPostService.deleteHouseTrainingPostByIds(ids));
    }
}
