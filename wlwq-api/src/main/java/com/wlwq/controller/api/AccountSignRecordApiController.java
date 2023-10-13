package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.AccountClocking;
import com.wlwq.api.domain.AccountSignRecord;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IAccountSignRecordService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.clocking.ClockingParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 用户签到
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/sign")
@AllArgsConstructor
public class AccountSignRecordApiController extends ApiController {


    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountSignRecordService signRecordService;

    private final TaskScoreService scoreService;

    /**
     * 签到提交
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/sub")
    public ApiResult sub(HttpServletRequest request, @Validated AccountSignRecord params, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        AccountSignRecord signRecord = AccountSignRecord.builder()
                .accountId(account.getAccountId())
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .signAddress(params.getSignAddress())
                .signPics(params.getSignPics())
                .signRemark(params.getSignRemark())
                .deptId(account.getDeptId())
                .companyId(account.getCompanyId())
                .postIds(account.getPostIds())
                .build();
        int count = signRecordService.insertAccountSignRecord(signRecord);
        if (count <= 0) {
            return ApiResult.fail("签到失败，请重新签到！");
        }
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.signScore(signRecord.getAccountSignRecordId(), account));
        return ApiResult.okMsg("成功！");
    }

    /**
     * 签到列表
     *
     * @param request
     * @param month   月份筛选，格式为2023-05
     * @return
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request, String month, PageParam pageParam) {
        AccountSignRecord record = AccountSignRecord.builder()
                .accountId(tokenService.getNoAccountId(request))
                .month(month)
                .build();
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询签到列表
        List<AccountSignRecord> signList = signRecordService.selectApiAccountSignRecordList(record);
        PageInfo<AccountSignRecord> pageInfo = new PageInfo<>(signList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageInfo", pageInfo);
        // 查询签到数量
        Integer signCount = signRecordService.selectApiAccountSignRecordCount(record);
        map.put("signCount", signCount);
        return ok(map);
    }
}
