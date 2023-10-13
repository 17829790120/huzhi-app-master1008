package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.StudyInformationCategoryMapper;
import com.wlwq.api.domain.StudyInformationCategory;
import com.wlwq.api.service.IStudyInformationCategoryService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯分类Service业务层处理
 *
 * @author web
 * @date 2023-05-16
 */
@Service
public class StudyInformationCategoryServiceImpl implements IStudyInformationCategoryService {

    @Autowired
    private StudyInformationCategoryMapper studyInformationCategoryMapper;

    /**
     * 查询资讯分类
     *
     * @param studyInformationCategoryId 资讯分类ID
     * @return 资讯分类
     */
    @Override
    public StudyInformationCategory selectStudyInformationCategoryById(Long studyInformationCategoryId) {
        return studyInformationCategoryMapper.selectStudyInformationCategoryById(studyInformationCategoryId);
    }

    /**
     * 查询资讯分类列表
     *
     * @param studyInformationCategory 资讯分类
     * @return 资讯分类
     */
    @Override
    //@DataScope(deptAlias = "d",userAlias = "u")
    public List<StudyInformationCategory> selectStudyInformationCategoryList(StudyInformationCategory studyInformationCategory) {
        return studyInformationCategoryMapper.selectStudyInformationCategoryList(studyInformationCategory);
    }

    /**
     * 新增资讯分类
     *
     * @param studyInformationCategory 资讯分类
     * @return 结果
     */
    @Override
    public int insertStudyInformationCategory(StudyInformationCategory studyInformationCategory) {
        studyInformationCategory.setCreateTime(DateUtils.getNowDate());
        return studyInformationCategoryMapper.insertStudyInformationCategory(studyInformationCategory);
    }

    /**
     * 修改资讯分类
     *
     * @param studyInformationCategory 资讯分类
     * @return 结果
     */
    @Override
    public int updateStudyInformationCategory(StudyInformationCategory studyInformationCategory) {
        studyInformationCategory.setUpdateTime(DateUtils.getNowDate());
        return studyInformationCategoryMapper.updateStudyInformationCategory(studyInformationCategory);
    }

    /**
     * 删除资讯分类对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStudyInformationCategoryByIds(String ids) {
        return studyInformationCategoryMapper.deleteStudyInformationCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯分类信息
     *
     * @param studyInformationCategoryId 资讯分类ID
     * @return 结果
     */
    @Override
    public int deleteStudyInformationCategoryById(Long studyInformationCategoryId) {
        // 查询下面有没有子集
        int countNumber = studyInformationCategoryMapper.countSonNumberByParentId(studyInformationCategoryId);
        if (countNumber > 0){
            throw new BusinessException("请先删除下级分类！");
        }
        return studyInformationCategoryMapper.deleteStudyInformationCategoryById(studyInformationCategoryId);
    }

    /**
     * 查询资讯分类列表
     *
     * @return 所有资讯分类信息
     */
    @Override
    public List<StudyInformationCategory> selectInformationCategoryVoList(StudyInformationCategory studyInformationCategory) {
        return studyInformationCategoryMapper.selectStudyInformationCategoryVoList(studyInformationCategory);
    }

    @Override
    public StringBuffer selectAncestorsById(Long studyInformationCategoryId, StringBuffer buf) {
        StudyInformationCategory informationCategory = studyInformationCategoryMapper.selectStudyInformationCategoryById(studyInformationCategoryId);
        if(informationCategory !=null && informationCategory.getParentId() == 0){
            return buf;
        }else if(informationCategory !=null){
            buf.append(informationCategory.getParentId()+",");
            selectAncestorsById(informationCategory.getParentId(),buf);
        }
        return buf;
    }
}
