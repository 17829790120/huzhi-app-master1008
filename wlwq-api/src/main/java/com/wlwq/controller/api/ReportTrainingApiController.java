package com.wlwq.controller.api;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.reportTraining.ReportTrainingParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.taskService.TaskReportTrainingServe;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 汇报训练
 *
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/reportTraining")
@AllArgsConstructor
public class ReportTrainingApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountEvaluateService evaluateService;

    private final IAccountLikeService likeService;

    private final ISysDeptService deptService;

    private final ITemplateService templateService;

    private final IReportTrainingService reportTrainingService;

    private final TaskScoreService scoreService;

    private final IReportTrainingReadRecordService readRecordService;

    private final TaskReportTrainingServe taskReportTrainingServe;


    /**
     * 各模版的列表
     * templateType 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     *
     * @return
     */
    @PassToken
    @GetMapping("/templateList")
    public ApiResult templateList(@RequestParam(defaultValue = "1") Integer templateType) {
        return ok(templateService.selectTemplate(Template.builder().templateType(templateType).build()));
    }

    /**
     * 汇报训练提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/sub")
    public ApiResult sub(HttpServletRequest request, @Validated ReportTraining params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
//        // 查询是否提交的是日训练，日训练一天只能提交一次
//        if (params.getTemplateType() == 1) {
//            // 查询今天是否提交过
//            int count = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
//                    .accountId(account.getAccountId())
//                    .templateType(params.getTemplateType())
//                    .date(DateUtil.today())
//                    .build());
//            if(count > 0){
//                return ApiResult.fail("一天只能提交一次，您今天已经提交过了！");
//            }
//        }
        params.setAccountId(account.getAccountId());
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        params.setDeptId(account.getDeptId());
        params.setPostIds(account.getPostIds());
        params.setCompanyId(account.getCompanyId());
        int count = reportTrainingService.insertReportTraining(params);
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.reportTrainingScore(params, account));
        return ApiResult.ok(reportTrainingScore(params, account));
    }

    /**
     * 汇报训练 列表
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request,
                          PageParam pageParam,
                          ReportTrainingParamVO paramVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<ReportTraining> trainingList = reportTrainingService.selectApiReportTrainingList(ReportTraining.builder()
                .accountId(account.getAccountId())
                .deptId(paramVO.getDeptId())
                .companyId(account.getCompanyId())
                .accountName(paramVO.getKeyword())
                .date(paramVO.getDate())
                .tag(paramVO.getTag())
                .month(paramVO.getMonth())
                .templateType(paramVO.getTemplateType())
                .build());
        trainingList.forEach(e -> {
            // 查询评论列表
            PageHelper.startPage(1, 2);
            List<AccountEvaluate> evaluateList = evaluateService.selectApiAccountEvaluateList(AccountEvaluate.builder()
                    .targetId(e.getReportTrainingId())
                    .evaluateType(paramVO.getTemplateType())
                    .parentId("0")
                    .build());
            e.setEvaluateList(new PageInfo<>(evaluateList));
            // 详情删除标识 0:否 1:可以删除
            e.setDelTag(account.getAccountId().equals(e.getAccountId()) ? 1 : 0);
            // 查询点赞的数量
            int likeCount = likeService.selectAccountLikeCount(AccountLike.builder().likeType(1).targetId(e.getReportTrainingId()).build());
            e.setLikeCount(likeCount);
            // 查询是否已读未读
            ReportTrainingReadRecord record = readRecordService.selectReportTrainingReadRecord(ReportTrainingReadRecord.builder()
                    .accountId(account.getAccountId())
                    .reportTrainingId(e.getReportTrainingId())
                    .build());
            e.setReadTag(record == null ? 0 : 1);
        });
        PageInfo<ReportTraining> pageInfo = new PageInfo<>(trainingList);
        return ok(pageInfo);
    }

    /**
     * 汇报训练 我的相关数量
     *
     * @param request
     * @return
     */
    @GetMapping("/myCount")
    public ApiResult myCount(HttpServletRequest request,
                             ReportTrainingParamVO paramVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        int count = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
                .accountId(account.getAccountId())
//                .deptId(paramVO.getDeptId())
                .companyId(account.getCompanyId())
                .accountName(paramVO.getKeyword())
                .date(paramVO.getDate())
                .tag(0)
                .month(paramVO.getMonth())
                .templateType(paramVO.getTemplateType())
                .build());
        return ok(count);
    }


    /**
     * 汇报训练修改提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editSub")
    public ApiResult editSub(HttpServletRequest request, @Validated ReportTraining params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        ReportTraining training = reportTrainingService.selectReportTrainingById(params.getReportTrainingId());
        if (training == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(training.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        params.setDeptId(account.getDeptId());
        params.setPostIds(account.getPostIds());
        params.setCompanyId(account.getCompanyId());
        int count = reportTrainingService.updateReportTraining(params);
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        return ApiResult.okMsg("提交成功！");
    }


    /**
     * 汇报训练删除
     *
     * @param request
     * @param reportTrainingId 汇报训练ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request, @RequestParam(defaultValue = "0") String reportTrainingId,Integer fileTag) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        ReportTraining training = reportTrainingService.selectReportTrainingById(reportTrainingId);
        if (training == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(training.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        int count = reportTrainingService.deleteReportTrainingById(reportTrainingId);

        if (count <= 0) {
            return ApiResult.fail("删除失败！");
        }
        training.setFileTag(fileTag);
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.reportTrainingDeleteScore(training, account));

        // 删除已读的记录
        readRecordService.delReportTrainingReadRecord(ReportTrainingReadRecord.builder().reportTrainingId(reportTrainingId).build());


        return ApiResult.ok(reportTrainingDeleteScore(training, account));
//        return ApiResult.okMsg("已删除");
    }

    /**
     * 汇报训练详情
     *
     * @param request
     * @param reportTrainingId 汇报训练ID
     * @return
     */
    @GetMapping("/detail")
    public ApiResult detail(HttpServletRequest request, @RequestParam(defaultValue = "0") String reportTrainingId) {
        ReportTraining training = reportTrainingService.selectReportTrainingById(reportTrainingId);
        if (training == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = new HashMap<>(8);
        // 详情
        map.put("training", training);
        // 详情删除标识 0:否 1:可以删除
        map.put("delTag", accountId.equals(training.getAccountId()) ? 1 : 0);
        // 详情点赞标识 0:否 1:已点赞
        AccountLike accountLike = likeService.selectAccountLike(AccountLike.builder().targetId(reportTrainingId).accountId(accountId).likeType(1).build());
        map.put("likeTag", accountLike == null ? 0 : 1);
        // 点赞列表
        List<AccountLike> likeList = likeService.selectAccountLikeList(AccountLike.builder().targetId(reportTrainingId).likeType(1).build());
        map.put("likeList", likeList);
        // 异步操作
        AsyncManager.me().execute(taskReportTrainingServe.record(training, accountId));
        return ok(map);
    }

    /**
     * 汇报训练相关数量
     *
     * @param request
     * @param date         日筛选 格式为2023-05-01
     * @param month        月筛选 格式为2023-05
     * @param templateType 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
     * @return
     */
    @GetMapping("/count")
    public ApiResult count(HttpServletRequest request, String date, String month, @RequestParam(defaultValue = "1") Integer templateType) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        Map<String, Object> map = new HashMap<>(5);
        // 查询本公司人数
        int companyCount = accountService.selectApiAccountCount(ApiAccount.builder().companyId(account.getCompanyId()).build());
        map.put("companyCount", companyCount);
        // 查询本月本公司已提交的人数
        int dayCount = reportTrainingService.selectApiReportTrainingAccountCount(ReportTraining.builder().templateType(templateType).companyId(account.getCompanyId()).date(date).month(month).build());
        map.put("dayCount", dayCount);
        // 查询本公司下的各个部门
        SysDept dept = new SysDept();
        dept.setParentId(account.getCompanyId());
        List<SysDept> deptList = deptService.selectApiDeptList(dept);
        deptList.forEach(e -> {
            e.setDeptPeopleCount(accountService.selectApiAccountCount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(e.getDeptId()).build()));
            e.setSubCount(reportTrainingService.selectApiReportTrainingAccountCount(ReportTraining.builder().templateType(templateType).companyId(account.getCompanyId()).date(date).month(month).deptId(e.getDeptId()).build()));
        });
        map.put("deptList", deptList);
        return ok(map);
    }

    /**
     * 汇报训练查询赠送积分
     *
     * @param params  汇报训练
     * @param account 用户
     * @return
     */
    public Integer reportTrainingScore(ReportTraining params, ApiAccount account) {
        // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
        Integer score = 0;
        Integer templateType = params.getTemplateType();
        if (templateType == 1) {
            score = reportTrainingScoreRecord(params, account, 1, 2);
        }
        if (templateType == 2) {
            score = reportTrainingScoreRecord(params, account, 4, 5);
        }
        if (templateType == 3) {
            score = reportTrainingScoreRecord(params, account, 6, 7);
        }
        if (templateType == 4) {
            score = reportTrainingScoreRecord(params, account, 8, 9);
        }
        if (templateType == 5) {
            score = reportTrainingScoreRecord(params, account, 10, 11);
        }
        if (templateType == 6) {
            score = reportTrainingScoreRecord(params, account, 12, 13);
        }
        if (templateType == 7) {
            score = reportTrainingScoreRecord(params, account, 14, 15);
        }
        if (templateType == 8) {
            score = reportTrainingScoreRecord(params, account, 16, 17);
        }
        return score;
    }

    /**
     * 汇报训练查询删除积分
     *
     * @param account      用户
     * @return
     */
    public Integer reportTrainingDeleteScore(ReportTraining training, ApiAccount account) {
        // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
        Integer score = 0;
        Integer templateType = training.getTemplateType();
        if (templateType == 1) {
            score = reportTrainingDeleteScoreRecord(training, account, 1, 2);
        }
        if (templateType == 2) {
            score = reportTrainingDeleteScoreRecord(training, account, 4, 5);
        }
        if (templateType == 3) {
            score = reportTrainingDeleteScoreRecord(training, account, 6, 7);
        }
        if (templateType == 4) {
            score = reportTrainingDeleteScoreRecord(training, account, 8, 9);
        }
        if (templateType == 5) {
            score = reportTrainingDeleteScoreRecord(training, account, 10, 11);
        }
        if (templateType == 6) {
            score = reportTrainingDeleteScoreRecord(training, account, 12, 13);
        }
        if (templateType == 7) {
            score = reportTrainingDeleteScoreRecord(training, account, 14, 15);
        }
        if (templateType == 8) {
            score = reportTrainingDeleteScoreRecord(training, account, 16, 17);
        }
        return score;
    }


    /**
     * 汇报训练删除积分记录
     *
     * @param training
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public Integer reportTrainingDeleteScoreRecord(ReportTraining training, ApiAccount account, Integer subType, Integer fileType) {
        // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
        Integer templateType = training.getTemplateType();
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = scoreService.selectScore(account, subType);
        if (selectScore1 > 0) {
            // 赠送用户积分
            score -= selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (training.getFileTag() != null && training.getFileTag() == 1) {
            int selectScore = scoreService.selectScore(account, fileType);
            if (selectScore > 0) {
                // 赠送用户积分
                score -= selectScore;
            }
        }
        if (templateType == 1) {
            // 日精进判断是否满勤
            // 查询当月的天数
            String month = DateUtil.format(new Date(), "yyyy-MM");
            int day = DateUtils.day(month);
            int reportCount = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
                    .accountId(account.getAccountId())
                    .templateType(1)
                    .month(month)
                    .build());
            if (reportCount >= day) {
                // 日精进满勤赠送积分
                int selectScore = scoreService.selectScore(account, 3);
                if (selectScore > 0) {
                    // 赠送用户积分
                    score -= selectScore;
                }
            }
        }
        return score;
    }


    /**
     * 汇报训练积分记录
     *
     * @param params
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public Integer reportTrainingScoreRecord(ReportTraining params, ApiAccount account, Integer subType, Integer fileType) {
        // 1:日精进 2:周汇报 3:月总结 4：季度工作报告 5：半年工作报告 6：年度工作报告 7：述职报告 8：其他汇报
        Integer templateType = params.getTemplateType();
        // 赠送积分
        Integer score = 0;
        // 每次提交都赠送积分
        int selectScore1 = scoreService.selectScore(account, subType);
        if (selectScore1 > 0) {
            // 赠送用户积分
            score += selectScore1;
        }
        // 判断是否上传文件 0：否 1:是
        if (params.getFileTag() != null && params.getFileTag() == 1) {
            int selectScore = scoreService.selectScore(account, fileType);
            if (selectScore > 0) {
                // 赠送用户积分
                score += selectScore;
            }
        }
        if (templateType == 1) {
            // 日精进判断是否满勤
            // 查询当月的天数
            String month = DateUtil.format(new Date(), "yyyy-MM");
            int day = DateUtils.day(month);
            int reportCount = reportTrainingService.selectApiReportTrainingCount(ReportTraining.builder()
                    .accountId(account.getAccountId())
                    .templateType(1)
                    .month(month)
                    .build());
            if (reportCount >= day) {
                // 日精进满勤赠送积分
                int selectScore = scoreService.selectScore(account, 3);
                if (selectScore > 0) {
                    // 赠送用户积分
                    score += selectScore;
                }
            }
        }
        return score;
    }
}
