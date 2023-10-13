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
import com.wlwq.params.FourRelationships.FourRelationshipsParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.taskService.TaskFourRelationshipsService;
import com.wlwq.taskService.TaskScoreService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 四类关系Controller
 *
 * @author dxy
 */
@RestController
@RequestMapping("/api/fourRelationships")
@AllArgsConstructor
public class FourRelationshipsApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IFourRelationshipsClassService fourRelationshipsClassService;

    private final IFourRelationshipsService fourRelationshipsService;

    private final IFourRelationshipsReadRecordService fourRelationshipsReadRecordService;

    private final TaskScoreService scoreService;

    private final IAccountEvaluateService evaluateService;

    private final IAccountLikeService likeService;

    private final TaskFourRelationshipsService taskFourRelationshipsService;

    private final ISysDeptService deptService;


    /**
     * 四类关系提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/sub")
    public ApiResult sub(HttpServletRequest request, @Validated FourRelationships params, BindingResult bindingResult) {
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
        int count = fourRelationshipsService.insertFourRelationships(params);
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.fourRelationshipsScore(params, account));
        return ApiResult.ok(reportTrainingScore(params, account));
    }

    /**
     * 四类关系列表
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ApiResult list(HttpServletRequest request,
                          PageParam pageParam,
                          FourRelationshipsParamVO paramVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());

        List<FourRelationships> fourRelationshipsList = fourRelationshipsService.selectApiFourRelationshipsList(FourRelationships.builder()
                .accountId(account.getAccountId())
                .deptId(paramVO.getDeptId())
                .companyId(account.getCompanyId())
                .threeStoreClassId(paramVO.getThreeStoreClassId())
                .date(paramVO.getDate())
                .month(paramVO.getMonth())
                .accountName(paramVO.getKeyword())
                .build());
        fourRelationshipsList.forEach(e -> {
            // 查询评论列表
            PageHelper.startPage(1, 2);
            List<AccountEvaluate> evaluateList = evaluateService.selectApiAccountEvaluateList(AccountEvaluate.builder()
                    .targetId(e.getFourRelationshipsId())
                    .threeStoreClassId(paramVO.getThreeStoreClassId())
                    .parentId("0")
                    .build());
            e.setEvaluateList(new PageInfo<>(evaluateList));
            // 详情删除标识 0:否 1:可以删除
            e.setDelTag(account.getAccountId().equals(e.getAccountId()) ? 1 : 0);
            // 查询点赞的数量
            int likeCount = likeService.selectAccountLikeCount(AccountLike.builder().likeType(2).targetId(e.getFourRelationshipsId()).build());
            e.setLikeCount(likeCount);
            // 查询是否已读未读
            FourRelationshipsReadRecord record = fourRelationshipsReadRecordService.selectFourRelationshipsReadRecord(FourRelationshipsReadRecord.builder()
                    .accountId(account.getAccountId())
                    .fourRelationshipsId(e.getFourRelationshipsId())
                    .build());
            e.setReadTag(record == null ? 0 : 1);
        });
        PageInfo<FourRelationships> pageInfo = new PageInfo<>(fourRelationshipsList);
        return ok(pageInfo);
    }

    /**
     * 四类关系 我的相关数量
     *
     * @param request
     * @return
     */
    @GetMapping("/myCount")
    public ApiResult myCount(HttpServletRequest request,
                             FourRelationshipsParamVO paramVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        int count = fourRelationshipsService.selectFourRelationshipsCount(FourRelationships.builder()
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
     * 四类关系修改提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editSub")
    public ApiResult editSub(HttpServletRequest request, @Validated FourRelationships params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        FourRelationships fourRelationships = fourRelationshipsService.selectFourRelationshipsById(params.getFourRelationshipsId());
        if (fourRelationships == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(fourRelationships.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        params.setAccountName(account.getNickName());
        params.setAccountPhone(account.getPhone());
        params.setAccountHead(account.getHeadPortrait());
        params.setDeptId(account.getDeptId());
        params.setPostIds(account.getPostIds());
        params.setCompanyId(account.getCompanyId());
        int count = fourRelationshipsService.updateFourRelationships(params);
        if (count <= 0) {
            return ApiResult.fail("提交失败！");
        }
        return ApiResult.okMsg("提交成功！");
    }


    /**
     * 四类关系删除
     *
     * @param request
     * @param fourRelationshipsId 四类关系ID
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request, @RequestParam(defaultValue = "0") String fourRelationshipsId, Integer fileTag) {

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        FourRelationships fourRelationships = fourRelationshipsService.selectFourRelationshipsById(fourRelationshipsId);
        if (fourRelationships == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        if (!account.getAccountId().equals(fourRelationships.getAccountId())) {
            return ApiResult.fail("自己只能修改自己发布的信息！");
        }
        int count = fourRelationshipsService.deleteFourRelationshipsById(fourRelationshipsId);

        if (count <= 0) {
            return ApiResult.fail("删除失败！");
        }
        fourRelationships.setFileTag(fileTag);
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.reportFourRelationshipsDeleteScore(fourRelationships, account));

        // 删除已读的记录
        fourRelationshipsReadRecordService.deleteFourRelationshipsReadRecord(FourRelationshipsReadRecord.builder().fourRelationshipsId(fourRelationshipsId).build());

        return ApiResult.ok(reportTrainingDeleteScore(fourRelationships, account));
    }

    /**
     * 四类关系详情
     *
     * @param request
     * @param fourRelationshipsId 四类关系ID
     * @return
     */
    @GetMapping("/detail")
    public ApiResult detail(HttpServletRequest request, @RequestParam(defaultValue = "0") String fourRelationshipsId) {
        FourRelationships fourRelationships = fourRelationshipsService.selectFourRelationshipsById(fourRelationshipsId);
        if (fourRelationships == null) {
            return ApiResult.fail("暂未查询到相关信息！");
        }
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = new HashMap<>(8);
        // 详情
        map.put("fourRelationships", fourRelationships);
        // 详情删除标识 0:否 1:可以删除
        map.put("delTag", accountId.equals(fourRelationships.getAccountId()) ? 1 : 0);
        // 详情点赞标识 0:否 1:已点赞
        AccountLike accountLike = likeService.selectAccountLike(AccountLike.builder().targetId(fourRelationshipsId).accountId(accountId).likeType(1).build());
        map.put("likeTag", accountLike == null ? 0 : 1);
        // 点赞列表
        List<AccountLike> likeList = likeService.selectAccountLikeList(AccountLike.builder().targetId(fourRelationshipsId).likeType(1).build());
        map.put("likeList", likeList);
        // 异步操作
        AsyncManager.me().execute(taskFourRelationshipsService.fourRelationshipsRecord(fourRelationships, accountId));
        return ok(map);
    }

    /**
     * 四类关系相关数量
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
        int dayCount = fourRelationshipsService.selectApiFourRelationshipsCount(FourRelationships.builder()
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
            e.setSubCount(fourRelationshipsService.selectApiFourRelationshipsCount(FourRelationships.builder()
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
     * 四类关系查询赠送积分
     *
     * @param params  四类关系
     * @param account 用户
     * @return
     */
    public Integer reportTrainingScore(FourRelationships params, ApiAccount account) {
        Integer score = 0;
        score = reportTrainingScoreRecord(params, account, 34, 35);
        return score;
    }

    /**
     * 四类关系查询删除积分
     *
     * @param account 用户
     * @return
     */
    public Integer reportTrainingDeleteScore(FourRelationships params, ApiAccount account) {
        Integer score = 0;
        score = reportTrainingDeleteScoreRecord(params, account, 34, 35);
        return score;
    }


    /**
     * 四类关系删除积分记录
     *
     * @param training
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public Integer reportTrainingDeleteScoreRecord(FourRelationships training, ApiAccount account, Integer subType, Integer fileType) {
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
     * 四类关系积分记录
     *
     * @param params
     * @param account
     * @param subType  提交类型 字典表中sys_set_score查看
     * @param fileType 提交文件类型 字典表中sys_set_score查看
     */
    public Integer reportTrainingScoreRecord(FourRelationships params, ApiAccount account, Integer subType, Integer fileType) {
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
