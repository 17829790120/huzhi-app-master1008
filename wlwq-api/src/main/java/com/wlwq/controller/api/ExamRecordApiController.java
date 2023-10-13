package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.courseExam.ExamRecordParam;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wwb
 * 考试记录
 */
@RestController
@RequestMapping(value = "/api/examRecord")
@AllArgsConstructor
public class ExamRecordApiController extends ApiController {

    private final IExamRecordService examRecordService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IExamQuestionRecordService examQuestionRecordService;

    private final IExamPaperRecordService examPaperRecordService;

    private final IAccountScoreService accountScoreService;

    /**
     * pc@Description
     * params 考试信息
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    public ApiResult add(HttpServletRequest request, @RequestBody ExamRecordParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        ExamRecord examRecord = ExamRecord.builder().build();
        BeanUtil.copyProperties(params, examRecord);

        examRecord.setAccountId(account.getAccountId());
        examRecord.setNickName(account.getNickName());
        examRecord.setHeadPortrait(account.getHeadPortrait());
        examRecord.setDeptId(account.getDeptId());
        examRecord.setDelStatus(0);
        examRecord.setBeginTime(new Date());
        int num = examRecordService.insertExamRecord(examRecord);
        if (num <= 0) {
            throw new ApiException("添加考试失败。");
        }
        map.put("result", examRecord);
        map.put("state", 200);
        return ok(map);
    }

    /**
     * 更新考试结束信息
     *
     * @param examRecordId 考试记录表id
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping(value = "/update/{examRecordId}")
    public ApiResult update(@PathVariable("examRecordId") String examRecordId, HttpServletRequest request) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        ExamRecord examRecord = examRecordService.selectExamRecordById(examRecordId);
        if (examRecord == null) {
            return fail("没有此考试记录信息。");
        }
        //examRecord.setEndTime(new Date());
        int num = examRecordService.updateExamRecord(examRecord);
        if (num <= 0) {
            throw new ApiException("更新考试失败。");
        }
        return ok("更新考试成功。");
    }

    /**
     * 获取考试记录得分情况
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/getScoreInformation")
    public ApiResult getScoreInformation(HttpServletRequest request, @RequestParam(defaultValue = "") String examRecordId) {
        if (examRecordId == null) {
            return fail("请选择需要查看的考试记录");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        HashMap<String, Object> map = new HashMap(3);
        ExamRecord examRecord = examRecordService.selectExamRecordById(examRecordId);
        if (examRecord == null) {
            return fail("没有此考试记录信息。");
        }
        //查询考试对应的获得积分数据
        AccountScore accountScore = accountScoreService.selectScoreByAccountIdAndTargetId(AccountScore
                .builder()
                .targetId(examRecord.getExamRecordId())
                .accountId(account.getAccountId())
                .build());
        map.put("integral", accountScore != null ? accountScore.getScore() : 0);
        map.put("examRecord", examRecord);

        List<ExamQuestionRecord> examQuestionRecordList = examQuestionRecordService.selectExamQuestionRecordList(ExamQuestionRecord
                .builder()
                .examRecordId(examRecordId)
                .build());

        List<ExamQuestionRecord> choiceQuestionList = examQuestionRecordList.stream()
                .filter(obj -> obj.getQuestionStatus() == 1 || obj.getQuestionStatus() == 2 || obj.getQuestionStatus() == 4)
                .collect(Collectors.toList());
        List<ExamQuestionRecord> answerQuestionsList = examQuestionRecordList.stream()
                .filter(obj -> obj.getQuestionStatus() == 3)
                .collect(Collectors.toList());
        List<ExamQuestionRecord> correctList = examQuestionRecordList.stream()
                .filter(obj -> (obj.getQuestionStatus() == 1 || obj.getQuestionStatus() == 2 || obj.getQuestionStatus() == 4) && obj.getMyScore() > 0)
                .collect(Collectors.toList());
        List<ExamQuestionRecord> errorList = examQuestionRecordList.stream()
                .filter(obj -> (obj.getQuestionStatus() == 1 || obj.getQuestionStatus() == 2 || obj.getQuestionStatus() == 4) && obj.getMyScore() == 0
                        && !StringUtils.isEmpty(obj.getMyAnswerStatus()))
                .collect(Collectors.toList());
        List<ExamQuestionRecord> puanDuanQuestionList = examQuestionRecordList.stream()
                .filter(obj -> obj.getQuestionStatus() == 4)
                .collect(Collectors.toList());
        //选择题
        map.put("choiceQuestionList", choiceQuestionList);
        //简答题
        map.put("answerQuestionsList", answerQuestionsList);
        //判断题
        map.put("puanDuanQuestionList", puanDuanQuestionList);
        //正确的选择题
        map.put("correctList", correctList);
        //错误的选择题
        map.put("errorList", errorList);
        return ok(map);
    }

    /**
     * 获取考试记录
     *
     * @param keyword 关键字搜索
     * @return
     */
    //@PassToken
    @GetMapping(value = "/getExamRecord")
    public ApiResult getExamRecord(PageParam pageParam, HttpServletRequest request,
                                   Integer questionType,
                                   Integer scoreStatus,
                                   Integer answerStatus,
                                   String keyword) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<ExamRecord> list = examRecordService.selectExamRecordList(ExamRecord
                .builder()
                .accountId(account.getAccountId())
                .scoreStatus(scoreStatus)
                .answerStatus(answerStatus)
                .questionType(questionType)
                .examPaperTitle(keyword)
                .build());
        PageInfo<ExamRecord> pageInfo = new PageInfo<>(list);
        list.forEach(
                obj -> {
                    //考试情况
                    List<ExamQuestionRecord> examRecordList = examQuestionRecordService.selectExamQuestionRecordList(ExamQuestionRecord
                            .builder()
                            .examRecordId(obj.getExamRecordId())
                            .build());
                    //考试状态：0：未考试；1：考试（得分计算中）；2：不及格，再次考试；3：考试及格
                    if (ObjectUtil.isNotEmpty(examRecordList) && examRecordList.size() > 0) {
                        obj.setExamStatus(1);
                    } else {
                        obj.setExamStatus(0);
                    }

                    //查询考试对应的获得积分数据
                    AccountScore accountScore = accountScoreService.selectScoreByAccountIdAndTargetId(AccountScore
                            .builder()
                            .targetId(obj.getExamRecordId())
                            .scoreStatus(1)
                            .accountId(account.getAccountId())
                            .build());
                    obj.setIntegral(accountScore != null ? accountScore.getScore() : 0);
                }
        );
        return ok(pageInfo);
    }

    /**
     * pc@Description
     * params 添加测试训练考试信息
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addTestTraining")
    public ApiResult addTestTraining(HttpServletRequest request, @RequestBody ExamRecordParam params, BindingResult bindingResult) {
        HashMap map = new HashMap(2);
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        ExamPaperRecord examPaperRecord = examPaperRecordService.selectExamPaperRecordById(params.getExamPaperRecordId());
        if (examPaperRecord == null) {
            return fail("没有此试卷信息。");
        }
        int num = examRecordService.deleteExamRecordById(params.getExamRecordId());
        if (num <= 0) {
            throw new ApiException("添加考试失败。");
        }
/*        List<ExamRecord> list = examRecordService.selectExamRecordList(ExamRecord
                .builder()
                .accountId(account.getAccountId())
                .examPaperRecordId(examRecord.getExamPaperRecordId())
                .build());

        if(list != null && list.size() > 0){
            return AjaxResult.error("用户已发放此试卷。");
        }*/
        ExamRecord examRecord = ExamRecord.builder().build();
        examRecord.setExamRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examRecord.setAccountId(account.getAccountId());
        examRecord.setHeadPortrait(account.getHeadPortrait());
        examRecord.setNickName(account.getNickName());
        examRecord.setExamPaperTitle(examPaperRecord.getExamPaperTitle());
        examRecord.setQuestionType(1);
        examRecord.setDeptId(account.getDeptId());
        examRecord.setBeginTime(examPaperRecord.getBeginTime());
        examRecord.setEndTime(examPaperRecord.getEndTime());
        examRecord.setExamPaperRecordId(examPaperRecord.getExamPaperRecordId());
        num = examRecordService.insertExamRecord(examRecord);
        if (num <= 0) {
            throw new ApiException("添加考试失败。");
        }
        map.put("examPaperRecord", examPaperRecord);
        map.put("result", examRecord);
        map.put("state", 200);
        return ok(map);
    }


}
