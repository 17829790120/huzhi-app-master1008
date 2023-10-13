package com.wlwq.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wlwq.api.domain.HouseTrainingCategory;
import com.wlwq.api.resultVO.SysDictTypeVO;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.HumpToGlideUtils;
import com.wlwq.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.NewsCenterCategoryMapper;
import com.wlwq.api.domain.NewsCenterCategory;
import com.wlwq.api.service.INewsCenterCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 新闻中心Service业务层处理
 *
 * @author wwb
 * @date 2023-05-06
 */
@Service
public class NewsCenterCategoryServiceImpl implements INewsCenterCategoryService {

    @Autowired
    private NewsCenterCategoryMapper newsCenterCategoryMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    /**
     * 查询新闻中心
     *
     * @param newsCenterCategoryId 新闻中心ID
     * @return 新闻中心
     */
    @Override
    public NewsCenterCategory selectNewsCenterCategoryById(Long newsCenterCategoryId) {
        return newsCenterCategoryMapper.selectNewsCenterCategoryById(newsCenterCategoryId);
    }

    /**
     * 查询新闻中心列表
     *
     * @param newsCenterCategory 新闻中心
     * @return 新闻中心
     */
    @Override
    @DataScope(deptAlias = "d",userAlias = "u")
    public List<NewsCenterCategory> selectNewsCenterCategoryList(NewsCenterCategory newsCenterCategory) {
        return newsCenterCategoryMapper.selectNewsCenterCategoryList(newsCenterCategory);
    }

    /**
     * 新增新闻中心
     *
     * @param newsCenterCategory 新闻中心
     * @return 结果
     */
    @Override
    public int insertNewsCenterCategory(NewsCenterCategory newsCenterCategory) {
        newsCenterCategory.setCreateTime(DateUtils.getNowDate());
        return newsCenterCategoryMapper.insertNewsCenterCategory(newsCenterCategory);
    }

    /**
     * 修改新闻中心
     *
     * @param newsCenterCategory 新闻中心
     * @return 结果
     */
    @Override
    public int updateNewsCenterCategory(NewsCenterCategory newsCenterCategory) {
        newsCenterCategory.setUpdateTime(DateUtils.getNowDate());
        return newsCenterCategoryMapper.updateNewsCenterCategory(newsCenterCategory);
    }

    /**
     * 删除新闻中心对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNewsCenterCategoryByIds(String ids) {
        return newsCenterCategoryMapper.deleteNewsCenterCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除新闻中心信息
     *
     * @param newsCenterCategoryId 新闻中心ID
     * @return 结果
     */
    @Override
    public int deleteNewsCenterCategoryById(Long newsCenterCategoryId) {
        return newsCenterCategoryMapper.deleteNewsCenterCategoryById(newsCenterCategoryId);
    }
    /**
     * 查询新闻中心列表
     *
     * @param newsCenterCategory 新闻中心
     * @return 新闻中心集合
     */
    @Override
    public List<NewsCenterCategory> selectNewsCenterCategoryVoList(NewsCenterCategory newsCenterCategory) {
        return newsCenterCategoryMapper.selectNewsCenterCategoryVoList(newsCenterCategory);
    }

    @Override
    public StringBuffer selectAncestorsById(Long informationCategoryId, StringBuffer buf) {
        NewsCenterCategory newsCenterCategory = newsCenterCategoryMapper.selectNewsCenterCategoryById(informationCategoryId);
        if(newsCenterCategory !=null && newsCenterCategory.getParentId() == 0){
            return buf;
        }else if(newsCenterCategory !=null){
            buf.append(newsCenterCategory.getParentId()+",");
            selectAncestorsById(newsCenterCategory.getParentId(),buf);
        }
        return buf;
    }

    @Override
    public List<SysDictTypeVO> findSysDictTypeVO(List<String> dictTypes) {
        List<String> ls = new ArrayList<>();
        ls.add(HumpToGlideUtils.teseDemo("appHotSearch"));
        List<SysDictTypeVO> sysDictTypeVO = sysDictDataMapper.findSysDictTypeVO(ls);
        return sysDictTypeVO;
    }
}
