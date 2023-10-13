package com.wlwq.web.controller.web;

import java.security.Security;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.CustomLevelDays;
import com.wlwq.api.domain.CustomType;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.service.ICustomLevelDaysService;
import com.wlwq.api.service.ICustomTypeService;
import com.wlwq.api.service.ICustomUserClaimService;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.domain.entity.SysUser;
import com.wlwq.common.utils.ShiroUtils;
import com.wlwq.system.service.ISysDeptService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.service.ICustomCustomInfoService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 客户Controller
 *
 * @author wlwq
 * @date 2023-06-02
 */
@Controller
@RequestMapping("/web/customCustomInfo")
public class CustomCustomInfoController extends BaseController {

    private String prefix = "web/customCustomInfo";

    @Autowired
    private ICustomCustomInfoService customCustomInfoService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ICustomUserClaimService customUserClaimService;
    @Autowired
    private ICustomTypeService customTypeService;
    @Autowired
    private ICustomLevelDaysService customLevelDaysService;
    @RequiresPermissions("web:customCustomInfo:view")
    @GetMapping()
    public String customCustomInfo() {
        return prefix + "/customCustomInfo";
    }

    /**
     * 查询客户列表
     */
    @RequiresPermissions("web:customCustomInfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CustomCustomInfo customCustomInfo) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if (deptId != null) {
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if (dept != null) {
                customCustomInfo.setCompanyId(dept.getDeptId());
            }
        }
        startPage();
        List<CustomCustomInfo> list = customCustomInfoService.selectCustomCustomInfoList(customCustomInfo);
        return getDataTable(list);
    }

    @GetMapping("/allAccount")
    public String allAccount(ModelMap mmap) {
        SysUser sysUser = ShiroUtils.getSysUser();
        mmap.put("deptId", sysUser.getDeptId());
        mmap.put("userId", sysUser.getUserId());
        return prefix + "/accountDept";
    }

    /**
     * 导出客户列表
     */
    @RequiresPermissions("web:customCustomInfo:export")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomCustomInfo customCustomInfo) {
        List<CustomCustomInfo> list = customCustomInfoService.selectCustomCustomInfoList(customCustomInfo);
        ExcelUtil<CustomCustomInfo> util = new ExcelUtil<CustomCustomInfo>(CustomCustomInfo.class);
        return util.exportExcel(list, "customCustomInfo");
    }

    /**
     * 新增客户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        //客户标签
        CustomType customType = new CustomType();
        CustomLevelDays customLevelDays=new CustomLevelDays();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customType.setCompanyId(dept.getDeptId());
                customLevelDays.setCompanyId(dept.getDeptId());
            }
        }
        List<CustomType> customTypes = customTypeService.selectCustomTypeList(customType);
        mmap.put("customTypes",customTypes);
        //客户等级
        List<CustomLevelDays> customLevelDaysList = customLevelDaysService.selectCustomLevelDaysList(customLevelDays);
        mmap.put("customLevelDaysList",customLevelDaysList);
        return prefix + "/add";
    }

    /**
     * 新增保存客户
     */
    @RequiresPermissions("web:customCustomInfo:add")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomCustomInfo customCustomInfo) throws ParseException {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser == null) {
            return AjaxResult.error("该登录用户不存在！");
        }
        customCustomInfo.setAddSource(0L);
        customCustomInfo.setClaimStatus(0L);
        customCustomInfo.setCreateBy(sysUser.getUserId().toString());
        customCustomInfo.setDeptId(sysUser.getDeptId());
        if(sysUser.getDeptId() != null){
            SysDept dept = deptService.selectDeptByDeptId(sysUser.getDeptId());
            if(dept != null){
                customCustomInfo.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(customCustomInfoService.insertCustomCustomInfo(customCustomInfo));
    }

    /**
     * 修改客户
     */
    @GetMapping("/edit/{customId}")
    public String edit(@PathVariable("customId") String customId, ModelMap mmap) {
        CustomCustomInfo customCustomInfo = customCustomInfoService.selectCustomCustomInfoById(customId);
        mmap.put("customCustomInfo", customCustomInfo);
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        //客户标签
        CustomType customType = new CustomType();
        CustomLevelDays customLevelDays=new CustomLevelDays();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                customType.setCompanyId(dept.getDeptId());
                customLevelDays.setCompanyId(dept.getDeptId());
            }
        }
        List<CustomType> customTypes = customTypeService.selectCustomTypeList(customType);
        mmap.put("customTypes",customTypes);
        //客户等级
        List<CustomLevelDays> customLevelDaysList = customLevelDaysService.selectCustomLevelDaysList(customLevelDays);
        mmap.put("customLevelDaysList",customLevelDaysList);
        return prefix + "/edit";
    }


    @Log(title = "客户认领", businessType = BusinessType.UPDATE)
    @PostMapping("/editcliam")
    @ResponseBody
    public AjaxResult editcustom(CustomCustomInfo customCustomInfo) {
        SysUser sysUser = ShiroUtils.getSysUser();
        if (sysUser == null) {
            return AjaxResult.error("该登录用户不存在！");
        }
        int i = customCustomInfoService.updateCustomCustomInfo(customCustomInfo);
        if (i > 0) {
            Long userId = sysUser.getUserId();
            CustomUserClaim customUserClaim = new CustomUserClaim();
            customUserClaim.setCreateBy(userId.toString());
            customUserClaim.setCustomInfoId(customCustomInfo.getCustomId());
            customUserClaim.setCustomUserId(userId.toString());
            customUserClaim.setStatus("0");
            customUserClaim.setClaimTime(new Date());
            customUserClaim.setCustomClaimId(IdUtil.getSnowflake(1, 1).nextIdStr());
            customUserClaimService.insertCustomUserClaim(customUserClaim);
        }
        return AjaxResult.success(i);
    }

    /**
     * 修改保存客户
     */
    @RequiresPermissions("web:customCustomInfo:edit")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomCustomInfo customCustomInfo) {
        return toAjax(customCustomInfoService.updateCustomCustomInfo(customCustomInfo));
    }

    /**
     * 删除客户
     */
    @RequiresPermissions("web:customCustomInfo:remove")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customCustomInfoService.deleteCustomCustomInfoByIds(ids));
    }
}
