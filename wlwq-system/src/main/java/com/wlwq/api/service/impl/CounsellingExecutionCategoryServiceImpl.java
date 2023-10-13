package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CounsellingExecutionCategoryMapper;
import com.wlwq.api.domain.CounsellingExecutionCategory;
import com.wlwq.api.service.ICounsellingExecutionCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 辅导实施类目Service业务层处理
 *
 * @author wwb
 * @date 2023-05-18
 */
@Service
public class CounsellingExecutionCategoryServiceImpl implements ICounsellingExecutionCategoryService {

    @Autowired
    private CounsellingExecutionCategoryMapper counsellingExecutionCategoryMapper;

    /**
     * 查询辅导实施类目
     *
     * @param counsellingExecutionCategoryId 辅导实施类目ID
     * @return 辅导实施类目
     */
    @Override
    public CounsellingExecutionCategory selectCounsellingExecutionCategoryById(String counsellingExecutionCategoryId) {
        return counsellingExecutionCategoryMapper.selectCounsellingExecutionCategoryById(counsellingExecutionCategoryId);
    }

    /**
     * 查询辅导实施类目列表
     *
     * @param counsellingExecutionCategory 辅导实施类目
     * @return 辅导实施类目
     */
    @Override
    public List<CounsellingExecutionCategory> selectCounsellingExecutionCategoryList(CounsellingExecutionCategory counsellingExecutionCategory) {
        return counsellingExecutionCategoryMapper.selectCounsellingExecutionCategoryList(counsellingExecutionCategory);
    }

    /**
     * 新增辅导实施类目
     *
     * @param counsellingExecutionCategory 辅导实施类目
     * @return 结果
     */
    @Override
    public int insertCounsellingExecutionCategory(CounsellingExecutionCategory counsellingExecutionCategory) {
        counsellingExecutionCategory.setCounsellingExecutionCategoryId(IdUtil.getSnowflake(1, 1).nextIdStr());
        counsellingExecutionCategory.setCreateTime(DateUtils.getNowDate());
        return counsellingExecutionCategoryMapper.insertCounsellingExecutionCategory(counsellingExecutionCategory);
    }

    /**
     * 修改辅导实施类目
     *
     * @param counsellingExecutionCategory 辅导实施类目
     * @return 结果
     */
    @Override
    public int updateCounsellingExecutionCategory(CounsellingExecutionCategory counsellingExecutionCategory) {
        return counsellingExecutionCategoryMapper.updateCounsellingExecutionCategory(counsellingExecutionCategory);
    }

    /**
     * 删除辅导实施类目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingExecutionCategoryByIds(String ids) {
        return counsellingExecutionCategoryMapper.deleteCounsellingExecutionCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除辅导实施类目信息
     *
     * @param counsellingExecutionCategoryId 辅导实施类目ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingExecutionCategoryById(String counsellingExecutionCategoryId) {
        return counsellingExecutionCategoryMapper.deleteCounsellingExecutionCategoryById(counsellingExecutionCategoryId);
    }
}
