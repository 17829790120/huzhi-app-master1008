package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.AccountScore;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ExperienceSharing;
import com.wlwq.api.domain.MessageRemind;
import com.wlwq.api.service.*;
import com.wlwq.bestPay.constant.ModuleConstant;
import com.wlwq.bestPay.mq.RabbitMQSendService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.information.domain.InformationCommentRecord;
import com.wlwq.information.service.IInformationCommentRecordService;
import com.wlwq.params.courseExam.ExperienceSharingParam;
import com.wlwq.service.TokenService;
import com.wlwq.system.domain.SysPost;
import com.wlwq.system.service.ISysConfigService;
import com.wlwq.system.service.ISysPostService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author wwb
 * 心得分享
 */
@RestController
@RequestMapping(value = "/api/experienceSharing")
@AllArgsConstructor
public class ExperienceSharingApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IExperienceSharingService experienceSharingService;

    private final ISysPostService postService;

    private final ISysConfigService configService;

    private final RabbitMQSendService rabbitMQSendService;

    private final IAccountMedalRecordService accountMedalRecordService;

    private final IAccountScoreService accountScoreService;

    private final IMessageRemindService messageRemindService;

    private final TaskScoreService taskScoreService;

    private final IInformationCommentRecordService commentRecordService;

    /**
     * pc@Description
     * params 添加心得分享
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    public ApiResult add(HttpServletRequest request, @RequestBody ExperienceSharingParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        ExperienceSharing experienceSharing = ExperienceSharing.builder().build();
        BeanUtil.copyProperties(params, experienceSharing);

        experienceSharing.setExperienceSharingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        experienceSharing.setAccountId(account.getAccountId());
        experienceSharing.setNickName(account.getNickName());
        experienceSharing.setHeadPortrait(account.getHeadPortrait());
        experienceSharing.setDeptId(account.getDeptId());
        experienceSharing.setCompanyId(account.getCompanyId());
        experienceSharing.setPostIds(account.getPostIds());

        String postIds = account.getPostIds();
        if (!StringUtils.isEmpty(postIds)) {
            String[] array = postIds.split(",");
            Arrays.sort(array);
            List<SysPost> list = postService.selectPostByIds(array);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0 && i == list.size() - 1) {
                    experienceSharing.setPostNames(list.get(i).getPostName());
                } else if (i == 0 && i < list.size() - 1) {
                    experienceSharing.setPostNames(list.get(i).getPostName() + ",");
                } else if (i < list.size() - 1) {
                    experienceSharing.setPostNames(experienceSharing.getPostNames() + list.get(i).getPostName() + ",");
                } else if (i == list.size() - 1) {
                    experienceSharing.setPostNames(experienceSharing.getPostNames() + list.get(i).getPostName());
                }
            }
        }
        int num = experienceSharingService.insertExperienceSharing(experienceSharing);
        if (num <= 0) {
            throw new ApiException("分享心得失败。");
        }
        //分享心得获取积分
        addScore(account,experienceSharing);
        map.put("result", experienceSharing);
        map.put("state", 200);

        // 将转训发送到延时队列，7天后还未转训则标注为转训过期失效
        String automaticReceiptTime = Optional.ofNullable(configService.selectConfigByKey("automatic_receipt_time")).orElse("7");
        Long expiration = new BigDecimal(automaticReceiptTime).multiply(BigDecimal.valueOf(24L)).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(1000L)).longValue();
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put("moduleName", ModuleConstant.TRANSFER_TRAINING_TAKE_DELIVERY_TIMEOUT);
        jsonObject.put("messageContent", experienceSharing.getExperienceSharingId());
        rabbitMQSendService.sendDelayMessage(expiration, jsonObject.toString());
        return ok(map);
    }

    /**
     * 分享心得列表
     *
     * @param status :0:查询全部，1：查询个人
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(@RequestParam(defaultValue = "") Long courseId, @RequestParam(defaultValue = "0") int status, PageParam pageParam, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail("请登录系统！");
        }
        if (account == null) {
            return fail("请选择对应的课程再查询心得分享。");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ExperienceSharing experienceSharing = ExperienceSharing.builder().build();
        experienceSharing.setCourseId(courseId);
        if (status == 1) {
            experienceSharing.setAccountId(account.getAccountId());
        }

        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<ExperienceSharing> list = experienceSharingService.selectExperienceSharingList(experienceSharing);
        PageInfo<ExperienceSharing> pageInfo = new PageInfo<>(list);
/*        list.forEach(
                obj -> {
                    List<AccountScore> accountScoreList = accountScoreService.selectApiAccountScoreList(AccountScore
                            .builder()
                            .targetId(obj.getExperienceSharingId())
                            .scoreType(-4)
                            .scoreStatus(1)
                            .build());
                    obj.setAccountScoreList(accountScoreList);
                }
        );*/
        return ok(pageInfo);
    }

    /**
     * pc@Description
     * params 添加心得分享得积分
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResult addScore(ApiAccount account,ExperienceSharing experienceSharing) {
        int score = taskScoreService.selectScore(account, 30);
        if (score <= 0) {
            return fail("心得分享的奖励积分为0。");
        }
        // 更新被打赏者用户信息
        accountService.updateApiAccount(ApiAccount.builder()
                .accountId(account.getAccountId())
                .surplusScore(account.getSurplusScore() + score)
                .totalScore(account.getTotalScore() + score)
                .build());
        // 查看是否满足勋章条件并更新勋章
        //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), params.getScore(), "分享心得获得积分");
        // 用户积分存入记录
        // 赠送用户积分
        accountScoreService.insertAccountScore(AccountScore.builder()
                .accountId(account.getAccountId())
                .targetId(experienceSharing.getExperienceSharingId())
                .scoreType(-5)
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .scoreSource("分享心得获得积分")
                .scoreStatus(1)
                .score(score)
                .courseId(experienceSharing.getCourseId())
                .build());
        // 发送系统消息
        // 查询消息是否存在
        messageRemindService.insertMessageRemind(MessageRemind.builder()
                .title("积分变动")
                .brief("分享心得获得积分,获得" + score + "积分,点击查看")
                .modelStatus(2)
                .jumpType(-2)
                .modelId("0")
                .accountId(account.getAccountId())
                .build());
        return ok("打赏成功。");
    }

    /**
     * pc@Description
     * params 家庭训练-打赏得积分（分享成果）
     * params informationCommentId 评论ID
     * params score 赠送积分
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addScore")
    public ApiResult addScore(HttpServletRequest request,@RequestParam(defaultValue = "0") Long informationCommentId,@RequestParam(defaultValue = "0") Integer score) {
        InformationCommentRecord commentRecord = commentRecordService.selectInformationCommentRecordById(informationCommentId);
        if (commentRecord == null) {
            return fail("要打赏的评论不存在！");
        }
        //被打赏者
        ApiAccount account = accountService.selectApiAccountById(commentRecord.getAccountId());
        if (account == null) {
            return fail("被打赏者不存在。");
        }
        if(score <= 0){
            return fail("请传入大于0的整数积分！");
        }
        //打赏者
        ApiAccount accountNew = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (accountNew == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (accountNew.getSurplusScore() - score < 0) {
            return fail("您的积分不够进行打赏。");
        }
        // 更新被打赏者用户信息
        accountService.updateApiAccount(ApiAccount.builder()
                .accountId(account.getAccountId())
                .totalScore(account.getTotalScore() + score)
                .surplusScore(account.getSurplusScore() + score)
                .build());
//        // 查看是否满足勋章条件并更新勋章
//        accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), score, "分享成果获得积分");
        // 用户积分存入记录
        // 赠送用户积分
        accountScoreService.insertAccountScore(AccountScore.builder()
                .accountId(account.getAccountId())
                .targetId(Convert.toStr(informationCommentId))
                .scoreType(-4)
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .scoreSource("分享成果被打赏获得积分")
                .scoreStatus(1)
                .score(score)
                .build());
        // 发送系统消息
        // 查询消息是否存在
        messageRemindService.insertMessageRemind(MessageRemind.builder()
                .title("积分变动")
                .brief("分享成功被打赏获得积分,获得" + score + "积分,点击查看")
                .modelStatus(2)
                .jumpType(-5)
                .modelId(Convert.toStr(informationCommentId))
                .accountId(account.getAccountId())
                .build());

        // 更新打赏者用户信息
        accountService.updateApiAccount(ApiAccount.builder()
                .accountId(accountNew.getAccountId())
                .surplusScore(accountNew.getSurplusScore() - score)
                .build());
        // 用户积分存入记录
        // 赠送用户积分
        accountScoreService.insertAccountScore(AccountScore.builder()
                .accountId(accountNew.getAccountId())
                .targetId(Convert.toStr(informationCommentId))
                .scoreType(-5)
                .accountName(accountNew.getNickName())
                .accountPhone(accountNew.getPhone())
                .accountHead(accountNew.getHeadPortrait())
                .scoreSource("打赏分享成果扣除积分")
                .scoreStatus(2)
                .score(score)
                .build());
        return ok("成功！");
    }

    /**
     * pc@Description
     * params 家庭训练-打赏得积分（分享成果） 查看打赏积分列表
     * params informationCommentId 评论ID
     *
     */
    @PassToken
    @GetMapping(value = "/shareCommentScoreList")
    public ApiResult shareCommentScoreList(@RequestParam(defaultValue = "0") Long informationCommentId) {
        List<AccountScore> scoreList = accountScoreService.selectApiAccountScoreList(AccountScore.builder().scoreStatus(1).scoreType(-5).targetId(Convert.toStr(informationCommentId)).build());
        return ok(scoreList);
    }

    /**
     * 获取对应的积分
     * params type 积分类型
     */
    @PassToken
    @GetMapping(value = "/getScore")
    public ApiResult getScore(HttpServletRequest request,Integer type) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail("查询没有对应的积分。");
        }
        if (type == null) {
            return fail("查询的积分类型不正确。");
        }
        return ok(taskScoreService.selectScore(account, type));
    }

    /**
     * 获取对应的积分
     * params experienceSharingId 心得分享ID
     */
    @PassToken
    @DeleteMapping(value = "/remove/{experienceSharingId}")
    public ApiResult remove(HttpServletRequest request,@PathVariable("experienceSharingId") String experienceSharingId) {
        ExperienceSharing experienceSharing = experienceSharingService.selectExperienceSharingById(experienceSharingId);
        if(ObjectUtil.isEmpty(experienceSharing)){
            return fail("数据不存在。");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if(!experienceSharing.getAccountId().equals(account.getAccountId())){
            return fail("只能删除自己的数据。");
        }
        int num = experienceSharingService.deleteExperienceSharingById(experienceSharingId);
        if(num <= 0){
            return fail("删除数据失败。");
        }
        return fail("删除数据成功。");
    }
}
