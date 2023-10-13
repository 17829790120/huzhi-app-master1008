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
import com.wlwq.api.domain.TargetTraining;
import com.wlwq.api.service.ITargetTrainingService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 目标训练管理Controller
 *
 * @author wwb
 * @date 2023-06-01
 */
@Controller
@RequestMapping("/web/targetTraining")
public class TargetTrainingController extends BaseController {

    private String prefix = "web/targetTraining";

    @Autowired
    private ITargetTrainingService targetTrainingService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:targetTraining:view")
    @GetMapping()
    public String targetTraining() {
        return prefix + "/targetTraining";
    }

    /**
     * 查询目标训练管理列表
     */
    @RequiresPermissions("web:targetTraining:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TargetTraining targetTraining) {
/*        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                targetTraining.setCompanyId(dept.getDeptId());
            }
        }*/
        startPage();
        List<TargetTraining> list = targetTrainingService.selectTargetTrainingList(targetTraining);
        return getDataTable(list);
    }

    /**
     * 导出目标训练管理列表
     */
    @RequiresPermissions("web:targetTraining:export")
    @Log(title = "目标训练管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TargetTraining targetTraining) {
        List<TargetTraining> list = targetTrainingService.selectTargetTrainingList(targetTraining);
        ExcelUtil<TargetTraining> util = new ExcelUtil<TargetTraining>(TargetTraining.class);
        return util.exportExcel(list, "targetTraining");
    }

    /**
     * 新增目标训练管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存目标训练管理
     */
    @RequiresPermissions("web:targetTraining:add")
    @Log(title = "目标训练管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TargetTraining targetTraining) {
        //1：先判断系统有无此年份的数据
        List<TargetTraining> list = targetTrainingService.selectTargetTrainingList(TargetTraining
                .builder()
                .years(targetTraining.getYears())
                .delStatus(0)
                .build());
        if(list != null && list.size() > 0){
            return error("已有此年份的目标管理，不能重复添加");
        }

        targetTraining.setCreatorId(ShiroUtils.getUserId());
        targetTraining.setCreatorHeadPortrait(ShiroUtils.getSysUser().getAvatar());
        targetTraining.setCreatorNickName(ShiroUtils.getSysUser().getUserName());
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                targetTraining.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(targetTrainingService.insertTargetTraining(targetTraining));
    }

    /**
     * 修改目标训练管理
     */
    @GetMapping("/edit/{targetTrainingId}")
    public String edit(@PathVariable("targetTrainingId") String targetTrainingId, ModelMap mmap) {
        TargetTraining targetTraining = targetTrainingService.selectTargetTrainingById(targetTrainingId);
        mmap.put("targetTraining", targetTraining);
        return prefix + "/edit";
    }

    /**
     * 修改保存目标训练管理
     */
    @RequiresPermissions("web:targetTraining:edit")
    @Log(title = "目标训练管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TargetTraining targetTraining) {
        //1：先判断系统有无此年份的数据
        List<TargetTraining> list = targetTrainingService.selectTargetTrainingList(TargetTraining
                .builder()
                .years(targetTraining.getYears())
                .delStatus(0)
                .build());
        if(list != null && list.size() > 0){
            if(list.size() > 1 || !targetTraining.getTargetTrainingId().equals(list.get(0).getTargetTrainingId())){
                return error("已有此年份的目标训练，不能重复添加。");
            }
        }
        if (list.get(0).getPublishStatus() == 1){
            return error("已发布的目标训练，不能进行修改。");
        }

        targetTraining.setCreatorId(ShiroUtils.getUserId());
        targetTraining.setCreatorHeadPortrait(ShiroUtils.getSysUser().getAvatar());
        targetTraining.setCreatorNickName(ShiroUtils.getSysUser().getUserName());
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                targetTraining.setCompanyId(dept.getDeptId());
            }
        }
        return toAjax(targetTrainingService.updateTargetTraining(targetTraining));
    }

    /**
     * 删除目标训练管理
     */
    @RequiresPermissions("web:targetTraining:remove")
    @Log(title = "目标训练管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(targetTrainingService.deleteTargetTrainingByIds(ids));
    }
}
