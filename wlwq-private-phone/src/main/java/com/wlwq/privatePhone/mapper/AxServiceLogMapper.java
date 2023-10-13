package com.wlwq.privatePhone.mapper;

import java.util.List;
import com.wlwq.privatePhone.domain.AxServiceLog;

/**
 * AX号码绑定日志Mapper接口
 * 
 * @author Rick wlwq
 * @date 2021-04-02
 */
public interface AxServiceLogMapper 
{
    /**
     * 查询AX号码绑定日志
     * 
     * @param axServiceLogId AX号码绑定日志ID
     * @return AX号码绑定日志
     */
    public AxServiceLog selectAxServiceLogById(Long axServiceLogId);

    /**
     * 查询AX号码绑定日志列表
     * 
     * @param axServiceLog AX号码绑定日志
     * @return AX号码绑定日志集合
     */
    public List<AxServiceLog> selectAxServiceLogList(AxServiceLog axServiceLog);

    /**
     * 新增AX号码绑定日志
     * 
     * @param axServiceLog AX号码绑定日志
     * @return 结果
     */
    public int insertAxServiceLog(AxServiceLog axServiceLog);

    /**
     * 修改AX号码绑定日志
     * 
     * @param axServiceLog AX号码绑定日志
     * @return 结果
     */
    public int updateAxServiceLog(AxServiceLog axServiceLog);

    /**
     * 删除AX号码绑定日志
     * 
     * @param axServiceLogId AX号码绑定日志ID
     * @return 结果
     */
    public int deleteAxServiceLogById(Long axServiceLogId);

    /**
     * 批量删除AX号码绑定日志
     * 
     * @param axServiceLogIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAxServiceLogByIds(String[] axServiceLogIds);
}
