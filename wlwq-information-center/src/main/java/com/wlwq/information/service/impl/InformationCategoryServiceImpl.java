package com.wlwq.information.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.information.mapper.InformationCategoryMapper;
import com.wlwq.information.domain.InformationCategory;
import com.wlwq.information.service.IInformationCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯分类Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-04-19
 */
@Service
public class InformationCategoryServiceImpl implements IInformationCategoryService 
{
    @Autowired
    private InformationCategoryMapper informationCategoryMapper;

    /**
     * 查询资讯分类
     * 
     * @param informationCategoryId 资讯分类ID
     * @return 资讯分类
     */
    @Override
    public InformationCategory selectInformationCategoryById(Long informationCategoryId)
    {
        return informationCategoryMapper.selectInformationCategoryById(informationCategoryId);
    }

    /**
     * 查询资讯分类列表
     * 
     * @param informationCategory 资讯分类
     * @return 资讯分类
     */
    @Override
    @DataScope(deptAlias = "d",userAlias = "u")
    public List<InformationCategory> selectInformationCategoryList(InformationCategory informationCategory)
    {
        return informationCategoryMapper.selectInformationCategoryList(informationCategory);
    }

    /**
     * 新增资讯分类
     * 
     * @param informationCategory 资讯分类
     * @return 结果
     */
    @Override
    public int insertInformationCategory(InformationCategory informationCategory)
    {
        informationCategory.setCreateTime(DateUtils.getNowDate());
        return informationCategoryMapper.insertInformationCategory(informationCategory);
    }

    /**
     * 修改资讯分类
     * 
     * @param informationCategory 资讯分类
     * @return 结果
     */
    @Override
    public int updateInformationCategory(InformationCategory informationCategory)
    {
        informationCategory.setUpdateTime(DateUtils.getNowDate());
        return informationCategoryMapper.updateInformationCategory(informationCategory);
    }

    /**
     * 删除资讯分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationCategoryByIds(String ids)
    {
        return informationCategoryMapper.deleteInformationCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯分类信息
     * 
     * @param informationCategoryId 资讯分类ID
     * @return 结果
     */
    @Override
    public int deleteInformationCategoryById(Long informationCategoryId)
    {
        // 查询下面有没有子集
        int countNumber = informationCategoryMapper.countSonNumberByParentId(informationCategoryId);
        if (countNumber > 0){
            throw new BusinessException("请先删除下级分类！");
        }
        return informationCategoryMapper.deleteInformationCategoryById(informationCategoryId);
    }

    /**
     * 查询资讯分类树列表
     * 
     * @return 所有资讯分类信息
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<Ztree> selectInformationCategoryTree()
    {
        List<InformationCategory> informationCategoryList = informationCategoryMapper.selectInformationCategoryList(new InformationCategory());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (InformationCategory informationCategory : informationCategoryList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(informationCategory.getInformationCategoryId());
            ztree.setpId(informationCategory.getParentId());
            ztree.setName(informationCategory.getInformationCategoryTitle());
            ztree.setTitle(informationCategory.getInformationCategoryTitle());
            ztrees.add(ztree);
        }
        return ztrees;
    }
    /**
     * 查询资讯分类列表
     *
     * @return 所有资讯分类信息
     */
    @Override
    public List<InformationCategory> selectInformationCategoryVoList(InformationCategory informationCategory) {
        return informationCategoryMapper.selectInformationCategoryVoList(informationCategory);
    }

    @Override
    public StringBuffer selectAncestorsById(Long informationCategoryId, StringBuffer buf) {
        InformationCategory informationCategory = informationCategoryMapper.selectInformationCategoryById(informationCategoryId);
        if(informationCategory !=null && informationCategory.getParentId() == 0){
            return buf;
        }else if(informationCategory !=null){
            buf.append(informationCategory.getParentId()+",");
            selectAncestorsById(informationCategory.getParentId(),buf);
        }
        return buf;
    }
}
