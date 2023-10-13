package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.ExamineModule;
import com.wlwq.common.core.domain.Ztree;

/**
 * 审批类型Service接口
 *
 * @author gaoce
 * @date 2023-05-10
 */
public interface IExamineModuleService {
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
     * 批量删除审批类型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteExamineModuleByIds(String ids);

    /**
     * 删除审批类型信息
     *
     * @param examineModuleId 审批类型ID
     * @return 结果
     */
    public int deleteExamineModuleById(Long examineModuleId);

    /**
     * 查询审批类型树列表
     *
     * @return 所有审批类型信息
     */
    public List<Ztree> selectExamineModuleTree();
    /**
     * 查询审批类型列表
     *
     * @param examineModule 审批类型
     * @return 审批类型集合
     */
    public List<ExamineModule> selectMeetingExamineModuleList(ExamineModule examineModule);
}
