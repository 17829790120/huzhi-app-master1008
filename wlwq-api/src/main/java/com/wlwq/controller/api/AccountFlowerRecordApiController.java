package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.AccountFlowerRecord;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IAccountFlowerRecordService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.flower.FlowerParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskFlowerService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author 红花记录
 */
@RestController
@RequestMapping(value = "/api/accountFlowerRecord")
@AllArgsConstructor
public class AccountFlowerRecordApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountFlowerRecordService flowerRecordService;

    private final TaskFlowerService taskFlowerService;

    /**
     * 红花奖励提交
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/flowerSub")
    public ApiResult flowerSub(HttpServletRequest request, @RequestBody @Validated FlowerParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (params.getFlowerList().size() == 0) {
            return ApiResult.fail("请提交相关信息！");
        }
        if (params.getFlowerList().size() * params.getNum() > account.getSurplusFlowerNum()) {
            return ApiResult.fail("您的红花余额不足，暂时不能赠送！");
        }
        // 先扣除账户余额
        int count = accountService.updateApiAccount(ApiAccount.builder()
                .accountId(account.getAccountId())
                .surplusFlowerNum(account.getSurplusFlowerNum() - params.getFlowerList().size() * params.getNum())
                .build());
        if (count <= 0) {
            return ApiResult.fail("账户余额扣除失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskFlowerService.flower(params,account));
        return ApiResult.okMsg("成功！");
    }


    /**
     * 红花收入列表
     *
     * @param request
     * @param pageParam 分页
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GetMapping("/flowerIncomeList")
    public ApiResult flowerIncomeList(HttpServletRequest request, PageParam pageParam,String startTime,String endTime) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountFlowerRecord> recordList = flowerRecordService.selectAccountFlowerRecordList(AccountFlowerRecord.builder()
                .accountId(tokenService.getNoAccountId(request))
                .startTime(startTime)
                .endTime(endTime)
                .build());
        PageInfo<AccountFlowerRecord> pageInfo = new PageInfo<>(recordList);
        return ok(pageInfo);
    }

    /**
     * 红花消耗列表
     *
     * @param request
     * @param pageParam 分页
     * @return
     */
    @GetMapping("/flowerConsumeList")
    public ApiResult flowerConsumeList(HttpServletRequest request, PageParam pageParam) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountFlowerRecord> recordList = flowerRecordService.selectAccountFlowerRecordList(AccountFlowerRecord.builder()
                .giveAccountId(tokenService.getNoAccountId(request))
                .giveStatus(1)
                .build());
        PageInfo<AccountFlowerRecord> pageInfo = new PageInfo<>(recordList);
        return ok(pageInfo);
    }
}
