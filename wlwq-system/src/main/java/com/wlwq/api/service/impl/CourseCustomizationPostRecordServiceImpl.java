package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CourseCustomizationPostRecordMapper;
import com.wlwq.api.domain.CourseCustomizationPostRecord;
import com.wlwq.api.service.ICourseCustomizationPostRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 预约记录Service业务层处理
 *
 * @author wwb
 * @date 2023-05-22
 */
@Service
public class CourseCustomizationPostRecordServiceImpl implements ICourseCustomizationPostRecordService {

    @Autowired
    private CourseCustomizationPostRecordMapper courseCustomizationPostRecordMapper;

    /**
     * 查询预约记录
     *
     * @param courseCustomizationPostRecordId 预约记录ID
     * @return 预约记录
     */
    @Override
    public CourseCustomizationPostRecord selectCourseCustomizationPostRecordById(String courseCustomizationPostRecordId) {
        return courseCustomizationPostRecordMapper.selectCourseCustomizationPostRecordById(courseCustomizationPostRecordId);
    }

    /**
     * 查询预约记录列表
     *
     * @param courseCustomizationPostRecord 预约记录
     * @return 预约记录
     */
    @Override
    public List<CourseCustomizationPostRecord> selectCourseCustomizationPostRecordList(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        return courseCustomizationPostRecordMapper.selectCourseCustomizationPostRecordList(courseCustomizationPostRecord);
    }

    /**
     * 新增预约记录
     *
     * @param courseCustomizationPostRecord 预约记录
     * @return 结果
     */
    @Override
    public int insertCourseCustomizationPostRecord(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        courseCustomizationPostRecord.setCourseCustomizationPostRecordId(IdUtil.getSnowflake(1, 1).nextIdStr());
        courseCustomizationPostRecord.setCreateTime(DateUtils.getNowDate());
        return courseCustomizationPostRecordMapper.insertCourseCustomizationPostRecord(courseCustomizationPostRecord);
    }

    /**
     * 修改预约记录
     *
     * @param courseCustomizationPostRecord 预约记录
     * @return 结果
     */
    @Override
    public int updateCourseCustomizationPostRecord(CourseCustomizationPostRecord courseCustomizationPostRecord) {
        courseCustomizationPostRecord.setUpdateTime(DateUtils.getNowDate());
        return courseCustomizationPostRecordMapper.updateCourseCustomizationPostRecord(courseCustomizationPostRecord);
    }

    /**
     * 删除预约记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCourseCustomizationPostRecordByIds(String ids) {
        return courseCustomizationPostRecordMapper.deleteCourseCustomizationPostRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预约记录信息
     *
     * @param courseCustomizationPostRecordId 预约记录ID
     * @return 结果
     */
    @Override
    public int deleteCourseCustomizationPostRecordById(String courseCustomizationPostRecordId) {
        return courseCustomizationPostRecordMapper.deleteCourseCustomizationPostRecordById(courseCustomizationPostRecordId);
    }
}
