package com.wlwq.information.controller;

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
import com.wlwq.information.domain.InformationCommentRecord;
import com.wlwq.information.service.IInformationCommentRecordService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.core.domain.Ztree;

/**
 * 资讯评论记录Controller
 *
 * @author Rick wlwq
 * @date 2021-04-21
 */
@Controller
@RequestMapping("/information/informationCommentRecord")
public class InformationCommentRecordWebController extends BaseController
{
    private String prefix = "information/informationCommentRecord";

    @Autowired
    private IInformationCommentRecordService informationCommentRecordService;

    @RequiresPermissions("information:informationCommentRecord:view")
    @GetMapping()
    public String informationCommentRecord(Long informationCommentId,ModelMap modelMap)
    {
        modelMap.put("informationCommentId",informationCommentId);
        return prefix + "/informationCommentRecord";
    }

    /**
     * 查询资讯评论记录树列表
     */
    @RequiresPermissions("information:informationCommentRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public List<InformationCommentRecord> list(InformationCommentRecord informationCommentRecord)
    {
        return informationCommentRecordService.selectInformationCommentRecordList(informationCommentRecord);
    }

    /**
     * 导出资讯评论记录列表
     */
    @RequiresPermissions("information:informationCommentRecord:export")
    @Log(title = "资讯评论记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(InformationCommentRecord informationCommentRecord)
    {
        List<InformationCommentRecord> list = informationCommentRecordService.selectInformationCommentRecordList(informationCommentRecord);
        ExcelUtil<InformationCommentRecord> util = new ExcelUtil<InformationCommentRecord>(InformationCommentRecord.class);
        return util.exportExcel(list, "informationCommentRecord");
    }

    /**
     * 新增资讯评论记录
     */
    @GetMapping(value = { "/add/{informationCommentId}", "/add/" })
    public String add(@PathVariable(value = "informationCommentId", required = false) Long informationCommentId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(informationCommentId))
        {
            mmap.put("informationCommentRecord", informationCommentRecordService.selectInformationCommentRecordById(informationCommentId));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存资讯评论记录
     */
    @RequiresPermissions("information:informationCommentRecord:add")
    @Log(title = "资讯评论记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(InformationCommentRecord informationCommentRecord)
    {
        return toAjax(informationCommentRecordService.insertInformationCommentRecord(informationCommentRecord));
    }

    /**
     * 修改资讯评论记录
     */
    @GetMapping("/edit/{informationCommentId}")
    public String edit(@PathVariable("informationCommentId") Long informationCommentId, ModelMap mmap)
    {
        InformationCommentRecord informationCommentRecord = informationCommentRecordService.selectInformationCommentRecordById(informationCommentId);
        mmap.put("informationCommentRecord", informationCommentRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存资讯评论记录
     */
    @RequiresPermissions("information:informationCommentRecord:edit")
    @Log(title = "资讯评论记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(InformationCommentRecord informationCommentRecord)
    {
        return toAjax(informationCommentRecordService.updateInformationCommentRecord(informationCommentRecord));
    }

    /**
     * 删除
     */
    @RequiresPermissions("information:informationCommentRecord:remove")
    @Log(title = "资讯评论记录", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{informationCommentId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("informationCommentId") Long informationCommentId)
    {
        return toAjax(informationCommentRecordService.deleteInformationCommentRecordById(informationCommentId));
    }

    /**
     * 选择资讯评论记录树
     */
    @GetMapping(value = { "/selectInformationCommentRecordTree/{informationCommentId}", "/selectInformationCommentRecordTree/" })
    public String selectInformationCommentRecordTree(@PathVariable(value = "informationCommentId", required = false) Long informationCommentId, ModelMap mmap)
    {
        if (StringUtils.isNotNull(informationCommentId))
        {
            mmap.put("informationCommentRecord", informationCommentRecordService.selectInformationCommentRecordById(informationCommentId));
        }
        return prefix + "/tree";
    }

    /**
     * 加载资讯评论记录树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = informationCommentRecordService.selectInformationCommentRecordTree();
        return ztrees;
    }
}
