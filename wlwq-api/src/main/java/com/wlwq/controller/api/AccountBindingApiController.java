package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.AccountBinding;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.score.AccountScoreResultVO;
import com.wlwq.api.service.IAccountBindingService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.clocking.ClockingSearchParamVO;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author gaoce 已废弃
 */
@Deprecated
@RestController
@RequestMapping(value = "/api/accountBinding")
@AllArgsConstructor
public class AccountBindingApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountBindingService bindingService;

    /**
     * 查询绑定账号列表
     *
     * @return
     */
    @PassToken
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request) {
        String accountId = tokenService.getNoAccountId(request);
        // 查询绑定人员列表
        List<AccountScoreResultVO> bindingAccountList = accountService.selectApiBindingAccountList(ApiAccount.builder().accountId(accountId).build());
        Map<String, Object> map = new HashMap<>(2);
        map.put("bindingAccountList", bindingAccountList);
        return ok(map);
    }

    /**
     * 移除账号
     * accountId 要移除的人员ID
     * @return
     */
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request,@RequestParam(defaultValue = "0") String accountId) {
        // 查询要移除的人员ID是否存在
        AccountBinding accountBinding = bindingService.selectAccountBinding(AccountBinding.builder().accountIds(accountId).accountId(tokenService.getAccountId(request)).build());
        if(accountBinding == null){
            return ApiResult.fail("要移除的账号不存在！");
        }
        int count = bindingService.deleteAccountBindingById(accountBinding.getAccountBindingId());
        if(count <= 0){
            return ApiResult.fail("移除失败！");
        }
        return ApiResult.okMsg("移除成功！");
    }



}
