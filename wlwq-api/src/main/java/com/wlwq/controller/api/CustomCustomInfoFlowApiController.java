package com.wlwq.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.annotation.PassToken;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.RepeatSubmit;
import com.wlwq.common.apiResult.ApiController;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.exception.ApiException;
import com.wlwq.framework.manager.AsyncManager;
import com.wlwq.params.examine.*;
import com.wlwq.service.TokenService;
import com.wlwq.taskService.TaskMeetingExamineFlowAccountService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.wlwq.common.apiResult.ApiResult.okMsg;

/**
 * @author wwb
 * 客户成交流程
 */
@RestController
@RequestMapping(value = "/api/customCustomInfoFlow")
@AllArgsConstructor
public class CustomCustomInfoFlowApiController extends ApiController {

    private final IExamineModuleService examineModuleService;

    private final TokenService tokenService;

    private final IMeetingExamineInitiateService meetingInitiateService;

    private final IMeetingExamineFlowService meetingFlowService;

    private final IMeetingExamineFlowAccountService meetingFlowAccountService;

    private final IApiAccountService accountService;

    private final TaskMeetingExamineFlowAccountService taskExamineFlowAccountService;

    private final IExamineFileService fileService;

    private final ICustomExamineRecordService customExamineRecordService;


    /**
     * 审批类型列表（一级）
     *
     * @return
     */
    @GetMapping(value = "/oneList")
    @PassToken
    public ApiResult oneList() {
        List<ExamineModule> classList = examineModuleService.selectMeetingExamineModuleList(ExamineModule.builder().parentId(0L).showStatus(1).build());
        return ok(classList);
    }

    /**
     * 审批类型列表（三级）
     *
     * @param examineModuleId 二级分类ID
     * @param keyword         关键词搜索
     * @return
     */
    @GetMapping(value = "/threeList")
    @PassToken
    public ApiResult threeList(@RequestParam(defaultValue = "0") Long examineModuleId, String keyword) {
        List<ExamineModule> classList = examineModuleService.selectMeetingExamineModuleList(ExamineModule
                .builder()
                .parentId(examineModuleId)
                .showStatus(1)
                .moduleName(keyword)
                .build());
        return ok(classList);
    }


    /**
     * 客户成交--客户成交审核提交
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/targetTrainingRecordFlowSub")
    public ApiResult targetTrainingRecordFlowSub(HttpServletRequest request, @RequestBody @Validated CustomCustomInfoParamVO params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        CustomExamineRecord meetingTraining = customExamineRecordService.selectCustomExamineRecordById(params.getTargetTrainingId());
        if (meetingTraining == null) {
            return ApiResult.fail("目标记录不存在！");
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
        if (examineFlowTag == 0) {
            // 查询用户所在的部门的某一各类型是否添加审批流程
            MeetingExamineFlow examineFlow = meetingFlowService.selectMeetingExamineFlow(MeetingExamineFlow.builder()
                    .companyId(account.getCompanyId())
                    .examineModuleId(31L)
                    .build());
            if (examineFlow == null) {
                return fail("该部门下该模块没有添加审批流程，请联系管理员！");
            }
        }

        MeetingExamineInitiate examineInitiate = MeetingExamineInitiate.builder().build();
        // 将传过来的参数赋值给要存入的实体
        BeanUtil.copyProperties(params, examineInitiate);
        examineInitiate.setCompanyId(account.getCompanyId());
        examineInitiate.setDeptId(account.getDeptId());
        examineInitiate.setAccountId(account.getAccountId());
        examineInitiate.setAccountName(account.getNickName());
        examineInitiate.setAccountPhone(account.getPhone());
        examineInitiate.setAccountHead(account.getHeadPortrait());
        examineInitiate.setSynopsis(account.getBriefIntroduction());
        examineInitiate.setDeptName(params.getDeptName());
        examineInitiate.setExamineModuleId(31);

        examineInitiate.setTargetId(params.getMeetingTrainingId());
        examineInitiate.setMeetingTrainingId(params.getMeetingTrainingId());

//        examineInitiate.setTitle(meetingTraining.getTitle());
//        examineInitiate.setOrganizerAccountId(meetingTraining.getApplicantId());
//        examineInitiate.setOrganizerHeadPortrait(meetingTraining.getApplicantrHeadPortrait());
//        examineInitiate.setOrganizerNickName(meetingTraining.getApplicantNickName());

        examineInitiate.setCustomId(params.getCustomId());
        examineInitiate.setCustomName(params.getCustomName());
        examineInitiate.setCustomHeadPortrait(params.getCustomHeadPortrait());
        examineInitiate.setCustomPhone(params.getCustomPhone());
        examineInitiate.setCustomeStrikeMoney(params.getCustomeStrikeMoney());
        examineInitiate.setCustomEnclosure(params.getCustomEnclosure());
        examineInitiate.setExamineTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        int count = meetingInitiateService.insertMeetingExamineInitiate(examineInitiate);
        if (count <= 0) {
            throw new ApiException("提交失败");
        }
        // 异步操作
        fileSave(params.getFileList(), examineInitiate.getExamineInitiateId());

        if (examineFlowTag == 1 && deptAccount != null) {
            // 部门领导审批
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            MeetingExamineFlow examineFlow1 = MeetingExamineFlow.builder()
                    .examineModuleId(31L)
                    .companyId(account.getCompanyId())
                    .minValue(params.getCustomeStrikeMoney())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, examineInitiate.getExamineInitiateId(), examineInitiate.getExamineTag()));

        }
        return okMsg("提交成功,请耐心等待审核！");
    }
    /**
     * 文件存储
     *
     * @param fileList
     */
    public void fileSave(List<ExamineFile> fileList, String examineInitiateId) {
        if (fileList != null && fileList.size() > 0) {
            fileList.forEach((ExamineFile file) -> {
                if (file.getExamineFileType() != null
                        && StringUtils.isNotBlank(file.getExamineFileName())
                        && StringUtils.isNotBlank(file.getExamineFileUrl())) {
                    // 计算文件的大小
                    try {
                        file.setExamineFileSize(fileSize(file.getExamineFileUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 将文件存储起来
                    file.setExamineInitiateId(examineInitiateId);
                    int countFlag = fileService.insertExamineFile(file);
                    if (countFlag <= 0) {
                        throw new ApiException("文件保存失败！");
                    }
                }
            });
        }
    }
    /**
     * 我发起的审批列表
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myInitiateList")
    public ApiResult myInitiateList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<MeetingExamineInitiate> initiateList = meetingInitiateService.selectExamineInitiateApiList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag() == null ? 1 : searchParamVO.getSearchTimeTag())
                .parentId("0")
                .customName(searchParamVO.getKeyword())
                .build());
        PageInfo<MeetingExamineInitiate> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }

    /**
     * 我发起的审批列表各个状态未读的数量
     *
     * @param request
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myInitiateCount")
    public ApiResult myInitiateCount(HttpServletRequest request, ExamineSearchParamVO searchParamVO) {
        MeetingExamineInitiate examineInitiate = MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .parentId("0")
                .readStatus(0)
                .build();
        Map<String, Object> map = new HashMap<>(10);
        // 待审批未读数量
        examineInitiate.setExamineStatus(1);
        map.put("stayExamineNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        // 审批中未读数量
        examineInitiate.setExamineStatus(2);
        map.put("examineIngNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        // 已通过未读数量
        examineInitiate.setExamineStatus(3);
        map.put("passExamineNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        // 已驳回未读数量
        examineInitiate.setExamineStatus(4);
        map.put("refuseExamineNoReadyCount", meetingInitiateService.selectExamineInitiateApiCount(examineInitiate));
        return ok(map);
    }


    /**
     * 我发起的审批详情
     *
     * @param initiateId 审批发起ID
     * @return
     */
    @GetMapping("/initiateDetail")
    public ApiResult initiateDetail(@RequestParam(defaultValue = "0") String initiateId) {
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        // 审批类型ID  1：会议训练审核 2：会议训练评价 3：其他
        if (examineInitiate.getExamineModuleId() == 31) {
            List<MeetingExamineInitiate> initiateList = meetingInitiateService.selectExamineInitiateApiList(MeetingExamineInitiate.builder().parentId(examineInitiate.getExamineInitiateId()).build());
            initiateList.forEach((MeetingExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.setInitiateDetailList(initiateList);
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);

        CustomExamineRecord customExamineRecord = customExamineRecordService.selectCustomExamineRecordById(examineInitiate.getMeetingTrainingId());
        if (customExamineRecord == null) {
            return ApiResult.fail("没有此记录数据！");
        }

        map.put("customExamineRecord", customExamineRecord);

        // 将信息改为已读
        meetingInitiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                .examineInitiateId(initiateId)
                .readStatus(1)
                .build());
        return ok(map);
    }

    /**
     * 查询员工第一次发起的审批管理人员列表
     *
     * @param examineModuleId 审批类型ID  24：会议训练审核 25：会议训练评价
     * @return
     */
    @GetMapping("/examineFlowList")
    public ApiResult examineFlowList(HttpServletRequest request, @RequestParam(defaultValue = "0") Long examineModuleId) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        // 查询审批流程
        MeetingExamineFlow examineFlow = MeetingExamineFlow.builder()
                .examineModuleId(examineModuleId)
                //.deptId(account.getDeptId())
                .companyId(account.getCompanyId())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = meetingFlowService.selectStaffInitiateExaminePeopleList(examineFlow);
        // 默认第一级是本部门的领导
        // 查询本部门的领导
        ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(account.getCompanyId()).deptId(account.getDeptId()).positionType("1").build());
        if (apiAccount != null) {
            List<ExamineFlowAccountResultVO> accountResultVOList = new ArrayList<>();
            accountResultVOList.add(ExamineFlowAccountResultVO.builder()
                    .accountId(apiAccount.getAccountId())
                    .accountName(apiAccount.getNickName())
                    .accountHead(apiAccount.getHeadPortrait())
                    .phone(apiAccount.getPhone())
                    .postIds(apiAccount.getPostIds())
                    .companyId(apiAccount.getCompanyId())
                    .postName(apiAccount.getPostNames())
                    .examineSequence(0)
                    .build());
            examineFlowResultVOList.add(ExamineFlowResultVO.builder()
                    .examineSequence(0)
                    .examinePeopleCount(1)
                    .accountResultVOList(accountResultVOList)
                    .build());
            // 排序，examineSequence从小到大
            Collections.sort(examineFlowResultVOList, Comparator.comparingInt(ExamineFlowResultVO::getExamineSequence));
        }
        return ok(examineFlowResultVOList);
    }


    /**
     * 我审批的审批列表
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myExamineList")
    public ApiResult myExamineList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> initiateList = meetingFlowAccountService.selectMyExamineList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .searchTimeTag(searchParamVO.getSearchTimeTag() == null ? 1 : searchParamVO.getSearchTimeTag())
                .customName(searchParamVO.getKeyword())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
        return ok(pageInfo);
    }

    /**
     * 全部客户列表（审核通过的）
     *
     * @param request
     * @param pageParam     分页
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/allExamineList")
    public ApiResult allExamineList(HttpServletRequest request, PageParam pageParam, ExamineSearchParamVO searchParamVO) {
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }
        if (pageParam == null) {
            pageParam = PageParam.builder().build();
        }
        // 查询列表
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<Map<String, Object>> initiateList = meetingFlowAccountService.selectAllExamineList(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(searchParamVO.getAccountId())
                .companyId(account.getCompanyId())
                .searchTimeTag(searchParamVO.getSearchTimeTag() == null ? 1 : searchParamVO.getSearchTimeTag())
                .tag(searchParamVO.getTag())
                .customName(searchParamVO.getKeyword())
                .build());
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(initiateList);
        if (initiateList != null && initiateList.size() > 0) {
            initiateList.forEach(
                    obj -> {
                        MeetingExamineInitiate meetingExamineInitiate = meetingInitiateService.selectInitiateByMeetingTrainingId(obj.get("meetingTrainingId").toString());
                        if (meetingExamineInitiate != null) {
                            obj.put("examineInitiateIdByEvaluate", meetingExamineInitiate.getExamineInitiateId());
                        }
                    }
            );
        }
        return ok(pageInfo);
    }

    /**
     * 我审批的各个状态未读的数量
     *
     * @param request
     * @param searchParamVO 搜索条件
     * @return
     */
    @GetMapping("/myExamineCount")
    public ApiResult myExamineCount(HttpServletRequest request, ExamineSearchParamVO searchParamVO) {
        Map<String, Object> map = new HashMap<>(10);
        // 待审批数量
        map.put("noExamineCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(1)
                .build()));
        // 待审批未读的数量
        map.put("noExamineNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(1)
                .readStatus(0)
                .build()));
        // 已通过未读的数量
        map.put("passNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(3)
                .readStatus(0)
                .build()));
        // 已驳回未读的数量
        map.put("rejectNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(4)
                .readStatus(0)
                .build()));
        // 审批中数量
        map.put("examineCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(2)
                .build()));
        // 审批中未读的数量
        map.put("examineNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder()
                .reason(searchParamVO.getTitle())
                .examineStatus(searchParamVO.getExamineStatus())
                .examineModuleStr(searchParamVO.getExamineModuleStr())
                .accountId(tokenService.getAccountId(request))
                .examineStatus(2)
                .readStatus(0)
                .build()));
        return ok(map);
    }


    /**
     * 我审批的审批详情
     *
     * @param request
     * @param flowAccountId 用户审批流程ID
     * @param tag           0:查询个人，1：查询全部
     * @return
     */
    @GetMapping("/examineDetail")
    public ApiResult examineDetail(HttpServletRequest request, @RequestParam(defaultValue = "0") String flowAccountId,
                                   @RequestParam(defaultValue = "0") int tag) {
        String accountId = tokenService.getAccountId(request);
        if (tag == 1) {
            accountId = null;
        }
        Map<String, Object> examineInitiate = meetingFlowAccountService.selectMyExamineDetail(accountId, flowAccountId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }

        CustomExamineRecord customExamineRecord = customExamineRecordService.selectCustomExamineRecordById(examineInitiate.get("meetingTrainingId").toString());
        if (customExamineRecord == null) {
            return ApiResult.fail("没有此记录数据！");
        }
        examineInitiate.put("customExamineRecord", customExamineRecord);

        // 审批类型ID
        if (Convert.toInt(examineInitiate.get("examineModuleId")) == 31) {
            List<MeetingExamineInitiate> initiateList = meetingInitiateService.selectExamineInitiateApiList(MeetingExamineInitiate.builder().parentId(Convert.toStr(examineInitiate.get("examineInitiateId"))).build());
            initiateList.forEach((MeetingExamineInitiate initiate) -> {
                // 查询文件
                List<ExamineFile> fileList = fileService.selectExamineFileList(ExamineFile.builder().examineInitiateId(initiate.getExamineInitiateId()).build());
                initiate.setFileList(fileList);
            });
            examineInitiate.put("initiateDetailList", initiateList);
        }
        Map<String, Object> map = new HashMap<>(10);
        map.put("examineInitiate", examineInitiate);
        // 将信息改为已读
        meetingFlowAccountService.updateMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                .examineFlowAccountId(flowAccountId)
                .readStatus(1)
                .readTag(1)
                .build());
        return ok(map);
    }


    /**
     * 我审批的审批提交 通过/拒绝
     *
     * @param request
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/examineSub")
    public ApiResult examineSub(HttpServletRequest request, @RequestBody @Validated ExamineSubVO examineSubVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = meetingFlowAccountService.selectMyExamineDetail(accountId, examineSubVO.getFlowAccountId());
        if (map == null) {
            return fail("该详情不存在！");
        }
        // 查询同等审批的是否都通过，通过的情况下进行下一个审批
        // 查询同等级审批的相关信息
        List<MeetingExamineFlowAccount> examineFlowAccountList = meetingFlowAccountService.selectMeetingExamineFlowAccountList(MeetingExamineFlowAccount.builder()
                .examineInitiateId(map.get("examineInitiateId").toString())
                .examineSequence(Integer.parseInt(map.get("examineSequence").toString()))
                .examineTag(Convert.toStr(map.get("examineTag")))
                .build());
        for (MeetingExamineFlowAccount examineFlowAccount : examineFlowAccountList) {
            // 查询最新的审批状态
            MeetingExamineFlowAccount examineFlowAccount1 = meetingFlowAccountService.selectNearLimitExamineFlowAccount(MeetingExamineFlowAccount.builder()
                    .accountId(examineFlowAccount.getAccountId())
                    .examineInitiateId(examineFlowAccount.getExamineInitiateId())
                    .examineTag(Convert.toStr(map.get("examineTag")))
                    .examineSequence(examineFlowAccount.getExamineSequence())
                    .build());
            if (examineFlowAccount1 != null && examineFlowAccount1.getExamineStatus() == 4) {
                return fail("该发起记录有审批人员已驳回，暂不能通过或拒绝，需等该条记录的发起者重新修改才能提交！");
            }
        }
        int count = meetingFlowAccountService.updateMeetingExamineFlowAccount(MeetingExamineFlowAccount.builder()
                .examineFlowAccountId(examineSubVO.getFlowAccountId())
                .examineStatus(examineSubVO.getExamineStatus())
                .rejectContent(examineSubVO.getContent())
                .pics(examineSubVO.getPics())
                .readStatus(1)
                .build());
        if (count <= 0) {
            return fail("审批失败！");
        }
        // 异步操作
        AsyncManager.me().execute(taskExamineFlowAccountService.editExamineInitiate(examineSubVO, map));
        return okMsg("提交成功");
    }


    /**
     * 审批流程人员列表(除员工第一次发起的除外)
     *
     * @param initiateId 审批发起ID
     * @return
     */
    @PassToken
    @GetMapping("/examineFlowAccountList")
    public ApiResult examineFlowAccountList(@RequestParam(defaultValue = "0") String initiateId) {
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该信息不存在！");
        }
        // 查询审批流程
        MeetingExamineFlow examineFlow = MeetingExamineFlow.builder()
                .examineModuleId(Long.valueOf(examineInitiate.getExamineModuleId()))
                .companyId(examineInitiate.getCompanyId())
                .minValue(examineInitiate.getCustomeStrikeMoney())
                .build();
        // 查询员工发起的审批管理人员列表
        List<ExamineFlowResultVO> examineFlowResultVOList = meetingFlowService.selectStaffInitiateExaminePeopleList(examineFlow, initiateId,examineInitiate.getExamineTag());
        return ok(examineFlowResultVOList);
    }


    /**
     * 审批  删除
     *
     * @param examineInitiateId 审批发起者Id
     * @return
     */
    @PostMapping("/del")
    public ApiResult del(HttpServletRequest request, @RequestParam(defaultValue = "0") String examineInitiateId) {
        MeetingExamineInitiate initiate = meetingInitiateService.selectMeetingExamineInitiateById(examineInitiateId);
        if (initiate == null) {
            return fail("审批不存在！");
        }
        if (initiate.getExamineStatus() != 4) {
            return fail("只有已驳回的才能删除！");
        }
        if (!initiate.getAccountId().equals(tokenService.getAccountId(request))) {
            return fail("只有审批发起者才能删除！");
        }
        // 查询审批发起信息
        int count = meetingInitiateService.deleteMeetingExamineInitiateById(examineInitiateId);
        if (count <= 0) {
            return fail("删除失败！");
        }
        return okMsg("已删除！");
    }

    /**
     * 审批首页的发起/审批数量
     *
     * @param request
     * @return
     */
    @GetMapping("/examineCount")
    public ApiResult examineCount(HttpServletRequest request) {
        String accountId = tokenService.getAccountId(request);
        Map<String, Object> map = new HashMap<>(2);
        // 我发起的未读的数量
        map.put("initiateNoReadCount", meetingInitiateService.selectExamineInitiateApiCount(MeetingExamineInitiate.builder().readStatus(0).accountId(accountId).build()));
        // 我审批的未读的数量
        map.put("examineNoReadCount", meetingFlowAccountService.selectMyExamineCount(MeetingExamineInitiate.builder().accountId(accountId).readStatus(0).build()));
        return ok(map);
    }

    /**
     * 我发起的审批撤回（只有在待审批的状态下才能撤回）
     *
     * @param request
     * @param initiateId 发起者审批ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/initiateRevocation")
    public ApiResult initiateRevocation(HttpServletRequest request, @RequestParam(defaultValue = "0") String initiateId) {
        String accountId = tokenService.getAccountId(request);
        MeetingExamineInitiate examineInitiate = meetingInitiateService.selectMeetingExamineInitiateById(initiateId);
        if (examineInitiate == null) {
            return fail("该详情不存在！");
        }
        if (!accountId.equals(examineInitiate.getAccountId())) {
            return fail("您发起的详情不存在！");
        }
        if (examineInitiate.getExamineStatus() != 1) {
            return fail("只有在待审批的状态下才能撤回！");
        }
        // 查询申批人员表，将其删除
        int count = meetingFlowAccountService.deleteMeetingExamineFlowAccountByInitiateId(initiateId);
        if (count <= 0) {
            throw new ApiException("删除审批人员失败！");
        }
        int result = meetingInitiateService.updateMeetingExamineInitiate(MeetingExamineInitiate.builder()
                .examineInitiateId(initiateId)
                .examineStatus(5)
                .build());
        if (result <= 0) {
            throw new ApiException("撤回失败！");
        }
        return okMsg("已撤回！");
    }


    /**
     * 修改目标流程提交（只有待审批的和已拒绝和已撤回的才能修改提交）
     *
     * @param request
     * @param params
     * @param bindingResult
     * @return
     */
    @RepeatSubmit
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/editSub")
    public ApiResult editLeaveSub(HttpServletRequest request, @RequestBody @Validated TargetTrainingRecordParamVO params,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResult.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        if (params.getExamineModuleId() == null) {
            return ApiResult.fail("请选择审批类型！");
        }
        ApiAccount account = accountService.selectApiAccountById(tokenService.getAccountId(request));
        if (account == null) {
            return ApiResult.fail("该用户不存在！");
        }

        MeetingExamineInitiate initiate = meetingInitiateService.selectMeetingExamineInitiateById(params.getExamineInitiateId());
        if (initiate == null) {
            return fail("该详情不存在！");
        }
        if (!account.getAccountId().equals(initiate.getAccountId())) {
            return fail("您发起的详情不存在！");
        }
        // 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
        Integer status = initiate.getExamineStatus();
        if (status != 1 && status != 4 && status != 5) {
            return fail("只有在待审批或者已驳回或者已撤回的状态下才能修改！");
        }
        // 查询用户所在的部门的某一各类型是否添加审批流程
        MeetingExamineFlow examineFlow = meetingFlowService.selectMeetingExamineFlow(MeetingExamineFlow.builder()
                .companyId(account.getCompanyId())
                .examineModuleId(Long.valueOf(params.getExamineModuleId()))
                .build());
        if (examineFlow == null) {
            return fail("该部门下该模块没有添加审批流程，请联系管理员！");
        }

        CustomExamineRecord meetingTraining = customExamineRecordService.selectCustomExamineRecordById(params.getTargetTrainingId());
        if (meetingTraining == null) {
            return ApiResult.fail("目标记录不存在！");
        }

        initiate.setCompanyId(account.getCompanyId());
        initiate.setDeptId(account.getDeptId());
        initiate.setAccountId(account.getAccountId());
        initiate.setAccountName(account.getNickName());
        initiate.setAccountPhone(account.getPhone());
        initiate.setAccountHead(account.getHeadPortrait());
        initiate.setSynopsis(account.getBriefIntroduction());
        initiate.setDeptName(params.getDeptName());
        initiate.setExamineModuleId(params.getExamineModuleId());
        //initiate.setExamineModuleId(28);

        initiate.setTargetId(params.getMeetingTrainingId());
        initiate.setMeetingTrainingId(params.getMeetingTrainingId());
//        initiate.setTitle(meetingTraining.getTitle());
//        initiate.setOrganizerAccountId(meetingTraining.getApplicantId());
//        initiate.setOrganizerHeadPortrait(meetingTraining.getApplicantrHeadPortrait());
//        initiate.setOrganizerNickName(meetingTraining.getApplicantNickName());
        initiate.setExamineStatus(1);

        initiate.setCustomId(params.getCustomId());
        initiate.setCustomName(params.getCustomName());
        initiate.setCustomHeadPortrait(params.getCustomHeadPortrait());
        initiate.setCustomPhone(params.getCustomPhone());
        initiate.setCustomeStrikeMoney(params.getCustomeStrikeMoney());
        initiate.setCustomEnclosure(params.getCustomEnclosure());
        String examineTag = IdUtil.getSnowflake(1,1).nextIdStr();
        initiate.setExamineTag(examineTag);
        int count = meetingInitiateService.updateMeetingExamineInitiate(initiate);
        if (count <= 0) {
            throw new ApiException("更新失败");
        }

        // 异步操作
        fileSave(params.getFileList(), params.getExamineInitiateId());

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
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowDeptAccount(deptAccount, initiate.getExamineInitiateId(), examineTag));

        }
        if (examineFlowTag == 0) {
            // 将审批流程的第一级人员信息存到记录表中
            // 异步操作
            MeetingExamineFlow examineFlow1 = MeetingExamineFlow.builder()
                    .examineModuleId(Long.valueOf(params.getExamineModuleId()))
                    .companyId(account.getCompanyId())
                    .build();
            AsyncManager.me().execute(taskExamineFlowAccountService.examineFlowAccount(examineFlow1, initiate.getExamineInitiateId(),examineTag));
        }
        return okMsg("提交成功,请耐心等待审核！");
    }

}
