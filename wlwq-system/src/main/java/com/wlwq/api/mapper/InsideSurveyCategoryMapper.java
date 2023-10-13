package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.InsideSurveyCategory;

/**
 * 内部调研类别Mapper接口
 *
 * @author wlwq
 * @date 2023-05-08
 */
public interface InsideSurveyCategoryMapper {
    /**
     * 查询内部调研类别
     *
     * @param insideSurveyCategoryId 内部调研类别ID
     * @return 内部调研类别
     */
    public InsideSurveyCategory selectInsideSurveyCategoryById(String insideSurveyCategoryId);

    /**
     * 查询内部调研类别列表
     *
     * @param insideSurveyCategory 内部调研类别
     * @return 内部调研类别集合
     */
    public List<InsideSurveyCategory> selectInsideSurveyCategoryList(InsideSurveyCategory insideSurveyCategory);

    /**
     * 新增内部调研类别
     *
     * @param insideSurveyCategory 内部调研类别
     * @return 结果
     */
    public int insertInsideSurveyCategory(InsideSurveyCategory insideSurveyCategory);

    /**
     * 修改内部调研类别
     *
     * @param insideSurveyCategory 内部调研类别
     * @return 结果
     */
    public int updateInsideSurveyCategory(InsideSurveyCategory insideSurveyCategory);

    /**
     * 删除内部调研类别
     *
     * @param insideSurveyCategoryId 内部调研类别ID
     * @return 结果
     */
    public int deleteInsideSurveyCategoryById(String insideSurveyCategoryId);

    /**
     * 批量删除内部调研类别
     *
     * @param insideSurveyCategoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteInsideSurveyCategoryByIds(String[] insideSurveyCategoryIds);
}
