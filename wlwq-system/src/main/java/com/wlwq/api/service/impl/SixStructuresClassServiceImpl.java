package com.wlwq.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.SixStructuresClassMapper;
import com.wlwq.api.domain.SixStructuresClass;
import com.wlwq.api.service.ISixStructuresClassService;
import com.wlwq.common.core.text.Convert;

/**
 * 六大架构分类Service业务层处理
 * 
 * @author wlwq
 * @date 2023-08-28
 */
@Service
public class SixStructuresClassServiceImpl implements ISixStructuresClassService {

    @Autowired
    private SixStructuresClassMapper sixStructuresClassMapper;

    /**
     * 查询六大架构分类
     * 
     * @param sixStructuresClassId 六大架构分类ID
     * @return 六大架构分类
     */
    @Override
    public SixStructuresClass selectSixStructuresClassById(Long sixStructuresClassId) {
        return sixStructuresClassMapper.selectSixStructuresClassById(sixStructuresClassId);
    }

    /**
     * 查询六大架构分类列表
     *
     * @param sixStructuresClass 六大架构分类
     * @return 六大架构分类
     */
    @Override
    public List<SixStructuresClass> selectSixStructuresClassList(SixStructuresClass sixStructuresClass) {
        return sixStructuresClassMapper.selectSixStructuresClassList(sixStructuresClass);
    }

    /**
     * 新增六大架构分类
     *
     * @param sixStructuresClass 六大架构分类
     * @return 结果
     */
    @Override
    public int insertSixStructuresClass(SixStructuresClass sixStructuresClass) {
        sixStructuresClass.setCreateTime(DateUtils.getNowDate());
        return sixStructuresClassMapper.insertSixStructuresClass(sixStructuresClass);
    }

    /**
     * 修改六大架构分类
     *
     * @param sixStructuresClass 六大架构分类
     * @return 结果
     */
    @Override
    public int updateSixStructuresClass(SixStructuresClass sixStructuresClass) {
        sixStructuresClass.setUpdateTime(DateUtils.getNowDate());
        return sixStructuresClassMapper.updateSixStructuresClass(sixStructuresClass);
    }

    /**
     * 删除六大架构分类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSixStructuresClassByIds(String ids) {
        return sixStructuresClassMapper.deleteSixStructuresClassByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除六大架构分类信息
     *
     * @param sixStructuresClassId 六大架构分类ID
     * @return 结果
     */
    @Override
    public int deleteSixStructuresClassById(Long sixStructuresClassId) {
        return sixStructuresClassMapper.deleteSixStructuresClassById(sixStructuresClassId);
    }

    /**
     * 加载服务分类树列表
     * @return
     */
    @Override
    public List<Ztree> selectStoreClassTree() {
        List<SixStructuresClass> structuresClassList = sixStructuresClassMapper.selectSixStructuresClassList(new SixStructuresClass());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        // 主目录
        Ztree mainTree = new Ztree();
        mainTree.setId(0L);
        mainTree.setName("主目录");
        mainTree.setTitle("主目录");
        ztrees.add(mainTree);
        for (SixStructuresClass storeClass : structuresClassList) {
            Ztree ztree = new Ztree();
            ztree.setId(storeClass.getSixStructuresClassId());
            ztree.setpId(storeClass.getParentId());
            ztree.setName(storeClass.getClassName());
            ztree.setTitle(storeClass.getClassName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * App六大架构列表
     * @param sixStructuresClass
     * @return
     */
    @Override
    public List<SixStructuresClass> selectSixStructuresClassVoList(SixStructuresClass sixStructuresClass) {
        return sixStructuresClassMapper.selectSixStructuresClassVoList(sixStructuresClass);
    }

    /**
     * App六大架构列表查询parentId
     * @param sixStructuresClass
     * @return
     */
    @Override
    public SixStructuresClass selectApiSixStructuresClassList(SixStructuresClass sixStructuresClass) {
        return sixStructuresClassMapper.selectApiSixStructuresClassList(sixStructuresClass);
    }
}
