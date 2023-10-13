package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.CounsellingExecutionCategory;

/**
 * 辅导实施类目Mapper接口
 *
 * @author wwb
 * @date 2023-05-18
 */
public interface CounsellingExecutionCategoryMapper {
    /**
     * 查询辅导实施类目
     *
     * @param counsellingExecutionCategoryId 辅导实施类目ID
     * @return 辅导实施类目
     */
    public CounsellingExecutionCategory selectCounsellingExecutionCategoryById(String counsellingExecutionCategoryId);

    /**
     * 查询辅导实施类目列表
     *
     * @param counsellingExecutionCategory 辅导实施类目
     * @return 辅导实施类目集合
     */
    public List<CounsellingExecutionCategory> selectCounsellingExecutionCategoryList(CounsellingExecutionCategory counsellingExecutionCategory);

    /**
     * 新增辅导实施类目
     *
     * @param counsellingExecutionCategory 辅导实施类目
     * @return 结果
     */
    public int insertCounsellingExecutionCategory(CounsellingExecutionCategory counsellingExecutionCategory);

    /**
     * 修改辅导实施类目
     *
     * @param counsellingExecutionCategory 辅导实施类目
     * @return 结果
     */
    public int updateCounsellingExecutionCategory(CounsellingExecutionCategory counsellingExecutionCategory);

    /**
     * 删除辅导实施类目
     *
     * @param counsellingExecutionCategoryId 辅导实施类目ID
     * @return 结果
     */
    public int deleteCounsellingExecutionCategoryById(String counsellingExecutionCategoryId);

    /**
     * 批量删除辅导实施类目
     *
     * @param counsellingExecutionCategoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCounsellingExecutionCategoryByIds(String[] counsellingExecutionCategoryIds);
}
