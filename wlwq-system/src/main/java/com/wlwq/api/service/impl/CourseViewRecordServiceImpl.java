package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CourseViewRecordMapper;
import com.wlwq.api.domain.CourseViewRecord;
import com.wlwq.api.service.ICourseViewRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 章节观看记录Service业务层处理
 *
 * @author wwb
 * @date 2023-04-24
 */
@Service
public class CourseViewRecordServiceImpl implements ICourseViewRecordService {

    @Autowired
    private CourseViewRecordMapper courseViewRecordMapper;

    /**
     * 查询章节观看记录
     *
     * @param courseViewRecordId 章节观看记录ID
     * @return 章节观看记录
     */
    @Override
    public CourseViewRecord selectCourseViewRecordById(String courseViewRecordId) {
        return courseViewRecordMapper.selectCourseViewRecordById(courseViewRecordId);
    }

    /**
     * 查询章节观看记录列表
     *
     * @param courseViewRecord 章节观看记录
     * @return 章节观看记录
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<CourseViewRecord> selectCourseViewRecordList(CourseViewRecord courseViewRecord) {
        return courseViewRecordMapper.selectCourseViewRecordList(courseViewRecord);
    }

    /**
     * 新增章节观看记录
     *
     * @param courseViewRecord 章节观看记录
     * @return 结果
     */
    @Override
    public int insertCourseViewRecord(CourseViewRecord courseViewRecord) {
        courseViewRecord.setCourseViewRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        courseViewRecord.setCreateTime(DateUtils.getNowDate());
        return courseViewRecordMapper.insertCourseViewRecord(courseViewRecord);
    }

    /**
     * 修改章节观看记录
     *
     * @param courseViewRecord 章节观看记录
     * @return 结果
     */
    @Override
    public int updateCourseViewRecord(CourseViewRecord courseViewRecord) {
        courseViewRecord.setUpdateTime(DateUtils.getNowDate());
        return courseViewRecordMapper.updateCourseViewRecord(courseViewRecord);
    }

    /**
     * 删除章节观看记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseViewRecordByIds(String ids) {
        return courseViewRecordMapper.deleteCourseViewRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除章节观看记录信息
     *
     * @param courseViewRecordId 章节观看记录ID
     * @return 结果
     */
    @Override
    public int deleteCourseViewRecordById(String courseViewRecordId) {
        return courseViewRecordMapper.deleteCourseViewRecordById(courseViewRecordId);
    }
}
