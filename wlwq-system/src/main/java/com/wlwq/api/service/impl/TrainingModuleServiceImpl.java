package com.wlwq.api.service.impl;

import java.util.List;
import cn.hutool.core.util.IdUtil;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.TrainingModuleMapper;
import com.wlwq.api.domain.TrainingModule;
import com.wlwq.api.service.ITrainingModuleService;
import com.wlwq.common.core.text.Convert;

/**
 * 人员训练模块Service业务层处理
 * 
 * @author gaoce
 * @date 2023-07-31
 */
@Service
public class TrainingModuleServiceImpl implements ITrainingModuleService {

    @Autowired
    private TrainingModuleMapper trainingModuleMapper;

    /**
     * 查询人员训练模块
     * 
     * @param trainingModuleId 人员训练模块ID
     * @return 人员训练模块
     */
    @Override
    public TrainingModule selectTrainingModuleById(String trainingModuleId) {
        return trainingModuleMapper.selectTrainingModuleById(trainingModuleId);
    }

    /**
     * 查询人员训练模块列表
     * 
     * @param trainingModule 人员训练模块
     * @return 人员训练模块
     */
    @Override
    public List<TrainingModule> selectTrainingModuleList(TrainingModule trainingModule) {
        return trainingModuleMapper.selectTrainingModuleList(trainingModule);
    }

    /**
     * 新增人员训练模块
     * 
     * @param trainingModule 人员训练模块
     * @return 结果
     */
    @Override
    public int insertTrainingModule(TrainingModule trainingModule) {
        trainingModule.setTrainingModuleId(IdUtil.getSnowflake(1,1).nextIdStr());
        trainingModule.setCreateTime(DateUtils.getNowDate());
        if(StringUtils.isBlank(trainingModule.getModuleIcon())){
            trainingModule.setModuleIcon("http://qiniu.sxhzxl.cn/person_1690785430738.png");
        }
        if(StringUtils.isBlank(trainingModule.getModuleImage())){
            trainingModule.setModuleImage("http://qiniu.sxhzxl.cn/ziji@2x_1690873686777.png");
        }
        return trainingModuleMapper.insertTrainingModule(trainingModule);
    }

    /**
     * 修改人员训练模块
     * 
     * @param trainingModule 人员训练模块
     * @return 结果
     */
    @Override
    public int updateTrainingModule(TrainingModule trainingModule) {
        trainingModule.setUpdateTime(DateUtils.getNowDate());
        return trainingModuleMapper.updateTrainingModule(trainingModule);
    }

    /**
     * 删除人员训练模块对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTrainingModuleByIds(String ids) {
        return trainingModuleMapper.deleteTrainingModuleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除人员训练模块信息
     * 
     * @param trainingModuleId 人员训练模块ID
     * @return 结果
     */
    @Override
    public int deleteTrainingModuleById(String trainingModuleId) {
        return trainingModuleMapper.deleteTrainingModuleById(trainingModuleId);
    }


    /**
     * 查询人员训练模块列表
     *
     * @param trainingModule 人员训练模块
     * @return 人员训练模块集合
     */
    @Override
    public List<TrainingModule> selectApiTrainingModuleList(TrainingModule trainingModule){
        return trainingModuleMapper.selectApiTrainingModuleList(trainingModule);
    }
}
