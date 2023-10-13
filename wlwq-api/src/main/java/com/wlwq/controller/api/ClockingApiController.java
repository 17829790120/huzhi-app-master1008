package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.clocking.AbsenteeismResultVO;
import com.wlwq.api.resultVO.clocking.ClockingDayResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.WeekVO;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.clocking.ClockingParamVO;
import com.wlwq.params.clocking.ClockingSearchParamVO;
import com.wlwq.params.clocking.ReissueClockingParamVO;
import com.wlwq.service.TokenService;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.taskService.TaskScoreService;
import com.wlwq.taskService.TaskExamineFlowAccountService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * @author gaoce
 */
@RestController
@RequestMapping(value = "/api/clocking")
@AllArgsConstructor
public class ClockingApiController extends ApiController {

    private final TokenService tokenService;

    private final IApiAccountService accountService;

    private final IAccountClockingService clockingService;

    private final ISysDeptService deptService;

    private final IExamineModuleService examineModuleService;

    private final IExamineFlowService flowService;

    private final IExamineInitiateService initiateService;

    private final TaskExamineFlowAccountService taskExamineFlowAccountService;

    private final IExamineFlowAccountService flowAccountService;

    private final TaskScoreService scoreService;

    /**
     * 常用图标
     *
     * @return
     */
    @GetMapping(value = "/oneList")
    @PassToken
    public ApiResult oneList() {
        List<ExamineModule> classList = examineModuleService.selectExamineModuleList(ExamineModule.builder().parentId(-1L).showStatus(1).build());
        return ok(classList);
    }

    /**
     * 查询员工的打卡时间及打卡范围
     * lon 经度
     * lat 纬度
     *
     * @return
     */
    @GetMapping("/selectClock")
    public ApiResult selectClock(HttpServletRequest request,
                                 @RequestParam(defaultValue = "0") String lon,
                                 @RequestParam(defaultValue = "0") String lat) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询考勤范围及上班时间
        SysDept dept = new SysDept();
        dept.setDeptId(account.getCompanyId());
        dept.setLon(lon);
        dept.setLat(lat);
        Map<String, Object> clock = deptService.selectClock(dept);
        // 查询考勤信息
        // 查询今天打过上班卡
        AccountClocking clocking = clockingService.selectAccountClocking(AccountClocking.builder()
                .accountId(account.getAccountId())
                .clockingDate(new Date())
                .build());
        clock.put("clocking", clocking);
        return ApiResult.ok(clock);
    }

    /**
     * 上班打卡(正常打卡/外勤打卡)
     * address 打卡地址
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/clickOnWorkClock")
    public ApiResult clickOnWorkClock(HttpServletRequest request, @Validated ClockingParamVO params, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        // 查询考勤范围及上班时间
        SysDept dept = new SysDept();
        dept.setDeptId(account.getCompanyId());
        dept.setLon(params.getLon());
        dept.setLat(params.getLat());
        Map<String, Object> clock = deptService.selectClock(dept);
        if (clock == null) {
            return ApiResult.fail("暂未查询到考勤打卡信息！");
        }
        if (clock.get("onWork") == null) {
            return ApiResult.fail("没有查询到上班的考勤时间，暂时不能打卡！");
        }
//        // clockingStatus 0:否 1：可以打卡
//        if ("0".equals(clock.get("clockingStatus"))) {
//            return ApiResult.fail("不在打卡范围内，暂时不能打卡");
//        }
        // 查询今天打过上班卡
        AccountClocking clocking = clockingService.selectAccountClocking(AccountClocking.builder().accountId(account.getAccountId()).clockingDate(new Date()).build());
        if (clocking != null) {
            return ApiResult.fail("您已经打过上班卡了，请勿重复打卡！");
        }
        AccountClocking accountClocking = AccountClocking.builder()
                .accountId(account.getAccountId())
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .clockingDate(new Date())
                .clockingAddress(params.getAddress())
                .clockingType(params.getStatus())
                .clockingStatus(DateUtils.time(DateUtil.format(new Date(), "yyyy-MM-dd") + " " + clock.get("onWork") + ":00") >= System.currentTimeMillis() ? 1 : 2)
                .onWorkTime(new Date())
                .deptId(account.getDeptId())
                .postIds(account.getPostIds())
                .companyId(account.getCompanyId())
                .pics(params.getPics())
                .remark(params.getRemark())
                .build();
        int count = clockingService.insertAccountClocking(accountClocking);
        if (count <= 0) {
            return ApiResult.fail("打卡失败，请重新打卡！");
        }
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.clockingScore(1,accountClocking.getClockingStatus(),accountClocking.getAccountClockingId(),account));
        return ApiResult.okMsg("成功！");
    }

    /**
     * 下班打卡(正常打卡/外勤打卡)
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @PostMapping("/clickOffWorkClock")
    public ApiResult clickOffWorkClock(HttpServletRequest request, @Validated ClockingParamVO params, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询考勤范围及上班时间
        SysDept dept = new SysDept();
        dept.setDeptId(account.getCompanyId());
        dept.setLon(params.getLon());
        dept.setLat(params.getLat());
        Map<String, Object> clock = deptService.selectClock(dept);
        if (clock == null) {
            return ApiResult.fail("暂未查询到考勤打卡信息！");
        }
        if (clock.get("onWork") == null) {
            return ApiResult.fail("没有查询到上班的考勤时间，暂时不能打卡！");
        }
//        // clockingStatus 0:否 1：可以打卡
//        if ("0".equals(clock.get("clockingStatus"))) {
//            return ApiResult.fail("不在打卡范围内，暂时不能打卡");
//        }
        // 查询今天打过上班卡
        AccountClocking clocking = clockingService.selectAccountClocking(AccountClocking.builder().accountId(account.getAccountId()).clockingDate(new Date()).build());
        // 上班点如果没打卡的情况下，查看是否过了下班点，是的话，只能打下班卡
        if (clocking == null) {
            if(DateUtils.time(DateUtil.format(new Date(), "yyyy-MM-dd") + " " + clock.get("offWork") + ":00") >= System.currentTimeMillis()){
                return ApiResult.fail("请先打上班卡！");
            }else{
                AccountClocking accountClocking = AccountClocking.builder()
                        .accountId(account.getAccountId())
                        .accountName(account.getNickName())
                        .accountPhone(account.getPhone())
                        .accountHead(account.getHeadPortrait())
                        .clockingDate(new Date())
                        .offClockingType(params.getStatus())
                        .offClockingAddress(params.getAddress())
                        .offClockingStatus(DateUtils.time(DateUtil.format(new Date(), "yyyy-MM-dd") + " " + clock.get("offWork") + ":00") > System.currentTimeMillis() ? 3 : 1)
                        .offWorkTime(new Date())
                        .deptId(account.getDeptId())
                        .postIds(account.getPostIds())
                        .companyId(account.getCompanyId())
                        .offClockingPics(params.getPics())
                        .offClockingRemark(params.getRemark())
                        .build();
                int count = clockingService.insertAccountClocking(accountClocking);
                if (count <= 0) {
                    return ApiResult.fail("打卡失败，请重新打卡！");
                }
                // 异步操作，送积分
                AsyncManager.me().execute(scoreService.clockingScore(1,accountClocking.getOffClockingStatus(),accountClocking.getAccountClockingId(),account));
                return ApiResult.okMsg("成功！");
            }

        }
        if (StringUtils.isNotNull(clocking.getOffWorkTime())) {
            return ApiResult.fail("您已经打过下班卡了，请勿重复打卡！");
        }
        AccountClocking accountClocking = AccountClocking.builder()
                .accountName(account.getNickName())
                .accountPhone(account.getPhone())
                .accountHead(account.getHeadPortrait())
                .offClockingType(params.getStatus())
                .offClockingAddress(params.getAddress())
                .offClockingStatus(DateUtils.time(DateUtil.format(new Date(), "yyyy-MM-dd") + " " + clock.get("offWork") + ":00") > System.currentTimeMillis() ? 3 : 1)
                .offWorkTime(new Date())
                .deptId(account.getDeptId())
                .postIds(account.getPostIds())
                .companyId(account.getCompanyId())
                .accountClockingId(clocking.getAccountClockingId())
                .offClockingPics(params.getPics())
                .offClockingRemark(params.getRemark())
                .build();
        int count = clockingService.updateAccountClocking(accountClocking);
        if (count <= 0) {
            return ApiResult.fail("打卡失败，请重新打卡！");
        }
        // 异步操作，送积分
        AsyncManager.me().execute(scoreService.clockingScore(1,accountClocking.getOffClockingStatus(),accountClocking.getAccountClockingId(),account));
        return ApiResult.okMsg("成功！");
    }

    /**
     * 补卡提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/reissueSub")
    public ApiResult reissueSub(HttpServletRequest request, @RequestBody @Validated ReissueClockingParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查看是否已打过卡，已打过卡的不能补卡
        // 查询今天打过上班卡
        AccountClocking clocking = clockingService.selectAccountClocking(AccountClocking.builder().accountId(account.getAccountId()).clockingDate(params.getReissueClockingDate()).build());
        if(clocking != null){
            // 1：上班打卡 2：下班打卡
            Integer clockingStatus = params.getClockingStatus();
            if(clockingStatus == 1 && clocking.getClockingStatus() != 0){
                return ApiResult.fail("该日期上班已打过卡了，请勿重复提交！");
            }
            if(clockingStatus == 2 && clocking.getOffClockingStatus() != 0){
                return ApiResult.fail("该日期下班已打过卡了，请勿重复提交！");
            }
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .examineModuleId(5L)
                .companyId(account.getCompanyId())
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }
        // 查询本月是否设置补卡、
        SysDept sysDept = deptService.selectDeptById(account.getCompanyId());
        if (sysDept == null) {
            return fail("暂未查询到所属公司！");
        }
        if (sysDept.getReissueNum() == 0) {
            return fail("该公司暂未设置补卡次数，不能补卡！");
        }
        // 查询补卡次数是否用完
        Integer reissueClockingCount = initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder()
                .accountId(account.getAccountId())
                .examineModuleId(5L)
                .month(DateUtil.format(params.getReissueClockingDate(), "yyyy-MM"))
                .examineStatusStr("1,2,3")
                .build());
        if (reissueClockingCount >= sysDept.getReissueNum()) {
            return fail("本月的补卡次数已用完，暂时不能补卡！");
        }
        ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineModuleId(5L);
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1,1).nextIdStr());
        int count = initiateService.insertExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
        }
        // 0:直接走后台设置的审批流 1：先部门领导审批，然后再走后台设置的审批流
        ApiAccount deptAccount = null;
        Integer examineFlowTag = 0;
        // 第一级审批都是自己部门的领导审批，如果是本部门领导提交,进入审批流程
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if (apiAccount != null) {
            examineFlowTag = 1;
            deptAccount = apiAccount;
        }
        if (examineFlowTag == 1 && deptAccount != null) {
            // 部门领导审批
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(),examineInitiate.getExamineTag()));
        }

        return okMsg("提交成功,请耐心等待审核！");
    }

    /**
     * 补卡次数
     * reissueClockingDate 补卡月份 yyyy-MM
     *
     * @param request
     * @return
     */
    @GetMapping("/reissueCount")
    public ApiResult reissueCount(HttpServletRequest request, String reissueClockingDate) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 已申请的补卡数量
        Integer reissueClockingCount = initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder()
                .accountId(account.getAccountId())
                .examineModuleId(5L)
                .month(reissueClockingDate)
                .examineStatusStr("1,2,3")
                .build());
        // 查询本月是否设置补卡、
        Integer surplusReissueClockingCount = 0;
        SysDept sysDept = deptService.selectDeptById(account.getCompanyId());
        if (sysDept != null) {
            surplusReissueClockingCount = sysDept.getReissueNum() - reissueClockingCount;
        }
        Map<String, Object> map = new HashMap<>(2);
        // 已申请的补卡数量
        map.put("reissueClockingCount", reissueClockingCount);
        // 剩余补卡次数
        map.put("surplusReissueClockingCount", surplusReissueClockingCount < 0 ? 0 : surplusReissueClockingCount);
        return ok(map);
    }

    /**
     * 修改 补卡提交（只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editReissueSub")
    public ApiResult editReissueSub(HttpServletRequest request, @RequestBody @Validated ReissueClockingParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        ExamineInitiate initiate = initiateService.selectExamineInitiateById(params.getExamineInitiateId());
        if (initiate == null) {
            return fail("该详情不存在！");
        }
        if (!account.getAccountId().equals(initiate.getAccountId())) {
            return fail("您发起的详情不存在！");
        }
        // 查看是否已打过卡，已打过卡的不能补卡
        // 查询今天打过上班卡
        AccountClocking clocking = clockingService.selectAccountClocking(AccountClocking.builder().accountId(account.getAccountId()).clockingDate(params.getReissueClockingDate()).build());
        if(clocking != null){
            // 1：上班打卡 2：下班打卡
            Integer clockingStatus = params.getClockingStatus();
            if(clockingStatus == 1 && clocking.getClockingStatus() != 0){
                return ApiResult.fail("该日期上班已打过卡了，请勿重复提交！");
            }
            if(clockingStatus == 2 && clocking.getOffClockingStatus() != 0){
                return ApiResult.fail("该日期下班已打过卡了，请勿重复提交！");
            }
        }
        // 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
        Integer status = initiate.getExamineStatus();
        if (status != 1 && status != 4 && status != 5) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        ExamineFlow examineFlow1 = ExamineFlow.builder()
                .examineModuleId(5L)
                .companyId(account.getCompanyId())
                .build();
        ExamineFlow examineFlow = flowService.selectExamineFlow(examineFlow1);
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }

        ExamineInitiate examineInitiate = ExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setExamineStatus(1);
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1,1).nextIdStr());
        int count = initiateService.updateExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
        }
        // 将审批流程的第一级人员信息存到记录表中
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(),examineInitiate.getExamineTag()));
        return okMsg("提交成功,请耐心等待审核！");
    }


    /**
     * 我的考勤记录
     *
     * @param request
     * @param pageParam 分页
     * @param paramVO   搜索条件
     * @return
     */
    @GetMapping("/myClockingList")
    public ApiResult myClockingList(HttpServletRequest request, PageParam pageParam, ClockingSearchParamVO paramVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        AccountClocking clocking = AccountClocking.builder()
                .accountId(tokenService.getNoAccountId(request))
                .month(paramVO.getMonth())
                .status(paramVO.getStatus())
                .build();
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询考勤列表
        List<AccountClocking> clockingList = clockingService.selectApiAccountClockingList(clocking);
        PageInfo<AccountClocking> pageInfo = new PageInfo<>(clockingList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageInfo", pageInfo);
        // 查询考勤数量
        Integer clockingCount = clockingService.selectApiAccountClockingCount(clocking);
        map.put("clockingCount", clockingCount);
        return ok(map);
    }

    /**
     * 我的考勤统计
     *
     * @param request
     * @param paramVO 搜索条件
     * @return
     */
    @PassToken
    @GetMapping("/myClockingStatistics")
    public ApiResult myClockingStatistics(HttpServletRequest request, ClockingSearchParamVO paramVO) {
        String accountId = tokenService.getNoAccountId(request);
        AccountClocking accountClocking = AccountClocking.builder()
                .accountId(accountId)
                .month(paramVO.getMonth())
                .build();
        // beLateCount:迟到次数  leaveEarlyCount:早退次数  lackCount:缺卡次数 clockingCount：出勤天数
        Map<String, Object> map = clockingService.selectApiAccountClockingStatisticsCount(accountClocking);
        // 缺卡次数
        // 0:相等 1：大于 2：小于
        if(DateUtils.monthCompare(paramVO.getMonth()) == 0){
            accountClocking.setDate(DateUtil.today());
            map.put("lackCount",clockingService.selectApiAccountClockingStatisticsCount(accountClocking).get("lackCount"));
        }
        if(DateUtils.monthCompare(paramVO.getMonth()) == 1){
            map.put("lackCount",0);
        }
        // 旷工次数
        Integer absenteeismDay = clockingService.absenteeismCount(accountId, paramVO.getMonth()).getAbsenteeismCount();
        map.put("absenteeismDay", absenteeismDay);

//        // 正常天数 = 正常天数 + 补卡天数
//        Integer normalDay = clockingService.selectApiAccountClockingCount(AccountClocking.builder()
//                .accountId(accountId)
//                .month(paramVO.getMonth())
//                .clockingStatus(1)
//                .offClockingStatus(1)
//                .build()) + clockingService.selectApiAccountClockingCount(AccountClocking.builder()
//                .accountId(accountId)
//                .month(paramVO.getMonth())
//                .clockingStatus(6)
//                .offClockingStatus(6)
//                .build());
        // 正常天数 = 正常天数 + 补卡天数
        Integer normalDay = clockingService.selectApiAccountNormalClockingDay(AccountClocking.builder()
                .accountId(accountId)
                .month(paramVO.getMonth())
                .build());
        map.put("normalDay", normalDay);

        // 异常天数
        Integer abnormalDay = clockingService.normalCount(accountId,paramVO.getMonth()) - normalDay;
        map.put("abnormalDay", abnormalDay < 0 ? 0 : abnormalDay);


        // 平均工时
        map.put("avgWorkHour", clockingService.selectApiAccountClockingWorkHour(accountClocking));


        // 休息天数
        // 0:相等 1：大于 2：小于
        Integer restDay = 0;
        if(DateUtils.monthCompare(paramVO.getMonth()) == 0){
            // 如果本月的话，查询本月剩余天数
            // 休息天数 = 本月已过天数 - 出勤天数 - 旷工天数
            restDay = DateUtils.alreadyDay() - Convert.toInt(map.get("clockingCount")) - absenteeismDay;
        }
        if(DateUtils.monthCompare(paramVO.getMonth()) == 2){
            // 休息天数 = 月的总天数 - 出勤天数 - 旷工天数
            restDay = DateUtils.day(paramVO.getMonth()) - Convert.toInt(map.get("clockingCount")) - absenteeismDay;
        }
        map.put("restDay", restDay < 0 ? 0 : restDay);

        // 请假次数
        Integer leaveCount = initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder()
                .accountId(accountId)
                .examineModuleId(1L)
                .leaveYear(paramVO.getLeaveYear())
                .examineStatus(3)
                .build());
        map.put("leaveCount", leaveCount);

        // 外勤次数
        accountClocking.setStatus(2);
        Integer signCount = clockingService.selectApiAccountClockingCount(accountClocking);
        map.put("signCount", signCount);

        // 补卡申请次数
        Integer reissueCount = initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder()
                .accountId(accountId)
                .examineModuleId(5L)
                .month(paramVO.getMonth())
                .examineStatus(3)
                .build());
        map.put("reissueCount", reissueCount);
        return ok(map);
    }






    /**
     * 考勤记录（迟到）
     *
     * @param request
     * @param pageParam 分页
     * @param paramVO   搜索条件
     * @return
     */
    @GetMapping("/beLateList")
    public ApiResult beLateList(HttpServletRequest request, PageParam pageParam, ClockingSearchParamVO paramVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        AccountClocking clocking = AccountClocking.builder()
                .accountId(tokenService.getNoAccountId(request))
                .month(paramVO.getMonth())
                .clockingStatus(2)
                .build();
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询考勤列表
        List<AccountClocking> clockingList = clockingService.selectApiAccountClockingList(clocking);
        PageInfo<AccountClocking> pageInfo = new PageInfo<>(clockingList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageInfo", pageInfo);
        return ok(map);
    }

    /**
     * 考勤记录（早退）
     *
     * @param request
     * @param pageParam 分页
     * @param paramVO   搜索条件
     * @return
     */
    @GetMapping("/leaveEarlyList")
    public ApiResult leaveEarlyList(HttpServletRequest request, PageParam pageParam, ClockingSearchParamVO paramVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        AccountClocking clocking = AccountClocking.builder()
                .accountId(tokenService.getNoAccountId(request))
                .month(paramVO.getMonth())
                .offClockingStatus(3)
                .build();
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询考勤列表
        List<AccountClocking> clockingList = clockingService.selectApiAccountClockingList(clocking);
        PageInfo<AccountClocking> pageInfo = new PageInfo<>(clockingList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageInfo", pageInfo);
        return ok(map);
    }

    /**
     * 考勤记录（缺卡）
     *
     * @param request
     * @param pageParam 分页
     * @param paramVO   搜索条件
     * @return
     */
    @GetMapping("/lackList")
    public ApiResult lackList(HttpServletRequest request, PageParam pageParam, ClockingSearchParamVO paramVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        AccountClocking clocking = AccountClocking.builder()
                .accountId(tokenService.getNoAccountId(request))
                .month(paramVO.getMonth())
                .tag(1)
                .build();
        // 缺卡次数
        // 0:相等 1：大于 2：小于
        if(DateUtils.monthCompare(paramVO.getMonth()) == 0) {
            clocking.setDate(DateUtil.today());
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询考勤列表
        List<AccountClocking> clockingList = clockingService.selectApiAccountClockingList(clocking);
        PageInfo<AccountClocking> pageInfo = new PageInfo<>(clockingList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageInfo", pageInfo);
        return ok(map);
    }

    /**
     * 考勤记录（旷工次数）
     *
     * @param request
     * @param paramVO   搜索条件
     * @return
     */
    @GetMapping("/absenteeismList")
    public ApiResult absenteeismList(HttpServletRequest request,ClockingSearchParamVO paramVO) {
        String accountId = tokenService.getNoAccountId(request);
        // 查询旷工次数
        List<ClockingDayResultVO> resultVOList = clockingService.absenteeismCount(accountId, paramVO.getMonth()).getClockingDayResultVOList();
        Map<String, Object> map = new HashMap<>(2);
        map.put("resultVOList", resultVOList);
        return ok(map);
    }


    /**
     * 考勤记录（外勤次数）
     *
     * @param request
     * @param pageParam 分页
     * @param paramVO   搜索条件
     * @return
     */
    @GetMapping("/signList")
    public ApiResult signList(HttpServletRequest request, PageParam pageParam, ClockingSearchParamVO paramVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        AccountClocking clocking = AccountClocking.builder()
                .accountId(tokenService.getNoAccountId(request))
                .month(paramVO.getMonth())
                .status(2)
                .build();
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        // 查询考勤列表
        List<AccountClocking> clockingList = clockingService.selectApiAccountClockingList(clocking);
        PageInfo<AccountClocking> pageInfo = new PageInfo<>(clockingList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageInfo", pageInfo);
        return ok(map);
    }


    /**
     * 考勤记录(休息天数)
     * 查询某个用户某个月的休息天数列表
     * accountId 用户ID
     * month 月份
     *
     * @return
     */
    @GetMapping("/restList")
    public ApiResult restList(HttpServletRequest request, ClockingSearchParamVO paramVO) {
        String accountId = tokenService.getNoAccountId(request);
        // 查询某个人某个月每一天的考勤情况
        List<ClockingDayResultVO> clockingDayResultVOList = clockingService.selectApiClockingListByMonth(AccountClocking.builder().accountId(accountId).month(paramVO.getMonth()).build());
        //
        clockingDayResultVOList = clockingDayResultVOList.stream().filter(t -> t.getClockingCount() == 0).collect(Collectors.toList());
        // 查询旷工情况
        AbsenteeismResultVO absenteeismResultVO = clockingService.absenteeismCount(accountId,paramVO.getMonth());
        // 休息天数 = 未统计到的考勤 - 旷工的天数
        List<ClockingDayResultVO> resultVOList = absenteeismResultVO.getClockingDayResultVOList();
        // 去重
        clockingDayResultVOList = distinct(clockingDayResultVOList,resultVOList);
        Map<String, Object> map = new HashMap<>(2);
        map.put("clockingDayResultVOList", clockingDayResultVOList);
        return ok(map);
    }

    /**
     * 使用迭代器去重
     * @param clockingDayResultVOList
     */
    public List<ClockingDayResultVO> distinct(List<ClockingDayResultVO> clockingDayResultVOList,List<ClockingDayResultVO> resultVOList) {
        if(resultVOList != null && resultVOList.size() > 0){
            Iterator<ClockingDayResultVO> iterator = clockingDayResultVOList.iterator();
            while (iterator.hasNext()) {
                // 获取循环的值
                ClockingDayResultVO item = iterator.next();
                // 如果存在两个相同的值
                for(ClockingDayResultVO resultVO : resultVOList){
                    if(item.getDate().getTime() == resultVO.getDate().getTime()){
                        // 移除最后那个相同的值
                        iterator.remove();
                    }
                }
            }
        }

        return clockingDayResultVOList;
    }

}
