package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TestTrainingCategoryMapper;
import com.wlwq.api.domain.TestTrainingCategory;
import com.wlwq.api.service.ITestTrainingCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 测试训练类别Service业务层处理
 *
 * @author wwb
 * @date 2023-05-26
 */
@Service
public class TestTrainingCategoryServiceImpl implements ITestTrainingCategoryService {

    @Autowired
    private TestTrainingCategoryMapper testTrainingCategoryMapper;

    /**
     * 查询测试训练类别
     *
     * @param testTrainingCategoryId 测试训练类别ID
     * @return 测试训练类别
     */
    @Override
    public TestTrainingCategory selectTestTrainingCategoryById(String testTrainingCategoryId) {
        return testTrainingCategoryMapper.selectTestTrainingCategoryById(testTrainingCategoryId);
    }

    /**
     * 查询测试训练类别列表
     *
     * @param testTrainingCategory 测试训练类别
     * @return 测试训练类别
     */
    @Override
    public List<TestTrainingCategory> selectTestTrainingCategoryList(TestTrainingCategory testTrainingCategory) {
        return testTrainingCategoryMapper.selectTestTrainingCategoryList(testTrainingCategory);
    }

    /**
     * 新增测试训练类别
     *
     * @param testTrainingCategory 测试训练类别
     * @return 结果
     */
    @Override
    public int insertTestTrainingCategory(TestTrainingCategory testTrainingCategory) {
        testTrainingCategory.setTestTrainingCategoryId(IdUtil.getSnowflake(1, 1).nextIdStr());
        testTrainingCategory.setCreateTime(DateUtils.getNowDate());
        return testTrainingCategoryMapper.insertTestTrainingCategory(testTrainingCategory);
    }

    /**
     * 修改测试训练类别
     *
     * @param testTrainingCategory 测试训练类别
     * @return 结果
     */
    @Override
    public int updateTestTrainingCategory(TestTrainingCategory testTrainingCategory) {
        return testTrainingCategoryMapper.updateTestTrainingCategory(testTrainingCategory);
    }

    /**
     * 删除测试训练类别对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestTrainingCategoryByIds(String ids) {
        return testTrainingCategoryMapper.deleteTestTrainingCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试训练类别信息
     *
     * @param testTrainingCategoryId 测试训练类别ID
     * @return 结果
     */
    @Override
    public int deleteTestTrainingCategoryById(String testTrainingCategoryId) {
        return testTrainingCategoryMapper.deleteTestTrainingCategoryById(testTrainingCategoryId);
    }
}
