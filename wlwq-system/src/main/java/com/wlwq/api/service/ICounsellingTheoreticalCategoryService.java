package com.wlwq.api.service;

import java.util.List;
import com.wlwq.api.domain.CounsellingTheoreticalCategory;

/**
 * 理论体系类目Service接口
 * 
 * @author wwb
 * @date 2023-05-18
 */
public interface ICounsellingTheoreticalCategoryService {
    /**
     * 查询理论体系类目
     * 
     * @param counsellingTheoreticalCategoryId 理论体系类目ID
     * @return 理论体系类目
     */
    public CounsellingTheoreticalCategory selectCounsellingTheoreticalCategoryById(String counsellingTheoreticalCategoryId);

    /**
     * 查询理论体系类目列表
     * 
     * @param counsellingTheoreticalCategory 理论体系类目
     * @return 理论体系类目集合
     */
    public List<CounsellingTheoreticalCategory> selectCounsellingTheoreticalCategoryList(CounsellingTheoreticalCategory counsellingTheoreticalCategory);

    /**
     * 新增理论体系类目
     * 
     * @param counsellingTheoreticalCategory 理论体系类目
     * @return 结果
     */
    public int insertCounsellingTheoreticalCategory(CounsellingTheoreticalCategory counsellingTheoreticalCategory);

    /**
     * 修改理论体系类目
     * 
     * @param counsellingTheoreticalCategory 理论体系类目
     * @return 结果
     */
    public int updateCounsellingTheoreticalCategory(CounsellingTheoreticalCategory counsellingTheoreticalCategory);

    /**
     * 批量删除理论体系类目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCounsellingTheoreticalCategoryByIds(String ids);

    /**
     * 删除理论体系类目信息
     * 
     * @param counsellingTheoreticalCategoryId 理论体系类目ID
     * @return 结果
     */
    public int deleteCounsellingTheoreticalCategoryById(String counsellingTheoreticalCategoryId);
}
