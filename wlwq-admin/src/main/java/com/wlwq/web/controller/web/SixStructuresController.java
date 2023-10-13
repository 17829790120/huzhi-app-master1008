package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.utils.JsonUtils;
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
import com.wlwq.api.domain.SixStructures;
import com.wlwq.api.service.ISixStructuresService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 六大架构Controller
 *
 * @author wlwq
 * @date 2023-08-28
 */
@Controller
@RequestMapping("/web/sixStructures")
public class SixStructuresController extends BaseController {

    private String prefix = "web/sixStructures";

    @Autowired
    private ISixStructuresService sixStructuresService;

    @RequiresPermissions("web:sixStructures:view")
    @GetMapping()
    public String sixStructures() {
        return prefix + "/sixStructures";
    }

    /**
     * 查询六大架构列表
     */
    @RequiresPermissions("web:sixStructures:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SixStructures sixStructures) {
        if(ShiroUtils.getUserId()!=1){
            sixStructures.setCompanyId(ShiroUtils.getSysUser().getDeptId());
        }
        startPage();
        List<SixStructures> list = sixStructuresService.selectWebSixStructuresList(sixStructures);
        return getDataTable(list);
    }

    /**
     * 导出六大架构列表
     */
    @RequiresPermissions("web:sixStructures:export")
    @Log(title = "六大架构", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SixStructures sixStructures) {
        if(ShiroUtils.getUserId()!=1){
            sixStructures.setCompanyId(ShiroUtils.getSysUser().getDeptId());
        }
        List<SixStructures> list = sixStructuresService.selectSixStructuresList(sixStructures);
        ExcelUtil<SixStructures> util = new ExcelUtil<SixStructures>(SixStructures.class);
        return util.exportExcel(list, "sixStructures");
    }

    /**
     * 新增六大架构
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存六大架构
     */
    @RequiresPermissions("web:sixStructures:add")
    @Log(title = "六大架构", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SixStructures sixStructures) {
        return toAjax(sixStructuresService.insertSixStructures(sixStructures));
    }

    /**
     * 修改六大架构
     */
    @GetMapping("/edit/{sixStructuresId}")
    public String edit(@PathVariable("sixStructuresId") String sixStructuresId, ModelMap mmap) {
        SixStructures sixStructures = sixStructuresService.selectSixStructuresById(sixStructuresId);
        mmap.put("sixStructures", sixStructures);
        return prefix + "/edit";
    }

    /**
     * 修改保存六大架构
     */
    @RequiresPermissions("web:sixStructures:edit")
    @Log(title = "六大架构", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SixStructures sixStructures) {
        return toAjax(sixStructuresService.updateSixStructures(sixStructures));
    }

    /**
     * 删除六大架构
     */
    @RequiresPermissions("web:sixStructures:remove")
    @Log(title = "六大架构", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sixStructuresService.deleteSixStructuresByIds(ids));
    }
}
