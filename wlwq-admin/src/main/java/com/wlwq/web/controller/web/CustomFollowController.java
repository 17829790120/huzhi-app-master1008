package com.wlwq.web.controller.web;

import java.text.ParseException;
import java.util.List;

import com.wlwq.api.resultVO.CustomFollowListVO;
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
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.service.ICustomFollowService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 客户跟进Controller
 *
 * @author wlwq
 * @date 2023-06-02
 */
@Controller
@RequestMapping("/web/customFollow")
public class CustomFollowController extends BaseController {

    private String prefix = "web/customFollow";

    @Autowired
    private ICustomFollowService customFollowService;
    @Autowired
    private ISysDeptService deptService;
    @RequiresPermissions("web:customFollow:view")
    @GetMapping()
    public String customFollow() {
        return prefix + "/customFollow";
    }

            /**
         * 查询客户跟进列表
         */
        @RequiresPermissions("web:customFollow:list")
        @PostMapping("/list")
        @ResponseBody
        public TableDataInfo list(CustomFollow customFollow) {
            Long deptId = ShiroUtils.getSysUser().getDeptId();
            if(deptId != null){
                SysDept dept = deptService.selectDeptByDeptId(deptId);
                if(dept != null){
                    deptId=dept.getDeptId();
                }
            }
            startPage();
            List<CustomFollowListVO> list = customFollowService.findCustomFollowListVO(deptId.toString(),customFollow.getCustomName());
            return getDataTable(list);
        }
    
    /**
     * 导出客户跟进列表
     */
    @RequiresPermissions("web:customFollow:export")
    @Log(title = "客户跟进", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustomFollow customFollow) {
        List<CustomFollow> list = customFollowService.selectCustomFollowList(customFollow);
        ExcelUtil<CustomFollow> util = new ExcelUtil<CustomFollow>(CustomFollow.class);
        return util.exportExcel(list, "customFollow");
    }

            /**
         * 新增客户跟进
         */
        @GetMapping("/add")
        public String add() {
            return prefix + "/add";
        }
    
    /**
     * 新增保存客户跟进
     */
    @RequiresPermissions("web:customFollow:add")
    @Log(title = "客户跟进", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomFollow customFollow) throws ParseException {
        return toAjax(customFollowService.insertCustomFollow(customFollow));
    }

    /**
     * 修改客户跟进
     */
    @GetMapping("/edit/{customFollowId}")
    public String edit(@PathVariable("customFollowId") String customFollowId, ModelMap mmap) {
        CustomFollow customFollow = customFollowService.selectCustomFollowById(customFollowId);
        mmap.put("customFollow", customFollow);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户跟进
     */
    @RequiresPermissions("web:customFollow:edit")
    @Log(title = "客户跟进", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomFollow customFollow) {
        return toAjax(customFollowService.updateCustomFollow(customFollow));
    }

            /**
         * 删除客户跟进
         */
        @RequiresPermissions("web:customFollow:remove")
        @Log(title = "客户跟进", businessType = BusinessType.DELETE)
        @PostMapping( "/remove")
        @ResponseBody
        public AjaxResult remove(String ids) {
            return toAjax(customFollowService.deleteCustomFollowByIds(ids));
        }
        }
