package com.wlwq.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.clocking.AbsenteeismResultVO;
import com.wlwq.api.resultVO.clocking.ClockingDayResultVO;
import com.wlwq.api.service.IApiAccountService;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.WeekVO;
import com.wlwq.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.AccountClockingMapper;
import com.wlwq.api.domain.AccountClocking;
import com.wlwq.api.service.IAccountClockingService;
import com.wlwq.common.core.text.Convert;

/**
 * 用户考勤Service业务层处理
 *
 * @author gaoce
 * @date 2023-05-15
 */
@Service
public class AccountClockingServiceImpl implements IAccountClockingService {

    @Autowired
    private AccountClockingMapper accountClockingMapper;

    @Autowired
    private IApiAccountService accountService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询用户考勤
     *
     * @param accountClockingId 用户考勤ID
     * @return 用户考勤
     */
    @Override
    public AccountClocking selectAccountClockingById(String accountClockingId) {
        return accountClockingMapper.selectAccountClockingById(accountClockingId);
    }

    /**
     * 查询用户考勤列表
     *
     * @param accountClocking 用户考勤
     * @return 用户考勤
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<AccountClocking> selectAccountClockingList(AccountClocking accountClocking) {
        return accountClockingMapper.selectAccountClockingList(accountClocking);
    }

    /**
     * 新增用户考勤
     *
     * @param accountClocking 用户考勤
     * @return 结果
     */
    @Override
    public int insertAccountClocking(AccountClocking accountClocking) {
        accountClocking.setAccountClockingId(IdUtil.getSnowflake(1, 1).nextIdStr());
        accountClocking.setCreateTime(DateUtils.getNowDate());
        accountClocking.setUpdateTime(DateUtils.getNowDate());
        return accountClockingMapper.insertAccountClocking(accountClocking);
    }

    /**
     * 修改用户考勤
     *
     * @param accountClocking 用户考勤
     * @return 结果
     */
    @Override
    public int updateAccountClocking(AccountClocking accountClocking) {
        accountClocking.setUpdateTime(DateUtils.getNowDate());
        return accountClockingMapper.updateAccountClocking(accountClocking);
    }

    /**
     * 删除用户考勤对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountClockingByIds(String ids) {
        return accountClockingMapper.deleteAccountClockingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户考勤信息
     *
     * @param accountClockingId 用户考勤ID
     * @return 结果
     */
    @Override
    public int deleteAccountClockingById(String accountClockingId) {
        return accountClockingMapper.deleteAccountClockingById(accountClockingId);
    }

    /**
     * 只查询某个用户的一条记录
     * @param accountClocking
     * @return
     */
    @Override
    public AccountClocking selectAccountClocking(AccountClocking accountClocking){
        return accountClockingMapper.selectAccountClocking(accountClocking);
    }

    /**
     * api查询考勤列表
     * @param accountClocking
     * @return
     */
    @Override
    public List<AccountClocking> selectApiAccountClockingList(AccountClocking accountClocking){
        return accountClockingMapper.selectApiAccountClockingList(accountClocking);
    }

    /**
     * api查询考勤数量
     * @param accountClocking
     * @return
     */
    @Override
    public Integer selectApiAccountClockingCount(AccountClocking accountClocking){
        return accountClockingMapper.selectApiAccountClockingCount(accountClocking);
    }

    /**
     * api查询考勤统计
     * @param accountClocking
     * @return
     */
    @Override
    public Map<String,Object> selectApiAccountClockingStatisticsCount(AccountClocking accountClocking){
        return accountClockingMapper.selectApiAccountClockingStatisticsCount(accountClocking);
    }

    /**
     * 查询某个人某个月每一天的考勤情况
     * @param accountClocking
     * @return
     */
    @Override
    public List<ClockingDayResultVO> selectApiClockingListByMonth(AccountClocking accountClocking){
        return accountClockingMapper.selectApiClockingListByMonth(accountClocking);
    }

    /**
     *  api查询平均工时
     * @param accountClocking
     * @return
     */
    @Override
    public Double selectApiAccountClockingWorkHour(AccountClocking accountClocking){
        return accountClockingMapper.selectApiAccountClockingWorkHour(accountClocking);
    }

    /**
     * 查询正常天数
     * @param accountClocking
     * @return
     */
    @Override
    public Integer selectApiAccountNormalClockingDay(AccountClocking accountClocking){
        return accountClockingMapper.selectApiAccountNormalClockingDay(accountClocking);
    }

    /**
     * 查询某个用户某个月的应该上班的正常天数
     * accountId 用户ID
     * month 月份
     *
     * @return
     */
    @Override
    public Integer normalCount(String accountId, String month) {
        ApiAccount account = accountService.selectApiAccountById(accountId);
        SysDept dept = deptService.selectDeptById(account == null ? 0 : account.getCompanyId());
        Integer normalCount = 0;
        // 未来月的话，旷工次数为0
        if(DateUtils.monthCompare(month) == 1){
            return normalCount;
        }
        // 查询某个人某个月每一天的考勤情况
        List<ClockingDayResultVO> clockingDayResultVOList = accountClockingMapper.selectApiClockingListByMonth(AccountClocking.builder().accountId(accountId).month(month).build());
        for (ClockingDayResultVO e : clockingDayResultVOList) {
            // 判断是否是当月 0:相等 1：大于 2：小于
            if(DateUtils.monthCompare(month) == 0){
                if(e.getDate().getTime() < DateUtil.parse(DateUtil.today()).getTime()){
                    // 节假日不休息的情况下，查询上班时间
                    WeekVO weekVO = DateUtils.getWeek(e.getDate());
                    // 节假日是否休息 0:否 1：节假日休息
                    if (dept.getHolidaysStatus() == 0) {
                        // 在设置的上班时间内
                        if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                            normalCount = normalCount + 1;
                        }
                    } else {
                        // 如果节假日休息的话，将本月的节假日排除掉
                        if (e.getHolidayTag() == 0) {
                            // 在设置的上班时间内
                            if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                                normalCount = normalCount + 1;
                            }
                        }
                    }
                }
            }else{
                // 节假日不休息的情况下，查询上班时间
                WeekVO weekVO = DateUtils.getWeek(e.getDate());
                // 节假日是否休息 0:否 1：节假日休息
                if (dept.getHolidaysStatus() == 0) {
                    // 在设置的上班时间内
                    if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                        normalCount = normalCount + 1;
                    }
                } else {
                    // 如果节假日休息的话，将本月的节假日排除掉
                    if (e.getHolidayTag() == 0) {
                        // 在设置的上班时间内
                        if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                            normalCount = normalCount + 1;
                        }
                    }
                }
            }

        }
        return normalCount;
    }

    /**
     * 查询某个用户某个月的旷工数量
     * accountId 用户ID
     * month 月份
     *
     * @return
     */
    @Override
    public AbsenteeismResultVO absenteeismCount(String accountId, String month) {
        // 未来月的话，旷工次数为0
        if(DateUtils.monthCompare(month) == 1){
            return AbsenteeismResultVO.builder().absenteeismCount(0).build();
        }
        List<ClockingDayResultVO> clockingDayResultVOList1 = new ArrayList<>();
        ApiAccount account = accountService.selectApiAccountById(accountId);
        SysDept dept = deptService.selectDeptById(account == null ? 0 : account.getCompanyId());
        Integer absenteeismCount = 0;
        // 查询某个人某个月每一天的考勤情况
        List<ClockingDayResultVO> clockingDayResultVOList = accountClockingMapper.selectApiClockingListByMonth(AccountClocking.builder().accountId(accountId).month(month).build());
        for (ClockingDayResultVO e : clockingDayResultVOList) {// 节假日不休息的情况下，查询上班时间
            // 判断是否是当月 0:相等 1：大于 2：小于
            if(DateUtils.monthCompare(month) == 0){
                if(e.getDate().getTime() < DateUtil.parse(DateUtil.today()).getTime()){
                    WeekVO weekVO = DateUtils.getWeek(e.getDate());
                    // 查询是否是旷工，先查询节假日是否休息
                    if (e.getClockingCount() == 0 && dept != null) {
                        // 节假日是否休息 0:否 1：节假日休息
                        if (dept.getHolidaysStatus() == 0) {
                            // 当天的考勤为0且在设置的上班时间内
                            if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                                absenteeismCount = absenteeismCount + 1;
                                clockingDayResultVOList1.add(e);
                            }
                        } else {
                            // 如果节假日休息的话，将本月的节假日排除掉
                            if (e.getHolidayTag() == 0) {
                                // 当天的考勤为0且在设置的上班时间内
                                if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                                    absenteeismCount = absenteeismCount + 1;
                                    clockingDayResultVOList1.add(e);
                                }
                            }
                        }
                    }
                }
            }else{
                WeekVO weekVO = DateUtils.getWeek(e.getDate());
                // 查询是否是旷工，先查询节假日是否休息
                if (e.getClockingCount() == 0 && dept != null) {
                    // 节假日是否休息 0:否 1：节假日休息
                    if (dept.getHolidaysStatus() == 0) {
                        // 当天的考勤为0且在设置的上班时间内
                        if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                            absenteeismCount = absenteeismCount + 1;
                            clockingDayResultVOList1.add(e);
                        }
                    } else {
                        // 如果节假日休息的话，将本月的节假日排除掉
                        if (e.getHolidayTag() == 0) {
                            // 当天的考勤为0且在设置的上班时间内
                            if (StringUtils.isNotBlank(dept.getWeeks()) && dept.getWeeks().contains(cn.hutool.core.convert.Convert.toStr(weekVO.getNum()))) {
                                absenteeismCount = absenteeismCount + 1;
                                clockingDayResultVOList1.add(e);
                            }
                        }
                    }
                }
            }

        }
        return AbsenteeismResultVO.builder().absenteeismCount(absenteeismCount).clockingDayResultVOList(clockingDayResultVOList1).build();
    }


}
