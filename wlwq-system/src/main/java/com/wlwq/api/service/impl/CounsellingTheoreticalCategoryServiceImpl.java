package com.wlwq.api.service.impl;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CounsellingTheoreticalCategoryMapper;
import com.wlwq.api.domain.CounsellingTheoreticalCategory;
import com.wlwq.api.service.ICounsellingTheoreticalCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 理论体系类目Service业务层处理
 *
 * @author wwb
 * @date 2023-05-18
 */
@Service
public class CounsellingTheoreticalCategoryServiceImpl implements ICounsellingTheoreticalCategoryService {

    @Autowired
    private CounsellingTheoreticalCategoryMapper counsellingTheoreticalCategoryMapper;

    /**
     * 查询理论体系类目
     *
     * @param counsellingTheoreticalCategoryId 理论体系类目ID
     * @return 理论体系类目
     */
    @Override
    public CounsellingTheoreticalCategory selectCounsellingTheoreticalCategoryById(String counsellingTheoreticalCategoryId) {
        return counsellingTheoreticalCategoryMapper.selectCounsellingTheoreticalCategoryById(counsellingTheoreticalCategoryId);
    }

    /**
     * 查询理论体系类目列表
     *
     * @param counsellingTheoreticalCategory 理论体系类目
     * @return 理论体系类目
     */
    @Override
    public List<CounsellingTheoreticalCategory> selectCounsellingTheoreticalCategoryList(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        return counsellingTheoreticalCategoryMapper.selectCounsellingTheoreticalCategoryList(counsellingTheoreticalCategory);
    }

    /**
     * 新增理论体系类目
     *
     * @param counsellingTheoreticalCategory 理论体系类目
     * @return 结果
     */
    @Override
    public int insertCounsellingTheoreticalCategory(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        counsellingTheoreticalCategory.setCounsellingTheoreticalCategoryId(IdUtil.getSnowflake(1, 1).nextIdStr());
        counsellingTheoreticalCategory.setCreateTime(DateUtils.getNowDate());
        return counsellingTheoreticalCategoryMapper.insertCounsellingTheoreticalCategory(counsellingTheoreticalCategory);
    }

    /**
     * 修改理论体系类目
     *
     * @param counsellingTheoreticalCategory 理论体系类目
     * @return 结果
     */
    @Override
    public int updateCounsellingTheoreticalCategory(CounsellingTheoreticalCategory counsellingTheoreticalCategory) {
        return counsellingTheoreticalCategoryMapper.updateCounsellingTheoreticalCategory(counsellingTheoreticalCategory);
    }

    /**
     * 删除理论体系类目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingTheoreticalCategoryByIds(String ids) {
        return counsellingTheoreticalCategoryMapper.deleteCounsellingTheoreticalCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除理论体系类目信息
     *
     * @param counsellingTheoreticalCategoryId 理论体系类目ID
     * @return 结果
     */
    @Override
    public int deleteCounsellingTheoreticalCategoryById(String counsellingTheoreticalCategoryId) {
        return counsellingTheoreticalCategoryMapper.deleteCounsellingTheoreticalCategoryById(counsellingTheoreticalCategoryId);
    }
}
