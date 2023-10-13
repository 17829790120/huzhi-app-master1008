package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.resultVO.CustomLevelVO;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CustomLevelDaysMapper;
import com.wlwq.api.domain.CustomLevelDays;
import com.wlwq.api.service.ICustomLevelDaysService;
import com.wlwq.common.core.text.Convert;

/**
 * 客户等级回访日维护Service业务层处理
 * 
 * @author wlwq
 * @date 2023-06-07
 */
@Service
public class CustomLevelDaysServiceImpl implements ICustomLevelDaysService {

    @Autowired
    private CustomLevelDaysMapper customLevelDaysMapper;

    /**
     * 查询客户等级回访日维护
     * 
     * @param id 客户等级回访日维护ID
     * @return 客户等级回访日维护
     */
    @Override
    public CustomLevelDays selectCustomLevelDaysById(String id) {
        return customLevelDaysMapper.selectCustomLevelDaysById(id);
    }

    /**
     * 查询客户等级回访日维护列表
     * 
     * @param customLevelDays 客户等级回访日维护
     * @return 客户等级回访日维护
     */
    @Override
    public List<CustomLevelDays> selectCustomLevelDaysList(CustomLevelDays customLevelDays) {
        return customLevelDaysMapper.selectCustomLevelDaysList(customLevelDays);
    }

    /**
     * 新增客户等级回访日维护
     * 
     * @param customLevelDays 客户等级回访日维护
     * @return 结果
     */
    @Override
    public int insertCustomLevelDays(CustomLevelDays customLevelDays) {
        customLevelDays.setId(IdUtil.getSnowflake(1,1).nextIdStr());
        customLevelDays.setCreateTime(DateUtils.getNowDate());
        return customLevelDaysMapper.insertCustomLevelDays(customLevelDays);
    }

    /**
     * 修改客户等级回访日维护
     * 
     * @param customLevelDays 客户等级回访日维护
     * @return 结果
     */
    @Override
    public int updateCustomLevelDays(CustomLevelDays customLevelDays) {
        customLevelDays.setUpdateTime(DateUtils.getNowDate());
        return customLevelDaysMapper.updateCustomLevelDays(customLevelDays);
    }

    /**
     * 删除客户等级回访日维护对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomLevelDaysByIds(String ids) {
        return customLevelDaysMapper.deleteCustomLevelDaysByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户等级回访日维护信息
     * 
     * @param id 客户等级回访日维护ID
     * @return 结果
     */
    @Override
    public int deleteCustomLevelDaysById(String id) {
        return customLevelDaysMapper.deleteCustomLevelDaysById(id);
    }

    /**
     * 查询各公司等级
     */
    @Override
    public List<CustomLevelVO> findCustomLevelVO(String companyId) {
        return customLevelDaysMapper.findCustomLevelVO(companyId);
    }



}
