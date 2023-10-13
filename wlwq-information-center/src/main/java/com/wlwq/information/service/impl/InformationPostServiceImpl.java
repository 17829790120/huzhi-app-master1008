package com.wlwq.information.service.impl;

import java.util.List;

import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.information.mapper.InformationPostMapper;
import com.wlwq.information.domain.InformationPost;
import com.wlwq.information.service.IInformationPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯文章Service业务层处理
 *
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Service
public class InformationPostServiceImpl implements IInformationPostService {
    @Autowired
    private InformationPostMapper informationPostMapper;

    /**
     * 查询资讯文章
     *
     * @param informationPostId 资讯文章ID
     * @return 资讯文章
     */
    @Override
    public InformationPost selectInformationPostById(Long informationPostId) {
        return informationPostMapper.selectInformationPostById(informationPostId);
    }

    /**
     * 查询资讯文章列表
     *
     * @param informationPost 资讯文章
     * @return 资讯文章
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<InformationPost> selectInformationPostList(InformationPost informationPost) {
        return informationPostMapper.selectInformationPostList(informationPost);
    }

    /**
     * 新增资讯文章
     *
     * @param informationPost 资讯文章
     * @return 结果
     */
    @Override
    public int insertInformationPost(InformationPost informationPost) {
        informationPost.setCreateTime(DateUtils.getNowDate());
        return informationPostMapper.insertInformationPost(informationPost);
    }

    /**
     * 修改资讯文章
     *
     * @param informationPost 资讯文章
     * @return 结果
     */
    @Override
    public int updateInformationPost(InformationPost informationPost) {
        informationPost.setUpdateTime(DateUtils.getNowDate());
        return informationPostMapper.updateInformationPost(informationPost);
    }

    /**
     * 删除资讯文章对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationPostByIds(String ids) {
        return informationPostMapper.deleteInformationPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯文章信息
     *
     * @param informationPostId 资讯文章ID
     * @return 结果
     */
    @Override
    public int deleteInformationPostById(Long informationPostId) {
        return informationPostMapper.deleteInformationPostById(informationPostId);
    }

    /**
     * api查询资讯文章列表
     *
     * @param informationPost 资讯文章
     * @return 资讯文章集合
     */
    @Override
    public List<InformationPost> selectApiInformationPostList(InformationPost informationPost){
        return informationPostMapper.selectApiInformationPostList(informationPost);
    }
}
