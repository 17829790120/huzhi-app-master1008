package com.wlwq.setting.service;

import com.wlwq.setting.domain.SettingProblem;
import com.wlwq.setting.result.ProblemListResult;

import java.util.List;

/**
 * 常见问题Service接口
 * 
 * @author Rick wlwq
 * @date 2021-06-07
 */
public interface ISettingProblemService 
{
    /**
     * 查询常见问题
     * 
     * @param problemId 常见问题ID
     * @return 常见问题
     */
    public SettingProblem selectSettingProblemById(Long problemId);

    /**
     * 查询常见问题列表
     * 
     * @param settingProblem 常见问题
     * @return 常见问题集合
     */
    public List<SettingProblem> selectSettingProblemList(SettingProblem settingProblem);

    /**
     * 新增常见问题
     * 
     * @param settingProblem 常见问题
     * @return 结果
     */
    public int insertSettingProblem(SettingProblem settingProblem);

    /**
     * 修改常见问题
     * 
     * @param settingProblem 常见问题
     * @return 结果
     */
    public int updateSettingProblem(SettingProblem settingProblem);

    /**
     * 批量删除常见问题
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingProblemByIds(String ids);

    /**
     * 删除常见问题信息
     * 
     * @param problemId 常见问题ID
     * @return 结果
     */
    public int deleteSettingProblemById(Long problemId);

    /**
     * 常见问题列表
     * @param problemTitle
     * @return
     */
    List<ProblemListResult> selectProblemList(String problemTitle);

    /**
     * 常见问题详情
     * @param problemId
     * @return
     */
    String selectProblemContentById(Long problemId);
}
