package com.wlwq.web.controller.web;

import java.util.Date;
import java.util.List;

import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.resultVO.CustomUserVO;
import com.wlwq.api.service.ICustomCustomInfoService;
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
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.service.ICustomUserClaimService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 客户认领Controller
 *
 * @author wlwq
 * @date 2023-06-02
 */
@Controller
@RequestMapping("/web/customUserClaim")
public class CustomUserClaimController extends BaseController {

    private String prefix = "web/customUserClaim";

    @Autowired
    private ICustomUserClaimService customUserClaimService;

    @Autowired
    private ICustomCustomInfoService customCustomInfoService;
    @Autowired
    private ISysDeptService deptService;
    @RequiresPermissions("web:customUserClaim:view")
    @GetMapping()
    public String customUserClaim() {
        return prefix + "/customUserClaim";
    }

    /**
     * 查询客户认领列表
     */
    @RequiresPermissions("web:customUserClaim:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CustomUserClaim customUserClaim) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customUserClaim.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<CustomUserVO> list = customUserClaimService.findCustomUserVO(customUserClaim.getCompanyId().toString(),customUserClaim.getCustomName());
        return getDataTable(list);
    }

    /**
     * 导出客户认领列表
     */
    @RequiresPermissions("web:customUserClaim:export")
    @Log(title = "客户认领", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomUserClaim customUserClaim) {
        List<CustomUserClaim> list = customUserClaimService.selectCustomUserClaimList(customUserClaim);
        ExcelUtil<CustomUserClaim> util = new ExcelUtil<CustomUserClaim>(CustomUserClaim.class);
        return util.exportExcel(list, "customUserClaim");
    }

    /**
     * 新增客户认领
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客户认领
     */
    @RequiresPermissions("web:customUserClaim:add")
    @Log(title = "客户认领", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomUserClaim customUserClaim) {
        return toAjax(customUserClaimService.insertCustomUserClaim(customUserClaim));
    }

    /**
     * 修改客户认领
     */
    @GetMapping("/edit/{customClaimId}")
    public String edit(@PathVariable("customClaimId") Long customClaimId, ModelMap mmap) {
        CustomUserClaim customUserClaim = customUserClaimService.selectCustomUserClaimById(customClaimId);
        mmap.put("customUserClaim", customUserClaim);
        return prefix + "/edit";
    }

    /**
     * 释放客户
     */
    @PostMapping("/release")
    @ResponseBody
    public AjaxResult release(CustomUserClaim customUserClaim) {
        //查询客户  释放到公海  修改客户表状态为为认领
        String customInfoId = customUserClaim.getCustomInfoId();
        CustomCustomInfo customCustomInfo = customCustomInfoService.selectCustomCustomInfoById(customInfoId);
        if(customCustomInfo != null){
            customCustomInfo.setClaimStatus(0L);
            customCustomInfo.setCustomId(customInfoId);
            customCustomInfoService.updateCustomCustomInfo(customCustomInfo);
        }else{
            return AjaxResult.error("该客户不存在");
        }
        customUserClaim.setReleaseTime(new Date());

        int i = customUserClaimService.updateCustomUserClaim(customUserClaim);
        return AjaxResult.success(i);
    }

    /**
     * 修改保存客户认领
     */
    @RequiresPermissions("web:customUserClaim:edit")
    @Log(title = "客户认领", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomUserClaim customUserClaim) {
        return toAjax(customUserClaimService.updateCustomUserClaim(customUserClaim));
    }

    /**
     * 删除客户认领
     */
    @RequiresPermissions("web:customUserClaim:remove")
    @Log(title = "客户认领", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customUserClaimService.deleteCustomUserClaimByIds(ids));
    }
}
