package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CounsellingTheoreticalPostMapper;
import com.wlwq.api.domain.CounsellingTheoreticalPost;
import com.wlwq.api.service.ICounsellingTheoreticalPostService;
import com.wlwq.common.core.text.Convert;

/**
 * 理论体系资讯Service业务层处理
 *
 * @author wwb
 * @date 2023-05-18
 */
@Service
public class CounsellingTheoreticalPostServiceImpl implements ICounsellingTheoreticalPostService {

    @Autowired
    private CounsellingTheoreticalPostMapper counsellingTheoreticalPostMapper;

    /**
     * 查询理论体系资讯
     *
     * @param counsellingTheoreticalPostId 理论体系资讯ID
     * @return 理论体系资讯
     */
    @Override
    public CounsellingTheoreticalPost selectCounsellingTheoreticalPostById(String counsellingTheoreticalPostId) {
        return counsellingTheoreticalPostMapper.selectCounsellingTheoreticalPostById(counsellingTheoreticalPostId);
    }

    /**
     * 查询理论体系资讯列表
     *
     * @param counsellingTheoreticalPost 理论体系资讯
     * @return 理论体系资讯
     */
    @Override
    public List<CounsellingTheoreticalPost> selectCounsellingTheoreticalPostList(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        return counsellingTheoreticalPostMapper.selectCounsellingTheoreticalPostList(counsellingTheoreticalPost);
    }

    /**
     * 新增理论体系资讯
     *
     * @param counsellingTheoreticalPost 理论体系资讯
     * @return 结果
     */
    @Override
    public int insertCounsellingTheoreticalPost(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        counsellingTheoreticalPost.setCounsellingTheoreticalPostId(IdUtil.getSnowflake(1, 1).nextIdStr());
        counsellingTheoreticalPost.setCreateTime(DateUtils.getNowDate());
        return counsellingTheoreticalPostMapper.insertCounsellingTheoreticalPost(counsellingTheoreticalPost);
    }

    /**
     * 修改理论体系资讯
     *
     * @param counsellingTheoreticalPost 理论体系资讯
     * @return 结果
     */
    @Override
    public int updateCounsellingTheoreticalPost(CounsellingTheoreticalPost counsellingTheoreticalPost) {
        counsellingTheoreticalPost.setUpdateTime(DateUtils.getNowDate());
        return counsellingTheoreticalPostMapper.updateCounsellingTheoreticalPost(counsellingTheoreticalPost);
    }

    /**
     * 删除理论体系资讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingTheoreticalPostByIds(String ids) {
        return counsellingTheoreticalPostMapper.deleteCounsellingTheoreticalPostByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除理论体系资讯信息
     *
     * @param counsellingTheoreticalPostId 理论体系资讯ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingTheoreticalPostById(String counsellingTheoreticalPostId) {
        return counsellingTheoreticalPostMapper.deleteCounsellingTheoreticalPostById(counsellingTheoreticalPostId);
    }
}
