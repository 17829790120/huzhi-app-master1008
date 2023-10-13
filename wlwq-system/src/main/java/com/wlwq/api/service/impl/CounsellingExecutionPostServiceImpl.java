package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CounsellingExecutionPostMapper;
import com.wlwq.api.domain.CounsellingExecutionPost;
import com.wlwq.api.service.ICounsellingExecutionPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 辅导实施资讯Service业务层处理
 *
 * @author wwb
 * @date 2023-05-18
 */
@Service
public class CounsellingExecutionPostServiceImpl implements ICounsellingExecutionPostService {

    @Autowired
    private CounsellingExecutionPostMapper counsellingExecutionPostMapper;

    /**
     * 查询辅导实施资讯
     *
     * @param counsellingExecutionPostId 辅导实施资讯ID
     * @return 辅导实施资讯
     */
    @Override
    public CounsellingExecutionPost selectCounsellingExecutionPostById(String counsellingExecutionPostId) {
        return counsellingExecutionPostMapper.selectCounsellingExecutionPostById(counsellingExecutionPostId);
    }

    /**
     * 查询辅导实施资讯列表
     *
     * @param counsellingExecutionPost 辅导实施资讯
     * @return 辅导实施资讯
     */
    @Override
    public List<CounsellingExecutionPost> selectCounsellingExecutionPostList(CounsellingExecutionPost counsellingExecutionPost) {
        return counsellingExecutionPostMapper.selectCounsellingExecutionPostList(counsellingExecutionPost);
    }

    /**
     * 新增辅导实施资讯
     *
     * @param counsellingExecutionPost 辅导实施资讯
     * @return 结果
     */
    @Override
    public int insertCounsellingExecutionPost(CounsellingExecutionPost counsellingExecutionPost) {
        counsellingExecutionPost.setCounsellingExecutionPostId(IdUtil.getSnowflake(1, 1).nextIdStr());
        counsellingExecutionPost.setCreateTime(DateUtils.getNowDate());
        return counsellingExecutionPostMapper.insertCounsellingExecutionPost(counsellingExecutionPost);
    }

    /**
     * 修改辅导实施资讯
     *
     * @param counsellingExecutionPost 辅导实施资讯
     * @return 结果
     */
    @Override
    public int updateCounsellingExecutionPost(CounsellingExecutionPost counsellingExecutionPost) {
        counsellingExecutionPost.setUpdateTime(DateUtils.getNowDate());
        return counsellingExecutionPostMapper.updateCounsellingExecutionPost(counsellingExecutionPost);
    }

    /**
     * 删除辅导实施资讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingExecutionPostByIds(String ids) {
        return counsellingExecutionPostMapper.deleteCounsellingExecutionPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除辅导实施资讯信息
     *
     * @param counsellingExecutionPostId 辅导实施资讯ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingExecutionPostById(String counsellingExecutionPostId) {
        return counsellingExecutionPostMapper.deleteCounsellingExecutionPostById(counsellingExecutionPostId);
    }
}
