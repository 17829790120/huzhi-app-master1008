package com.wlwq.controller.login;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.service.TokenService;
import com.wlwq.vo.LoginAccount;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LoginController
 * @Description 登录相关
 * @Date 2021/2/6 15:08
 * @Author Rick wlwq
 */
@RestController
@RequestMapping(value = "/api/login")
@AllArgsConstructor
public class LoginTestController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    // 加密使用国密加密算法SmUtil.sm3(密码)

    @RequestMapping(value = "/login")
    @PassToken
    public ApiResult login(){
        // 根据现在查询出UUID删除token
        // 账户不能空的情况下，先删除UUID
        // tokenService.delToken(account.getUuid());
        return ApiResult.ok(tokenService.createToken(LoginAccount.builder()
                .accountId("100")
                .build()));
    }
    @RequestMapping(value = "/loginInfo")
    public ApiResult loginInfo(HttpServletRequest request){
        return ok(tokenService.getAccountId(request));
    }


    /**
     * 根据token获取用户信息
     */
    @GetMapping(value = "/getMyInfoByToken")
    public ApiResult getMyInfoByToken(HttpServletRequest request) {
        return ok(accountService.selectApiAccountById(tokenService.getAccountId(request)));
    }

    /**
     * 修改用户信息
     */
    @PostMapping(value = "/updateAccountByToken")
    public ApiResult updateAccountByToken(HttpServletRequest request, ApiAccount account) {
        account.setAccountId(tokenService.getAccountId(request));
        return ok(accountService.updateApiAccount(account));
    }
}
