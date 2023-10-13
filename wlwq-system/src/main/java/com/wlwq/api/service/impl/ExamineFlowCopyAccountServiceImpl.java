package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ExamineInitiate;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineFlowCopyAccountMapper;
import com.wlwq.api.domain.ExamineFlowCopyAccount;
import com.wlwq.api.service.IExamineFlowCopyAccountService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户审批的抄送流程Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Service
public class ExamineFlowCopyAccountServiceImpl implements IExamineFlowCopyAccountService {

    @Autowired
    private ExamineFlowCopyAccountMapper examineFlowCopyAccountMapper;

    /**
     * 查询用户审批的抄送流程
     *
     * @param examineFlowCopyAccountId 用户审批的抄送流程ID
     * @return 用户审批的抄送流程
     */
    @Override
    public ExamineFlowCopyAccount selectExamineFlowCopyAccountById(String examineFlowCopyAccountId) {
        return examineFlowCopyAccountMapper.selectExamineFlowCopyAccountById(examineFlowCopyAccountId);
    }

    /**
     * 查询用户审批的抄送流程列表
     *
     * @param examineFlowCopyAccount 用户审批的抄送流程
     * @return 用户审批的抄送流程
     */
    @Override
    public List<ExamineFlowCopyAccount> selectExamineFlowCopyAccountList(ExamineFlowCopyAccount examineFlowCopyAccount) {
        return examineFlowCopyAccountMapper.selectExamineFlowCopyAccountList(examineFlowCopyAccount);
    }

    /**
     * 新增用户审批的抄送流程
     *
     * @param examineFlowCopyAccount 用户审批的抄送流程
     * @return 结果
     */
    @Override
    public int insertExamineFlowCopyAccount(ExamineFlowCopyAccount examineFlowCopyAccount) {
        examineFlowCopyAccount.setExamineFlowCopyAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineFlowCopyAccount.setCreateTime(DateUtils.getNowDate());
        return examineFlowCopyAccountMapper.insertExamineFlowCopyAccount(examineFlowCopyAccount);
    }

    /**
     * 修改用户审批的抄送流程
     *
     * @param examineFlowCopyAccount 用户审批的抄送流程
     * @return 结果
     */
    @Override
    public int updateExamineFlowCopyAccount(ExamineFlowCopyAccount examineFlowCopyAccount) {
        examineFlowCopyAccount.setUpdateTime(DateUtils.getNowDate());
        return examineFlowCopyAccountMapper.updateExamineFlowCopyAccount(examineFlowCopyAccount);
    }

    /**
     * 删除用户审批的抄送流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineFlowCopyAccountByIds(String ids) {
        return examineFlowCopyAccountMapper.deleteExamineFlowCopyAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户审批的抄送流程信息
     *
     * @param examineFlowCopyAccountId 用户审批的抄送流程ID
     * @return 结果
     */
    @Override
    public int deleteExamineFlowCopyAccountById(String examineFlowCopyAccountId) {
        return examineFlowCopyAccountMapper.deleteExamineFlowCopyAccountById(examineFlowCopyAccountId);
    }

    /**
     * 抄送我的审批列表
     * @param examineInitiate
     * @return
     */
    @Override
    public List<Map<String,Object>> selectMyCopyExamineList(ExamineInitiate examineInitiate){
        return examineFlowCopyAccountMapper.selectMyCopyExamineList(examineInitiate);
    }

    /**
     * 抄送我的审批详情
     * @param accountId 账号ID
     * @param flowCopyAccountId 抄送ID
     * @return
     */
    @Override
    public Map<String,Object> selectCopyMyExamineDetail(String accountId,String flowCopyAccountId){
        return examineFlowCopyAccountMapper.selectCopyMyExamineDetail(accountId, flowCopyAccountId);
    }

    /**
     * 更新抄送某个用户的已读状态
     * @param copyAccountId 抄送者ID
     * @return
     */
    @Override
    public Integer updateReadStatusByCopyAccountById(String copyAccountId){
        return examineFlowCopyAccountMapper.updateReadStatusByCopyAccountById(copyAccountId);
    }

    /**
     * 查询数量
     * @param examineFlowCopyAccount
     * @return
     */
    @Override
    public Integer selectExamineFlowCopyAccountCount(ExamineFlowCopyAccount examineFlowCopyAccount){
        return examineFlowCopyAccountMapper.selectExamineFlowCopyAccountCount(examineFlowCopyAccount);
    }
}
