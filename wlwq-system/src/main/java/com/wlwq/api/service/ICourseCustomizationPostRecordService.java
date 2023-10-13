package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.CourseCustomizationPostRecord;

/**
 * 预约记录Service接口
 *
 * @author wwb
 * @date 2023-05-22
 */
public interface ICourseCustomizationPostRecordService {
    /**
     * 查询预约记录
     *
     * @param courseCustomizationPostRecordId 预约记录ID
     * @return 预约记录
     */
    public CourseCustomizationPostRecord selectCourseCustomizationPostRecordById(String courseCustomizationPostRecordId);

    /**
     * 查询预约记录列表
     *
     * @param courseCustomizationPostRecord 预约记录
     * @return 预约记录集合
     */
    public List<CourseCustomizationPostRecord> selectCourseCustomizationPostRecordList(CourseCustomizationPostRecord courseCustomizationPostRecord);

    /**
     * 新增预约记录
     *
     * @param courseCustomizationPostRecord 预约记录
     * @return 结果
     */
    public int insertCourseCustomizationPostRecord(CourseCustomizationPostRecord courseCustomizationPostRecord);

    /**
     * 修改预约记录
     *
     * @param courseCustomizationPostRecord 预约记录
     * @return 结果
     */
    public int updateCourseCustomizationPostRecord(CourseCustomizationPostRecord courseCustomizationPostRecord);

    /**
     * 批量删除预约记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseCustomizationPostRecordByIds(String ids);

    /**
     * 删除预约记录信息
     *
     * @param courseCustomizationPostRecordId 预约记录ID
     * @return 结果
     */
    public int deleteCourseCustomizationPostRecordById(String courseCustomizationPostRecordId);
}
