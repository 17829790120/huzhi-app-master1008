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
import com.wlwq.api.domain.Holidays;
import com.wlwq.api.service.IHolidaysService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 节假日Controller
 *
 * @author gaoce
 * @date 2023-05-25
 */
@Controller
@RequestMapping("/web/holidays")
public class HolidaysController extends BaseController {

    private String prefix = "web/holidays";

    @Autowired
    private IHolidaysService holidaysService;

    @RequiresPermissions("web:holidays:view")
    @GetMapping()
    public String holidays() {
        return prefix + "/holidays";
    }

    /**
     * 查询节假日列表
     */
    @RequiresPermissions("web:holidays:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Holidays holidays) {
        startPage();
        List<Holidays> list = holidaysService.selectHolidaysList(holidays);
        return getDataTable(list);
    }

    /**
     * 导出节假日列表
     */
    @RequiresPermissions("web:holidays:export")
    @Log(title = "节假日", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Holidays holidays) {
        List<Holidays> list = holidaysService.selectHolidaysList(holidays);
        ExcelUtil<Holidays> util = new ExcelUtil<Holidays>(Holidays.class);
        return util.exportExcel(list, "holidays");
    }

    /**
     * 新增节假日
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存节假日
     */
    @RequiresPermissions("web:holidays:add")
    @Log(title = "节假日", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Holidays holidays) {
        return toAjax(holidaysService.insertHolidays(holidays));
    }

    /**
     * 修改节假日
     */
    @GetMapping("/edit/{holidaysId}")
    public String edit(@PathVariable("holidaysId") String holidaysId, ModelMap mmap) {
        Holidays holidays = holidaysService.selectHolidaysById(holidaysId);
        mmap.put("holidays", holidays);
        return prefix + "/edit";
    }

    /**
     * 修改保存节假日
     */
    @RequiresPermissions("web:holidays:edit")
    @Log(title = "节假日", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Holidays holidays) {
        return toAjax(holidaysService.updateHolidays(holidays));
    }

    /**
     * 删除节假日
     */
    @RequiresPermissions("web:holidays:remove")
    @Log(title = "节假日", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(holidaysService.deleteHolidaysByIds(ids));
    }
}
