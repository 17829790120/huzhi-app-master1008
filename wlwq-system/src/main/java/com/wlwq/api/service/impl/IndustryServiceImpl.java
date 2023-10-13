package com.wlwq.api.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.IndustryMapper;
import com.wlwq.api.domain.Industry;
import com.wlwq.api.service.IIndustryService;
import com.wlwq.common.core.text.Convert;

/**
 * 行业信息Service业务层处理
 *
 * @author wwb
 * @date 2023-05-23
 */
@Service
public class IndustryServiceImpl implements IIndustryService {

    @Autowired
    private IndustryMapper industryMapper;

    /**
     * 查询行业信息
     *
     * @param id 行业信息ID
     * @return 行业信息
     */
    @Override
    public Industry selectIndustryById(Long id) {
        return industryMapper.selectIndustryById(id);
    }

    /**
     * 查询行业信息列表
     *
     * @param industry 行业信息
     * @return 行业信息
     */
    @Override
    public List<Industry> selectIndustryList(Industry industry) {
        return industryMapper.selectIndustryList(industry);
    }

    /**
     * 新增行业信息
     *
     * @param industry 行业信息
     * @return 结果
     */
    @Override
    public int insertIndustry(Industry industry) {
        industry.setCreateTime(DateUtils.getNowDate());
        return industryMapper.insertIndustry(industry);
    }

    /**
     * 修改行业信息
     *
     * @param industry 行业信息
     * @return 结果
     */
    @Override
    public int updateIndustry(Industry industry) {
        return industryMapper.updateIndustry(industry);
    }

    /**
     * 删除行业信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteIndustryByIds(String ids) {
        return industryMapper.deleteIndustryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除行业信息信息
     *
     * @param id 行业信息ID
     * @return 结果
     */
    @Override
    public int deleteIndustryById(Long id) {
        return industryMapper.deleteIndustryById(id);
    }

    /**
     * 查询行业信息树列表
     *
     * @return 所有行业信息信息
     */
    @Override
    public List<Ztree> selectIndustryTree() {
        List<Industry> industryList = industryMapper.selectIndustryList(new Industry());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (Industry industry : industryList) {
            Ztree ztree = new Ztree();
            ztree.setId(industry.getId());
            ztree.setpId(industry.getParentId());
            ztree.setName(industry.getName());
            ztree.setTitle(industry.getName());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
