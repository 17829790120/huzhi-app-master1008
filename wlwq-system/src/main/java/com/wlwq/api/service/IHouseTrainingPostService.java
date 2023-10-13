package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.HouseTrainingPost;

/**
 * 家庭训练资讯Service接口
 * 
 * @author wwb
 * @date 2023-05-15
 */
public interface IHouseTrainingPostService {
    /**
     * 查询家庭训练资讯
     * 
     * @param houseTrainingPostId 家庭训练资讯ID
     * @return 家庭训练资讯
     */
    public HouseTrainingPost selectHouseTrainingPostById(Long houseTrainingPostId);

    /**
     * 查询家庭训练资讯列表
     * 
     * @param houseTrainingPost 家庭训练资讯
     * @return 家庭训练资讯集合
     */
    public List<HouseTrainingPost> selectHouseTrainingPostList(HouseTrainingPost houseTrainingPost);

    /**
     * 新增家庭训练资讯
     * 
     * @param houseTrainingPost 家庭训练资讯
     * @return 结果
     */
    public int insertHouseTrainingPost(HouseTrainingPost houseTrainingPost);

    /**
     * 修改家庭训练资讯
     * 
     * @param houseTrainingPost 家庭训练资讯
     * @return 结果
     */
    public int updateHouseTrainingPost(HouseTrainingPost houseTrainingPost);

    /**
     * 批量删除家庭训练资讯
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHouseTrainingPostByIds(String ids);

    /**
     * 删除家庭训练资讯信息
     * 
     * @param houseTrainingPostId 家庭训练资讯ID
     * @return 结果
     */
    public int deleteHouseTrainingPostById(Long houseTrainingPostId);
}
