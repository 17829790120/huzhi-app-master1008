package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.TargetTrainingRecordContent;

/**
 * 目标训练记录自定义内容Service接口
 * 
 * @author wwb
 * @date 2023-06-05
 */
public interface ITargetTrainingRecordContentService {
    /**
     * 查询目标训练记录自定义内容
     * 
     * @param targetTrainingRecordContentId 目标训练记录自定义内容ID
     * @return 目标训练记录自定义内容
     */
    public TargetTrainingRecordContent selectTargetTrainingRecordContentById(String targetTrainingRecordContentId);

    /**
     * 查询目标训练记录自定义内容列表
     * 
     * @param targetTrainingRecordContent 目标训练记录自定义内容
     * @return 目标训练记录自定义内容集合
     */
    public List<TargetTrainingRecordContent> selectTargetTrainingRecordContentList(TargetTrainingRecordContent targetTrainingRecordContent);

    /**
     * 新增目标训练记录自定义内容
     * 
     * @param targetTrainingRecordContent 目标训练记录自定义内容
     * @return 结果
     */
    public int insertTargetTrainingRecordContent(TargetTrainingRecordContent targetTrainingRecordContent);

    /**
     * 修改目标训练记录自定义内容
     * 
     * @param targetTrainingRecordContent 目标训练记录自定义内容
     * @return 结果
     */
    public int updateTargetTrainingRecordContent(TargetTrainingRecordContent targetTrainingRecordContent);

    /**
     * 批量删除目标训练记录自定义内容
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTargetTrainingRecordContentByIds(String ids);

    /**
     * 删除目标训练记录自定义内容信息
     * 
     * @param targetTrainingRecordContentId 目标训练记录自定义内容ID
     * @return 结果
     */
    public int deleteTargetTrainingRecordContentById(String targetTrainingRecordContentId);
}
