package com.wlwq.api.service;

import java.util.List;
import java.util.Map;

import com.wlwq.api.domain.AccountClocking;
import com.wlwq.api.resultVO.clocking.AbsenteeismResultVO;
import com.wlwq.api.resultVO.clocking.ClockingDayResultVO;

/**
 * 用户考勤Service接口
 * 
 * @author gaoce
 * @date 2023-05-15
 */
public interface IAccountClockingService {
    /**
     * 查询用户考勤
     * 
     * @param accountClockingId 用户考勤ID
     * @return 用户考勤
     */
    public AccountClocking selectAccountClockingById(String accountClockingId);

    /**
     * 查询用户考勤列表
     * 
     * @param accountClocking 用户考勤
     * @return 用户考勤集合
     */
    public List<AccountClocking> selectAccountClockingList(AccountClocking accountClocking);

    /**
     * 新增用户考勤
     * 
     * @param accountClocking 用户考勤
     * @return 结果
     */
    public int insertAccountClocking(AccountClocking accountClocking);

    /**
     * 修改用户考勤
     * 
     * @param accountClocking 用户考勤
     * @return 结果
     */
    public int updateAccountClocking(AccountClocking accountClocking);

    /**
     * 批量删除用户考勤
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountClockingByIds(String ids);

    /**
     * 删除用户考勤信息
     * 
     * @param accountClockingId 用户考勤ID
     * @return 结果
     */
    public int deleteAccountClockingById(String accountClockingId);

    /**
     * 只查询某个用户的一条记录
     * @param accountClocking
     * @return
     */
    public AccountClocking selectAccountClocking(AccountClocking accountClocking);

    /**
     * api查询考勤列表
     * @param accountClocking
     * @return
     */
    public List<AccountClocking> selectApiAccountClockingList(AccountClocking accountClocking);

    /**
     * api查询考勤数量
     * @param accountClocking
     * @return
     */
    public Integer selectApiAccountClockingCount(AccountClocking accountClocking);

    /**
     * api查询考勤统计
     * @param accountClocking
     * @return
     */
    public Map<String,Object> selectApiAccountClockingStatisticsCount(AccountClocking accountClocking);

    /**
     * 查询某个人某个月每一天的考勤情况
     * @param accountClocking
     * @return
     */
    public List<ClockingDayResultVO> selectApiClockingListByMonth(AccountClocking accountClocking);

    /**
     *  api查询平均工时
     * @param accountClocking
     * @return
     */
    public Double selectApiAccountClockingWorkHour(AccountClocking accountClocking);

    /**
     * 查询正常天数
     * @param accountClocking
     * @return
     */
    public Integer selectApiAccountNormalClockingDay(AccountClocking accountClocking);

    /**
     * 查询某个用户某个月的应该上班的正常天数
     * accountId 用户ID
     * month 月份
     *
     * @return
     */
    public Integer normalCount(String accountId, String month);

    /**
     * 查询某个用户某个月的旷工数量
     * accountId 用户ID
     * month 月份
     *
     * @return
     */
    public AbsenteeismResultVO absenteeismCount(String accountId, String month);

}
