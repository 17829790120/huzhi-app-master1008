package com.wlwq.web.controller.system;

import java.util.List;

import com.wlwq.common.core.domain.entity.SysDept;
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
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.system.domain.SysNotice;
import com.wlwq.system.service.ISysNoticeService;

/**
 * 公告 信息操作处理
 *
 * @author wlwq
 */
@Controller
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    private String prefix = "system/notice";

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice(ModelMap modelMap) {
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        modelMap.put("deptList", deptList);
        return prefix + "/notice";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 新增公告
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        modelMap.put("deptList",deptList);
        return prefix + "/add";
    }

    /**
     * 新增保存公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysNotice notice) {
        notice.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") String noticeId, ModelMap mmap) {
        mmap.put("notice", noticeService.selectNoticeById(noticeId));
        SysDept dept = new SysDept();
        dept.setDeptLevel(-1);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        mmap.put("deptList",deptList);
        return prefix + "/edit";
    }

    /**
     * 修改保存公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysNotice notice) {
        notice.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(noticeService.deleteNoticeByIds(ids));
    }
}
