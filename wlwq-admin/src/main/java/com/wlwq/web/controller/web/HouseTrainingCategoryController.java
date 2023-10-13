package com.wlwq.web.controller.web;

import java.util.ArrayList;
import java.util.List;

import com.wlwq.api.domain.NewsCenterCategory;
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
import com.wlwq.api.domain.HouseTrainingCategory;
import com.wlwq.api.service.IHouseTrainingCategoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 家庭训练管理Controller
 *
 * @author wwb
 * @date 2023-05-15
 */
@Controller
@RequestMapping("/web/houseTrainingCategory")
public class HouseTrainingCategoryController extends BaseController {

    private String prefix = "web/houseTrainingCategory";

    @Autowired
    private IHouseTrainingCategoryService houseTrainingCategoryService;

    @RequiresPermissions("web:houseTrainingCategory:view")
    @GetMapping()
    public String houseTrainingCategory() {
        return prefix + "/houseTrainingCategory";
    }

    /**
     * 查询家庭训练管理列表
     */
    @RequiresPermissions("web:houseTrainingCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public List<HouseTrainingCategory> list(HouseTrainingCategory houseTrainingCategory) {
        List<HouseTrainingCategory> list = houseTrainingCategoryService.selectHouseTrainingCategoryList(houseTrainingCategory);
        return list;
    }

    /**
     * 导出家庭训练管理列表
     */
    @RequiresPermissions("web:houseTrainingCategory:export")
    @Log(title = "家庭训练管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HouseTrainingCategory houseTrainingCategory) {
        List<HouseTrainingCategory> list = houseTrainingCategoryService.selectHouseTrainingCategoryList(houseTrainingCategory);
        ExcelUtil<HouseTrainingCategory> util = new ExcelUtil<HouseTrainingCategory>(HouseTrainingCategory.class);
        return util.exportExcel(list, "houseTrainingCategory");
    }

    /**
     * 新增家庭训练管理
     */
    @GetMapping(value = { "/add/{houseTrainingCategoryId}", "/add/" })
    public String add(@PathVariable(value = "houseTrainingCategoryId", required = false) Long houseTrainingCategoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(houseTrainingCategoryId))
        {
            mmap.put("houseTrainingCategory", houseTrainingCategoryService.selectHouseTrainingCategoryById(houseTrainingCategoryId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存家庭训练管理
     */
    @RequiresPermissions("web:houseTrainingCategory:add")
    @Log(title = "家庭训练管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HouseTrainingCategory houseTrainingCategory) {
        houseTrainingCategory.setUserId(ShiroUtils.getUserId());
        houseTrainingCategory.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return toAjax(houseTrainingCategoryService.insertHouseTrainingCategory(houseTrainingCategory));
    }

    /**
     * 修改家庭训练管理
     */
    @GetMapping("/edit/{houseTrainingCategoryId}")
    public String edit(@PathVariable("houseTrainingCategoryId") Long houseTrainingCategoryId, ModelMap mmap) {
        HouseTrainingCategory houseTrainingCategory = houseTrainingCategoryService.selectHouseTrainingCategoryById(houseTrainingCategoryId);
        mmap.put("houseTrainingCategory", houseTrainingCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存家庭训练管理
     */
    @RequiresPermissions("web:houseTrainingCategory:edit")
    @Log(title = "家庭训练管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HouseTrainingCategory houseTrainingCategory) {
        return toAjax(houseTrainingCategoryService.updateHouseTrainingCategory(houseTrainingCategory));
    }

    /**
     * 删除家庭训练管理
     */
    @RequiresPermissions("web:houseTrainingCategory:remove")
    @Log(title = "家庭训练管理", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{houseTrainingCategoryId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("houseTrainingCategoryId")Long houseTrainingCategoryId) {
        return toAjax(houseTrainingCategoryService.deleteHouseTrainingCategoryById(houseTrainingCategoryId));
    }

    /**
     * 删除家庭训练管理
     */
    @RequiresPermissions("web:houseTrainingCategory:remove")
    @Log(title = "家庭训练管理", businessType = BusinessType.DELETE)
    @PostMapping("/removeAll")
    @ResponseBody
    public AjaxResult removeAll(String ids) {
        return toAjax(houseTrainingCategoryService.deleteHouseTrainingCategoryByIds(ids));
    }

    /**
     * 选择资讯分类树
     */
    @GetMapping(value = { "/selectHouseTrainingCategoryTree/{houseTrainingCategoryId}", "/selectHouseTrainingCategoryTree/" })
    public String selectHouseTrainingCategoryTree(@PathVariable(value = "houseTrainingCategoryId", required = false) Long houseTrainingCategoryId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(houseTrainingCategoryId))
        {
            mmap.put("houseTrainingCategory", houseTrainingCategoryService.selectHouseTrainingCategoryById(houseTrainingCategoryId));
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
        List<HouseTrainingCategory> houseTrainingCategoryList = houseTrainingCategoryService.selectHouseTrainingCategoryList(HouseTrainingCategory
                .builder()
                .delStatus(0)
                .build());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (HouseTrainingCategory houseTrainingCategory : houseTrainingCategoryList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(houseTrainingCategory.getHouseTrainingCategoryId());
            ztree.setpId(houseTrainingCategory.getParentId());
            ztree.setName(houseTrainingCategory.getTitle());
            ztree.setTitle(houseTrainingCategory.getTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
