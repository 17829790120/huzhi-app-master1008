package com.wlwq.api.service.impl;

import com.wlwq.api.domain.Categorys;
import com.wlwq.api.domain.TrainingModule;
import com.wlwq.api.mapper.CategoryMapper;
import com.wlwq.api.service.ICategoryService;
import com.wlwq.api.service.ITrainingModuleService;
import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试类目Service业务层处理
 *
 * @author Renbowen
 * @date 2021-01-07
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ITrainingModuleService moduleService;

    /**
     * 查询考试类目
     *
     * @param categoryId 考试类目ID
     * @return 考试类目
     */
    @Override
    public Categorys selectCategoryById(Long categoryId) {
        return categoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 查询考试类目列表
     *
     * @param categorys 考试类目
     * @return 考试类目
     */
    @Override
    public List<Categorys> selectCategoryList(Categorys categorys) {
        List<Categorys> categorysList = categoryMapper.selectCategoryList(categorys);
        categorysList.forEach(e->{
            if("0".equals(e.getTrainingModuleId())){
                e.setTrainingModuleName("自己与自己的关系");
            }else{
                TrainingModule trainingModule = moduleService.selectTrainingModuleById(e.getTrainingModuleId());
                e.setTrainingModuleName(trainingModule == null ? "" : trainingModule.getTitleName());
            }

        });
        return categorysList;
    }

    /**
     * 新增考试类目
     *
     * @param categorys 考试类目
     * @return 结果
     */
    @Override
    public int insertCategory(Categorys categorys) {
        categorys.setCreateTime(DateUtils.getNowDate());
        return categoryMapper.insertCategory(categorys);
    }

    /**
     * 修改考试类目
     *
     * @param categorys 考试类目
     * @return 结果
     */
    @Override
    public int updateCategory(Categorys categorys) {
        return categoryMapper.updateCategory(categorys);
    }

    /**
     * 删除考试类目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String ids) {
        return categoryMapper.deleteCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除考试类目信息
     *
     * @param categoryId 考试类目ID
     * @return 结果
     */
    @Override
    public int deleteCategoryById(Long categoryId) {
        return categoryMapper.deleteCategoryById(categoryId);
    }

    /**
     * 查询考试类目树列表
     *
     * @return 所有考试类目信息
     */
    @Override
    public List<Ztree> selectCategoryTree() {
        List<Categorys> categorysList = categoryMapper.selectCategoryList(new Categorys());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (Categorys categorys : categorysList) {
            Ztree ztree = new Ztree();
            ztree.setId(categorys.getCategoryId());
            ztree.setpId(categorys.getParentId());
            ztree.setName(categorys.getCategoryName());
            ztree.setTitle(categorys.getCategoryName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * 根据分类的id查询其中一条分类
     *
     * @param parentId 考试类目ID
     * @return 考试类目
     */
    @Override
    public Categorys selectCategoryByCategoryById(Long parentId){
        return categoryMapper.selectCategoryByCategoryById(parentId);
    }


    /**
     *  根据分类的id查询其对应的老师微信二维码
     *
     *  @param categoryId 考试分类ID
     *  @return 考试类目对应老师二维码
     */
    @Override
    public Categorys selectCategoryCode(Long categoryId){
        return categoryMapper.selectCategoryCode(categoryId);
    }

    /**
     * 根据父类ID查询子类
     *
     * @param parentId 考试类目ID
     * @return 考试类目
     */
    @Override
    public List<Categorys> selectCategoryListByParentId(String parentId){
        return categoryMapper.selectCategoryListByParentId(parentId);
    }

    /**
     * 查询二级分类列表
     *
     * @return 二级分类列表
     */
    @Override
    public List<Categorys> selectTwoCategoryList() {
        return categoryMapper.selectTwoCategoryList();
    }
    /**
     * 手机端查看分类
     * @param categorys
     * @return
     */
    @Override
    public List<Categorys> selectApiCategoryList(Categorys categorys) {
        return categoryMapper.selectApiCategoryList(categorys);
    }
}
