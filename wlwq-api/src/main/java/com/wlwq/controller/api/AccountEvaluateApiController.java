package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.AccountEvaluate;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ReportTraining;
import com.wlwq.api.service.IAccountEvaluateService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.params.reportTraining.ReportTrainingParamVO;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 评价
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/accountEvaluate")
@AllArgsConstructor
public class AccountEvaluateApiController extends ApiController {
    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountEvaluateService evaluateService;

    /**
     * 评价/回复提交
     */
    @RepeatSubmit
    @PostMapping(value = "/sub")
    public ApiResult sub(HttpServletRequest request, @Validated AccountEvaluate params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        params.setAccountId(account.getAccountId());
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        int count = evaluateService.insertAccountEvaluate(params);
        if(count <= 0){
            return ApiResult.fail("评价失败！");
        }
        return ApiResult.okMsg("评价成功！");
    }

    /**
     * 评价删除
     *
     * @param request
     * @param accountEvaluateId 评价ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request, @RequestParam(defaultValue = "0") String accountEvaluateId) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        AccountEvaluate evaluate = evaluateService.selectAccountEvaluateById(accountEvaluateId);
        if (evaluate == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(evaluate.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        int count = evaluateService.deleteAccountEvaluateById(accountEvaluateId);
        if(count <= 0){
            return ApiResult.fail("删除失败！");
        }
        return ApiResult.okMsg("已删除");
    }

    /**
     * 评价列表
     *
     * @param request
     * @param targetId 模块ID
     * @param templateType 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     * @return
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request,
                          PageParam pageParam,
                          @RequestParam(defaultValue = "0") String targetId,
                          @RequestParam(defaultValue = "") Integer templateType,
                          @RequestParam(defaultValue = "") String threeStoreClassId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountEvaluate> evaluateList = evaluateService.selectApiAccountEvaluateList(AccountEvaluate.builder()
                .targetId(targetId)
                .evaluateType(templateType)
                .threeStoreClassId(threeStoreClassId)
                .parentId("0")
                .build());
        evaluateList.forEach(e -> {
            e.setEvaluateTag(account.getAccountId().equals(e.getAccountId()) ? 1 : 0);
            // 查询回复列表
            List<AccountEvaluate> replyList = evaluateService.selectApiAccountEvaluateList(AccountEvaluate.builder()
                    .parentId(e.getAccountEvaluateId())
                    .build());
            replyList.forEach(reply -> {
                reply.setEvaluateTag(account.getAccountId().equals(reply.getAccountId()) ? 1 : 0);
            });
            e.setReplyList(replyList);
        });
        PageInfo<AccountEvaluate> pageInfo = new PageInfo<>(evaluateList);
        return ok(pageInfo);
    }

}
