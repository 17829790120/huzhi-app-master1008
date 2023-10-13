package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TargetTrainingRecordContentMapper;
import com.wlwq.api.domain.TargetTrainingRecordContent;
import com.wlwq.api.service.ITargetTrainingRecordContentService;
import com.wlwq.common.core.text.Convert;

/**
 * 目标训练记录自定义内容Service业务层处理
 * 
 * @author wwb
 * @date 2023-06-05
 */
@Service
public class TargetTrainingRecordContentServiceImpl implements ITargetTrainingRecordContentService {

    @Autowired
    private TargetTrainingRecordContentMapper targetTrainingRecordContentMapper;

    /**
     * 查询目标训练记录自定义内容
     * 
     * @param targetTrainingRecordContentId 目标训练记录自定义内容ID
     * @return 目标训练记录自定义内容
     */
    @Override
    public TargetTrainingRecordContent selectTargetTrainingRecordContentById(String targetTrainingRecordContentId) {
        return targetTrainingRecordContentMapper.selectTargetTrainingRecordContentById(targetTrainingRecordContentId);
    }

    /**
     * 查询目标训练记录自定义内容列表
     * 
     * @param targetTrainingRecordContent 目标训练记录自定义内容
     * @return 目标训练记录自定义内容
     */
    @Override
    public List<TargetTrainingRecordContent> selectTargetTrainingRecordContentList(TargetTrainingRecordContent targetTrainingRecordContent) {
        return targetTrainingRecordContentMapper.selectTargetTrainingRecordContentList(targetTrainingRecordContent);
    }

    /**
     * 新增目标训练记录自定义内容
     * 
     * @param targetTrainingRecordContent 目标训练记录自定义内容
     * @return 结果
     */
    @Override
    public int insertTargetTrainingRecordContent(TargetTrainingRecordContent targetTrainingRecordContent) {
        if(StringUtils.isEmpty(targetTrainingRecordContent.getTargetTrainingRecordContentId())){
            targetTrainingRecordContent.setTargetTrainingRecordContentId(IdUtil.getSnowflake(1,1).nextIdStr());
        }
        return targetTrainingRecordContentMapper.insertTargetTrainingRecordContent(targetTrainingRecordContent);
    }

    /**
     * 修改目标训练记录自定义内容
     * 
     * @param targetTrainingRecordContent 目标训练记录自定义内容
     * @return 结果
     */
    @Override
    public int updateTargetTrainingRecordContent(TargetTrainingRecordContent targetTrainingRecordContent) {
        return targetTrainingRecordContentMapper.updateTargetTrainingRecordContent(targetTrainingRecordContent);
    }

    /**
     * 删除目标训练记录自定义内容对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTargetTrainingRecordContentByIds(String ids) {
        return targetTrainingRecordContentMapper.deleteTargetTrainingRecordContentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除目标训练记录自定义内容信息
     * 
     * @param targetTrainingRecordContentId 目标训练记录自定义内容ID
     * @return 结果
     */
    @Override
    public int deleteTargetTrainingRecordContentById(String targetTrainingRecordContentId) {
        return targetTrainingRecordContentMapper.deleteTargetTrainingRecordContentById(targetTrainingRecordContentId);
    }
}
