package com.wlwq.web.controller.web;

import java.util.List;

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
import com.wlwq.api.domain.SysDeptScore;
import com.wlwq.api.service.ISysDeptScoreService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 公司积分设置Controller
 *
 * @author gaoce
 * @date 2023-06-06
 */
@Controller
@RequestMapping("/web/sysDeptScore")
public class SysDeptScoreController extends BaseController {

    private String prefix = "web/sysDeptScore";

    @Autowired
    private ISysDeptScoreService sysDeptScoreService;

    /**
     *
     * @param deptId 公司ID
     * @return
     */
    @RequiresPermissions("web:sysDeptScore:view")
    @GetMapping()
    public String sysDeptScore(Long deptId,ModelMap modelMap) {
        modelMap.put("deptId",deptId);
        return prefix + "/sysDeptScore";
    }

    /**
     * 查询公司积分设置列表
     */
    @RequiresPermissions("web:sysDeptScore:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDeptScore sysDeptScore) {
        startPage();
        List<SysDeptScore> list = sysDeptScoreService.selectSysDeptScoreList(sysDeptScore);
        return getDataTable(list);
    }

    /**
     * 导出公司积分设置列表
     */
    @RequiresPermissions("web:sysDeptScore:export")
    @Log(title = "公司积分设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDeptScore sysDeptScore) {
        List<SysDeptScore> list = sysDeptScoreService.selectSysDeptScoreList(sysDeptScore);
        ExcelUtil<SysDeptScore> util = new ExcelUtil<SysDeptScore>(SysDeptScore.class);
        return util.exportExcel(list, "sysDeptScore");
    }

    /**
     * 新增公司积分设置
     * @param deptId 公司ID
     */
    @GetMapping("/add")
    public String add(Long deptId,ModelMap modelMap) {
        modelMap.put("deptId",deptId);
        return prefix + "/add";
    }

    /**
     * 新增保存公司积分设置
     */
    @RequiresPermissions("web:sysDeptScore:add")
    @Log(title = "公司积分设置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDeptScore sysDeptScore) {
        return toAjax(sysDeptScoreService.insertSysDeptScore(sysDeptScore));
    }

    /**
     * 修改公司积分设置
     */
    @GetMapping("/edit/{sysDeptScoreId}")
    public String edit(@PathVariable("sysDeptScoreId") String sysDeptScoreId, ModelMap mmap) {
        SysDeptScore sysDeptScore = sysDeptScoreService.selectSysDeptScoreById(sysDeptScoreId);
        mmap.put("sysDeptScore", sysDeptScore);
        return prefix + "/edit";
    }

    /**
     * 修改保存公司积分设置
     */
    @RequiresPermissions("web:sysDeptScore:edit")
    @Log(title = "公司积分设置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDeptScore sysDeptScore) {
        return toAjax(sysDeptScoreService.updateSysDeptScore(sysDeptScore));
    }

    /**
     * 删除公司积分设置
     */
    @RequiresPermissions("web:sysDeptScore:remove")
    @Log(title = "公司积分设置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysDeptScoreService.deleteSysDeptScoreByIds(ids));
    }
}
