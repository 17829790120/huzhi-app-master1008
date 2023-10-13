package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.StringUtils;
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
import com.wlwq.api.domain.Armory;
import com.wlwq.api.service.IArmoryService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 英雄榜列表Controller
 *
 * @author wwb
 * @date 2023-04-18
 */
@Controller
@RequestMapping("/web/armory")
public class ArmoryController extends BaseController {

    private String prefix = "web/armory";

    @Autowired
    private IArmoryService armoryService;

    @Autowired
    private IApiAccountService apiAccountService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:armory:view")
    @GetMapping()
    public String armory() {
        return prefix + "/armory";
    }

    /**
     * 查询英雄榜列表列表
     */
    @RequiresPermissions("web:armory:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Armory armory) {
        startPage();
        List<Armory> list = armoryService.selectArmoryList(armory);
        return getDataTable(list);
    }

    /**
     * 导出英雄榜列表列表
     */
    @RequiresPermissions("web:armory:export")
    @Log(title = "英雄榜列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Armory armory) {
        List<Armory> list = armoryService.selectArmoryList(armory);
        ExcelUtil<Armory> util = new ExcelUtil<Armory>(Armory.class);
        return util.exportExcel(list, "armory");
    }

    /**
     * 新增英雄榜列表
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        SysDept dept = new SysDept();
        //dept.setDeptLevel(-1);
        //dept.setDeptLevel(3);
        dept.setDeptLevel(0);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        List<ApiAccount> accountList = apiAccountService.selectApiAccountList(ApiAccount.builder().delStatus(0).build());
        modelMap.put("accountList",accountList);
        modelMap.put("deptList",deptList);
        return prefix + "/add";
    }

    /**
     * 新增保存英雄榜列表
     */
    @RequiresPermissions("web:armory:add")
    @Log(title = "英雄榜列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Armory armory) {
        String accountId = armory.getAccountId();
        if(StringUtils.isEmpty(accountId)){
            return error("请选择上榜用户");
        }
        ApiAccount account = apiAccountService.selectApiAccountById(accountId);
        if(account == null ){
            return error("系统没有此用户");
        }

//        List<Armory> accountList = armoryService.selectArmoryList(Armory
//                .builder()
//                .accountId(accountId)
//                .deptId(account.getDeptId())
//                .build());
//        if(accountList!=null && accountList.size()>0){
//            return error("英雄榜已有此用户，不能重复添加");
//        }
        armory.setCompanyId(account.getCompanyId());
        armory.setDeptId(account.getDeptId());
        armory.setDeptName(account.getDeptName());
        armory.setHeadPortrait(StringUtils.isNotBlank(armory.getHeadPortrait()) ? armory.getHeadPortrait() : account.getHeadPortrait());
        armory.setNickName(account.getNickName());
        armory.setPostIds(account.getPostIds());
        armory.setPostNames(account.getPostNames());
        return toAjax(armoryService.insertArmory(armory));
    }

    /**
     * 修改英雄榜列表
     */
    @GetMapping("/edit/{armoryId}")
    public String edit(@PathVariable("armoryId") String armoryId, ModelMap mmap) {
        Armory armory = armoryService.selectArmoryById(armoryId);
        SysDept dept = new SysDept();
        //dept.setDeptLevel(-1);
        //dept.setDeptLevel(3);
        dept.setDeptLevel(0);
        List<SysDept> deptList = deptService.selectDeptList(dept);
        List<ApiAccount> accountList = apiAccountService.selectApiAccountList(ApiAccount.builder().delStatus(0).build());
        mmap.put("accountList",accountList);
        mmap.put("deptList",deptList);
        mmap.put("armory", armory);
        return prefix + "/edit";
    }

    /**
     * 修改保存英雄榜列表
     */
    @RequiresPermissions("web:armory:edit")
    @Log(title = "英雄榜列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Armory armory) {
        return toAjax(armoryService.updateArmory(armory));
    }

    /**
     * 删除英雄榜列表
     */
    @RequiresPermissions("web:armory:remove")
    @Log(title = "英雄榜列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(armoryService.deleteArmoryByIds(ids));
    }
}
