package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.HouseTrainingPostMapper;
import com.wlwq.api.domain.HouseTrainingPost;
import com.wlwq.api.service.IHouseTrainingPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 家庭训练资讯Service业务层处理
 *
 * @author wwb
 * @date 2023-05-15
 */
@Service
public class HouseTrainingPostServiceImpl implements IHouseTrainingPostService {

    @Autowired
    private HouseTrainingPostMapper houseTrainingPostMapper;

    /**
     * 查询家庭训练资讯
     *
     * @param houseTrainingPostId 家庭训练资讯ID
     * @return 家庭训练资讯
     */
    @Override
    public HouseTrainingPost selectHouseTrainingPostById(Long houseTrainingPostId) {
        return houseTrainingPostMapper.selectHouseTrainingPostById(houseTrainingPostId);
    }

    /**
     * 查询家庭训练资讯列表
     *
     * @param houseTrainingPost 家庭训练资讯
     * @return 家庭训练资讯
     */
    @DataScope(deptAlias = "n")
    @Override
    public List<HouseTrainingPost> selectHouseTrainingPostList(HouseTrainingPost houseTrainingPost) {
        return houseTrainingPostMapper.selectHouseTrainingPostList(houseTrainingPost);
    }

    /**
     * 新增家庭训练资讯
     *
     * @param houseTrainingPost 家庭训练资讯
     * @return 结果
     */
    @Override
    public int insertHouseTrainingPost(HouseTrainingPost houseTrainingPost) {
        houseTrainingPost.setCreateTime(DateUtils.getNowDate());
        return houseTrainingPostMapper.insertHouseTrainingPost(houseTrainingPost);
    }

    /**
     * 修改家庭训练资讯
     *
     * @param houseTrainingPost 家庭训练资讯
     * @return 结果
     */
    @Override
    public int updateHouseTrainingPost(HouseTrainingPost houseTrainingPost) {
        houseTrainingPost.setUpdateTime(DateUtils.getNowDate());
        return houseTrainingPostMapper.updateHouseTrainingPost(houseTrainingPost);
    }

    /**
     * 删除家庭训练资讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHouseTrainingPostByIds(String ids) {
        return houseTrainingPostMapper.deleteHouseTrainingPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除家庭训练资讯信息
     *
     * @param houseTrainingPostId 家庭训练资讯ID
     * @return 结果
     */
    @Override
    public int deleteHouseTrainingPostById(Long houseTrainingPostId) {
        return houseTrainingPostMapper.deleteHouseTrainingPostById(houseTrainingPostId);
    }
}
