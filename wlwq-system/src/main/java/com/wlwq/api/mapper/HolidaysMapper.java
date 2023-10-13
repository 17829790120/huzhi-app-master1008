package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.Holidays;

/**
 * 节假日Mapper接口
 *
 * @author gaoce
 * @date 2023-05-25
 */
public interface HolidaysMapper {
    /**
     * 查询节假日
     *
     * @param holidaysId 节假日ID
     * @return 节假日
     */
    public Holidays selectHolidaysById(String holidaysId);

    /**
     * 查询节假日列表
     *
     * @param holidays 节假日
     * @return 节假日集合
     */
    public List<Holidays> selectHolidaysList(Holidays holidays);

    /**
     * 新增节假日
     *
     * @param holidays 节假日
     * @return 结果
     */
    public int insertHolidays(Holidays holidays);

    /**
     * 修改节假日
     *
     * @param holidays 节假日
     * @return 结果
     */
    public int updateHolidays(Holidays holidays);

    /**
     * 删除节假日
     *
     * @param holidaysId 节假日ID
     * @return 结果
     */
    public int deleteHolidaysById(String holidaysId);

    /**
     * 批量删除节假日
     *
     * @param holidaysIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteHolidaysByIds(String[] holidaysIds);
}
