package com.wlwq.controller.api;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.SixStructures.SixStructuresParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.taskService.TaskScoreService;
import com.wlwq.taskService.TaskSixStructuresService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 六大架构Controller
 *
 * @author wlwq
 * @date 2023-08-28
 */
@RestController
@RequestMapping("/api/sixStructures")
@AllArgsConstructor
public class SixStructuresApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final ISixStructuresClassService sixStructuresClassService;

    private final ISixStructuresService sixStructuresService;

    private final ISixStructuresReadRecordService sixStructuresReadRecordService;

    private final TaskScoreService scoreService;

    private final IAccountEvaluateService evaluateService;

    private final IAccountLikeService likeService;

    private final TaskSixStructuresService tasksixStructuresService;

    private final ISysDeptService deptService;


    /**
     * 六大架构提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/sub")
    public ApiResult sub(HttpServletRequest request, @Validated SixStructures params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        params.setAccountId(account.getAccountId());
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        params.setDeptId(account.getDeptId());
        params.setPostIds(account.getPostIds());
        params.setCompanyId(account.getCompanyId());
        int count = sixStructuresService.insertSixStructures(params);
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.sixStructuresScore(params, account));
        return ApiResult.ok(reportTrainingScore(params, account));
    }

    /**
     * 六大架构列表
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request,
                          PageParam pageParam,
                          SixStructuresParamVO paramVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());

        List<SixStructures> sixStructuresList = sixStructuresService.selectApiSixStructuresList(SixStructures.builder()
                .accountId(account.getAccountId())
                .deptId(paramVO.getDeptId())
                .companyId(account.getCompanyId())
                .threeStoreClassId(paramVO.getThreeStoreClassId())
                .date(paramVO.getDate())
                .month(paramVO.getMonth())
                .tag(paramVO.getTag())
                .accountName(paramVO.getKeyword())
                .build());
        sixStructuresList.forEach(e -> {
            // 查询评论列表
            PageHelper.startPage(1, 2);
            List<AccountEvaluate> evaluateList = evaluateService.selectApiAccountEvaluateList(AccountEvaluate.builder()
                    .targetId(e.getSixStructuresId())
                    .threeStoreClassId(paramVO.getThreeStoreClassId())
                    .parentId("0")
                    .build());
            e.setEvaluateList(new PageInfo<>(evaluateList));
            // 详情删除标识 0:否 1:可以删除
            e.setDelTag(account.getAccountId().equals(e.getAccountId()) ? 1 : 0);
            // 查询点赞的数量
            int likeCount = likeService.selectAccountLikeCount(AccountLike.builder().likeType(2).targetId(e.getSixStructuresId()).build());
            e.setLikeCount(likeCount);
            // 查询是否已读未读
            SixStructuresReadRecord record = sixStructuresReadRecordService.selectSixStructuresReadRecord(SixStructuresReadRecord.builder()
                    .accountId(account.getAccountId())
                    .sixStructuresId(e.getSixStructuresId())
                    .build());
            e.setReadTag(record == null ? 0 : 1);
        });
        PageInfo<SixStructures> pageInfo = new PageInfo<>(sixStructuresList);
        return ok(pageInfo);
    }

    /**
     * 六大架构 我的相关数量
     *
     * @param request
     * @return
     */
    @GetMapping("/myCount")
    public ApiResult myCount(HttpServletRequest request,
                             SixStructuresParamVO paramVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        int count = sixStructuresService.selectSixStructuresCount(SixStructures.builder()
                .accountId(account.getAccountId())
                .companyId(account.getCompanyId())
                .accountName(paramVO.getKeyword())
                .tag(0)
                .date(paramVO.getDate())
                .month(paramVO.getMonth())
                .threeStoreClassId(paramVO.getThreeStoreClassId())
                .build());
        return ok(count);
    }


    /**
     * 六大架构修改提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editSub")
    public ApiResult editSub(HttpServletRequest request, @Validated SixStructures params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        SixStructures sixStructures = sixStructuresService.selectSixStructuresById(params.getSixStructuresId());
        if (sixStructures == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(sixStructures.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        params.setDeptId(account.getDeptId());
        params.setPostIds(account.getPostIds());
        params.setCompanyId(account.getCompanyId());
        int count = sixStructuresService.updateSixStructures(params);
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        return ApiResult.okMsg("提交成功！");
    }


    /**
     * 六大架构删除
     *
     * @param request
     * @param sixStructuresId 六大架构ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request, @RequestParam(defaultValue = "0") String sixStructuresId, Integer fileTag) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        SixStructures sixStructures = sixStructuresService.selectSixStructuresById(sixStructuresId);
        if (sixStructures == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(sixStructures.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        int count = sixStructuresService.deleteSixStructuresById(sixStructuresId);

        if (count <= 0) {
            return ApiResult.fail("删除失败！");
        }
        sixStructures.setFileTag(fileTag);
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.reportSixStructuresDeleteScore(sixStructures, account));

        // 删除已读的记录
        sixStructuresReadRecordService.deleteSixStructuresReadRecord(SixStructuresReadRecord.builder().sixStructuresId(sixStructuresId).build());

        return ApiResult.ok(reportTrainingDeleteScore(sixStructures, account));
    }

    /**
     * 六大架构详情
     *
     * @param request
     * @param sixStructuresId 六大架构ID
     * @return
     */
    @GetMapping("/detail")
    public ApiResult detail(HttpServletRequest request, @RequestParam(defaultValue = "0") String sixStructuresId) {
        SixStructures sixStructures = sixStructuresService.selectSixStructuresById(sixStructuresId);
        if (sixStructures == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = new HashMap<>(8);
        // 详情
        map.put("sixStructures", sixStructures);
        // 详情删除标识 0:否 1:可以删除
        map.put("delTag", accountId.equals(sixStructures.getAccountId()) ? 1 : 0);
        // 详情点赞标识 0:否 1:已点赞
        AccountLike accountLike = likeService.selectAccountLike(AccountLike.builder().targetId(sixStructuresId).accountId(accountId).likeType(1).build());
        map.put("likeTag", accountLike == null ? 0 : 1);
        // 点赞列表
        List<AccountLike> likeList = likeService.selectAccountLikeList(AccountLike.builder().targetId(sixStructuresId).likeType(1).build());
        map.put("likeList", likeList);
        // 异步操作
        AsyncManager.me().execute(tasksixStructuresService.sixStructuresRecord(sixStructures, accountId));
        return ok(map);
    }

    /**
     * 六大架构相关数量
     *
     * @param request
     * @param date              日筛选 格式为2023-05-01
     * @param month             月筛选 格式为2023-05
     * @param threeStoreClassId 三级分类id
     * @return
     */
    @GetMapping("/count")
    public ApiResult count(HttpServletRequest request, String date, String month, @RequestParam(defaultValue = "1") String threeStoreClassId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        Map<String, Object> map = new HashMap<>(5);
        // 查询本公司人数
        int companyCount = accountService.selectApiAccountCount(ApiAccount.builder().companyId(account.getCompanyId()).build());
        map.put("companyCount", companyCount);
        // 查询本月本公司已提交的人数
        int dayCount = sixStructuresService.selectApiSixStructuresCount(SixStructures.builder()
                .threeStoreClassId(threeStoreClassId)
                .companyId(account.getCompanyId())
                .date(date)
                .month(month)
                .build());
        map.put("dayCount", dayCount);
        // 查询本公司下的各个部门
        SysDept dept = new SysDept();
        dept.setParentId(account.getCompanyId());
        List<SysDept> deptList = deptService.selectApiDeptList(dept);
        deptList.forEach(e -> {
            e.setDeptPeopleCount(accountService.selectApiAccountCount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(e.getDeptId()).build()));
            e.setSubCount(sixStructuresService.selectApiSixStructuresCount(SixStructures.builder()
                    .threeStoreClassId(threeStoreClassId)
                    .companyId(account.getCompanyId())
                    .date(date)
                    .month(month)
                    .deptId(e.getDeptId())
                    .build()));
        });
        map.put("deptList", deptList);
        return ok(map);
    }

    /**
     * 六大架构查询赠送积分
     *
     * @param params  六大架构
     * @param account 用户
     * @return
     */
    public Integer reportTrainingScore(SixStructures params, ApiAccount account) {
        Integer score = 0;
        score = reportTrainingScoreRecord(params, account, 34, 35);
        return score;
    }

    /**
     * 六大架构查询删除积分
     *
     * @param account 用户
     * @return
     */
    public Integer reportTrainingDeleteScore(SixStructures params, ApiAccount account) {
        Integer score = 0;
        score = reportTrainingDeleteScoreRecord(params, account, 34, 35);
        return score;
    }


    /**
     * 六大架构删除积分记录
     *
     * @param training
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public Integer reportTrainingDeleteScoreRecord(SixStructures training, ApiAccount account, Integer subType, Integer fileType) {
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
        return score;
    }


    /**
     * 六大架构积分记录
     *
     * @param params
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public Integer reportTrainingScoreRecord(SixStructures params, ApiAccount account, Integer subType, Integer fileType) {
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
        return score;
    }
}
