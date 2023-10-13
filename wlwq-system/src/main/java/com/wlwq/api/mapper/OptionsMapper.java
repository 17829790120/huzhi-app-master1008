package com.wlwq.api.mapper;

import com.wlwq.api.domain.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 附件设置Mapper接口
 *
 * @author Renbowen
 * @date 2020-09-26
 */
public interface OptionsMapper {
    /**
     * 查询附件设置
     *
     * @param id 附件设置ID
     * @return 附件设置
     */
    public Options selectOptionsById(Long id);

    /**
     * 查询附件设置列表
     *
     * @param options 附件设置
     * @return 附件设置集合
     */
    public List<Options> selectOptionsList(Options options);

    /**
     * 新增附件设置
     *
     * @param options 附件设置
     * @return 结果
     */
    public int insertOptions(Options options);

    /**
     * 修改附件设置
     *
     * @param options 附件设置
     * @return 结果
     */
    public int updateOptions(Options options);

    /**
     * 删除附件设置
     *
     * @param id 附件设置ID
     * @return 结果
     */
    public int deleteOptionsById(Long id);

    /**
     * 批量删除附件设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOptionsByIds(String[] ids);

    /**
     * 根据键查询值
     *
     * @param key
     * @return
     */
    String selectValueByKey(String key);

    /**
     * 根据key修改value
     *
     * @param key
     * @param value
     */
    void updateOptionsByKey(@Param("key") String key, @Param("value") String value);
}
