package com.wlwq.api.service.impl;

import com.wlwq.api.domain.Options;
import com.wlwq.api.mapper.OptionsMapper;
import com.wlwq.api.service.IOptionsService;
import com.wlwq.common.core.text.Convert;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件设置Service业务层处理
 *
 * @author Renbowen
 * @date 2020-09-26
 */
@Service
public class OptionsServiceImpl implements IOptionsService
{
    private final OptionsMapper optionsMapper;

    public OptionsServiceImpl(OptionsMapper optionsMapper) {
        this.optionsMapper = optionsMapper;
    }

    /**
     * 查询附件设置
     *
     * @param id 附件设置ID
     * @return 附件设置
     */
    @Override
    public Options selectOptionsById(Long id)
    {
        return optionsMapper.selectOptionsById(id);
    }

    /**
     * 查询附件设置列表
     *
     * @param options 附件设置
     * @return 附件设置
     */
    @Override
    public List<Options> selectOptionsList(Options options)
    {
        return optionsMapper.selectOptionsList(options);
    }

    /**
     * 新增附件设置
     *
     * @param options 附件设置
     * @return 结果
     */
    @Override
    public int insertOptions(Options options)
    {
        return optionsMapper.insertOptions(options);
    }

    /**
     * 修改附件设置
     *
     * @param options 附件设置
     * @return 结果
     */
    @Override
    public int updateOptions(Options options)
    {
        return optionsMapper.updateOptions(options);
    }

    /**
     * 删除附件设置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOptionsByIds(String ids)
    {
        return optionsMapper.deleteOptionsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除附件设置信息
     *
     * @param id 附件设置ID
     * @return 结果
     */
    @Override
    public int deleteOptionsById(Long id)
    {
        return optionsMapper.deleteOptionsById(id);
    }

    /**
     * 根据键查询值
     * @param key
     * @return
     */
    @Override
    public String selectValueByKey(String key) {
        return optionsMapper.selectValueByKey(key);
    }

    @Override
    public void updateOptionsByKey(String key, String value) {
        optionsMapper.updateOptionsByKey(key,value);
    }


}
