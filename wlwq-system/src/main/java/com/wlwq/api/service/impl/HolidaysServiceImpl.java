package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.HolidaysMapper;
import com.wlwq.api.domain.Holidays;
import com.wlwq.api.service.IHolidaysService;
import com.wlwq.common.core.text.Convert;

/**
 * 节假日Service业务层处理
 * 
 * @author gaoce
 * @date 2023-05-25
 */
@Service
public class HolidaysServiceImpl implements IHolidaysService {

    @Autowired
    private HolidaysMapper holidaysMapper;

    /**
     * 查询节假日
     * 
     * @param holidaysId 节假日ID
     * @return 节假日
     */
    @Override
    public Holidays selectHolidaysById(String holidaysId) {
        return holidaysMapper.selectHolidaysById(holidaysId);
    }

    /**
     * 查询节假日列表
     * 
     * @param holidays 节假日
     * @return 节假日
     */
    @Override
    public List<Holidays> selectHolidaysList(Holidays holidays) {
        return holidaysMapper.selectHolidaysList(holidays);
    }

    /**
     * 新增节假日
     * 
     * @param holidays 节假日
     * @return 结果
     */
    @Override
    public int insertHolidays(Holidays holidays) {
        holidays.setHolidaysId(IdUtil.getSnowflake(1,1).nextIdStr());
        holidays.setCreateTime(DateUtils.getNowDate());
        return holidaysMapper.insertHolidays(holidays);
    }

    /**
     * 修改节假日
     * 
     * @param holidays 节假日
     * @return 结果
     */
    @Override
    public int updateHolidays(Holidays holidays) {
        holidays.setUpdateTime(DateUtils.getNowDate());
        return holidaysMapper.updateHolidays(holidays);
    }

    /**
     * 删除节假日对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHolidaysByIds(String ids) {
        return holidaysMapper.deleteHolidaysByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除节假日信息
     * 
     * @param holidaysId 节假日ID
     * @return 结果
     */
    @Override
    public int deleteHolidaysById(String holidaysId) {
        return holidaysMapper.deleteHolidaysById(holidaysId);
    }
}
