package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.HouseTrainingCategoryMapper;
import com.wlwq.api.domain.HouseTrainingCategory;
import com.wlwq.api.service.IHouseTrainingCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 家庭训练管理Service业务层处理
 *
 * @author wwb
 * @date 2023-05-15
 */
@Service
public class HouseTrainingCategoryServiceImpl implements IHouseTrainingCategoryService {

    @Autowired
    private HouseTrainingCategoryMapper houseTrainingCategoryMapper;

    /**
     * 查询家庭训练管理
     *
     * @param houseTrainingCategoryId 家庭训练管理ID
     * @return 家庭训练管理
     */
    @Override
    public HouseTrainingCategory selectHouseTrainingCategoryById(Long houseTrainingCategoryId) {
        return houseTrainingCategoryMapper.selectHouseTrainingCategoryById(houseTrainingCategoryId);
    }

    /**
     * 查询家庭训练管理列表
     *
     * @param houseTrainingCategory 家庭训练管理
     * @return 家庭训练管理
     */
    @Override
    @DataScope(deptAlias = "d",userAlias = "u")
    public List<HouseTrainingCategory> selectHouseTrainingCategoryList(HouseTrainingCategory houseTrainingCategory) {
        return houseTrainingCategoryMapper.selectHouseTrainingCategoryList(houseTrainingCategory);
    }

    /**
     * 新增家庭训练管理
     *
     * @param houseTrainingCategory 家庭训练管理
     * @return 结果
     */
    @Override
    public int insertHouseTrainingCategory(HouseTrainingCategory houseTrainingCategory) {
        houseTrainingCategory.setCreateTime(DateUtils.getNowDate());
        return houseTrainingCategoryMapper.insertHouseTrainingCategory(houseTrainingCategory);
    }

    /**
     * 修改家庭训练管理
     *
     * @param houseTrainingCategory 家庭训练管理
     * @return 结果
     */
    @Override
    public int updateHouseTrainingCategory(HouseTrainingCategory houseTrainingCategory) {
        houseTrainingCategory.setUpdateTime(DateUtils.getNowDate());
        return houseTrainingCategoryMapper.updateHouseTrainingCategory(houseTrainingCategory);
    }

    /**
     * 删除家庭训练管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHouseTrainingCategoryByIds(String ids) {
        return houseTrainingCategoryMapper.deleteHouseTrainingCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除家庭训练管理信息
     *
     * @param houseTrainingCategoryId 家庭训练管理ID
     * @return 结果
     */
    @Override
    public int deleteHouseTrainingCategoryById(Long houseTrainingCategoryId) {
        return houseTrainingCategoryMapper.deleteHouseTrainingCategoryById(houseTrainingCategoryId);
    }
    /**
     * 查询家庭训练管理分类列表
     *
     * @return 所有家庭训练管理分类信息
     */
    @Override
    public List<HouseTrainingCategory> selectHouseTrainingCategoryVoList(HouseTrainingCategory houseTrainingCategory) {
        return houseTrainingCategoryMapper.selectHouseTrainingCategoryVoList(houseTrainingCategory);
    }

    @Override
    public StringBuffer selectHouseTrainingAncestorsById(Long informationCategoryId, StringBuffer buf) {
        HouseTrainingCategory houseTrainingCategory = houseTrainingCategoryMapper.selectHouseTrainingCategoryById(informationCategoryId);
        if(houseTrainingCategory !=null && houseTrainingCategory.getParentId() == 0){
            return buf;
        }else if(houseTrainingCategory !=null){
            buf.append(houseTrainingCategory.getParentId()+",");
            selectHouseTrainingAncestorsById(houseTrainingCategory.getParentId(),buf);
        }
        return buf;
    }

}
