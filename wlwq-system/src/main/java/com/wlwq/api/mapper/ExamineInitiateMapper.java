package com.wlwq.api.mapper;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.ExamineInitiate;

/**
 * 审批发起Mapper接口
 *
 * @author gaoce
 * @date 2023-05-11
 */
public interface ExamineInitiateMapper {
    /**
     * 查询审批发起
     *
     * @param examineInitiateId 审批发起ID
     * @return 审批发起
     */
    public ExamineInitiate selectExamineInitiateById(String examineInitiateId);

    /**
     * 查询审批发起列表
     *
     * @param examineInitiate 审批发起
     * @return 审批发起集合
     */
    public List<ExamineInitiate> selectExamineInitiateList(ExamineInitiate examineInitiate);

    /**
     * 新增审批发起
     *
     * @param examineInitiate 审批发起
     * @return 结果
     */
    public int insertExamineInitiate(ExamineInitiate examineInitiate);

    /**
     * 修改审批发起
     *
     * @param examineInitiate 审批发起
     * @return 结果
     */
    public int updateExamineInitiate(ExamineInitiate examineInitiate);

    /**
     * 删除审批发起
     *
     * @param examineInitiateId 审批发起ID
     * @return 结果
     */
    public int deleteExamineInitiateById(String examineInitiateId);

    /**
     * 批量删除审批发起
     *
     * @param examineInitiateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineInitiateByIds(String[] examineInitiateIds);

    /**
     * App查询审批发起列表
     *
     * @param examineInitiate 审批发起
     * @return 审批发起集合
     */
    public List<ExamineInitiate> selectExamineInitiateApiList(ExamineInitiate examineInitiate);

    /**
     * App查询审批发起数量
     *
     * @param examineInitiate 审批发起
     * @return 审批发起数量
     */
    public Integer selectExamineInitiateApiCount(ExamineInitiate examineInitiate);

    /**
     * 发起审批的ID
     * @param examineInitiateId
     * @return
     */
    public Integer deleteExamineInitiateByParentId(String examineInitiateId);

    public List<Map<String,Object>> selectList();
}
