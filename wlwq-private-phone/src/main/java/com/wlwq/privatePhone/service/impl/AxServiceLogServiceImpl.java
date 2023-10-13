package com.wlwq.privatePhone.service.impl;

import java.util.List;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.privatePhone.mapper.AxServiceLogMapper;
import com.wlwq.privatePhone.domain.AxServiceLog;
import com.wlwq.privatePhone.service.IAxServiceLogService;
import com.wlwq.common.core.text.Convert;

/**
 * AX号码绑定日志Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-04-02
 */
@Service
public class AxServiceLogServiceImpl implements IAxServiceLogService 
{
    @Autowired
    private AxServiceLogMapper axServiceLogMapper;

    /**
     * 查询AX号码绑定日志
     * 
     * @param axServiceLogId AX号码绑定日志ID
     * @return AX号码绑定日志
     */
    @Override
    public AxServiceLog selectAxServiceLogById(Long axServiceLogId)
    {
        return axServiceLogMapper.selectAxServiceLogById(axServiceLogId);
    }

    /**
     * 查询AX号码绑定日志列表
     * 
     * @param axServiceLog AX号码绑定日志
     * @return AX号码绑定日志
     */
    @Override
    public List<AxServiceLog> selectAxServiceLogList(AxServiceLog axServiceLog)
    {
        return axServiceLogMapper.selectAxServiceLogList(axServiceLog);
    }

    /**
     * 新增AX号码绑定日志
     * 
     * @param axServiceLog AX号码绑定日志
     * @return 结果
     */
    @Override
    public int insertAxServiceLog(AxServiceLog axServiceLog)
    {
        axServiceLog.setCreateTime(DateUtils.getNowDate());
        return axServiceLogMapper.insertAxServiceLog(axServiceLog);
    }

    /**
     * 修改AX号码绑定日志
     * 
     * @param axServiceLog AX号码绑定日志
     * @return 结果
     */
    @Override
    public int updateAxServiceLog(AxServiceLog axServiceLog)
    {
        return axServiceLogMapper.updateAxServiceLog(axServiceLog);
    }

    /**
     * 删除AX号码绑定日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAxServiceLogByIds(String ids)
    {
        return axServiceLogMapper.deleteAxServiceLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除AX号码绑定日志信息
     * 
     * @param axServiceLogId AX号码绑定日志ID
     * @return 结果
     */
    @Override
    public int deleteAxServiceLogById(Long axServiceLogId)
    {
        return axServiceLogMapper.deleteAxServiceLogById(axServiceLogId);
    }
}
