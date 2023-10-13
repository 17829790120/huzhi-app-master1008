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
import com.wlwq.api.domain.LiveVideo;
import com.wlwq.api.service.ILiveVideoService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 直播列表Controller
 *
 * @author wwb
 * @date 2023-05-13
 */
@Controller
@RequestMapping("/web/liveVideo")
public class LiveVideoController extends BaseController {

    private String prefix = "web/liveVideo";

    @Autowired
    private ILiveVideoService liveVideoService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:liveVideo:view")
    @GetMapping()
    public String liveVideo() {
        return prefix + "/liveVideo";
    }

    /**
     * 查询直播列表列表
     */
    @RequiresPermissions("web:liveVideo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(LiveVideo liveVideo) {
        startPage();
        List<LiveVideo> list = liveVideoService.selectLiveVideoList(liveVideo);
        return getDataTable(list);
    }

    /**
     * 导出直播列表列表
     */
    @RequiresPermissions("web:liveVideo:export")
    @Log(title = "直播列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(LiveVideo liveVideo) {
        List<LiveVideo> list = liveVideoService.selectLiveVideoList(liveVideo);
        ExcelUtil<LiveVideo> util = new ExcelUtil<LiveVideo>(LiveVideo.class);
        return util.exportExcel(list, "liveVideo");
    }

    /**
     * 新增直播列表
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存直播列表
     */
    @RequiresPermissions("web:liveVideo:add")
    @Log(title = "直播列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(LiveVideo liveVideo) {
        liveVideo.setDeptId(ShiroUtils.getSysUser().getDeptId());
        liveVideo.setUserId(ShiroUtils.getUserId());
        SysDept sysDept = deptService.selectDeptByDeptId(ShiroUtils.getSysUser().getDeptId());
        if(sysDept != null){
            liveVideo.setCompanyId(sysDept.getDeptId());
        }
        return toAjax(liveVideoService.insertLiveVideo(liveVideo));
    }

    /**
     * 修改直播列表
     */
    @GetMapping("/edit/{liveVideoId}")
    public String edit(@PathVariable("liveVideoId") String liveVideoId, ModelMap mmap) {
        LiveVideo liveVideo = liveVideoService.selectLiveVideoById(liveVideoId);
        mmap.put("liveVideo", liveVideo);
        return prefix + "/edit";
    }

    /**
     * 修改保存直播列表
     */
    @RequiresPermissions("web:liveVideo:edit")
    @Log(title = "直播列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(LiveVideo liveVideo) {
        return toAjax(liveVideoService.updateLiveVideo(liveVideo));
    }

    /**
     * 删除直播列表
     */
    @RequiresPermissions("web:liveVideo:remove")
    @Log(title = "直播列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(liveVideoService.deleteLiveVideoByIds(ids));
    }
}
