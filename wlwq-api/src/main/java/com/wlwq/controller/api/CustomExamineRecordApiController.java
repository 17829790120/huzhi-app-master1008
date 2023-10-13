package com.wlwq.controller.api;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.CustomExamineRecord;
import com.wlwq.api.resultVO.CustomLevelVO;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ICustomExamineRecordService;
import com.wlwq.common.annotation.Log;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.controller.BaseController;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.core.page.TableDataInfo;
import com.wlwq.common.enums.BusinessType;
import com.wlwq.common.utils.poi.ExcelUtil;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 客户成交Controller
 *
 * @author wlwq
 * @date 2023-06-10
 */
@RestController
@RequestMapping("/api/record")
@AllArgsConstructor
public class CustomExamineRecordApiController extends ApiController {


    @Autowired
    private ICustomExamineRecordService customExamineRecordService;
    @Autowired
    private IApiAccountService accountService;
    @Autowired
    private TokenService tokenService;
    /**
     * 客户成交保存
     */
    @PostMapping("/addCustomExamineRecord")
    public ApiResult list(HttpServletRequest request,@RequestBody CustomExamineRecord customExamineRecord) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        customExamineRecord.setDeptId(account.getDeptId());
        customExamineRecord.setCompanyId(account.getCompanyId());
        customExamineRecord.setCustomUserId(Long.parseLong(account.getAccountId()));
        customExamineRecord.setCustomUserName(account.getNickName());
        String l = IdUtil.getSnowflake(1, 1).nextIdStr();
        customExamineRecord.setCustomExamineRecordId(l);
        if(ObjectUtil.isEmpty(customExamineRecord.getCustomeStrikeMoney())||customExamineRecord.getCustomeStrikeMoney().compareTo(new BigDecimal("0"))==0){
            return ApiResult.fail("金额不能为空");
        }
        customExamineRecordService.insertCustomExamineRecord(customExamineRecord);
        return ApiResult.ok(l);
    }

    /**
     * 成交详情
     */
    @GetMapping("/getCustomExamineVO")
    public ApiResult list(@RequestParam String customClaimId) {
        return ApiResult.ok(customExamineRecordService.getCustomExamineVO(customClaimId));
    }
}
