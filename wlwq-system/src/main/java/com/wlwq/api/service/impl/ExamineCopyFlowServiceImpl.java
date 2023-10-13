package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.domain.ExamineFlow;
import com.wlwq.api.domain.ExamineModule;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.api.service.IExamineModuleService;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineCopyFlowMapper;
import com.wlwq.api.domain.ExamineCopyFlow;
import com.wlwq.api.service.IExamineCopyFlowService;
import com.wlwq.common.core.text.Convert;

/**
 * 审批抄送流程Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Service
public class ExamineCopyFlowServiceImpl implements IExamineCopyFlowService {

    @Autowired
    private ExamineCopyFlowMapper examineCopyFlowMapper;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private IExamineModuleService examineModuleService;

    /**
     * 查询审批抄送流程
     *
     * @param examineCopyFlowId 审批抄送流程ID
     * @return 审批抄送流程
     */
    @Override
    public ExamineCopyFlow selectExamineCopyFlowById(String examineCopyFlowId) {
        return examineCopyFlowMapper.selectExamineCopyFlowById(examineCopyFlowId);
    }

    /**
     * 查询审批抄送流程列表
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 审批抄送流程
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<ExamineCopyFlow> selectExamineCopyFlowList(ExamineCopyFlow examineCopyFlow) {
        List<ExamineCopyFlow> flowList = examineCopyFlowMapper.selectExamineCopyFlowList(examineCopyFlow);
        flowList.forEach((ExamineCopyFlow examineFlow1) -> {
            ApiAccount apiAccount = accountService.selectApiAccountLimitByPostIdAndDeptId(examineFlow1.getDeptId(),examineFlow1.getPostId());
            examineFlow1.setAccountName(apiAccount == null ? "" : apiAccount.getNickName());
            // 审批模块
            ExamineModule examineModule = examineModuleService.selectExamineModuleById(examineFlow1.getExamineModuleId());
            examineFlow1.setExamineModuleName(examineModule == null ? "" : examineModule.getModuleName());
        });
        return flowList;
    }

    /**
     * 新增审批抄送流程
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 结果
     */
    @Override
    public int insertExamineCopyFlow(ExamineCopyFlow examineCopyFlow) {
        examineCopyFlow.setExamineCopyFlowId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineCopyFlow.setCreateTime(DateUtils.getNowDate());
        return examineCopyFlowMapper.insertExamineCopyFlow(examineCopyFlow);
    }

    /**
     * 修改审批抄送流程
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 结果
     */
    @Override
    public int updateExamineCopyFlow(ExamineCopyFlow examineCopyFlow) {
        examineCopyFlow.setUpdateTime(DateUtils.getNowDate());
        return examineCopyFlowMapper.updateExamineCopyFlow(examineCopyFlow);
    }

    /**
     * 删除审批抄送流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineCopyFlowByIds(String ids) {
        return examineCopyFlowMapper.deleteExamineCopyFlowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批抄送流程信息
     *
     * @param examineCopyFlowId 审批抄送流程ID
     * @return 结果
     */
    @Override
    public int deleteExamineCopyFlowById(String examineCopyFlowId) {
        return examineCopyFlowMapper.deleteExamineCopyFlowById(examineCopyFlowId);
    }

    /**
     * api查询审批抄送流程列表
     *
     * @param examineCopyFlow 审批抄送流程
     * @return 审批抄送流程集合
     */
    @Override
    public List<ExamineCopyFlow> selectApiExamineCopyFlowList(ExamineCopyFlow examineCopyFlow){
        List<ExamineCopyFlow> flowList = examineCopyFlowMapper.selectApiExamineCopyFlowList(examineCopyFlow);
        flowList.forEach((ExamineCopyFlow examineFlow1) -> {
            ApiAccount apiAccount = accountService.selectApiAccountLimitByPostIdAndDeptId(examineFlow1.getDeptId(),examineFlow1.getPostId());
            examineFlow1.setAccountName(apiAccount == null ? "" : apiAccount.getNickName());
            // 审批模块
            ExamineModule examineModule = examineModuleService.selectExamineModuleById(examineFlow1.getExamineModuleId());
            examineFlow1.setExamineModuleName(examineModule == null ? "" : examineModule.getModuleName());
        });
        return flowList;
    }
}
