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
import com.wlwq.api.domain.QuestionManager;
import com.wlwq.api.service.IQuestionManagerService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 试题列表Controller
 *
 * @author wwb
 * @date 2023-04-23
 */
@Controller
@RequestMapping("/web/questionManager")
public class QuestionManagerController extends BaseController {

    private String prefix = "web/questionManager";

    @Autowired
    private IQuestionManagerService questionManagerService;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("web:questionManager:view")
    @GetMapping()
    public String questionManager() {
        return prefix + "/questionManager";
    }

    /**
     * 查询试题列表列表
     */
    @RequiresPermissions("web:questionManager:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionManager questionManager) {
//        Long deptId = ShiroUtils.getSysUser().getDeptId();
//        if(deptId != null){
//            SysDept dept = deptService.selectDeptByDeptId(deptId);
//            if(dept != null){
//                questionManager.setCompanyId(dept.getDeptId());
//            }
//        }
        questionManager.setQuestionType(0);
        startPage();
        List<QuestionManager> list = questionManagerService.selectQuestionManagerList(questionManager);
        return getDataTable(list);
    }

    /**
     * 导出试题列表列表
     */
    @RequiresPermissions("web:questionManager:export")
    @Log(title = "试题列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionManager questionManager) {
        List<QuestionManager> list = questionManagerService.selectQuestionManagerList(questionManager);
        ExcelUtil<QuestionManager> util = new ExcelUtil<QuestionManager>(QuestionManager.class);
        return util.exportExcel(list, "questionManager");
    }

    /**
     * 新增试题列表
     */
    @GetMapping("/add")
    public String add(Long chapterId, Long courseId, ModelMap mmap) {
        mmap.put("courseId", courseId);
        mmap.put("chapterId", chapterId);
        return prefix + "/add";
    }

    /**
     * 新增保存试题列表
     */
    @RequiresPermissions("web:questionManager:add")
    @Log(title = "试题列表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionManager questionManager) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                questionManager.setCompanyId(dept.getDeptId());
            }
        }
        questionManager.setQuestionType(0);
        return toAjax(questionManagerService.insertQuestionManager(questionManager));
    }

    /**
     * 修改试题列表
     */
    @GetMapping("/edit/{questionManagerId}")
    public String edit(@PathVariable("questionManagerId") String questionManagerId, ModelMap mmap) {
        QuestionManager questionManager = questionManagerService.selectQuestionManagerById(questionManagerId);
        mmap.put("questionManager", questionManager);
        return prefix + "/edit";
    }

    /**
     * 修改保存试题列表
     */
    @RequiresPermissions("web:questionManager:edit")
    @Log(title = "试题列表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionManager questionManager) {
        Long deptId = ShiroUtils.getSysUser().getDeptId();
        if(deptId != null){
            SysDept dept = deptService.selectDeptByDeptId(deptId);
            if(dept != null){
                questionManager.setCompanyId(dept.getDeptId());
            }
        }
        questionManager.setQuestionType(0);
        return toAjax(questionManagerService.updateQuestionManager(questionManager));
    }

    /**
     * 删除试题列表
     */
    @RequiresPermissions("web:questionManager:remove")
    @Log(title = "试题列表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionManagerService.deleteQuestionManagerByIds(ids));
    }


    /**
     * 查询试题列表列表
     * chapterId 章节ID
     * courseId  课程ID
     */
    @GetMapping("/questionManagerList")
    public String questionManagerList(ModelMap modelMap,Long chapterId,Long courseId) {
        modelMap.put("chapterId",chapterId);
        modelMap.put("courseId",courseId);
        return prefix + "/questionManager";
    }
}
