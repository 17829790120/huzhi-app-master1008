package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.StudyInformationPostMapper;
import com.wlwq.api.domain.StudyInformationPost;
import com.wlwq.api.service.IStudyInformationPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯文章Service业务层处理
 *
 * @author web
 * @date 2023-05-16
 */
@Service
public class StudyInformationPostServiceImpl implements IStudyInformationPostService {

    @Autowired
    private StudyInformationPostMapper studyInformationPostMapper;

    /**
     * 查询资讯文章
     *
     * @param studyInformationPostId 资讯文章ID
     * @return 资讯文章
     */
    @Override
    public StudyInformationPost selectStudyInformationPostById(Long studyInformationPostId) {
        return studyInformationPostMapper.selectStudyInformationPostById(studyInformationPostId);
    }

    /**
     * 查询资讯文章列表
     *
     * @param studyInformationPost 资讯文章
     * @return 资讯文章
     */
    @Override
    public List<StudyInformationPost> selectStudyInformationPostList(StudyInformationPost studyInformationPost) {
        return studyInformationPostMapper.selectStudyInformationPostList(studyInformationPost);
    }

    /**
     * 新增资讯文章
     *
     * @param studyInformationPost 资讯文章
     * @return 结果
     */
    @Override
    public int insertStudyInformationPost(StudyInformationPost studyInformationPost) {
        studyInformationPost.setCreateTime(DateUtils.getNowDate());
        return studyInformationPostMapper.insertStudyInformationPost(studyInformationPost);
    }

    /**
     * 修改资讯文章
     *
     * @param studyInformationPost 资讯文章
     * @return 结果
     */
    @Override
    public int updateStudyInformationPost(StudyInformationPost studyInformationPost) {
        studyInformationPost.setUpdateTime(DateUtils.getNowDate());
        return studyInformationPostMapper.updateStudyInformationPost(studyInformationPost);
    }

    /**
     * 删除资讯文章对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStudyInformationPostByIds(String ids) {
        return studyInformationPostMapper.deleteStudyInformationPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯文章信息
     *
     * @param studyInformationPostId 资讯文章ID
     * @return 结果
     */
    @Override
    public int deleteStudyInformationPostById(Long studyInformationPostId) {
        return studyInformationPostMapper.deleteStudyInformationPostById(studyInformationPostId);
    }
}
