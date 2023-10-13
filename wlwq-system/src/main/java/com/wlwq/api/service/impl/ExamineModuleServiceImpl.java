package com.wlwq.api.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineModuleMapper;
import com.wlwq.api.domain.ExamineModule;
import com.wlwq.api.service.IExamineModuleService;
import com.wlwq.common.core.text.Convert;

/**
 * 审批类型Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-10
 */
@Service
public class ExamineModuleServiceImpl implements IExamineModuleService {

    @Autowired
    private ExamineModuleMapper examineModuleMapper;

    /**
     * 查询审批类型
     *
     * @param examineModuleId 审批类型ID
     * @return 审批类型
     */
    @Override
    public ExamineModule selectExamineModuleById(Long examineModuleId) {
        return examineModuleMapper.selectExamineModuleById(examineModuleId);
    }

    /**
     * 查询审批类型列表
     *
     * @param examineModule 审批类型
     * @return 审批类型
     */
    @Override
    public List<ExamineModule> selectExamineModuleList(ExamineModule examineModule) {
        return examineModuleMapper.selectExamineModuleList(examineModule);
    }

    /**
     * 新增审批类型
     *
     * @param examineModule 审批类型
     * @return 结果
     */
    @Override
    public int insertExamineModule(ExamineModule examineModule) {
        examineModule.setCreateTime(DateUtils.getNowDate());
        return examineModuleMapper.insertExamineModule(examineModule);
    }

    /**
     * 修改审批类型
     *
     * @param examineModule 审批类型
     * @return 结果
     */
    @Override
    public int updateExamineModule(ExamineModule examineModule) {
        examineModule.setUpdateTime(DateUtils.getNowDate());
        return examineModuleMapper.updateExamineModule(examineModule);
    }

    /**
     * 删除审批类型对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineModuleByIds(String ids) {
        return examineModuleMapper.deleteExamineModuleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批类型信息
     *
     * @param examineModuleId 审批类型ID
     * @return 结果
     */
    @Override
    public int deleteExamineModuleById(Long examineModuleId) {
        return examineModuleMapper.deleteExamineModuleById(examineModuleId);
    }

    /**
     * 查询审批类型树列表
     *
     * @return 所有审批类型信息
     */
    @Override
    public List<Ztree> selectExamineModuleTree() {
        List<ExamineModule> examineModuleList = examineModuleMapper.selectExamineModuleList(new ExamineModule());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (ExamineModule examineModule : examineModuleList) {
            Ztree ztree = new Ztree();
            ztree.setId(examineModule.getExamineModuleId());
            ztree.setpId(examineModule.getParentId());
            ztree.setName(examineModule.getModuleName());
            ztree.setTitle(examineModule.getModuleName());
            ztrees.add(ztree);
        }
        return ztrees;
    }
    /**
     * 查询审批类型列表
     *
     * @param examineModule 审批类型
     * @return 审批类型
     */
    @Override
    public List<ExamineModule> selectMeetingExamineModuleList(ExamineModule examineModule) {
        return examineModuleMapper.selectMeetingExamineModuleList(examineModule);
    }
}
