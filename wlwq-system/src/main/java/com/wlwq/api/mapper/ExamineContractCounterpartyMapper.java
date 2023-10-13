package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamineContractCounterparty;

/**
 * 合同相对方信息Mapper接口
 *
 * @author gaoce
 * @date 2023-05-11
 */
public interface ExamineContractCounterpartyMapper {
    /**
     * 查询合同相对方信息
     *
     * @param examineContractCounterpartyId 合同相对方信息ID
     * @return 合同相对方信息
     */
    public ExamineContractCounterparty selectExamineContractCounterpartyById(String examineContractCounterpartyId);

    /**
     * 查询合同相对方信息列表
     *
     * @param examineContractCounterparty 合同相对方信息
     * @return 合同相对方信息集合
     */
    public List<ExamineContractCounterparty> selectExamineContractCounterpartyList(ExamineContractCounterparty examineContractCounterparty);

    /**
     * 新增合同相对方信息
     *
     * @param examineContractCounterparty 合同相对方信息
     * @return 结果
     */
    public int insertExamineContractCounterparty(ExamineContractCounterparty examineContractCounterparty);

    /**
     * 修改合同相对方信息
     *
     * @param examineContractCounterparty 合同相对方信息
     * @return 结果
     */
    public int updateExamineContractCounterparty(ExamineContractCounterparty examineContractCounterparty);

    /**
     * 删除合同相对方信息
     *
     * @param examineContractCounterpartyId 合同相对方信息ID
     * @return 结果
     */
    public int deleteExamineContractCounterpartyById(String examineContractCounterpartyId);

    /**
     * 批量删除合同相对方信息
     *
     * @param examineContractCounterpartyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineContractCounterpartyByIds(String[] examineContractCounterpartyIds);

    /**
     * 根据发起审批ID删除相对方信息
     * @param examineInitiateId 发起审批ID
     * @return
     */
    public int deleteExamineContractCounterpartyByExamineInitiateId(String examineInitiateId);
}
