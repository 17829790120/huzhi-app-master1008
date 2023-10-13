package com.wlwq.controller.api;

import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wwb
 * @课程章节Controller
 */
@RestController
@RequestMapping(value = "/api/courseChapter")
@AllArgsConstructor
public class CourseChapterApiController extends ApiController {

    private final ICourseChapterService courseChapterService;

    private final ICourseViewRecordService courseViewRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IExamRecordService examRecordService;

    private final IExperienceSharingService experienceSharingService;

    private final IMeetingTrainingService meetingTrainingService;

    private final IMeetingExamineInitiateService initiateService;

    private final IQuestionManagerService questionManagerService;

    private final TaskScoreService taskScoreService;

    private final IAccountScoreService accountScoreService;


    /**
     * 课程章节列表
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(HttpServletRequest request,
                          @RequestParam(defaultValue = "") Long parentId,
                          @RequestParam(defaultValue = "") Long courseId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        List<CourseChapter> list = courseChapterService.selectCourseChapterList(CourseChapter.builder()
                .parentId(parentId)
                .courseId(courseId)
                .build());
        if (parentId != null && parentId != 0) {
            list.forEach(obj -> {
                        //判断用户是否通过观看课程视频获取过积分
                        AccountScore accountScore = accountScoreService.selectScoreByAccountIdAndTargetId(AccountScore.builder()
                                .accountId(account.getAccountId())
                                .targetId(Convert.toStr(obj.getChapterId()))
                                .build());
                        obj.setScoreStatus(accountScore == null ? 0 : 1);
                        //学习情况
                        List<CourseViewRecord> courseViewRecordList = courseViewRecordService.selectCourseViewRecordList(CourseViewRecord
                                .builder()
                                .accountId(account.getAccountId())
                                .chapterId(obj.getChapterId())
                                .courseId(obj.getCourseId())
                                .build());
                        if (courseViewRecordList != null && courseViewRecordList.size() > 0) {
                            obj.setWatchStatus(courseViewRecordList.get(0).getWatchStatus());
                            //考试情况
                            List<ExamRecord> examRecordList = examRecordService.selectExamRecordList(ExamRecord
                                    .builder()
                                    .accountId(account.getAccountId())
                                    .chapterId(obj.getChapterId())
                                    .courseId(obj.getCourseId())
                                    .build());
                            //考试状态：0：未考试；1：考试（得分计算中）；2：不及格，再次考试；3：考试及格
                            if (examRecordList != null && examRecordList.size() > 0) {
                                //未评分
                                List<ExamRecord> weiPingFenList = examRecordList.stream()
                                        .filter(object -> (object.getScoreStatus() == 0))
                                        .collect(Collectors.toList());
                                if (weiPingFenList != null && weiPingFenList.size() > 0) {
                                    obj.setExamStatus(1);
                                    obj.setExamRecordId(weiPingFenList.get(0).getExamRecordId());
                                    return;
                                }
                                //及格
                                List<ExamRecord> jiGeList = examRecordList.stream()
                                        .filter(object -> (object.getScoreStatus() == 1 && object.getScore() >= 60))
                                        .collect(Collectors.toList());
                                if (jiGeList != null && jiGeList.size() > 0) {
                                    obj.setExamStatus(3);
                                    obj.setScore(jiGeList.get(0).getScore());
                                    obj.setExamRecordId(jiGeList.get(0).getExamRecordId());

                                    //分享
                                    List<ExperienceSharing> experienceSharingList = experienceSharingService.selectExperienceSharingList(ExperienceSharing
                                            .builder()
                                            .accountId(account.getAccountId())
                                            .chapterId(obj.getChapterId())
                                            .courseId(obj.getCourseId())
                                            .build());
                                    if (experienceSharingList != null && experienceSharingList.size() > 0) {
                                        obj.setExperienceSharingStatus(2);
                                    } else {
                                        //现在时间大于考试及格的考试记录时间7天，则分享失效
                                        if (DateUtils.isLatestWeek(jiGeList.get(0).getCreateTime(), new Date())) {
                                            obj.setExperienceSharingStatus(0);
                                        } else {
                                            obj.setExperienceSharingStatus(1);
                                        }
                                    }
                                    //申请转训信息查询（转训信息对应的是会议训练信息）
                                    MeetingTraining meetingTraining = meetingTrainingService.selectMeetingTrainingByTransferTraining(MeetingTraining
                                            .builder()
                                            .delStatus(0)
                                            .chapterId(obj.getChapterId())
                                            .courseId(obj.getCourseId())
                                            .organizerAccountId(account.getAccountId())
                                            .build());
                                    if (meetingTraining != null) {
                                        //申请转训信息状态查询（转训审核信息对应的是会议训练审核信息）
                                        MeetingExamineInitiate meetingExamineInitiate = initiateService.selectMeetingExamineInitiateByTransferTraining(
                                                meetingTraining.getMeetingTrainingId(), 24);
                                        if (meetingExamineInitiate != null) {
                                            obj.setTransferTrainingStatus(meetingExamineInitiate.getExamineStatus());
                                        }
                                        //审核通过的给积分显示
                                        int score = 0;
                                        if (meetingExamineInitiate != null && meetingExamineInitiate.getExamineStatus() == 3) {
                                            score = taskScoreService.selectScore(account, 29);
                                        }
                                        obj.setIntegral(score);
                                    } else {
                                        obj.setTransferTrainingStatus(-1);
                                        obj.setIntegral(0);
                                    }
                                } else {
                                    //不及格
                                    List<ExamRecord> buJiGeList = examRecordList.stream()
                                            .filter(object -> (object.getScoreStatus() == 1 && object.getScore() < 60))
                                            .collect(Collectors.toList());
                                    if (buJiGeList != null && buJiGeList.size() > 0) {
                                        obj.setExamStatus(2);
                                        return;
                                    }
                                }
                            } else {
                                obj.setExamStatus(0);
                            }
                        } else {
                            obj.setWatchStatus(0);
                        }
                    }
            );
        }
        if (parentId != null && parentId == 0 && courseId != null) {
            list.forEach(
                    obj -> {
                        HashMap<String, Long> map = courseChapterService.selectCourseChapterWatchByChapterId(obj.getChapterId(), account.getAccountId());
                        if (map.get("watchStatus") == 1) {
                            obj.setWatchStatus(1);
                        } else {
                            obj.setWatchStatus(0);
                        }
                    }
            );
        }
        return ok(list);
    }

    /**
     * 课程章节列表观看记录
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/courseChapterWatch")
    public ApiResult list(HttpServletRequest request, @RequestParam(defaultValue = "") Long courseId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        HashMap<String, Long> map = courseChapterService.selectCourseChapterWatch(courseId, account.getAccountId());
        return ok(map);
    }

    /**
     * 观看课程获得积分
     *
     * @return
     */
    @GetMapping(value = "/getCourseScore")
    public ApiResult getCourseScore(HttpServletRequest request,
                                    @RequestParam(defaultValue = "0") Long courseId,
                                    @RequestParam(defaultValue = "0") Long chapterId) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户没登录！");
        }
        CourseChapter courseChapter = courseChapterService.selectCourseChapterById(chapterId);
        if (courseChapter == null) {
            return ApiResult.fail("该课程视频不存在！");
        }
        int score = taskScoreService.selectScore(account, 32);
        if (score > 0) {
            // 更新用户信息
            account.setAccountId(account.getAccountId());
            account.setSurplusScore(account.getSurplusScore() + score);
            account.setTotalScore(account.getTotalScore() + score);


            // 用户积分存入记录
            // 赠送用户积分
            AccountScore accountScore = AccountScore.builder()
                    .accountId(account.getAccountId())
                    .targetId(Convert.toStr(chapterId))
                    .courseId(courseId)
                    .scoreType(-9)
                    .accountName(account.getNickName())
                    .accountPhone(account.getPhone())
                    .accountHead(account.getHeadPortrait())
                    .scoreSource(taskScoreService.scoreSource(32))
                    .scoreStatus(1)
                    .score(score)
                    .build();
            // 发送系统消息
            // 查询消息是否存在
            MessageRemind messageRemind = MessageRemind.builder()
                    .title("积分变动")
                    .brief("观看课程,获得" + score + "积分,点击查看")
                    .modelStatus(2)
                    .jumpType(-2)
                    .modelId("32")
                    .accountId(account.getAccountId())
                    .build();
            taskScoreService.insertIntegralScore(account, accountScore, messageRemind);
        }
        return ApiResult.ok("恭喜您获得积分。");
    }


    /**
     * 课程章节信息详情
     *
     * @param chapterId 章节ID
     * @return
     */
    @PassToken
    @GetMapping(value = "/detail")
    public ApiResult detail(@RequestParam(defaultValue = "0") Long chapterId) {
        Map<String, Object> map = new HashMap<>();
        CourseChapter courseChapter = courseChapterService.selectCourseChapterById(chapterId);
        if (courseChapter != null) {
            //根据课程章节id查询对应的试题数量
            int count = questionManagerService.selectQuestionManagerCountByChapterId(chapterId);
            map.put("courseChapter", courseChapter);
            map.put("count", count);
        } else {
            map.put("courseChapter", courseChapter);
            map.put("count", 0);
        }
        return ok(map);
    }
}
