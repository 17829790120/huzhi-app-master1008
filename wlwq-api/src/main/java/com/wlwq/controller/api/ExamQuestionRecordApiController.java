package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.bestPay.constant.ModuleConstant;
import com.wlwq.bestPay.mq.RabbitMQSendService;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.domain.AjaxResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.courseExam.ExamQuestionParam;
import com.wlwq.params.courseExam.ExamQuestionRecordParam;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wwb
 * 考试答题记录
 */
@RestController
@RequestMapping(value = "/api/examQuestionRecord")
@AllArgsConstructor
public class ExamQuestionRecordApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IExamQuestionRecordService examQuestionRecordService;

    private final IQuestionManagerService questionManagerService;

    private final IExamQuestionManagerService examQuestionManagerService;

    private final IExamRecordService examRecordService;

    private final ISysConfigService configService;

    private final RabbitMQSendService rabbitMQSendService;

    private final ICourseChapterService courseChapterService;

    private final IAccountMedalRecordService accountMedalRecordService;

    private final IAccountScoreService accountScoreService;

    private final IMessageRemindService messageRemindService;

    private IExamPaperRecordService examPaperRecordService;

    /**
     * pc@Description
     * params 考试答题记录
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    @PassToken
    public ApiResult add(HttpServletRequest request, @RequestBody ExamQuestionRecordParam params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        List<ExamQuestionParam> list = params.getList();
        if(list == null || list.size()<=0){
            return fail("请添加考试答题记录数据");
        }
        //是否全是客观题（全是选择题，判断题）
        AtomicBoolean isAllObjectiveQuestion = new AtomicBoolean(true);
        //添加考试记录，并计算非客观讲的得分。如果全是非客观题，直接给总分。
        list.forEach(
                obj->{
                    ExamQuestionRecord examQuestionRecord = ExamQuestionRecord.builder().build();
                    //试题类型（0：章节课程；1：测试训练）
                    if(params.getQuestionType() != null && params.getQuestionType() == 1){
                        ExamQuestionManager examQuestionManager = examQuestionManagerService.selectExamQuestionManagerById(obj.getQuestionManagerId());
                        BeanUtil.copyProperties(examQuestionManager,examQuestionRecord);
                        examQuestionRecord.setQuestionManagerId(examQuestionManager.getExamQuestionManagerId());
                    }else{
                        QuestionManager questionManager = questionManagerService.selectQuestionManagerById(obj.getQuestionManagerId());
                        BeanUtil.copyProperties(questionManager,examQuestionRecord);
                        examQuestionRecord.setQuestionManagerId(questionManager.getQuestionManagerId());
                    }

                    examQuestionRecord.setExamRecordId(obj.getExamRecordId());
                    examQuestionRecord.setMyAnswerStatus(obj.getMyAnswerStatus());

                    String answerStatus = examQuestionRecord.getAnswerStatus();
                    String myAnswerStatus = examQuestionRecord.getMyAnswerStatus();
                    if(examQuestionRecord.getQuestionStatus() == 1 || examQuestionRecord.getQuestionStatus() == 2
                            || examQuestionRecord.getQuestionStatus() == 4){
                        if(StringUtils.isEmpty(answerStatus) || StringUtils.isEmpty(myAnswerStatus)){
                            examQuestionRecord.setMyScore(0.0);
                        }else{
                            String[] answerStatusArray = answerStatus.split(",");
                            Arrays.sort(answerStatusArray);
                            String[] myAnswerStatusArray = myAnswerStatus.split(",");
                            Arrays.sort(myAnswerStatusArray);
                            if(Arrays.equals(answerStatusArray, myAnswerStatusArray)){
                                examQuestionRecord.setMyScore(examQuestionRecord.getScore());
                            }else{
                                examQuestionRecord.setMyScore(0.0);
                            }
                        }
                    }

                    //1:单项选择题 2：多项选择题 3：解答题
                    if(examQuestionRecord.getQuestionStatus() == 3){
                        isAllObjectiveQuestion.set(false);
                        examQuestionRecord.setMyScore(-0.0);
                    }

                    int num = examQuestionRecordService.insertExamQuestionRecord(examQuestionRecord);
                    if(num<=0){
                        throw new ApiException("添加考试失败。");
                    }
                }
        );
        //更新考试记录状态为已考试
        ExamRecord examRecord = examRecordService.selectExamRecordById(params.getExamRecordId());
        if(examRecord != null){
            examRecord.setAnswerStatus(1);
            if(isAllObjectiveQuestion.get()){
                Double score = examQuestionRecordService.getScore(examRecord.getExamRecordId());
                examRecord.setScore(score);
                examRecord.setScoreStatus(1);
                //获得积分逻辑处理
                setIntegral(isAllObjectiveQuestion,examRecord,account,score);
            }
            int num = examRecordService.updateExamRecord(examRecord);
            if(num<=0){
                throw new ApiException("添加考试失败。");
            }
        }
        return ok("添加考试成功。");
    }

    /**
     * 获得积分，徽章逻辑处理
     */
    private AjaxResult setIntegral(AtomicBoolean isAllObjectiveQuestion,ExamRecord examRecord,ApiAccount account,Double score){
        //更新考试得分信息,如果全是非客观题，直接给总分。
        if(isAllObjectiveQuestion.get()){
            Integer integral = 0;
            String message = "";
            Double passingScore = 0.0;
            if(score == null){
                return AjaxResult.error("得分为0，不进行操作。");
            }
            //考试及格后获得积分
            // 异步操作，送积分
            //试题类型（0：章节课程；1：测试训练）
            if(examRecord.getQuestionType() == 0){
                CourseChapter courseChapter= courseChapterService.selectCourseChapterById(examRecord.getChapterId());
                if(courseChapter != null){
                    //判断分数所对应的级别（及格，优秀，满分等）
                    if(new BigDecimal(courseChapter.getFullScore()).compareTo(new BigDecimal(score)) ==0 && courseChapter.getFullScore()> 0.0){
                        //满分获得的积分
                        integral = courseChapter.getFullIntegralScore();
                        message = "章节考试满分获得积分";
                    }else if(new BigDecimal(courseChapter.getExcellentScore()).compareTo(new BigDecimal(score)) < 0 && courseChapter.getExcellentScore()> 0.0){
                        //优秀分数获得的积分
                        integral = courseChapter.getExcellentIntegralScore();
                        message = "章节考试优秀获得积分";
                    }else if(new BigDecimal(courseChapter.getGoodScore()).compareTo(new BigDecimal(score)) < 0 && courseChapter.getGoodScore()> 0.0){
                        //良好分数获得的积分
                        integral = courseChapter.getGoodIntegralScore();
                        message = "章节考试良好获得积分";
                    }else if(new BigDecimal(courseChapter.getPassingScore()).compareTo(new BigDecimal(score)) < 0 && courseChapter.getPassingScore()> 0.0){
                        //及格分数获得的积分
                        integral = courseChapter.getPassingIntegralScore();
                        message = "章节考试及格获得积分";
                    }

                    passingScore = courseChapter.getPassingScore();
                }
            }else if(examRecord.getQuestionType() == 1){
                //测试训练考试得积分逻辑
                //测试训练考试记录
                ExamPaperRecord examPaperRecord = examPaperRecordService.selectExamPaperRecordById(examRecord.getExamPaperRecordId());
                if(examPaperRecord != null){
                    //判断分数所对应的级别（及格，优秀，满分等）
                    if(new BigDecimal(examPaperRecord.getFullScore()).compareTo(new BigDecimal(score)) ==0 && examPaperRecord.getFullScore()> 0.0){
                        //满分获得的积分
                        integral = examPaperRecord.getFullIntegralScore();
                        message = "测试训练考试满分获得积分";
                    }else if(new BigDecimal(examPaperRecord.getExcellentScore()).compareTo(new BigDecimal(score)) < 0 && examPaperRecord.getExcellentScore()> 0.0){
                        //优秀分数获得的积分
                        integral = examPaperRecord.getExcellentIntegralScore();
                        message = "测试训练考试优秀获得积分";
                    }else if(new BigDecimal(examPaperRecord.getGoodScore()).compareTo(new BigDecimal(score)) < 0 && examPaperRecord.getGoodScore()> 0.0){
                        //良好分数获得的积分
                        integral = examPaperRecord.getGoodIntegralScore();
                        message = "测试训练考试良好获得积分";
                    }else if(new BigDecimal(examPaperRecord.getPassingScore()).compareTo(new BigDecimal(score)) < 0 && examPaperRecord.getPassingScore()> 0.0){
                        //及格分数获得的积分
                        integral = examPaperRecord.getPassingIntegralScore();
                        message = "测试训练考试及格获得积分";
                    }
                    passingScore = examPaperRecord.getPassingScore();
                }
            }
            Long courseId = 0L;
            if(examRecord.getScore() != null &&  new BigDecimal(examRecord.getScore()).compareTo(new BigDecimal(passingScore)) > 0){
                if(examRecord.getQuestionType() == 0){
                    courseId = examRecord.getCourseId();
                    // 将心得分享发送到延时队列，7天后还未分享心得则标注为未分享心得，心得分享失效
                    String automaticReceiptTime = Optional.ofNullable(configService.selectConfigByKey("experience_sharing_automatic_expiration_time")).orElse("7");
                    Long expiration = new BigDecimal(automaticReceiptTime).multiply(BigDecimal.valueOf(24L)).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(60L)).multiply(BigDecimal.valueOf(1000L)).longValue();
                    com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
                    jsonObject.put("moduleName", ModuleConstant.EXPERIENCE_SHARING_TAKE_DELIVERY_TIMEOUT);
                    jsonObject.put("messageContent", examRecord.getExamRecordId());
                    rabbitMQSendService.sendDelayMessage(expiration, jsonObject.toString());
                }

                if(integral == 0){
                    return AjaxResult.success("积分为0，不进行操作。");
                }
                // 更新用户信息
                accountService.updateApiAccount(ApiAccount.builder()
                        .accountId(account.getAccountId())
                        .surplusScore(account.getSurplusScore() + integral)
                        .totalScore(account.getTotalScore() + integral)
                        .build());
                // 查看是否满足勋章条件并更新勋章
                //accountMedalRecordService.updateMedalRecord(accountService.selectApiAccountById(account.getAccountId()), integral, message);
                // 用户积分存入记录
                // 赠送用户积分
                accountScoreService.insertAccountScore(AccountScore.builder()
                        .accountId(account.getAccountId())
                        .targetId(examRecord.getExamRecordId())
                        .scoreType(-2)
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .scoreSource(message)
                        .scoreStatus(1)
                        .score(integral)
                        .courseId(courseId)
                        .build());
                // 发送系统消息
                // 查询消息是否存在
                messageRemindService.insertMessageRemind(MessageRemind.builder()
                        .title("积分变动")
                        .brief(message+",获得" + integral + "积分,点击查看")
                        .modelStatus(2)
                        .jumpType(-2)
                        .modelId("0")
                        .accountId(account.getAccountId())
                        .build());
            }
        }
        return AjaxResult.error("得分操作成功。");
    }
}
