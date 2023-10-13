package com.wlwq.web.controller.web;

import java.util.List;

import com.wlwq.common.exception.BusinessException;
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
import com.wlwq.api.domain.QuestionnaireAccount;
import com.wlwq.api.service.IQuestionnaireAccountService;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.common.core.page.TableDataInfo;

/**
 * 问卷发放记录Controller
 *
 * @author wwb
 * @date 2023-05-11
 */
@Controller
@RequestMapping("/web/questionnaireAccount")
public class QuestionnaireAccountController extends BaseController {

    private String prefix = "web/questionnaireAccount";

    @Autowired
    private IQuestionnaireAccountService questionnaireAccountService;

    //@RequiresPermissions("web:questionnaireAccount:view")
    @GetMapping()
    public String questionnaireAccount(String questionnaireDistributeRecordId, ModelMap modelMap) {
        if (questionnaireDistributeRecordId == null) {
            throw new BusinessException("请选择问卷！");
        }
        modelMap.put("questionnaireDistributeRecordId", questionnaireDistributeRecordId);
        return prefix + "/questionnaireAccount";
    }

    /**
     * 查询问卷发放记录列表
     */
    @RequiresPermissions("web:questionnaireAccount:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(QuestionnaireAccount questionnaireAccount) {
        startPage();
        List<QuestionnaireAccount> list = questionnaireAccountService.selectQuestionnaireAccountList(questionnaireAccount);
        return getDataTable(list);
    }

    /**
     * 导出问卷发放记录列表
     */
    @RequiresPermissions("web:questionnaireAccount:export")
    @Log(title = "问卷发放记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(QuestionnaireAccount questionnaireAccount) {
        List<QuestionnaireAccount> list = questionnaireAccountService.selectQuestionnaireAccountList(questionnaireAccount);
        ExcelUtil<QuestionnaireAccount> util = new ExcelUtil<QuestionnaireAccount>(QuestionnaireAccount.class);
        return util.exportExcel(list, "questionnaireAccount");
    }

    /**
     * 新增问卷发放记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存问卷发放记录
     */
    @RequiresPermissions("web:questionnaireAccount:add")
    @Log(title = "问卷发放记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(QuestionnaireAccount questionnaireAccount) {
        return toAjax(questionnaireAccountService.insertQuestionnaireAccount(questionnaireAccount));
    }

    /**
     * 修改问卷发放记录
     */
    @GetMapping("/edit/{questionnaireAccountId}")
    public String edit(@PathVariable("questionnaireAccountId") String questionnaireAccountId, ModelMap mmap) {
        QuestionnaireAccount questionnaireAccount = questionnaireAccountService.selectQuestionnaireAccountById(questionnaireAccountId);
        mmap.put("questionnaireAccount", questionnaireAccount);
        return prefix + "/edit";
    }

    /**
     * 修改保存问卷发放记录
     */
    @RequiresPermissions("web:questionnaireAccount:edit")
    @Log(title = "问卷发放记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(QuestionnaireAccount questionnaireAccount) {
        return toAjax(questionnaireAccountService.updateQuestionnaireAccount(questionnaireAccount));
    }

    /**
     * 删除问卷发放记录
     */
    @RequiresPermissions("web:questionnaireAccount:remove")
    @Log(title = "问卷发放记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(questionnaireAccountService.deleteQuestionnaireAccountByIds(ids));
    }
}
