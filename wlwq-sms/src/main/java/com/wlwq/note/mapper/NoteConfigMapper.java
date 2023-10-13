package com.wlwq.note.mapper;

import com.wlwq.note.domain.NoteConfig;

import java.util.List;

/**
 * 短信配置Mapper接口
 *
 * @author Rick wlwq
 * @date 2021-07-07
 */
public interface NoteConfigMapper
{
    /**
     * 查询短信配置
     *
     * @param noteConfigId 短信配置ID
     * @return 短信配置
     */
    public NoteConfig selectNoteConfigById(String noteConfigId);

    /**
     * 查询短信配置列表
     *
     * @param noteConfig 短信配置
     * @return 短信配置集合
     */
    public List<NoteConfig> selectNoteConfigList(NoteConfig noteConfig);

    /**
     * 新增短信配置
     *
     * @param noteConfig 短信配置
     * @return 结果
     */
    public int insertNoteConfig(NoteConfig noteConfig);

    /**
     * 修改短信配置
     *
     * @param noteConfig 短信配置
     * @return 结果
     */
    public int updateNoteConfig(NoteConfig noteConfig);

    /**
     * 删除短信配置
     *
     * @param noteConfigId 短信配置ID
     * @return 结果
     */
    public int deleteNoteConfigById(String noteConfigId);

    /**
     * 批量删除短信配置
     *
     * @param noteConfigIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteConfigByIds(String[] noteConfigIds);
}