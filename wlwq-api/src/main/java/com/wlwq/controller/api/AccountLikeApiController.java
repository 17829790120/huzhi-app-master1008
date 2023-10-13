package com.wlwq.controller.api;

import com.wlwq.api.domain.AccountLike;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IAccountLikeService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户点赞
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/accountLike")
@AllArgsConstructor
public class AccountLikeApiController extends ApiController {
    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountLikeService accountLikeService;

    /**
     * 点赞/取消点赞
     * likeType 点赞类型（1：汇报训练）
     * targetId 目标对象ID
     */
//    @RepeatSubmit
    @PostMapping(value = "/sub")
    public ApiResult sub(String targetId,
                         Integer likeType,
                         HttpServletRequest request) {
        if (StringUtils.isEmpty(targetId)) {
            return ApiResult.fail("目标对象不能为空！");
        }
        if (likeType == null) {
            return ApiResult.fail("类型不能为空！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        //查询用户是否收藏该对象（点赞）
        AccountLike accountLike = accountLikeService.selectAccountLike(AccountLike.builder()
                .accountId(account.getAccountId())
                .likeType(likeType)
                .targetId(targetId)
                .build());
        if (accountLike == null) {
            int flag = accountLikeService.insertAccountLike(AccountLike.builder()
                    .accountId(account.getAccountId())
                    .targetId(targetId)
                    .likeType(likeType)
                    .accountHead(account.getHeadPortrait())
                    .accountName(account.getNickName())
                    .accountPhone(account.getPhone())
                    .build());
            if (flag < 1) {
                throw new ApiException("点赞失败！");
            }
            return ApiResult.ok(account.getNickName());
        }
        int flag = accountLikeService.deleteAccountLikeById(accountLike.getAccountLikeId());
        if (flag < 1) {
            throw new ApiException("取消点赞失败！");
        }
        return ApiResult.okMsg("已取消！");
    }

}
