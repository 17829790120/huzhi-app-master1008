package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineContractCounterpartyMapper;
import com.wlwq.api.domain.ExamineContractCounterparty;
import com.wlwq.api.service.IExamineContractCounterpartyService;
import com.wlwq.common.core.text.Convert;

/**
 * 合同相对方信息Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Service
public class ExamineContractCounterpartyServiceImpl implements IExamineContractCounterpartyService {

    @Autowired
    private ExamineContractCounterpartyMapper examineContractCounterpartyMapper;

    /**
     * 查询合同相对方信息
     *
     * @param examineContractCounterpartyId 合同相对方信息ID
     * @return 合同相对方信息
     */
    @Override
    public ExamineContractCounterparty selectExamineContractCounterpartyById(String examineContractCounterpartyId) {
        return examineContractCounterpartyMapper.selectExamineContractCounterpartyById(examineContractCounterpartyId);
    }

    /**
     * 查询合同相对方信息列表
     *
     * @param examineContractCounterparty 合同相对方信息
     * @return 合同相对方信息
     */
    @Override
    public List<ExamineContractCounterparty> selectExamineContractCounterpartyList(ExamineContractCounterparty examineContractCounterparty) {
        return examineContractCounterpartyMapper.selectExamineContractCounterpartyList(examineContractCounterparty);
    }

    /**
     * 新增合同相对方信息
     *
     * @param examineContractCounterparty 合同相对方信息
     * @return 结果
     */
    @Override
    public int insertExamineContractCounterparty(ExamineContractCounterparty examineContractCounterparty) {
        examineContractCounterparty.setExamineContractCounterpartyId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineContractCounterparty.setCreateTime(DateUtils.getNowDate());
        return examineContractCounterpartyMapper.insertExamineContractCounterparty(examineContractCounterparty);
    }

    /**
     * 修改合同相对方信息
     *
     * @param examineContractCounterparty 合同相对方信息
     * @return 结果
     */
    @Override
    public int updateExamineContractCounterparty(ExamineContractCounterparty examineContractCounterparty) {
        examineContractCounterparty.setUpdateTime(DateUtils.getNowDate());
        return examineContractCounterpartyMapper.updateExamineContractCounterparty(examineContractCounterparty);
    }

    /**
     * 删除合同相对方信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineContractCounterpartyByIds(String ids) {
        return examineContractCounterpartyMapper.deleteExamineContractCounterpartyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除合同相对方信息信息
     *
     * @param examineContractCounterpartyId 合同相对方信息ID
     * @return 结果
     */
    @Override
    public int deleteExamineContractCounterpartyById(String examineContractCounterpartyId) {
        return examineContractCounterpartyMapper.deleteExamineContractCounterpartyById(examineContractCounterpartyId);
    }

    /**
     * 根据发起审批ID删除相对方信息
     * @param examineInitiateId 发起审批ID
     * @return
     */
    @Override
    public int deleteExamineContractCounterpartyByExamineInitiateId(String examineInitiateId){
        return examineContractCounterpartyMapper.deleteExamineContractCounterpartyByExamineInitiateId(examineInitiateId);
    }
}
