package com.wlwq.controller.api;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ReportRecord;
import com.wlwq.api.service.IAccountScoreService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IReportRecordService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.information.domain.InformationCommentRecord;
import com.wlwq.information.domain.InformationLikeRecord;
import com.wlwq.information.service.IInformationCommentRecordService;
import com.wlwq.information.service.IInformationLikeRecordService;
import com.wlwq.params.ReportRecordParams;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wwb
 * 评论记录
 */
@RestController
@RequestMapping(value = "/api/informationCommentRecord")
@AllArgsConstructor
public class InformationCommentRecordApiController extends ApiController {

    private IInformationCommentRecordService informationCommentRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IInformationLikeRecordService informationLikeRecordService;

    private final IAccountScoreService accountScoreService;

    private final IReportRecordService reportRecordService;

    /**
     * 添加评论
     *
     * @param params
     * @param bindingResult
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addComment")
    public ApiResult addComment(@Validated InformationCommentRecord params, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        params.setAccountId(account.getAccountId());
        params.setAccountHead(account.getHeadPortrait());
        params.setAccountName(account.getNickName());
        params.setAuditStatus(1);
        int flag = informationCommentRecordService.insertInformationCommentRecord(params);
        if (flag < 1) {
            throw new ApiException("发表评论失败！");
        }
        return ApiResult.ok(params);
    }

    /**
     * 删除评论
     *
     * @param informationCommentId
     * @param request
     * @return
     */
    @PostMapping(value = "/deleteComment")
    public ApiResult deleteComment(Long informationCommentId, HttpServletRequest request) {
        InformationCommentRecord comment = informationCommentRecordService.selectInformationCommentRecordById(informationCommentId);
        if (comment == null) {
            return ApiResult.fail("该评论不存在！");
        }
        if (!comment.getAccountId().equals(tokenService.getAccountId(request))) {
            return ApiResult.fail("这不是您的评论，您无权操作哦！");
        }
        int flag = informationCommentRecordService.deleteInformationCommentRecordById(informationCommentId);
        if (flag < 1) {
            return ApiResult.fail("删除失败！");
        }
        return ApiResult.ok("删除成功！");
    }


    /**
     * 举报动态
     */
    @PostMapping("/reportRecordSave")
    @ResponseBody
    public ApiResult reportRecordSave(HttpServletRequest request,
                                      @Validated ReportRecordParams reportRecordParams,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        ApiAccount apiAccount = accountService.selectApiAccountById(tokenService.getLoginUser(request).getAccountId());
        if (apiAccount == null) {
            return fail("用户不存在或已被管理员删除");
        }
        int count = reportRecordService.insertReportRecord(ReportRecord.builder()
                .accountId(apiAccount.getAccountId())
                .reportContent(reportRecordParams.getReportContent())
                .reportTime(DateUtils.getNowDate())
                .reportImages(reportRecordParams.getReportImages())
                .targetId(reportRecordParams.getTargetId())
                .build());
        if (count < 1) {
            return fail("举报失败");
        }
        return ApiResult.okMsg("举报成功");
    }

    /**
     * 评论列表
     *
     * @param pageParam
     * @param informationPostId
     * @param hostOrNewStatus   1 最新  2 最热
     * @return
     */
    @PassToken
    @GetMapping(value = "/findPostCommentList")
    public ApiResult findPostCommentList(PageParam pageParam, Long informationPostId, @RequestParam(defaultValue = "1") String hostOrNewStatus) {
        if (informationPostId == null) {
            return ApiResult.fail("评论标识不能为空！");
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        //查询当前对象的评论列表
        List<Map<String, Object>> oneLevelComment = informationCommentRecordService.selectCommentListByPostId(informationPostId, hostOrNewStatus);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(oneLevelComment);

        if (oneLevelComment == null) {
            oneLevelComment = new ArrayList<Map<String, Object>>();
        }
        if (oneLevelComment != null && oneLevelComment.size() > 0) {
            for (int i = 0; i < oneLevelComment.size(); i++) {
                Map<String, Object> tempResult = oneLevelComment.get(i);
                //评论回复列表
                PageHelper.startPage(1, 3);
                List<Map<String, Object>> replyList = informationCommentRecordService.selectCommentReplyListByPrimaryId(Long.valueOf(tempResult.get("informationCommentId").toString()));
                PageInfo<Map<String, Object>> twoLevelCommentPage = new PageInfo<Map<String, Object>>(replyList);
                tempResult.put("replyList", replyList);
                tempResult.put("replyCount", twoLevelCommentPage.getTotal());
            }
        }
        return ApiResult.ok(pageInfo);
    }

    /**
     * 家庭训练评论列表
     *
     * @param pageParam
     * @param informationPostId
     * @param hostOrNewStatus   1 最新  2 最热
     * @return
     */
    @PassToken
    @GetMapping(value = "/findHouseTrainingPostCommentList")
    public ApiResult findHouseTrainingPostCommentList(PageParam pageParam, Long informationPostId,
                                                      @RequestParam(defaultValue = "1") String hostOrNewStatus,
                                                      HttpServletRequest request) {
        if (informationPostId == null) {
            return ApiResult.fail("评论标识不能为空！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        //查询当前对象的评论列表
        List<Map<String, Object>> oneLevelComment = informationCommentRecordService.selectCommentListByPostId(informationPostId, hostOrNewStatus);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(oneLevelComment);
        if (oneLevelComment == null) {
            oneLevelComment = new ArrayList<Map<String, Object>>();
        }
        if (oneLevelComment != null && oneLevelComment.size() > 0) {
            for (int i = 0; i < oneLevelComment.size(); i++) {
                Map<String, Object> tempResult = oneLevelComment.get(i);
                //查询用户是否点赞过
                InformationLikeRecord commonLike = informationLikeRecordService.selectInformationLikeByPrimaryIdAndAccountId(
                        Long.valueOf(tempResult.get("informationCommentId").toString()), account.getAccountId());
                if (commonLike != null) {
                    tempResult.put("isLike", 1);
                } else {
                    tempResult.put("isLike", 0);
                }
                List<InformationLikeRecord> likeList = informationLikeRecordService.selectInformationLikeRecordList(InformationLikeRecord
                        .builder()
                        .primaryId(Long.valueOf(tempResult.get("informationCommentId").toString()))
                        .build());
                if (likeList != null && likeList.size() > 0) {
                    tempResult.put("isLikeCount", likeList.size());
                } else {
                    tempResult.put("isLikeCount", 0);
                }
                //评论回复列表
                PageHelper.startPage(1, 3);
                List<Map<String, Object>> replyList = informationCommentRecordService.selectCommentReplyListByPrimaryId(Long.valueOf(tempResult.get("informationCommentId").toString()));
                PageInfo<Map<String, Object>> twoLevelCommentPage = new PageInfo<Map<String, Object>>(replyList);
                tempResult.put("replyList", replyList);
                tempResult.put("replyCount", twoLevelCommentPage.getTotal());
                // 查询获得打赏积分列表
                List<AccountScore> scoreList = accountScoreService.selectApiAccountScoreList(AccountScore.builder().scoreStatus(1).scoreType(-5).targetId(Convert.toStr(tempResult.get("informationCommentId"))).build());
                tempResult.put("scoreList", scoreList);
            }
        }
        return ApiResult.ok(pageInfo);
    }


    /**
     * 查看更多回复
     *
     * @param pageParam
     * @param informationCommentId
     * @param request
     * @return
     */
    @PassToken
    @GetMapping(value = "/findPostCommentReplyList")
    public ApiResult findPostCommentReplyList(PageParam pageParam, Long informationCommentId, HttpServletRequest request) {
        if (informationCommentId == null) {
            return ApiResult.fail("评论标识不能为空！");
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> list = informationCommentRecordService.selectCommentReplyListByPrimaryId(informationCommentId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return ApiResult.ok(pageInfo);
    }
}
