package com.wlwq.note.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.note.domain.NoteValidConfig;
import com.wlwq.note.mapper.NoteValidConfigMapper;
import com.wlwq.note.service.INoteValidConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人机验证配置Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-07-08
 */
@Service
public class NoteValidConfigServiceImpl implements INoteValidConfigService 
{
    @Autowired
    private NoteValidConfigMapper noteValidConfigMapper;

    /**
     * 查询人机验证配置
     * 
     * @param noteValidConfigId 人机验证配置ID
     * @return 人机验证配置
     */
    @Override
    public NoteValidConfig selectNoteValidConfigById(String noteValidConfigId)
    {
        return noteValidConfigMapper.selectNoteValidConfigById(noteValidConfigId);
    }

    /**
     * 查询人机验证配置列表
     * 
     * @param noteValidConfig 人机验证配置
     * @return 人机验证配置
     */
    @Override
    public List<NoteValidConfig> selectNoteValidConfigList(NoteValidConfig noteValidConfig)
    {
        return noteValidConfigMapper.selectNoteValidConfigList(noteValidConfig);
    }

    /**
     * 新增人机验证配置
     * 
     * @param noteValidConfig 人机验证配置
     * @return 结果
     */
    @Override
    public int insertNoteValidConfig(NoteValidConfig noteValidConfig)
    {
        noteValidConfig.setCreateTime(DateUtils.getNowDate());
        return noteValidConfigMapper.insertNoteValidConfig(noteValidConfig);
    }

    /**
     * 修改人机验证配置
     * 
     * @param noteValidConfig 人机验证配置
     * @return 结果
     */
    @Override
    public int updateNoteValidConfig(NoteValidConfig noteValidConfig)
    {
        noteValidConfig.setUpdateTime(DateUtils.getNowDate());
        return noteValidConfigMapper.updateNoteValidConfig(noteValidConfig);
    }

    /**
     * 删除人机验证配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoteValidConfigByIds(String ids)
    {
        return noteValidConfigMapper.deleteNoteValidConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除人机验证配置信息
     * 
     * @param noteValidConfigId 人机验证配置ID
     * @return 结果
     */
    @Override
    public int deleteNoteValidConfigById(String noteValidConfigId)
    {
        return noteValidConfigMapper.deleteNoteValidConfigById(noteValidConfigId);
    }
}
