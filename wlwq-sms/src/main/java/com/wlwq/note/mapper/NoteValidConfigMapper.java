package com.wlwq.note.mapper;

import com.wlwq.note.domain.NoteValidConfig;

import java.util.List;

/**
 * 人机验证配置Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-07-08
 */
public interface NoteValidConfigMapper 
{
    /**
     * 查询人机验证配置
     * 
     * @param noteValidConfigId 人机验证配置ID
     * @return 人机验证配置
     */
    public NoteValidConfig selectNoteValidConfigById(String noteValidConfigId);

    /**
     * 查询人机验证配置列表
     * 
     * @param noteValidConfig 人机验证配置
     * @return 人机验证配置集合
     */
    public List<NoteValidConfig> selectNoteValidConfigList(NoteValidConfig noteValidConfig);

    /**
     * 新增人机验证配置
     * 
     * @param noteValidConfig 人机验证配置
     * @return 结果
     */
    public int insertNoteValidConfig(NoteValidConfig noteValidConfig);

    /**
     * 修改人机验证配置
     * 
     * @param noteValidConfig 人机验证配置
     * @return 结果
     */
    public int updateNoteValidConfig(NoteValidConfig noteValidConfig);

    /**
     * 删除人机验证配置
     * 
     * @param noteValidConfigId 人机验证配置ID
     * @return 结果
     */
    public int deleteNoteValidConfigById(String noteValidConfigId);

    /**
     * 批量删除人机验证配置
     * 
     * @param noteValidConfigIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteValidConfigByIds(String[] noteValidConfigIds);
}
