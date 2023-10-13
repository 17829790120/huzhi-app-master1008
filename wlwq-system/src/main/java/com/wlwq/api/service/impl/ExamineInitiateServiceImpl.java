package com.wlwq.api.service.impl;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.ExamineInitiateMapper;
import com.wlwq.api.domain.ExamineInitiate;
import com.wlwq.api.service.IExamineInitiateService;
import com.wlwq.common.core.text.Convert;

/**
 * 审批发起Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-11
 */
@Service
public class ExamineInitiateServiceImpl implements IExamineInitiateService {

    @Autowired
    private ExamineInitiateMapper examineInitiateMapper;

    /**
     * 查询审批发起
     *
     * @param examineInitiateId 审批发起ID
     * @return 审批发起
     */
    @Override
    public ExamineInitiate selectExamineInitiateById(String examineInitiateId) {
        return examineInitiateMapper.selectExamineInitiateById(examineInitiateId);
    }

    /**
     * 查询审批发起列表
     *
     * @param examineInitiate 审批发起
     * @return 审批发起
     */
    @Override
    public List<ExamineInitiate> selectExamineInitiateList(ExamineInitiate examineInitiate) {
        return examineInitiateMapper.selectExamineInitiateList(examineInitiate);
    }

    /**
     * 新增审批发起
     *
     * @param examineInitiate 审批发起
     * @return 结果
     */
    @Override
    public int insertExamineInitiate(ExamineInitiate examineInitiate) {
        examineInitiate.setExamineInitiateId(IdUtil.getSnowflake(1, 1).nextIdStr());
        examineInitiate.setCreateTime(DateUtils.getNowDate());
        return examineInitiateMapper.insertExamineInitiate(examineInitiate);
    }

    /**
     * 修改审批发起
     *
     * @param examineInitiate 审批发起
     * @return 结果
     */
    @Override
    public int updateExamineInitiate(ExamineInitiate examineInitiate) {
        examineInitiate.setUpdateTime(DateUtils.getNowDate());
        return examineInitiateMapper.updateExamineInitiate(examineInitiate);
    }

    /**
     * 删除审批发起对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteExamineInitiateByIds(String ids) {
        return examineInitiateMapper.deleteExamineInitiateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审批发起信息
     *
     * @param examineInitiateId 审批发起ID
     * @return 结果
     */
    @Override
    public int deleteExamineInitiateById(String examineInitiateId) {
        return examineInitiateMapper.deleteExamineInitiateById(examineInitiateId);
    }

    /**
     * App查询审批发起列表
     *
     * @param examineInitiate 审批发起
     * @return 审批发起集合
     */
    @Override
    public List<ExamineInitiate> selectExamineInitiateApiList(ExamineInitiate examineInitiate){
        return examineInitiateMapper.selectExamineInitiateApiList(examineInitiate);
    }

    /**
     * App查询审批发起数量
     *
     * @param examineInitiate 审批发起
     * @return 审批发起数量
     */
    @Override
    public Integer selectExamineInitiateApiCount(ExamineInitiate examineInitiate){
        return examineInitiateMapper.selectExamineInitiateApiCount(examineInitiate);
    }

    /**
     * 发起审批的ID
     * @param examineInitiateId
     * @return
     */
    @Override
    public Integer deleteExamineInitiateByParentId(String examineInitiateId){
        return examineInitiateMapper.deleteExamineInitiateByParentId(examineInitiateId);
    }

    @Override
    public List<Map<String,Object>> selectList(){
        return examineInitiateMapper.selectList();
    }
}
