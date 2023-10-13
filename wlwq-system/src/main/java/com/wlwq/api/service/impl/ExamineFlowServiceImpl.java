package com.wlwq.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.resultVO.examine.ExamineFlowAccountResultVO;
import com.wlwq.api.resultVO.examine.ExamineFlowResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineFlowMapper;
import com.wlwq.common.core.text.Convert;

/**
 * 审批流程Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Service
public class ExamineFlowServiceImpl implements IExamineFlowService {

    @Autowired
    private ExamineFlowMapper examineFlowMapper;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private IExamineModuleService examineModuleService;

    @Autowired
    private IExamineFlowAccountService flowAccountService;

    @Autowired
    private ITaskFlowAccountService taskFlowAccountService;

    @Autowired
    private IScoreFlowAccountService scoreFlowAccountService;
    @Autowired
    private IExamineInitiateService initiateService;
    @Autowired
    private IAccountScoreService accountScoreService;

    /**
     * 查询审批流程
     *
     * @param examineFlowId 审批流程ID
     * @return 审批流程
     */
    @Override
    public ExamineFlow selectExamineFlowById(String examineFlowId) {
        return examineFlowMapper.selectExamineFlowById(examineFlowId);
    }

    /**
     * 查询审批流程列表
     *
     * @param examineFlow 审批流程
     * @return 审批流程
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<ExamineFlow> selectExamineFlowList(ExamineFlow examineFlow) {
        List<ExamineFlow> examineFlowList = examineFlowMapper.selectExamineFlowList(examineFlow);
        examineFlowList.forEach((ExamineFlow examineFlow1) -> {
            ApiAccount apiAccount = accountService.selectApiAccountLimitByPostIdAndDeptId(examineFlow1.getDeptId(), examineFlow1.getPostId());
            examineFlow1.setAccountName(apiAccount == null ? "" : apiAccount.getNickName());
            // 审批模块
            ExamineModule examineModule = examineModuleService.selectExamineModuleById(examineFlow1.getExamineModuleId());
            examineFlow1.setExamineModuleName(examineModule == null ? "" : examineModule.getModuleName());
        });
        return examineFlowList;
    }

    /**
     * 新增审批流程
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public int insertExamineFlow(ExamineFlow examineFlow) {
        examineFlow.setExamineFlowId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineFlow.setCreateTime(DateUtils.getNowDate());
        return examineFlowMapper.insertExamineFlow(examineFlow);
    }

    /**
     * 修改审批流程
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public int updateExamineFlow(ExamineFlow examineFlow) {
        examineFlow.setUpdateTime(DateUtils.getNowDate());
        return examineFlowMapper.updateExamineFlow(examineFlow);
    }

    /**
     * 删除审批流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineFlowByIds(String ids) {
        return examineFlowMapper.deleteExamineFlowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批流程信息
     *
     * @param examineFlowId 审批流程ID
     * @return 结果
     */
    @Override
    public int deleteExamineFlowById(String examineFlowId) {
        return examineFlowMapper.deleteExamineFlowById(examineFlowId);
    }


    /**
     * 查询某一条数据是否存在
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public ExamineFlow selectExamineFlow(ExamineFlow examineFlow) {
        return examineFlowMapper.selectExamineFlow(examineFlow);
    }

    /**
     * 根据模块和类型查询统一审批等级查看审批顺序最小值
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public ExamineFlowResultVO selectMinExamineGroupBySequence(ExamineFlow examineFlow) {
        return examineFlowMapper.selectMinExamineGroupBySequence(examineFlow);
    }

    /**
     * app查询发起的审批列表
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public List<ExamineFlowAccountResultVO> selectAppExamineFlowList(ExamineFlow examineFlow) {
        return examineFlowMapper.selectAppExamineFlowList(examineFlow);
    }

    /**
     * 根据模块和类型和等级查询下一个等级
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public ExamineFlowResultVO selectNextExamineSequence(ExamineFlow examineFlow) {
        return examineFlowMapper.selectNextExamineSequence(examineFlow);
    }

    /**
     * 查询员工第一次发起的审批管理人员列表
     *
     * @param examineFlow
     * @return
     */
    @Override
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(ExamineFlow examineFlow) {
        // 根据模块和类型查询统一审批等级查看的人数及审批顺序
        List<ExamineFlowResultVO> examineFlowResultVOList = examineFlowMapper.selectExamineListGroupBySequence(examineFlow);
        for (ExamineFlowResultVO examineFlowResultVO : examineFlowResultVOList) {
            // 查询用户信息
            examineFlow.setExamineSequence(examineFlowResultVO.getExamineSequence());
            List<ExamineFlowAccountResultVO> accountResultVOList = examineFlowMapper.selectAppExamineFlowList(examineFlow);
            examineFlowResultVO.setAccountResultVOList(accountResultVOList);
        }
        return examineFlowResultVOList;
    }

    /**
     * 查询审批管理人员列表（除查询员工第一次发起外）
     *
     * @param examineFlow
     * @param initiateId  审批发起ID
     * @param examineTag  同一批审核唯一标识
     * @return
     */
    @Override
    public List<ExamineFlowResultVO> selectStaffInitiateExaminePeopleList(ExamineFlow examineFlow, String initiateId,String examineTag) {
        ExamineInitiate examineInitiate = initiateService.selectExamineInitiateById(initiateId);
        // 根据模块和类型查询同一审批等级查看的人数及审批顺序
        List<ExamineFlowResultVO> examineFlowResultVOList = examineFlowMapper.selectExamineListGroupBySequence(examineFlow);
        examineFlowResultVOList.add(ExamineFlowResultVO.builder()
                .examineSequence(0)
                .examinePeopleCount(1)
                .build());
        // 排序，examineSequence从小到大
        Collections.sort(examineFlowResultVOList, Comparator.comparingInt(ExamineFlowResultVO::getExamineSequence));
        for (ExamineFlowResultVO examineFlowResultVO : examineFlowResultVOList) {
            // 查询用户信息
            examineFlow.setExamineSequence(examineFlowResultVO.getExamineSequence());
            List<ExamineFlowAccountResultVO> accountResultVOList = examineFlowMapper.selectAppExamineFlowList(examineFlow);
            if(examineFlowResultVO.getExamineSequence() == 0 && examineInitiate != null){
                // 默认第一级是本部门的领导
                // 查询本部门的领导
                ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(examineInitiate.getCompanyId()).deptId(examineInitiate.getDeptId()).positionType("1").build());
                if (apiAccount != null) {
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
                }
            }
            for (ExamineFlowAccountResultVO examineFlowAccountResultVO : accountResultVOList) {
                // 查询用户的审批状态
                ExamineFlowAccount examineFlowAccount = flowAccountService.selectNearLimitExamineFlowAccount(ExamineFlowAccount.builder()
                        .examineInitiateId(initiateId)
                        .accountId(examineFlowAccountResultVO.getAccountId())
                        .postId(examineFlowAccountResultVO.getPostId())
                        .deptId(examineFlowAccountResultVO.getDeptId())
                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                        .examineTag(examineTag)
                        .build());
                // 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
                examineFlowAccountResultVO.setExamineStatus(examineFlowAccount == null ? 0 : examineFlowAccount.getExamineStatus());
                Integer examineStatus = examineFlowAccountResultVO.getExamineStatus();
                if (examineStatus == 4) {
                    examineFlowResultVO.setExamineFlowStatus(4);
                    break;
                } else if (examineStatus == 3) {
                    examineFlowResultVO.setExamineFlowStatus(3);
                } else {
                    examineFlowResultVO.setExamineFlowStatus(0);
                }
            }
            examineFlowResultVO.setAccountResultVOList(accountResultVOList);
        }
        return examineFlowResultVOList;
    }

    /**
     * 根据模块和类型查询统一审批等级查看的人数及审批顺序
     *
     * @param examineFlow 审批流程
     * @return 结果
     */
    @Override
    public List<ExamineFlowResultVO> selectExamineListGroupBySequence(ExamineFlow examineFlow) {
        return examineFlowMapper.selectExamineListGroupBySequence(examineFlow);
    }


    /**
     * 查询任务审批管理人员列表
     *
     * @param examineFlow
     * @param taskInitiate 任务发起信息
     * @param taskReceiveId 任务接收ID
     * @param status        1:延期审批 2:完成审批
     * @return
     */
    @Override
    public List<ExamineFlowResultVO> selectTaskExaminePeopleList(ExamineFlow examineFlow,TaskInitiate taskInitiate, String taskReceiveId, Integer status) {
        // 根据模块和类型查询同一审批等级查看的人数及审批顺序
        List<ExamineFlowResultVO> examineFlowResultVOList = examineFlowMapper.selectExamineListGroupBySequence(examineFlow);
        examineFlowResultVOList.add(ExamineFlowResultVO.builder()
                .examineSequence(0)
                .examinePeopleCount(1)
                .build());
        // 排序，examineSequence从小到大
        Collections.sort(examineFlowResultVOList, Comparator.comparingInt(ExamineFlowResultVO::getExamineSequence));
        for (ExamineFlowResultVO examineFlowResultVO : examineFlowResultVOList) {
            // 查询用户信息
            examineFlow.setExamineSequence(examineFlowResultVO.getExamineSequence());
            List<ExamineFlowAccountResultVO> accountResultVOList = examineFlowMapper.selectAppExamineFlowList(examineFlow);
            if(examineFlowResultVO.getExamineSequence() == 0 && taskInitiate != null) {
                // 默认第一级是本部门的领导
                // 查询本部门的领导
                ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(taskInitiate.getCompanyId()).deptId(taskInitiate.getDeptId()).positionType("1").build());
                if (apiAccount != null) {
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
                }
            }
            for (ExamineFlowAccountResultVO examineFlowAccountResultVO : accountResultVOList) {
                // 查询用户的审批状态
                TaskFlowAccount examineFlowAccount = taskFlowAccountService.selectNearLimitTaskFlowAccount(TaskFlowAccount.builder()
                        .taskReceiveId(taskReceiveId)
                        .accountId(examineFlowAccountResultVO.getAccountId())
                        .postId(examineFlowAccountResultVO.getPostId())
                        .deptId(examineFlowAccountResultVO.getDeptId())
                        .examineType(status)
                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                        .delStatus(0)
                        .build());
                // 更新时间
                examineFlowAccountResultVO.setUpdateTime(examineFlowAccount == null ? null : examineFlowAccount.getUpdateTime());
                // 审批状态 0:未审批 1延期待审批  2：延期审批中3：延期已通过 4：延期已驳回 5：延期已撤回 6完成待审批  7：正常完成审批中 8：完成已通过 9：正常完成已驳回 10正常完成已撤回  11：逾期完成待审批 12：逾期完成审批中 13：逾期完成已驳回 14逾期完成已通过 15逾期完成已撤回 16：延期完成待审批 17：延期完成审批中 18：延期完成已驳回 19延期完成已撤回 20：延期完成已通过
                examineFlowAccountResultVO.setExamineStatus(examineFlowAccount == null ? 0 : examineFlowAccount.getExamineStatus());
                Integer examineStatus = examineFlowAccountResultVO.getExamineStatus();
                if (examineStatus == 4 || examineStatus == 9 || examineStatus == 13 || examineStatus == 18) {
                    examineFlowResultVO.setExamineFlowStatus(examineStatus);
                    // 拒绝原因
                    examineFlowAccountResultVO.setRejectContent(examineFlowAccount == null ? "" : examineFlowAccount.getRejectContent());
                    // 拒绝图片
                    examineFlowAccountResultVO.setPics(examineFlowAccount == null ? "" : examineFlowAccount.getPics());
                    break;
                } else if (examineStatus == 3 || examineStatus == 8 || examineStatus == 14 || examineStatus == 20) {
                    examineFlowResultVO.setExamineFlowStatus(examineStatus);
                } else {
                    examineFlowResultVO.setExamineFlowStatus(0);
                }

            }
            examineFlowResultVO.setAccountResultVOList(accountResultVOList);
        }
        return examineFlowResultVOList;
    }

    /**
     * 查询积分兑换审批管理人员列表
     *
     * @param examineFlow
     * @param accountScoreId 用户积分ID
     * @return
     */
    @Override
    public List<ExamineFlowResultVO> selectAccountScoreExaminePeopleList(ExamineFlow examineFlow, String accountScoreId) {
        AccountScore accountScore = accountScoreService.selectAccountScoreById(accountScoreId);
        // 根据模块和类型查询同一审批等级查看的人数及审批顺序
        List<ExamineFlowResultVO> examineFlowResultVOList = examineFlowMapper.selectExamineListGroupBySequence(examineFlow);
        examineFlowResultVOList.add(ExamineFlowResultVO.builder()
                .examineSequence(0)
                .examinePeopleCount(1)
                .build());
        // 排序，examineSequence从小到大
        Collections.sort(examineFlowResultVOList, Comparator.comparingInt(ExamineFlowResultVO::getExamineSequence));
        for (ExamineFlowResultVO examineFlowResultVO : examineFlowResultVOList) {
            // 查询用户信息
            examineFlow.setExamineSequence(examineFlowResultVO.getExamineSequence());
            List<ExamineFlowAccountResultVO> accountResultVOList = examineFlowMapper.selectAppExamineFlowList(examineFlow);
            if(examineFlowResultVO.getExamineSequence() == 0 && accountScore != null){
                // 默认第一级是本部门的领导
                // 查询本部门的领导
                ApiAccount apiAccount = accountService.selectApiAccount(ApiAccount.builder().companyId(accountScore.getCompanyId()).deptId(accountScore.getDeptId()).positionType("1").build());
                if (apiAccount != null) {
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
                }
            }
            for (ExamineFlowAccountResultVO examineFlowAccountResultVO : accountResultVOList) {
                // 查询用户的审批状态
                ScoreFlowAccount examineFlowAccount = scoreFlowAccountService.selectNearLimitScoreFlowAccount(ScoreFlowAccount.builder()
                        .accountScoreId(accountScoreId)
                        .accountId(examineFlowAccountResultVO.getAccountId())
                        .postId(examineFlowAccountResultVO.getPostId())
                        .deptId(examineFlowAccountResultVO.getDeptId())
                        .examineSequence(examineFlowAccountResultVO.getExamineSequence())
                        .delStatus(0)
                        .build());
                // 审批状态 0:未审批 1：待审批  2：审批中3：已通过 4：已驳回 5：已撤回
                examineFlowAccountResultVO.setExamineStatus(examineFlowAccount == null ? 0 : examineFlowAccount.getExamineStatus());
                Integer examineStatus = examineFlowAccountResultVO.getExamineStatus();
                if (examineStatus == 4) {
                    examineFlowResultVO.setExamineFlowStatus(4);
                    break;
                } else if (examineStatus == 3) {
                    examineFlowResultVO.setExamineFlowStatus(3);
                } else {
                    examineFlowResultVO.setExamineFlowStatus(0);
                }
            }
            examineFlowResultVO.setAccountResultVOList(accountResultVOList);
        }
        return examineFlowResultVOList;
    }

}
