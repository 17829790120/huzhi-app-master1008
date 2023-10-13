package com.wlwq.controller.api;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.IAccountScoreService;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.INewsCenterCategoryService;
import com.wlwq.api.service.INewsCenterPostService;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.information.domain.InformationLikeRecord;
import com.wlwq.information.service.IInformationLikeRecordService;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wwb
 * 新闻中心
 */
@RestController
@RequestMapping(value = "/api/newsCenterCategory")
@AllArgsConstructor
public class NewsCenterCategoryApiController extends ApiController {

    private final INewsCenterCategoryService newsCenterCategoryService;

    private final INewsCenterPostService newsCenterPostService;

    private final IInformationLikeRecordService informationLikeRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final TaskScoreService taskScoreService;

    private final IAccountScoreService accountScoreService;


    /**
     * 新闻中心分类列表
     *
     * @param deptId 部门ID，默认总公司（100）
     * @return
     */
    @PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "0") Long deptId) {
        List<NewsCenterCategory> list = newsCenterCategoryService.selectNewsCenterCategoryVoList(NewsCenterCategory.builder()
                .deptId(deptId)
                .tag(0)
                .delStatus(0)
                .build());
        return ok(list);
    }

    /**
     * 新闻中心资讯列表
     *
     * @param deptId  部门ID，默认总公司（100）
     * @param keyword 关键字搜索
     * @return
     */
    @PassToken
    @GetMapping(value = "/newsCenterList")
    public ApiResult newsCenterList(PageParam pageParam, @RequestParam(defaultValue = "") Long deptId,
                                    @RequestParam(defaultValue = "") Long informationCategoryId, String keyword, HttpServletRequest request) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<NewsCenterPost> list = newsCenterPostService.selectNewsCenterPostList(NewsCenterPost.builder()
                .deptId(deptId)
                .informationCategoryId(informationCategoryId)
                .delStatus(0)
                .informationPostTitle(keyword)
                .build());
        PageInfo<NewsCenterPost> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj -> {
                    if (account == null) {
                        obj.setIsLike(0);
                    } else {
                        //查询用户是否点赞过
                        InformationLikeRecord commonLike = informationLikeRecordService.selectInformationLikeByPrimaryIdAndAccountId(obj.getNewsCenterPostId(), account.getAccountId());
                        if (commonLike != null) {
                            obj.setIsLike(1);
                        } else {
                            obj.setIsLike(0);
                        }
                    }
                }
        );
        return ok(pageInfo);
    }

    /**
     * 新闻中心资讯详情
     *
     * @param newsCenterPostId 资讯ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/details")
    public ApiResult details(@RequestParam(defaultValue = "-1") Long newsCenterPostId,
                             HttpServletRequest request) {
        Map<String, Object> map = new HashMap(5);
        NewsCenterPost newsCenterPost = newsCenterPostService.selectNewsCenterPostById(newsCenterPostId);
        ApiAccount account = accountService.selectApiAccountById(tokenService.getNoAccountId(request));
        if (account == null) {
            newsCenterPost.setIsLike(0);
        } else {
            //查询用户是否点赞过
            InformationLikeRecord commonLike = informationLikeRecordService.selectInformationLikeByPrimaryIdAndAccountId(newsCenterPost.getNewsCenterPostId(), tokenService.getNoAccountId(request));
            if (commonLike != null) {
                newsCenterPost.setIsLike(1);
            } else {
                newsCenterPost.setIsLike(0);
            }
        }
        // 阅读获取积分
        map.put("getScore", account == null ? 0 : taskScoreService.selectScore(account, 26));
        map.put("newsCenterPost", newsCenterPost);
        //判断用户是否获取过积分（0：未获取过 1：获取过）
        AccountScore accountScore = accountScoreService.selectScoreByAccountIdAndTargetId(AccountScore.builder()
                .accountId(account.getAccountId())
                .targetId(Convert.toStr(newsCenterPostId))
                .build());
        map.put("getScoreStatus", accountScore == null ? 0 : 1);
        return ok(map);
    }

    /**
     * 新闻中心资讯详情
     *
     * @return
     */
    @GetMapping(value = "/findSysDictTypeVO")
    public ApiResult findSysDictTypeVO() {
        return ApiResult.ok(newsCenterCategoryService.findSysDictTypeVO(new ArrayList<>()));
    }

    /**
     * 新闻中心获得积分
     *
     * @return
     */
    @GetMapping(value = "/getScore")
    public ApiResult getScore(HttpServletRequest request, @RequestParam(defaultValue = "0") String targetId) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户没登录！");
        }
        NewsCenterPost newsCenterPost = newsCenterPostService.selectNewsCenterPostById(Convert.toLong(targetId));
        if (newsCenterPost == null) {
            return ApiResult.fail("该信息不存在！");
        }
        int score = taskScoreService.selectScore(account, 26);
        if (score > 0) {
            // 更新用户信息
            account.setAccountId(account.getAccountId());
            account.setSurplusScore(account.getSurplusScore() + score);
            account.setTotalScore(account.getTotalScore() + score);

            // 用户积分存入记录
            // 赠送用户积分
            AccountScore accountScore = AccountScore.builder()
                    .accountId(account.getAccountId())
                    .targetId(targetId)
                    .scoreType(-6)
                    .accountName(account.getNickName())
                    .accountPhone(account.getPhone())
                    .accountHead(account.getHeadPortrait())
                    .scoreSource(taskScoreService.scoreSource(26))
                    .scoreStatus(1)
                    .score(score)
                    .build();
            // 发送系统消息
            // 查询消息是否存在
            MessageRemind messageRemind = MessageRemind.builder()
                    .title("积分变动")
                    .brief("阅读新闻,获得" + score + "积分,点击查看")
                    .modelStatus(2)
                    .jumpType(-2)
                    .modelId("26")
                    .accountId(account.getAccountId())
                    .build();
            taskScoreService.insertIntegralScore(account, accountScore, messageRemind);
        }
        return ApiResult.ok("恭喜您获得积分。");
    }

}
