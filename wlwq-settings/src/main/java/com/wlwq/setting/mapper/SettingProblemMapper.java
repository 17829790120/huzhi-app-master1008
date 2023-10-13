package com.wlwq.setting.mapper;

import com.wlwq.setting.domain.SettingProblem;
import com.wlwq.setting.result.ProblemListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 常见问题Mapper接口
 *
 * @author Rick wlwq
 * @date 2021-06-07
 */
public interface SettingProblemMapper {
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
     * 删除常见问题
     *
     * @param problemId 常见问题ID
     * @return 结果
     */
    public int deleteSettingProblemById(Long problemId);

    /**
     * 批量删除常见问题
     *
     * @param problemIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSettingProblemByIds(String[] problemIds);

    /**
     * 常见问题列表
     *
     * @param problemTitle
     * @return
     */
    List<ProblemListResult> selectProblemList(@Param("problemTitle") String problemTitle);

    /**
     * 常见问题详情
     *
     * @param problemId
     * @return
     */
    String selectProblemContentById(@Param("problemId") Long problemId);
}
