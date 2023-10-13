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
import com.wlwq.api.domain.GroupInfor;
import com.wlwq.api.service.IGroupInforService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 小组信息Controller
 *
 * @author wwb
 * @date 2023-06-10
 */
@Controller
@RequestMapping("/web/groupInfor")
public class GroupInforController extends BaseController {

    private String prefix = "web/groupInfor";

    @Autowired
    private IGroupInforService groupInforService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:groupInfor:view")
    @GetMapping()
    public String groupInfor(Long companyId,ModelMap modelMap) {
        if(companyId == null){
            Long deptId = ShiroUtils.getSysUser().getDeptId();
            if(deptId != null){
                SysDept dept = deptService.selectDeptByDeptId(deptId);
                if(dept != null){
                    companyId=dept.getDeptId();
                }
            }
        }
        modelMap.put("companyId",companyId);
        return prefix + "/groupInfor";
    }

    /**
     * 查询小组信息列表
     */
    @RequiresPermissions("web:groupInfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GroupInfor groupInfor) {
/*        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                groupInfor.setCompanyId(dept.getDeptId());
            }
        }*/
        startPage();
        List<GroupInfor> list = groupInforService.selectGroupInforList(groupInfor);
        return getDataTable(list);
    }

    /**
     * 导出小组信息列表
     */
    @RequiresPermissions("web:groupInfor:export")
    @Log(title = "小组信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroupInfor groupInfor) {
        List<GroupInfor> list = groupInforService.selectGroupInforList(groupInfor);
        ExcelUtil<GroupInfor> util = new ExcelUtil<GroupInfor>(GroupInfor.class);
        return util.exportExcel(list, "groupInfor");
    }

    /**
     * 新增小组信息
     */
    @GetMapping("/add")
    public String add(Long companyId,ModelMap modelMap) {
        modelMap.put("companyId",companyId);
        return prefix + "/add";
    }

    /**
     * 新增保存小组信息
     */
    @RequiresPermissions("web:groupInfor:add")
    @Log(title = "小组信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GroupInfor groupInfor) {
        groupInfor.setCreatorId(ShiroUtils.getUserId());
        groupInfor.setCreatorHeadPortrait(ShiroUtils.getSysUser().getAvatar());
        groupInfor.setCreatorNickName(ShiroUtils.getSysUser().getUserName());
/*        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                groupInfor.setCompanyId(dept.getDeptId());
            }
        }*/
        return toAjax(groupInforService.insertGroupInfor(groupInfor));
    }

    /**
     * 修改小组信息
     */
    @GetMapping("/edit/{groupInforId}")
    public String edit(@PathVariable("groupInforId") Long groupInforId, ModelMap mmap) {
        GroupInfor groupInfor = groupInforService.selectGroupInforById(groupInforId);
        mmap.put("groupInfor", groupInfor);
        return prefix + "/edit";
    }

    /**
     * 修改保存小组信息
     */
    @RequiresPermissions("web:groupInfor:edit")
    @Log(title = "小组信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GroupInfor groupInfor) {
        groupInfor.setCreatorId(ShiroUtils.getUserId());
        groupInfor.setCreatorHeadPortrait(ShiroUtils.getSysUser().getAvatar());
        groupInfor.setCreatorNickName(ShiroUtils.getSysUser().getUserName());
/*        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                groupInfor.setCompanyId(dept.getDeptId());
            }
        }*/
        return toAjax(groupInforService.updateGroupInfor(groupInfor));
    }

    /**
     * 删除小组信息
     */
    @RequiresPermissions("web:groupInfor:remove")
    @Log(title = "小组信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(groupInforService.deleteGroupInforByIds(ids));
    }
}
