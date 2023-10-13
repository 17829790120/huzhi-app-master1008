package com.wlwq.web.controller.system;

import com.wlwq.api.domain.Attachments;
import com.wlwq.api.service.IAttachmentsService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 附件Controller
 *
 * @author Renbowen
 * @date 2020-09-26
 */
@Controller
@RequestMapping("/system/attachments")
public class AttachmentsController extends BaseController
{
    private String prefix = "system/attachments";

    @Autowired
    private IAttachmentsService attachmentsService;

    @RequiresPermissions("api:attachments:view")
    @GetMapping()
    public String attachments()
    {
        return prefix + "/attachments";
    }

    /**
     * 查询附件列表
     */
    @RequiresPermissions("api:attachments:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Attachments attachments)
    {
        startPage();
        List<Attachments> list = attachmentsService.selectAttachmentsList(attachments);
        return getDataTable(list);
    }

    /**
     * 导出附件列表
     */
    @RequiresPermissions("api:attachments:export")
    @Log(title = "附件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Attachments attachments)
    {
        List<Attachments> list = attachmentsService.selectAttachmentsList(attachments);
        ExcelUtil<Attachments> util = new ExcelUtil<Attachments>(Attachments.class);
        return util.exportExcel(list, "attachments");
    }

    /**
     * 新增附件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存附件
     */
    @RequiresPermissions("api:attachments:add")
    @Log(title = "附件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Attachments attachments)
    {
        return toAjax(attachmentsService.insertAttachments(attachments));
    }

    /**
     * 修改附件
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Attachments attachments = attachmentsService.selectAttachmentsById(id);
        mmap.put("attachments", attachments);
        return prefix + "/edit";
    }

    /**
     * 修改保存附件
     */
    @RequiresPermissions("api:attachments:edit")
    @Log(title = "附件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Attachments attachments)
    {
        return toAjax(attachmentsService.updateAttachments(attachments));
    }

    /**
     * 删除附件
     */
    @RequiresPermissions("api:attachments:remove")
    @Log(title = "附件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(attachmentsService.deleteAttachmentsByIds(ids));
    }
}
