package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.information.domain.InformationBrowseRecord;
import com.wlwq.information.service.IInformationBrowseRecordService;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wwb
 * 浏览记录
 */
@RestController
@RequestMapping(value = "/api/informationBrowseRecord")
@AllArgsConstructor
public class InformationBrowseRecordApiController extends ApiController {

    private final IInformationBrowseRecordService informationBrowseRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 添加浏览记录
     * 浏览记录类型（1：文化展示；2：新闻中心）
     */
    @PostMapping(value = "/add")
    public ApiResult collectionOrCancelCollection(@Validated InformationBrowseRecord params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.getInformationPostId())) {
            return ApiResult.fail("资讯不能为空！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }

        params.setAccountId(account.getAccountId());
        params.setAccountHead(account.getHeadPortrait());
        params.setAccountName(account.getNickName());
        params.setDeptId(account.getDeptId());
        int flag = informationBrowseRecordService.insertInformationBrowseRecord(params);
        if (flag < 1) {
            throw new ApiException("添加浏览量失败！");
        }
        return ApiResult.ok("添加浏览量成功");
    }

}
