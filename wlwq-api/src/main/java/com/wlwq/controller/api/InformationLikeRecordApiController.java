package com.wlwq.controller.api;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.information.domain.InformationLikeRecord;
import com.wlwq.information.service.IInformationLikeRecordService;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wwb
 * 点赞记录
 */
@RestController
@RequestMapping(value = "/api/informationLikeRecord")
@AllArgsConstructor
public class InformationLikeRecordApiController extends ApiController {

    private final IInformationLikeRecordService informationLikeRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    /**
     * 点赞/取消点赞
     */
    @PostMapping(value = "/likeOrUnlike")
    public ApiResult likeOrUnlike(@Validated InformationLikeRecord params,HttpServletRequest request){
        if (StringUtils.isEmpty(params.getPrimaryId())){
            return ApiResult.fail("被点赞标识不能为空！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if(account == null){
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        Map<String,Integer> resultMap = new HashMap<String, Integer>(1);
        //查询用户是否点赞过
        InformationLikeRecord commonLike = informationLikeRecordService.selectInformationLikeByPrimaryIdAndAccountId(params.getPrimaryId(),account.getAccountId());
        if (commonLike == null){
            params.setAccountId(account.getAccountId());
            params.setAccountHead(account.getHeadPortrait());
            params.setAccountName(account.getNickName());
            int flag = informationLikeRecordService.insertInformationLikeRecord(params);
            if (flag < 1){
                throw new ApiException("点赞失败！");
            }
            resultMap.put("fabulousType",1);

            //点赞消息
/*            LikeMessage message = LikeMessage.builder().build();
            message.setLikeAccountId(account.getAccountId());
            message.setLikeAccountHead(account.getHeadPortrait());
            message.setLikeAccountName(account.getNickName());

            AccountMessage accountMessage = AccountMessage.builder()
                    //消息类型（1系统消息 2订单消息 3点赞消息 4评价消息 ）
                    .messageType(3)
                    .readStatus(0)
                    .build();
            if("5".equals(likeType)){
                //动态点赞
                CircleDynamics circleDynamics = circleDynamicsService.selectCircleDynamicsById(primaryId);
                message.setPostId(primaryId);
                message.setPostContent(circleDynamics.getContent());
                message.setPostTitle(circleDynamics.getTitle());
                accountMessage.setAccountId(circleDynamics.getAccountId());
                accountMessage.setSkipType(5);
                // 发送消息
                accountMessage.setMessageContentJson(new Gson().toJson(message));
                messageService.insertAccountMessage(accountMessage);
            }*/
            return ApiResult.ok(resultMap);
        }else{
            int flag = informationLikeRecordService.deleteInformationLikeRecordById(commonLike.getInformationLikeRecordId());
            if (flag < 1){
                return ApiResult.fail("取消点赞失败！");
            }
            resultMap.put("fabulousType",2);
            return ApiResult.ok(resultMap);
        }
    }
}
