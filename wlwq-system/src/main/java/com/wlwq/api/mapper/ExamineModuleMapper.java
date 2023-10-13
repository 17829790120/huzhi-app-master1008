package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.ExamineModule;

/**
 * 审批类型Mapper接口
 *
 * @author gaoce
 * @date 2023-05-10
 */
public interface ExamineModuleMapper {
    /**
     * 查询审批类型
     *
     * @param examineModuleId 审批类型ID
     * @return 审批类型
     */
    public ExamineModule selectExamineModuleById(Long examineModuleId);

    /**
     * 查询审批类型列表
     *
     * @param examineModule 审批类型
     * @return 审批类型集合
     */
    public List<ExamineModule> selectExamineModuleList(ExamineModule examineModule);

    /**
     * 新增审批类型
     *
     * @param examineModule 审批类型
     * @return 结果
     */
    public int insertExamineModule(ExamineModule examineModule);

    /**
     * 修改审批类型
     *
     * @param examineModule 审批类型
     * @return 结果
     */
    public int updateExamineModule(ExamineModule examineModule);

    /**
     * 删除审批类型
     *
     * @param examineModuleId 审批类型ID
     * @return 结果
     */
    public int deleteExamineModuleById(Long examineModuleId);

    /**
     * 批量删除审批类型
     *
     * @param examineModuleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineModuleByIds(String[] examineModuleIds);
    /**
     * 查询审批类型列表
     *
     * @param examineModule 审批类型
     * @return 审批类型
     */
    List<ExamineModule> selectMeetingExamineModuleList(ExamineModule examineModule);
}
