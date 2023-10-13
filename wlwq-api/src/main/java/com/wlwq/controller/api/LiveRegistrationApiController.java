package com.wlwq.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.LiveRegistration;
import com.wlwq.api.domain.LiveVideo;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.ILiveRegistrationService;
import com.wlwq.api.service.ILiveVideoService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.params.courseExam.LiveRegistrationParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author wwb
 * 直播
 */
@RestController
@RequestMapping(value = "/api/liveRegistration")
@AllArgsConstructor
public class LiveRegistrationApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ILiveRegistrationService liveRegistrationService;

    private final ILiveVideoService liveVideoService;

    /**
     * 查询直播报名列表
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(PageParam pageParam, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<LiveRegistration> liveVideoList = liveRegistrationService.selectLiveRegistrationList(LiveRegistration
                .builder()
                //.companyId(account.getCompanyId())
                .accountId(account.getAccountId())
                .delStatus(0)
                .build());
        PageInfo<LiveRegistration> pageInfo = new PageInfo<>(liveVideoList);
        liveVideoList.forEach(
                obj -> {
                    LiveVideo liveVideo = liveVideoService.selectLiveVideoById(obj.getLiveVideoId());
                    if(liveVideo != null){
                        obj.setLiveVideo(liveVideo);
                    }
                }
        );
        return ok(pageInfo);
    }

    /**
     * pc@Description
     * params 添加直播报名记录
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    public ApiResult add(HttpServletRequest request, @RequestBody LiveRegistrationParam param, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        LiveVideo liveVideo = liveVideoService.selectLiveVideoById(param.getLiveVideoId());
        if(liveVideo == null){
            return fail("没有此直播信息");
        }

        int num = liveRegistrationService.insertLiveRegistration(LiveRegistration
                .builder()
                .accountId(account.getAccountId())
                .nickName(account.getNickName())
                .headPortrait(account.getHeadPortrait())
                .companyId(account.getCompanyId())
                .companyName(param.getCompanyName())
                .deptId(account.getDeptId())
                .deptName(param.getDeptName())
                .liveVideoId(param.getLiveVideoId())
                .build());
        if (num <= 0) {
            throw new ApiException("添加报名失败。");
        }
        return ok("报名成功。");
    }
}
