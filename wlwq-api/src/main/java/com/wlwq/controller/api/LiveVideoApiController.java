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
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wwb
 * 直播
 */
@RestController
@RequestMapping(value = "/api/liveVideo")
@AllArgsConstructor
public class LiveVideoApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ILiveVideoService liveVideoService;

    private final ILiveRegistrationService liveRegistrationService;

    /**
     * 查询直播列表
     * keyword 关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(PageParam pageParam, HttpServletRequest request,@RequestParam(defaultValue = "")String keyword) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<LiveVideo> liveVideoList = liveVideoService.selectLiveVideoList(LiveVideo
                .builder()
                //.companyId(account.getCompanyId())
                .title(keyword)
                .delStatus(0)
                .build());
        PageInfo<LiveVideo> pageInfo = new PageInfo<>(liveVideoList);
        liveVideoList.forEach(
                obj -> {
                    List<LiveRegistration> list = liveRegistrationService.selectLiveRegistrationList(LiveRegistration
                            .builder()
                            .liveVideoId(obj.getLiveVideoId())
                            .accountId(account.getAccountId())
                            .build());
                    if(list != null && list.size() > 0){
                        obj.setRegistrationsStatus(1);
                    }else{
                        obj.setRegistrationsStatus(0);
                    }
                }
        );
        return ok(pageInfo);
    }

    /**
     * 获取直播详情
     *
     * @return
     */
    @PassToken
    @GetMapping(value = "/getLiveVideo")
    public ApiResult getLiveVideo(@RequestParam(defaultValue = "0") String liveVideoId, HttpServletRequest request) {
        LiveVideo liveVideo = liveVideoService.selectLiveVideoById(liveVideoId);
        if(liveVideo != null ){
            List<LiveRegistration> list = liveRegistrationService.selectLiveRegistrationList(LiveRegistration
                    .builder()
                    .liveVideoId(liveVideo.getLiveVideoId())
                    .accountId(tokenService.getNoAccountId(request))
                    .build());
            if(list != null && list.size() > 0){
                liveVideo.setRegistrationsStatus(1);
            }else{
                liveVideo.setRegistrationsStatus(0);
            }
        }
        return ok(liveVideo);
    }
}
