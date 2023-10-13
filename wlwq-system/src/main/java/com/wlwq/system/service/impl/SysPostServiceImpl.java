package com.wlwq.system.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.service.IApiAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.common.constant.UserConstants;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.system.domain.SysPost;
import com.wlwq.system.mapper.SysPostMapper;
import com.wlwq.system.mapper.SysUserPostMapper;
import com.wlwq.system.service.ISysPostService;

/**
 * 岗位信息 服务层处理
 *
 * @author wlwq
 */
@Service
public class SysPostServiceImpl implements ISysPostService {
    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private IApiAccountService accountService;

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return postMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        return postMapper.selectPostAll();
    }

    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostsByUserId(Long userId) {
        List<SysPost> userPosts = postMapper.selectPostsByUserId(userId);
        List<SysPost> posts = postMapper.selectPostAll();
        for (SysPost post : posts) {
            for (SysPost userRole : userPosts) {
                if (post.getPostId().longValue() == userRole.getPostId().longValue()) {
                    post.setFlag(true);
                    break;
                }
            }
        }
        return posts;
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId) {
        return postMapper.selectPostById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deletePostByIds(String ids) throws BusinessException {
        Long[] postIds = Convert.toLongArray(ids);
        for (Long postId : postIds) {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(SysPost post) {
        return postMapper.insertPost(post);
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(SysPost post) {
        return postMapper.updatePost(post);
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId) {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostNameUnique(post);
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue() && post.getPostType() == 2) {
            return UserConstants.POST_NAME_NOT_UNIQUE;
        }
        return UserConstants.POST_NAME_UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.POST_CODE_NOT_UNIQUE;
        }
        return UserConstants.POST_CODE_UNIQUE;
    }

    /**
     * 查询岗位信息
     *
     * @param ids 岗位id集合
     * @return 结果
     */
    @Override
    public List<SysPost> selectPostByIds(String[] ids) {
        return postMapper.selectPostByIds(ids);
    }


    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List selectWebPostList(SysPost post){
        List<SysPost> postList = postMapper.selectPostList(post);
        if("0".equals(post.getAccountId())){
            //删除集合中某一元素值
            Iterator<SysPost> sListIterator = postList.iterator();
            while (sListIterator.hasNext()) {
                SysPost sysPost = sListIterator.next();
                // 1：普通岗 2：领导岗
                if(sysPost.getPostType() == 2){
                    // 查询岗位及是否已占用
                    int count = accountService.selectApiAccountCount(ApiAccount.builder().companyId(post.getCompanyId()).postId(sysPost.getPostId()).build());
                    if(count > 0){
                        // 剔除岗位表的岗位
                        sListIterator.remove();
                    }
                }
            }
        }
        return postList;
    }

    /**
     * 查询岗位信息集合(除自己外)
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectWebOtherPostList(SysPost post){
        List<SysPost> postList = postMapper.selectPostList(post);
        //删除集合中某一元素值
        Iterator<SysPost> sListIterator = postList.iterator();
        while (sListIterator.hasNext()) {
            SysPost sysPost = sListIterator.next();
            // 1：普通岗 2：领导岗
            if(sysPost.getPostType() == 2){
                // 查询岗位及是否已占用
                int count = accountService.selectApiAccountCount(ApiAccount.builder().companyId(post.getCompanyId()).accountId(post.getAccountId()).postId(sysPost.getPostId()).build());
                if(count > 0){
                    // 剔除岗位表的岗位
                    sListIterator.remove();
                }
            }
        }
        return postList;
    }

    /**
     * 只查询一条信息
     * @param post
     * @return
     */
    @Override
    public SysPost checkPost(SysPost post){
        return postMapper.checkPost(post);
    }
}
