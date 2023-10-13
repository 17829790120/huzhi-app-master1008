package com.wlwq.api.service;

import java.util.List;

import com.wlwq.api.domain.TestTrainingCategory;

/**
 * 测试训练类别Service接口
 *
 * @author wwb
 * @date 2023-05-26
 */
public interface ITestTrainingCategoryService {
    /**
     * 查询测试训练类别
     *
     * @param testTrainingCategoryId 测试训练类别ID
     * @return 测试训练类别
     */
    public TestTrainingCategory selectTestTrainingCategoryById(String testTrainingCategoryId);

    /**
     * 查询测试训练类别列表
     *
     * @param testTrainingCategory 测试训练类别
     * @return 测试训练类别集合
     */
    public List<TestTrainingCategory> selectTestTrainingCategoryList(TestTrainingCategory testTrainingCategory);

    /**
     * 新增测试训练类别
     *
     * @param testTrainingCategory 测试训练类别
     * @return 结果
     */
    public int insertTestTrainingCategory(TestTrainingCategory testTrainingCategory);

    /**
     * 修改测试训练类别
     *
     * @param testTrainingCategory 测试训练类别
     * @return 结果
     */
    public int updateTestTrainingCategory(TestTrainingCategory testTrainingCategory);

    /**
     * 批量删除测试训练类别
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestTrainingCategoryByIds(String ids);

    /**
     * 删除测试训练类别信息
     *
     * @param testTrainingCategoryId 测试训练类别ID
     * @return 结果
     */
    public int deleteTestTrainingCategoryById(String testTrainingCategoryId);
}
