package com.wlwq.controller.api;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.flower.AccountFlowerResultVO;
import com.wlwq.api.resultVO.score.AccountScoreResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author gaoce
 * 排行榜
 */
@RestController
@RequestMapping(value = "/api/rank")
@AllArgsConstructor
public class RankApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountScoreService accountScoreService;

    private final IAccountMedalService accountMedalService;

    private final IAccountMedalRecordService medalRecordService;

    private final IAccountFlowerRecordService flowerRecordService;

    /**
     * 积分
     *
     * @param request
     * @param pageParam 分页
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @PassToken
    @GetMapping("/scoreList")
    public ApiResult scoreList(HttpServletRequest request,
                               PageParam pageParam,
                               String startTime,
                               String endTime) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getNoAccountId(request));
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountScoreResultVO> scoreList = accountService.selectApiScoreAccountList(ApiAccount.builder()
                .startTime(startTime)
                .endTime(endTime)
                .companyId(account == null ? -1L : account.getCompanyId())
                .appellationType(1)
                .build());
        PageInfo<AccountScoreResultVO> pageInfo = new PageInfo<>(scoreList);
        return ok(pageInfo);
    }

    /**
     * 积分排行榜点击到详情 查看用户信息
     *
     * @param accountId 用户ID
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @GetMapping("/scoreDetail")
    private ApiResult scoreDetail(String startTime,
                                  String endTime,
                                  @RequestParam(defaultValue = "0") String accountId) {
        Map<String, Object> map = new HashMap<>(3);
        // 查询用户信息
        AccountScoreResultVO account = accountService.selectApiScoreAccount(ApiAccount.builder()
                .startTime(startTime)
                .endTime(endTime)
                .accountId(accountId)
                .appellationType(1)
                .build());
        map.put("account", account);
        return ok(map);
    }

    /**
     * 积分排行榜点击到详情 查看积分获得列表
     *
     * @param pageParam 分页
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @PassToken
    @GetMapping("/scoreDetailList")
    public ApiResult scoreDetailList(@RequestParam(defaultValue = "0") String accountId,
                                     PageParam pageParam,
                                     String startTime,
                                     String endTime) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountScore> scoreList = accountScoreService.selectApiAccountScoreList(AccountScore.builder()
                .startTime(startTime)
                .endTime(endTime)
                .accountId(accountId)
                .scoreStatus(1)
                .build());
        PageInfo<AccountScore> pageInfo = new PageInfo<>(scoreList);
        return ok(pageInfo);
    }


    /**
     * 勋章排行榜点击到详情
     * @param accountId 用户ID
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @GetMapping("/medalDetail")
    private ApiResult medalDetail(@RequestParam(defaultValue = "0") String accountId,
                                  String startTime,
                                  String endTime) {
        // 查询用户信息
        AccountScoreResultVO account = accountService.selectApiScoreAccount(ApiAccount.builder()
                .accountId(accountId)
                .appellationType(2)
                .build());

        // 查询已领取的一级勋章数量
        int dayCount = medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder()
                .accountId(accountId)
                .startTime(StringUtils.isBlank(startTime) ? DateUtil.today() : startTime)
                .endTime(StringUtils.isBlank(endTime) ? DateUtil.today() : endTime)
                .tag(0)
                .build());

        // 查询已领取的一级勋章数量
        int count = medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder()
                .accountId(accountId)
                .tag(0)
                .build());

        // 查询已获得的一级勋章列表
        List<AccountMedalRecord> alreadyList = medalRecordService.selectApiAccountMedalRecordList(AccountMedalRecord.builder()
                .accountId(accountId)
                .startTime(startTime)
                .endTime(endTime)
                .medalParentId(0L)
                .build());
        alreadyList.forEach(e-> {
            // 查询一级下面有哪些二级分类
            e.setChildList(childList(e.getAccountMedalId(),accountId));
        });

        Map<String, Object> map = new HashMap<>(5);
        map.put("medalCount", count);
        map.put("dayMedalCount", dayCount);
        map.put("alreadyList",alreadyList);
        map.put("account", account);
        return ok(map);
    }

    /**
     * 个人中心 我的勋章展示
     *
     * @return
     */
    @GetMapping("/myMedalList")
    private ApiResult myMedalList(HttpServletRequest request,
                                  String startTime,
                                  String endTime) {
        String accountId = tokenService.getNoAccountId(request);
        // 查询用户信息
        AccountScoreResultVO account = accountService.selectApiScoreAccount(ApiAccount.builder()
                .accountId(accountId)
                .appellationType(2)
                .build());

        // 查询已领取的一级勋章数量
        int count = medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder()
                .accountId(accountId)
                .startTime(startTime)
                .endTime(endTime)
                .tag(0)
                .build());

        // 查询已获得的一级勋章列表
        List<AccountMedalRecord> alreadyList = medalRecordService.selectApiAccountMedalRecordList(AccountMedalRecord.builder()
                .accountId(accountId)
                .startTime(startTime)
                .endTime(endTime)
                .medalParentId(0L)
                .build());
//        alreadyList.forEach(e-> {
//            // 查询一级下面有哪些二级分类
//            e.setChildList(childList(e.getAccountMedalId(),accountId));
//        });

        // 查询未获得的一级勋章列表
        List<AccountMedal> noAlReadyList = accountMedalService.selectApiAccountMedalList(AccountMedal.builder().accountId(accountId).parentId(0L).showStatus(1).build());
//        noAlReadyList.forEach(e-> {
//            // 查询一级下面有哪些二级分类
//            e.setChildList(childList(e.getAccountMedalId(),accountId));
//        });
        Map<String, Object> map = new HashMap<>(5);
        map.put("medalCount", count);
        map.put("alreadyList",alreadyList);
        map.put("noAlReadyList",noAlReadyList);
        map.put("account", account);
        return ok(map);
    }

    /**
     * 查询一级下面有哪些二级分类的勋章是够已领取
     * @param accountMedalId 一级勋章分类ID
     * @return
     */
    @GetMapping("/childList")
    public ApiResult childList(@RequestParam(defaultValue = "0") Long accountMedalId,HttpServletRequest request){
        return ok(childList(accountMedalId,tokenService.getNoAccountId(request)));
    }

    /**
     * 查询一级下面有哪些二级分类的勋章是够已领取
     * @param accountMedalId 一级勋章分类ID
     * @param accountId 用户ID
     * @return
     */
    private List<AccountMedal> childList(Long accountMedalId,String accountId){
        // 查询一级下面有哪些二级分类
        List<AccountMedal> childList = accountMedalService.selectApiAccountMedalList(AccountMedal.builder().showStatus(1).parentId(accountMedalId).build());
        childList.forEach(c->{
            // 查询勋章是否领取
            int alreadyCount = medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder().accountId(accountId).accountMedalId(c.getAccountMedalId()).build());
            c.setAlreadyTag(alreadyCount > 0 ? 1 : 0);
            // 二级勋章数量
            c.setAlreadyCount(alreadyCount);
        });
        return childList;
    }


    /**
     * 勋章排行榜列表
     *
     * @param request
     * @param pageParam 分页
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @PassToken
    @GetMapping("/medalList")
    public ApiResult medalList(HttpServletRequest request,
                               PageParam pageParam,
                               String startTime,
                               String endTime) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getNoAccountId(request));
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountScoreResultVO> scoreList = medalRecordService.selectApiMedalAccountList(ApiAccount.builder()
                .startTime(startTime)
                .endTime(endTime)
                .companyId(account == null ? -1L : account.getCompanyId())
                .appellationType(2)
                .build());
        PageInfo<AccountScoreResultVO> pageInfo = new PageInfo<>(scoreList);
        return ok(pageInfo);
    }

    /**
     * 红花排行榜列表
     *
     * @param request
     * @param pageParam 分页
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @PassToken
    @GetMapping("/flowerList")
    public ApiResult flowerList(HttpServletRequest request,
                               PageParam pageParam,
                               String startTime,
                               String endTime) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getNoAccountId(request));
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountFlowerResultVO> flowerList = flowerRecordService.selectApiFlowerAccountList(ApiAccount.builder()
                .startTime(startTime)
                .endTime(endTime)
                .companyId(account == null ? -1L : account.getCompanyId())
                .build());
        PageInfo<AccountFlowerResultVO> pageInfo = new PageInfo<>(flowerList);
        return ok(pageInfo);
    }

    /**
     * 排行榜点击到详情 查看用户信息
     *
     * @param accountId 用户ID
     * @param startTime 时间筛选，格式为2023-05-01
     * @param endTime   时间筛选，格式为2023-05-02
     * @return
     */
    @PassToken
    @GetMapping("/flowerDetail")
    private ApiResult flowerDetail(String startTime,
                                  String endTime,
                                  @RequestParam(defaultValue = "0") String accountId) {
        Map<String, Object> map = new HashMap<>(3);
        // 查询用户信息
        List<AccountFlowerResultVO> flowerList = flowerRecordService.selectApiFlowerAccountList(ApiAccount.builder()
                .startTime(startTime)
                .endTime(endTime)
                .accountId(accountId)
                .build());
        map.put("account", flowerList);
        return ok(map);
    }

    /**
     * 排行榜红花收入消耗列表
     *
     * @param pageParam 分页
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @returnapi/accountFlowerRecord/flowerIncomeList
     */
    @PassToken
    @GetMapping("/flowerIncomeList")
    public ApiResult flowerIncomeList(PageParam pageParam,String startTime,String endTime,@RequestParam(defaultValue = "0") String accountId ) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<AccountFlowerRecord> recordList = flowerRecordService.selectAccountFlowerRecordTotalList(AccountFlowerRecord.builder()
                .accountId(accountId)
                .startTime(startTime)
                .endTime(endTime)
                .build());
        PageInfo<AccountFlowerRecord> pageInfo = new PageInfo<>(recordList);
        return ok(pageInfo);
    }
}
