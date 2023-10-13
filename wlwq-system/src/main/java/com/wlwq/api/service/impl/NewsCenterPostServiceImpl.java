package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.NewsCenterPostMapper;
import com.wlwq.api.domain.NewsCenterPost;
import com.wlwq.api.service.INewsCenterPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 新闻中心资讯Service业务层处理
 *
 * @author wwb
 * @date 2023-05-06
 */
@Service
public class NewsCenterPostServiceImpl implements INewsCenterPostService {

    @Autowired
    private NewsCenterPostMapper newsCenterPostMapper;

    /**
     * 查询新闻中心资讯
     *
     * @param newsCenterPostId 新闻中心资讯ID
     * @return 新闻中心资讯
     */
    @Override
    public NewsCenterPost selectNewsCenterPostById(Long newsCenterPostId) {
        return newsCenterPostMapper.selectNewsCenterPostById(newsCenterPostId);
    }

    /**
     * 查询新闻中心资讯列表
     *
     * @param newsCenterPost 新闻中心资讯
     * @return 新闻中心资讯
     */
    @Override
    public List<NewsCenterPost> selectNewsCenterPostList(NewsCenterPost newsCenterPost) {
        return newsCenterPostMapper.selectNewsCenterPostList(newsCenterPost);
    }

    /**
     * 新增新闻中心资讯
     *
     * @param newsCenterPost 新闻中心资讯
     * @return 结果
     */
    @Override
    public int insertNewsCenterPost(NewsCenterPost newsCenterPost) {
        newsCenterPost.setCreateTime(DateUtils.getNowDate());
        return newsCenterPostMapper.insertNewsCenterPost(newsCenterPost);
    }

    /**
     * 修改新闻中心资讯
     *
     * @param newsCenterPost 新闻中心资讯
     * @return 结果
     */
    @Override
    public int updateNewsCenterPost(NewsCenterPost newsCenterPost) {
        newsCenterPost.setUpdateTime(DateUtils.getNowDate());
        return newsCenterPostMapper.updateNewsCenterPost(newsCenterPost);
    }

    /**
     * 删除新闻中心资讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNewsCenterPostByIds(String ids) {
        return newsCenterPostMapper.deleteNewsCenterPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除新闻中心资讯信息
     *
     * @param newsCenterPostId 新闻中心资讯ID
     * @return 结果
     */
    @Override
    public int deleteNewsCenterPostById(Long newsCenterPostId) {
        return newsCenterPostMapper.deleteNewsCenterPostById(newsCenterPostId);
    }
}
