package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.service.*;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.params.courseExam.*;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysDeptService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wwb
 * 问卷发放记录
 */
@RestController
@RequestMapping(value = "/api/questionnaireAccount")
@AllArgsConstructor
public class QuestionnaireAccountApiController extends ApiController {

    private final IQuestionnaireAccountService questionnaireAccountService;

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IQuestionnaireRecordService questionnaireRecordService;

    private final IQuestionnaireRecordAnswerService questionnaireRecordAnswerService;

    private final IQuestionnaireAccountAnswerService questionnaireAccountAnswerService;

    private final IQuestionnaireAccountAnswerResultService questionnaireAccountAnswerResultService;

    private final IQuestionnaireDistributeRecordService questionnaireDistributeRecordService;

    private final ISysDeptService deptService;

    /**
     * pc@Description
     * params 添加问卷发放记录
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add")
    @PassToken
    public ApiResult add(HttpServletRequest request, @RequestBody QuestionnaireAccountRecordParam params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        List<QuestionnaireAccountParam> list = params.getList();
        if (list == null || list.size() <= 0) {
            return fail("请添加问卷数据");
        }

        String uuid = IdUtil.getSnowflake(1, 1).nextIdStr();
        QuestionnaireDistributeRecord questionnaireDistributeRecord = QuestionnaireDistributeRecord.builder().questionnaireDistributeRecordId(uuid).build();
        BeanUtil.copyProperties(list.get(0), questionnaireDistributeRecord);
        int num = questionnaireDistributeRecordService.insertQuestionnaireDistributeRecord(questionnaireDistributeRecord);
        if (num <= 0) {
            throw new ApiException("发放问卷调查失败。");
        }

        list.forEach(
                obj -> {
                    QuestionnaireAccount questionnaireAccount = QuestionnaireAccount.builder().build();
                    BeanUtil.copyProperties(obj, questionnaireAccount);
                    questionnaireAccount.setQuestionnaireDistributeRecordId(uuid);
                    int count = questionnaireAccountService.insertQuestionnaireAccount(questionnaireAccount);
                    if (count <= 0) {
                        throw new ApiException("发放问卷调查失败。");
                    }
                }
        );
        return ok("发放问卷调查成功。");
    }

    /**
     * 问卷发放记录列表
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/list")
    public ApiResult list(PageParam pageParam, HttpServletRequest request) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<QuestionnaireAccount> list = questionnaireAccountService.selectQuestionnaireAccountList(QuestionnaireAccount
                .builder()
                .delStatus(0)
                .accountId(account.getAccountId())
                .companyId(account.getCompanyId())
                .build());
        PageInfo<QuestionnaireAccount> pageInfo = new PageInfo<>(list);
        return ok(pageInfo);
    }

    /**
     * 问卷发放记录对应的答题数据列表
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/questionnaireRecordList")
    public ApiResult questionnaireRecordList(HttpServletRequest request, String questionnaireAccountId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if (StringUtils.isEmpty(questionnaireAccountId)) {
            return ApiResult.fail("请选择问卷");
        }
        QuestionnaireAccount questionnaireAccount = questionnaireAccountService.selectQuestionnaireAccountById(questionnaireAccountId);
        if (questionnaireAccount == null) {
            return ApiResult.fail("没有此问卷记录");
        }
        //未答卷
        if (questionnaireAccount.getAnswerStatus() == 0) {
            List<QuestionnaireRecord> list = questionnaireRecordService.selectQuestionnaireRecordList(QuestionnaireRecord
                    .builder()
                    .questionnaireId(questionnaireAccount.getQuestionnaireId())
                    .delStatus(0)
                    .build());
            list.forEach(
                    obj -> {
                        List<QuestionnaireRecordAnswer> questionnaireRecordAnswerList = questionnaireRecordAnswerService.selectQuestionnaireRecordAnswerList(QuestionnaireRecordAnswer
                                .builder()
                                .questionnaireRecordId(obj.getQuestionnaireRecordId())
                                .delStatus(0)
                                .build());
                        obj.setQuestionnaireRecordAnswerList(questionnaireRecordAnswerList);
                    }
            );
            return ApiResult.ok(list);
        }
        if (questionnaireAccount.getAnswerStatus() == 1) {
            //已答卷，查询答卷记录表
            List<QuestionnaireAccountAnswer> list = questionnaireAccountAnswerService.selectQuestionnaireAccountAnswerList(QuestionnaireAccountAnswer
                    .builder()
                    .accountId(account.getAccountId())
                    .questionnaireAccountId(questionnaireAccountId)
                    .build());
            list.forEach(
                    obj -> {
                        List<QuestionnaireAccountAnswerResult> questionnaireAccountAnswerResultList = questionnaireAccountAnswerResultService
                                .selectQuestionnaireAccountAnswerResultList(QuestionnaireAccountAnswerResult
                                        .builder()
                                        .questionnaireAccountAnswerId(obj.getQuestionnaireAccountAnswerId())
                                        .delStatus(0)
                                        .build());
                        obj.setQuestionnaireAccountAnswerResultList(questionnaireAccountAnswerResultList);
                    }
            );
            return ApiResult.ok(list);
        }
        return ApiResult.ok(null);
    }

    /**
     * pc@Description
     * params 问卷发放记录对应的答题答案数据列表
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addResult")
    @PassToken
    public ApiResult addResult(HttpServletRequest request, @RequestBody QuestionnaireParam params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        List<QuestionnaireAccountAnswerParam> list = params.getList();
        if (list == null || list.size() <= 0) {
            return fail("请添加问卷答题记录数据");
        }
        QuestionnaireAccount questionnaireAccount = questionnaireAccountService.selectQuestionnaireAccountById(params.getQuestionnaireAccountId());
        if (questionnaireAccount == null) {
            throw new ApiException("没有此问卷记录。");
        }
        list.forEach(
                obj -> {
                    QuestionnaireAccountAnswer questionnaireAccountAnswer = QuestionnaireAccountAnswer.builder().build();
                    BeanUtil.copyProperties(obj, questionnaireAccountAnswer);
                    questionnaireAccountAnswer.setQuestionnaireAccountAnswerId(IdUtil.getSnowflake(1, 1).nextIdStr());

                    List<QuestionnaireAccountAnswerResultParam> questionnaireAccountAnswerResultList = obj.getQuestionnaireAccountAnswerResultList();
                    questionnaireAccountAnswerResultList.forEach(
                            objOne -> {
                                QuestionnaireAccountAnswerResult questionnaireAccountAnswerResult = QuestionnaireAccountAnswerResult.builder().build();
                                BeanUtil.copyProperties(objOne, questionnaireAccountAnswerResult);
                                questionnaireAccountAnswerResult.setQuestionnaireAccountAnswerId(questionnaireAccountAnswer.getQuestionnaireAccountAnswerId());
                                if (objOne.getSelectStatus() == 1) {
                                    objOne.setMyScore(objOne.getScore()==null?0.0:objOne.getScore());
                                    questionnaireAccountAnswer.setMyScore(questionnaireAccountAnswer.getMyScore()==null?0.0:questionnaireAccountAnswer.getMyScore() + objOne.getScore());
                                }
                                questionnaireAccountAnswerResult.setAccountName(account.getNickName());
                                questionnaireAccountAnswerResult.setAccountPhone(account.getPhone());
                                questionnaireAccountAnswerResult.setAccountHead(account.getHeadPortrait());
                                questionnaireAccountAnswerResult.setDeptId(account.getDeptId());
                                int num = questionnaireAccountAnswerResultService.insertQuestionnaireAccountAnswerResult(questionnaireAccountAnswerResult);
                                if (num <= 0) {
                                    throw new ApiException("添加问卷答案失败。");
                                }
                            }
                    );
                    questionnaireAccount.setScore((questionnaireAccount.getScore()==null?0.0:questionnaireAccount.getScore())
                            +(questionnaireAccountAnswer.getMyScore()==null?0.0:questionnaireAccountAnswer.getMyScore()));
                    questionnaireAccountAnswer.setAnswerStatus(1);
                    int num = questionnaireAccountAnswerService.insertQuestionnaireAccountAnswer(questionnaireAccountAnswer);
                    if (num <= 0) {
                        throw new ApiException("添加问卷失败。");
                    }
                }
        );
        int num = questionnaireAccountService.updateQuestionnaireAccount(QuestionnaireAccount
                .builder()
                .questionnaireAccountId(questionnaireAccount.getQuestionnaireAccountId())
                .score(questionnaireAccount.getScore()==null?0.0:questionnaireAccount.getScore())
                .answerStatus(1)
                .submitTime(new Date())
                .build());
        if (num <= 0) {
            throw new ApiException("更新问卷得分失败。");
        }
        return ok("添加考试成功。");
    }


    /**
     * 问卷答题情况统计
     *
     * @return
     */
    //@PassToken
    @GetMapping(value = "/questionnaireTj")
    public ApiResult questionnaireTj(PageParam pageParam,HttpServletRequest request) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }

        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }

        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<QuestionnaireDistributeRecord> list = questionnaireDistributeRecordService.selectQuestionnaireDistributeRecordList(QuestionnaireDistributeRecord
                .builder()
                .companyId(account.getCompanyId())
                .build());
        PageInfo<QuestionnaireDistributeRecord> pageInfo = new PageInfo<>(list);

        List<QuestionnaireAccount> questionnaireAccountList = questionnaireAccountService.selectQuestionnaireAccountList(QuestionnaireAccount
                .builder()
                .companyId(account.getCompanyId())
                .build());
        List<QuestionnaireAccount> wdjList = questionnaireAccountList.stream().filter(objOne -> (objOne.getAnswerStatus() == 0))
                .collect(Collectors.toList());
        List<QuestionnaireAccount> ydjList = questionnaireAccountList.stream().filter(objOne -> (objOne.getAnswerStatus() == 1))
                .collect(Collectors.toList());

        list.forEach(
                obj -> {
                    Map<String, Object> map = new HashMap<>(2);
                    if(questionnaireAccountList != null && questionnaireAccountList.size() > 0){
                        map.put("wdjCount",wdjList.stream().filter(objOne -> (objOne.getQuestionnaireDistributeRecordId()
                                .equals(obj.getQuestionnaireDistributeRecordId())))
                                .collect(Collectors.toList()).size());
                        map.put("ydjCount",ydjList.stream().filter(objOne -> (objOne.getQuestionnaireDistributeRecordId()
                                .equals(obj.getQuestionnaireDistributeRecordId())))
                                .collect(Collectors.toList()).size());
                        map.put("allCount",questionnaireAccountList.size());
                    }else{
                        map.put("wdjCount",0);
                        map.put("ydjCount",0);
                        map.put("allCount",0);
                    }
                    obj.setMap(map);
                }
        );
        return ok(pageInfo);
    }

    /***
     * 查询部门及部门下人数统计
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDeptQuestionnaireAccount")
    public ApiResult getDeptQuestionnaireAccount(HttpServletRequest request,String questionnaireDistributeRecordId) {
        String accountId = tokenService.getAccountId(request);
        ApiAccount apiAccount = accountService.selectApiAccountById(accountId);
        if (apiAccount == null) {
            return fail(ApiCode.DONT_LOGIN.getMsg());
        }
        if(apiAccount.getCompanyId() == null){
            return fail("此用户未安排部门。");
        }

        List<HashMap<String,Object>> deptList = deptService.selectDeptMap(apiAccount.getCompanyId());
        List<QuestionnaireAccount> questionnaireAccountList = questionnaireAccountService.selectQuestionnaireAccountList(QuestionnaireAccount
                .builder()
                .companyId(apiAccount.getCompanyId())
                .questionnaireDistributeRecordId(questionnaireDistributeRecordId)
                .build());

        Map<Long, List<QuestionnaireAccount>> questionnaireAccountByDeptIdList = null ;
        if(questionnaireAccountList != null && questionnaireAccountList.size() > 0){
            questionnaireAccountByDeptIdList = questionnaireAccountList.stream().collect(Collectors.groupingBy(QuestionnaireAccount::getDeptId));
        }

        if(deptList != null && deptList.size() > 0){
            for (int i = 0; i < deptList.size(); i++) {
                HashMap<String, Object> obj = deptList.get(i);

                Map<String, Object> map = new HashMap<>(2);
                if(questionnaireAccountByDeptIdList != null && questionnaireAccountByDeptIdList.size() > 0){
                    List<QuestionnaireAccount> list = questionnaireAccountByDeptIdList.get(obj.get("deptId"));
                    if(list == null || list.size() == 0){
                        map.put("wdjCount",0);
                        map.put("allCount",0);
                    }else{
                        map.put("wdjCount",list.stream()
                                .filter(objOne -> (objOne.getAnswerStatus() == 0))
                                .collect(Collectors.toList()).size());
                        map.put("allCount",questionnaireAccountByDeptIdList.size());
                        map.put("list",list);
                    }
                }else{
                    map.put("wdjCount",0);
                    map.put("allCount",0);
                }
                obj.put("map",map);
            }
        }
        return ok(deptList);
    }

}
