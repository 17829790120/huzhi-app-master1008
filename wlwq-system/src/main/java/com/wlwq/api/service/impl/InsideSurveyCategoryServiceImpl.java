package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.InsideSurveyCategoryMapper;
import com.wlwq.api.domain.InsideSurveyCategory;
import com.wlwq.api.service.IInsideSurveyCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 内部调研类别Service业务层处理
 * 
 * @author wlwq
 * @date 2023-05-08
 */
@Service
public class InsideSurveyCategoryServiceImpl implements IInsideSurveyCategoryService {

    @Autowired
    private InsideSurveyCategoryMapper insideSurveyCategoryMapper;

    /**
     * 查询内部调研类别
     * 
     * @param insideSurveyCategoryId 内部调研类别ID
     * @return 内部调研类别
     */
    @Override
    public InsideSurveyCategory selectInsideSurveyCategoryById(String insideSurveyCategoryId) {
        return insideSurveyCategoryMapper.selectInsideSurveyCategoryById(insideSurveyCategoryId);
    }

    /**
     * 查询内部调研类别列表
     * 
     * @param insideSurveyCategory 内部调研类别
     * @return 内部调研类别
     */
    @Override
    public List<InsideSurveyCategory> selectInsideSurveyCategoryList(InsideSurveyCategory insideSurveyCategory) {
        return insideSurveyCategoryMapper.selectInsideSurveyCategoryList(insideSurveyCategory);
    }

    /**
     * 新增内部调研类别
     * 
     * @param insideSurveyCategory 内部调研类别
     * @return 结果
     */
    @Override
    public int insertInsideSurveyCategory(InsideSurveyCategory insideSurveyCategory) {
        insideSurveyCategory.setInsideSurveyCategoryId(IdUtil.getSnowflake(1,1).nextIdStr());
        insideSurveyCategory.setCreateTime(DateUtils.getNowDate());
        return insideSurveyCategoryMapper.insertInsideSurveyCategory(insideSurveyCategory);
    }

    /**
     * 修改内部调研类别
     * 
     * @param insideSurveyCategory 内部调研类别
     * @return 结果
     */
    @Override
    public int updateInsideSurveyCategory(InsideSurveyCategory insideSurveyCategory) {
        insideSurveyCategory.setUpdateTime(DateUtils.getNowDate());
        return insideSurveyCategoryMapper.updateInsideSurveyCategory(insideSurveyCategory);
    }

    /**
     * 删除内部调研类别对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInsideSurveyCategoryByIds(String ids) {
        return insideSurveyCategoryMapper.deleteInsideSurveyCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内部调研类别信息
     * 
     * @param insideSurveyCategoryId 内部调研类别ID
     * @return 结果
     */
    @Override
    public int deleteInsideSurveyCategoryById(String insideSurveyCategoryId) {
        return insideSurveyCategoryMapper.deleteInsideSurveyCategoryById(insideSurveyCategoryId);
    }
}
