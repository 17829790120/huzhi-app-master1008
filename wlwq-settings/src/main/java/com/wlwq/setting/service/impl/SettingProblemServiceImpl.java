package com.wlwq.setting.service.impl;

import com.wlwq.common.core.text.Convert;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.setting.domain.SettingProblem;
import com.wlwq.setting.mapper.SettingProblemMapper;
import com.wlwq.setting.result.ProblemListResult;
import com.wlwq.setting.service.ISettingProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 常见问题Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
@Service
public class SettingProblemServiceImpl implements ISettingProblemService 
{
    @Autowired
    private SettingProblemMapper settingProblemMapper;

    /**
     * 查询常见问题
     * 
     * @param problemId 常见问题ID
     * @return 常见问题
     */
    @Override
    public SettingProblem selectSettingProblemById(Long problemId)
    {
        return settingProblemMapper.selectSettingProblemById(problemId);
    }

    /**
     * 查询常见问题列表
     * 
     * @param settingProblem 常见问题
     * @return 常见问题
     */
    @Override
    public List<SettingProblem> selectSettingProblemList(SettingProblem settingProblem)
    {
        return settingProblemMapper.selectSettingProblemList(settingProblem);
    }

    /**
     * 新增常见问题
     * 
     * @param settingProblem 常见问题
     * @return 结果
     */
    @Override
    public int insertSettingProblem(SettingProblem settingProblem)
    {
        settingProblem.setCreateTime(DateUtils.getNowDate());
        return settingProblemMapper.insertSettingProblem(settingProblem);
    }

    /**
     * 修改常见问题
     * 
     * @param settingProblem 常见问题
     * @return 结果
     */
    @Override
    public int updateSettingProblem(SettingProblem settingProblem)
    {
        settingProblem.setUpdateTime(DateUtils.getNowDate());
        return settingProblemMapper.updateSettingProblem(settingProblem);
    }

    /**
     * 删除常见问题对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSettingProblemByIds(String ids)
    {
        return settingProblemMapper.deleteSettingProblemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除常见问题信息
     * 
     * @param problemId 常见问题ID
     * @return 结果
     */
    @Override
    public int deleteSettingProblemById(Long problemId)
    {
        return settingProblemMapper.deleteSettingProblemById(problemId);
    }


    /**
     * 常见问题列表
     * @param problemTitle
     * @return
     */
    @Override
    public List<ProblemListResult> selectProblemList(String problemTitle) {
        return settingProblemMapper.selectProblemList(problemTitle);
    }

    /**
     * 常见问题详情
     * @param problemId
     * @return
     */
    @Override
    public String selectProblemContentById(Long problemId) {
        return settingProblemMapper.selectProblemContentById(problemId);
    }
}
