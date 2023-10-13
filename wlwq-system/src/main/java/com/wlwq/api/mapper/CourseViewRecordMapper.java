package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.CourseViewRecord;

/**
 * 章节观看记录Mapper接口
 *
 * @author wwb
 * @date 2023-04-24
 */
public interface CourseViewRecordMapper {
    /**
     * 查询章节观看记录
     *
     * @param courseViewRecordId 章节观看记录ID
     * @return 章节观看记录
     */
    public CourseViewRecord selectCourseViewRecordById(String courseViewRecordId);

    /**
     * 查询章节观看记录列表
     *
     * @param courseViewRecord 章节观看记录
     * @return 章节观看记录集合
     */
    public List<CourseViewRecord> selectCourseViewRecordList(CourseViewRecord courseViewRecord);

    /**
     * 新增章节观看记录
     *
     * @param courseViewRecord 章节观看记录
     * @return 结果
     */
    public int insertCourseViewRecord(CourseViewRecord courseViewRecord);

    /**
     * 修改章节观看记录
     *
     * @param courseViewRecord 章节观看记录
     * @return 结果
     */
    public int updateCourseViewRecord(CourseViewRecord courseViewRecord);

    /**
     * 删除章节观看记录
     *
     * @param courseViewRecordId 章节观看记录ID
     * @return 结果
     */
    public int deleteCourseViewRecordById(String courseViewRecordId);

    /**
     * 批量删除章节观看记录
     *
     * @param courseViewRecordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCourseViewRecordByIds(String[] courseViewRecordIds);
}
