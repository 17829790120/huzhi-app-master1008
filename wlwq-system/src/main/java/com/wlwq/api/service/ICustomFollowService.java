package com.wlwq.api.service;

import java.text.ParseException;
import java.util.List;
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.resultVO.CustomFollowListVO;

/**
 * 客户跟进Service接口
 * 
 * @author wlwq
 * @date 2023-06-02
 */
public interface ICustomFollowService {
    /**
     * 查询客户跟进
     * 
     * @param customFollowId 客户跟进ID
     * @return 客户跟进
     */
    public CustomFollow selectCustomFollowById(String customFollowId);

    /**
     * 查询客户跟进列表
     * 
     * @param customFollow 客户跟进
     * @return 客户跟进集合
     */
    public List<CustomFollow> selectCustomFollowList(CustomFollow customFollow);

    /**
     * 新增客户跟进
     * 
     * @param customFollow 客户跟进
     * @return 结果
     */
    public int insertCustomFollow(CustomFollow customFollow) throws ParseException;

    /**
     * 修改客户跟进
     * 
     * @param customFollow 客户跟进
     * @return 结果
     */
    public int updateCustomFollow(CustomFollow customFollow);

    /**
     * 批量删除客户跟进
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCustomFollowByIds(String ids);

    /**
     * 删除客户跟进信息
     * 
     * @param customFollowId 客户跟进ID
     * @return 结果
     */
    public int deleteCustomFollowById(String customFollowId);

    /**
     * 查询列表
     */
    List<CustomFollowListVO> findCustomFollowListVO(String companyId,String keyword);

}
