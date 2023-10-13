package com.wlwq.note.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.note.domain.NoteConfig;
import com.wlwq.note.mapper.NoteConfigMapper;
import com.wlwq.note.service.INoteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信配置Service业务层处理
 *
 * @author Rick wlwq
 * @date 2021-07-07
 */
@Service
public class NoteConfigServiceImpl implements INoteConfigService
{
    @Autowired
    private NoteConfigMapper noteConfigMapper;

    /**
     * 查询短信配置
     *
     * @param noteConfigId 短信配置ID
     * @return 短信配置
     */
    @Override
    public NoteConfig selectNoteConfigById(String noteConfigId)
    {
        return noteConfigMapper.selectNoteConfigById(noteConfigId);
    }

    /**
     * 查询短信配置列表
     *
     * @param noteConfig 短信配置
     * @return 短信配置
     */
    @Override
    public List<NoteConfig> selectNoteConfigList(NoteConfig noteConfig)
    {
        return noteConfigMapper.selectNoteConfigList(noteConfig);
    }

    /**
     * 新增短信配置
     *
     * @param noteConfig 短信配置
     * @return 结果
     */
    @Override
    public int insertNoteConfig(NoteConfig noteConfig)
    {
        noteConfig.setCreateTime(DateUtils.getNowDate());
        return noteConfigMapper.insertNoteConfig(noteConfig);
    }

    /**
     * 修改短信配置
     *
     * @param noteConfig 短信配置
     * @return 结果
     */
    @Override
    public int updateNoteConfig(NoteConfig noteConfig)
    {
        noteConfig.setUpdateTime(DateUtils.getNowDate());
        return noteConfigMapper.updateNoteConfig(noteConfig);
    }

    /**
     * 删除短信配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteConfigByIds(String ids)
    {
        return noteConfigMapper.deleteNoteConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除短信配置信息
     *
     * @param noteConfigId 短信配置ID
     * @return 结果
     */
    @Override
    public int deleteNoteConfigById(String noteConfigId)
    {
        return noteConfigMapper.deleteNoteConfigById(noteConfigId);
    }
}