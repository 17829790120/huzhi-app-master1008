package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.HouseTrainingCategory;

/**
 * 家庭训练管理Service接口
 * 
 * @author wwb
 * @date 2023-05-15
 */
public interface IHouseTrainingCategoryService {
    /**
     * 查询家庭训练管理
     * 
     * @param houseTrainingCategoryId 家庭训练管理ID
     * @return 家庭训练管理
     */
    public HouseTrainingCategory selectHouseTrainingCategoryById(Long houseTrainingCategoryId);

    /**
     * 查询家庭训练管理列表
     * 
     * @param houseTrainingCategory 家庭训练管理
     * @return 家庭训练管理集合
     */
    public List<HouseTrainingCategory> selectHouseTrainingCategoryList(HouseTrainingCategory houseTrainingCategory);

    /**
     * 新增家庭训练管理
     * 
     * @param houseTrainingCategory 家庭训练管理
     * @return 结果
     */
    public int insertHouseTrainingCategory(HouseTrainingCategory houseTrainingCategory);

    /**
     * 修改家庭训练管理
     * 
     * @param houseTrainingCategory 家庭训练管理
     * @return 结果
     */
    public int updateHouseTrainingCategory(HouseTrainingCategory houseTrainingCategory);

    /**
     * 批量删除家庭训练管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHouseTrainingCategoryByIds(String ids);

    /**
     * 删除家庭训练管理信息
     * 
     * @param houseTrainingCategoryId 家庭训练管理ID
     * @return 结果
     */
    public int deleteHouseTrainingCategoryById(Long houseTrainingCategoryId);
    /**
     * 查询家庭训练管理分类列表
     *
     * @return 所有家庭训练管理分类信息
     */
    List<HouseTrainingCategory> selectHouseTrainingCategoryVoList(HouseTrainingCategory houseTrainingCategory);

    StringBuffer selectHouseTrainingAncestorsById(Long informationCategoryId,StringBuffer buf);
}
