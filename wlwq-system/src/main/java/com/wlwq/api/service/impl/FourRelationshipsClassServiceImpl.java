package com.wlwq.api.service.impl;

import com.wlwq.api.domain.FourRelationshipsClass;
import com.wlwq.api.mapper.FourRelationshipsClassMapper;
import com.wlwq.api.service.IFourRelationshipsClassService;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 四类关系分类Service业务层处理
 * 
 * @author dxy
 */
@Service
public class FourRelationshipsClassServiceImpl implements IFourRelationshipsClassService {

    @Autowired
    private FourRelationshipsClassMapper fourRelationshipsClassMapper;

    /**
     * 查询四类关系分类
     * 
     * @param fourRelationshipsClassId 四类关系分类ID
     * @return 四类关系分类
     */
    @Override
    public FourRelationshipsClass selectFourRelationshipsClassById(Long fourRelationshipsClassId) {
        return fourRelationshipsClassMapper.selectFourRelationshipsClassById(fourRelationshipsClassId);
    }

    /**
     * 查询四类关系分类列表
     *
     * @param fourRelationshipsClass 四类关系分类
     * @return 四类关系分类
     */
    @Override
    public List<FourRelationshipsClass> selectFourRelationshipsClassList(FourRelationshipsClass fourRelationshipsClass) {
        return fourRelationshipsClassMapper.selectFourRelationshipsClassList(fourRelationshipsClass);
    }

    /**
     * 新增四类关系分类
     *
     * @param fourRelationshipsClass 四类关系分类
     * @return 结果
     */
    @Override
    public int insertFourRelationshipsClass(FourRelationshipsClass fourRelationshipsClass) {
        fourRelationshipsClass.setCreateTime(DateUtils.getNowDate());
        return fourRelationshipsClassMapper.insertFourRelationshipsClass(fourRelationshipsClass);
    }

    /**
     * 修改四类关系分类
     *
     * @param fourRelationshipsClass 四类关系分类
     * @return 结果
     */
    @Override
    public int updateFourRelationshipsClass(FourRelationshipsClass fourRelationshipsClass) {
        fourRelationshipsClass.setUpdateTime(DateUtils.getNowDate());
        return fourRelationshipsClassMapper.updateFourRelationshipsClass(fourRelationshipsClass);
    }

    /**
     * 删除四类关系分类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFourRelationshipsClassByIds(String ids) {
        return fourRelationshipsClassMapper.deleteFourRelationshipsClassByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除四类关系分类信息
     *
     * @param fourRelationshipsClassId 六大架构分类ID
     * @return 结果
     */
    @Override
    public int deleteFourRelationshipsClassById(Long fourRelationshipsClassId) {
        return fourRelationshipsClassMapper.deleteFourRelationshipsClassById(fourRelationshipsClassId);
    }

    /**
     * 加载服务分类树列表
     * @return
     */
    @Override
    public List<Ztree> selectStoreClassTree() {
        List<FourRelationshipsClass> relationshipsClassList = fourRelationshipsClassMapper.selectFourRelationshipsClassList(new FourRelationshipsClass());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        // 主目录
        Ztree mainTree = new Ztree();
        mainTree.setId(0L);
        mainTree.setName("主目录");
        mainTree.setTitle("主目录");
        ztrees.add(mainTree);
        for (FourRelationshipsClass storeClass : relationshipsClassList) {
            Ztree ztree = new Ztree();
            ztree.setId(storeClass.getFourRelationshipsClassId());
            ztree.setpId(storeClass.getParentId());
            ztree.setName(storeClass.getClassName());
            ztree.setTitle(storeClass.getClassName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * App四类关系列表
     * @param fourRelationshipsClass
     * @return
     */
    @Override
    public List<FourRelationshipsClass> selectFourRelationshipsClassVoList(FourRelationshipsClass fourRelationshipsClass) {
        return fourRelationshipsClassMapper.selectFourRelationshipsClassVoList(fourRelationshipsClass);
    }

    /**
     * App四类关系列表查询parentId
     * @param fourRelationshipsClass
     * @return
     */
    @Override
    public FourRelationshipsClass selectApiFourRelationshipsClassList(FourRelationshipsClass fourRelationshipsClass) {
        return fourRelationshipsClassMapper.selectApiFourRelationshipsClassList(fourRelationshipsClass);
    }
}
