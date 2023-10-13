package com.wlwq.api.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SmUtil;
import com.wlwq.api.domain.*;
import com.wlwq.api.mapper.ApiAccountMapper;
import com.wlwq.api.resultVO.clocking.AccountClockingResultVO;
import com.wlwq.api.resultVO.score.AccountScoreResultVO;
import com.wlwq.api.service.*;
import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.domain.entity.SysDictData;
import com.wlwq.common.core.text.Convert;
import com.wlwq.common.exception.BusinessException;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.common.utils.StringUtils;
import com.wlwq.common.utils.security.Md5Utils;
import com.wlwq.system.domain.SysPost;
import com.wlwq.system.mapper.SysDictDataMapper;
import com.wlwq.system.service.ISysConfigService;
import com.wlwq.system.service.ISysDeptService;
import com.wlwq.system.service.ISysPostService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户账户Service业务层处理
 *
 * @author Renbowen
 * @date 2020-09-25
 */
@Service
public class ApiAccountServiceImpl implements IApiAccountService {
    private static final Logger log = LoggerFactory.getLogger(ApiAccountServiceImpl.class);

    @Autowired
    private ApiAccountMapper apiAccountMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IAccountAppellationService accountAppellationService;

    @Autowired
    private IAccountMedalRecordService medalRecordService;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private ISysPostService sysPostService;

    @Autowired
    private IAccountClockingService clockingService;
    @Autowired
    private IExamineInitiateService initiateService;

    /**
     * 查询用户账户
     *
     * @param accountId 用户账户ID
     * @return 用户账户
     */
    @Override
    public ApiAccount selectApiAccountById(String accountId) {
        return apiAccountMapper.selectApiAccountById(accountId);
    }

    /**
     * 查询用户账户列表
     *
     * @param apiAccount 用户账户
     * @return 用户账户
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<ApiAccount> selectApiAccountList(ApiAccount apiAccount) {
        List<ApiAccount> accountList = apiAccountMapper.selectApiAccountList(apiAccount);
        accountList.forEach(e -> {
            // 查询获得的一级勋章数量
            int count = medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder().accountId(e.getAccountId()).tag(0).build());
            e.setMedalCount(count);
            // 查询二级获得的勋章数量
            int twoCount = medalRecordService.selectAccountMedalRecordCountByParentId(AccountMedalRecord.builder().accountId(e.getAccountId()).tag(0).build());
            e.setMedalTwoCount(twoCount);
        });
        return accountList;
    }

    /**
     * 新增用户账户
     *
     * @param apiAccount 用户账户
     * @return 结果
     */
    @Override
    public int insertApiAccount(ApiAccount apiAccount) {
        apiAccount.setAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
        apiAccount.setCreateTime(new Date());
        apiAccount.setLastTime(new Date());
        if (StringUtils.isBlank(apiAccount.getHeadPortrait())) {
            apiAccount.setHeadPortrait("http://qiniu.sxhzxl.cn/head_1686884360530.png");
        }
        if (StringUtils.isBlank(apiAccount.getPassword())) {
            apiAccount.setPassword(SmUtil.sm3("123456"));
        }
        if (StringUtils.isBlank(apiAccount.getBindingTag())) {
            apiAccount.setBindingTag(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        return apiAccountMapper.insertApiAccount(apiAccount);
    }

    /**
     * 修改用户账户
     *
     * @param apiAccount 用户账户
     * @return 结果
     */
    @Override
    public int updateApiAccount(ApiAccount apiAccount) {
        apiAccount.setLastTime(new Date());
        return apiAccountMapper.updateApiAccount(apiAccount);
    }

    /**
     * 删除用户账户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteApiAccountByIds(String ids) {
        return apiAccountMapper.deleteApiAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户账户信息
     *
     * @param accountId 用户账户ID
     * @return 结果
     */
    @Override
    public int deleteApiAccountById(String accountId) {
        return apiAccountMapper.deleteApiAccountById(accountId);
    }

    /**
     * 根据openId获取用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public ApiAccount selectApiAccountByWxOpenId(String openid) {
        return apiAccountMapper.selectApiAccountByWxOpenId(openid);
    }

    /**
     * 我邀请的一级人数
     *
     * @param accountId
     * @return
     */
    @Override
    public Integer countMyInventNum(String accountId, String conditionDate) {
        return apiAccountMapper.countMyInventNum(accountId, conditionDate);
    }

    /**
     * 我邀请的二级人数
     *
     * @param accountId
     * @return
     */
    @Override
    public Integer countMyTwoInventNum(String accountId, String conditionDate) {
        return apiAccountMapper.countMyTwoInventNum(accountId, conditionDate);
    }

    /**
     * 我的一级邀请好友列表
     *
     * @param accountId
     * @param conditionDate
     * @return
     */
    @Override
    public List<ApiAccount> selectApiAccountOneList(String accountId, String conditionDate) {
        return apiAccountMapper.selectApiAccountOneList(accountId, conditionDate);
    }

    /**
     * 我的二级邀请好友列表
     *
     * @param accountId
     * @param conditionDate
     * @return
     */
    @Override
    public List<ApiAccount> selectApiAccountTwoList(String accountId, String conditionDate) {
        return apiAccountMapper.selectApiAccountTwoList(accountId, conditionDate);
    }

    /**
     * 根据用户手机号查询
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public ApiAccount selectApiAccountByPhone(String phone) {
        return apiAccountMapper.selectApiAccountByPhone(phone);
    }

    /**
     * 查询用户账户
     *
     * @param apiAccount 用户账户
     * @return 用户账户集合
     */
    @Override
    public ApiAccount selectApiAccount(ApiAccount apiAccount) {
        return apiAccountMapper.selectApiAccount(apiAccount);
    }


    /**
     * 微信解绑
     *
     * @param accountId 用户账户ID
     * @return 结果
     */
    @Override
    public int wxUntie(String accountId) {
        return apiAccountMapper.wxUntie(accountId);
    }


    /**
     * 根据微信小程序openId获取用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public ApiAccount selectApiAccountByWxAppletOpenId(String openid) {
        return apiAccountMapper.selectApiAccountByWxAppletOpenId(openid);
    }

    /**
     * 导入用户数据
     *
     * @param accountList     用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<ApiAccount> accountList, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(accountList) || accountList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (ApiAccount account : accountList) {
            account.setSex(account.getSex());
            account.setHeadPortrait("http://qiniu.sxhzxl.cn/head_1686884360530.png");
            // 查询公司信息
            SysDept companyDept = new SysDept();
            companyDept.setDeptName(account.getCompanyName());
            companyDept.setParentId(100L);
            SysDept company = deptService.selectApiNearbyCity(companyDept);
            if (company != null) {
                account.setCompanyId(company.getDeptId());
                // 查询部门信息
                SysDept dept = new SysDept();
                dept.setDeptName(account.getDeptName());
                dept.setParentId(company.getDeptId());
                SysDept dept1 = deptService.selectApiNearbyCity(dept);
                if (dept1 != null) {
                    account.setDeptId(dept1.getDeptId());
                }
                // 查询岗位信息
                SysPost post = new SysPost();
                post.setCompanyId(company.getDeptId());
                post.setPostName(account.getPostNames());
                SysPost sysPost = sysPostService.checkPost(post);
                if (sysPost != null) {
                    account.setPostIds(sysPost.getPostIds());
                }
            }
            try {
                // 验证是否存在这个用户
                ApiAccount accountNew = apiAccountMapper.selectApiAccountByPhone(account.getPhone());
                if (StringUtils.isNull(accountNew)) {
                    account.setPassword(SmUtil.sm3(password));
                    account.setCreateBy(operName);
                    account.setAccountId(IdUtil.getSnowflake(1, 1).nextIdStr());
                    account.setCreateTime(new Date());
                    account.setLastTime(new Date());
                    // 新增用户信息
                    apiAccountMapper.insertApiAccount(account);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + account.getPhone() + " 导入成功");
                } else if (isUpdateSupport) {
                    ApiAccount apiAccount = apiAccountMapper.selectApiAccountByPhone(account.getPhone());
                    account.setUpdateBy(operName);
                    account.setUpdateTime(new Date());
                    account.setLastTime(new Date());
                    account.setAccountId(apiAccount.getAccountId());
                    apiAccountMapper.updateApiAccount(account);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + account.getPhone() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + account.getPhone() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + account.getPhone() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 按照公司与部门查询人员信息
     *
     * @param deptId    部门
     * @param companyId 公司
     * @return 结果
     */
    @Override
    public List<HashMap<String, Object>> selectAccountMap(Long deptId, Long companyId, String keyword) {
        return apiAccountMapper.selectAccountMap(deptId, companyId, keyword);
    }

    /**
     * 根据部门和岗位获取某一个用户信息
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return
     */
    @Override
    public ApiAccount selectApiAccountLimitByPostIdAndDeptId(Long deptId, Long postId) {
        return apiAccountMapper.selectApiAccountLimitByPostIdAndDeptId(deptId, postId);
    }


    /**
     * 根据部门和岗位获取用户列表信息列表
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return
     */
    @Override
    public List<ApiAccount> selectApiAccountListByPostIdAndDeptId(Long deptId, Long postId) {
        return apiAccountMapper.selectApiAccountListByPostIdAndDeptId(deptId, postId);
    }

    /**
     * 按部门查询
     *
     * @return
     */
    @Override
    public List<ApiAccount> selectListByDept(ApiAccount apiAccount) {
        return apiAccountMapper.selectListByDept(apiAccount);
    }

    /**
     * 查询用户数量
     *
     * @param apiAccount
     * @return
     */
    @Override
    public int selectApiAccountCount(ApiAccount apiAccount) {
        return apiAccountMapper.selectApiAccountCount(apiAccount);
    }

    /**
     * 积分排行榜
     *
     * @param apiAccount
     * @return
     */
    @Override
    public List<AccountScoreResultVO> selectApiScoreAccountList(ApiAccount apiAccount) {
        List<AccountScoreResultVO> resultVOList = apiAccountMapper.selectApiScoreAccountList(apiAccount);
        resultVOList.forEach(e -> {
            // 查询积分称谓
            String appellationName = accountAppellationService.selectAccountAppellationName(AccountAppellation.builder()
                    .minScore(e.getTotalScore())
                    .maxScore(e.getTotalScore())
                    .appellationType(apiAccount.getAppellationType())
                    .build());
            e.setAppellationName(appellationName);
        });
        return resultVOList;
    }

    /**
     * 查询某个用户某个时间段的积分
     *
     * @param apiAccount
     * @return
     */
    @Override
    public AccountScoreResultVO selectApiScoreAccount(ApiAccount apiAccount) {
        AccountScoreResultVO resultVO = apiAccountMapper.selectApiScoreAccount(apiAccount);
        if (resultVO != null) {
            // 查询积分称谓
            String appellationName = accountAppellationService.selectAccountAppellationName(AccountAppellation.builder()
                    .minScore(resultVO.getTotalScore())
                    .maxScore(resultVO.getTotalScore())
                    .appellationType(apiAccount.getAppellationType())
                    .build());
            resultVO.setAppellationName(appellationName);
        }
        return resultVO;
    }

    /**
     * 按照公司与小组查询人员信息
     *
     * @param groupInforId
     * @param companyId
     * @return
     */
    @Override
    public List<HashMap<String, Object>> selectAccountMapByGroup(Long groupInforId, Long companyId) {
        return apiAccountMapper.selectAccountMapByGroup(groupInforId, companyId);
    }

    @Override
    public String getUserIsLeader(String postIds, String type) {
        return apiAccountMapper.getUserIsLeader(postIds, type);
    }

    @Override
    public ApiResult addExperienceAccount(String phone) {
        //新增用户信息
        ApiAccount apiAccount = new ApiAccount();
        apiAccount.setCompanyId(125L);
        apiAccount.setDeptId(126L);
        apiAccount.setNickName("体验用户");
        apiAccount.setHeadPortrait("http://qiniu.sxhzxl.cn/head_1686884360530.png");
        apiAccount.setPhone(phone);
        apiAccount.setPostIds("36");
//        apiAccount.setJobNumber("001");
        apiAccount.setIsExperience("1");
        //查询体验时长
        List<SysDictData> experienceTime = sysDictDataMapper.selectDictDataByType("app_experience_time");
        if (ObjectUtil.isEmpty(experienceTime) && experienceTime.size() <= 0) {
            return ApiResult.fail("未设置体验时长,新增体验用户失败");
        }
        Long time;
        try {
            time = Long.parseLong(experienceTime.get(0).getDictValue());
            apiAccount.setExperienceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LocalDate.now().plusDays(time) + " 23:59:59"));
        } catch (Exception e) {
            return ApiResult.fail("时间转换失败,请联系管理员");
        }
        this.insertApiAccount(apiAccount);
        return ApiResult.ok();
    }

    @Override
    public boolean isExpire(String phone) throws ParseException {
        String expireAccount = apiAccountMapper.findExpireAccount(phone);
        if (StringUtils.isEmpty(expireAccount)) {
            return false;
        }
        Date expireAccountTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireAccount);
        Date date = new Date();
        if (date.compareTo(expireAccountTime) == NumberUtils.INTEGER_ONE) {
            return false;
        }
        return true;
    }

    /**
     * 查询绑定的人员列表
     *
     * @param apiAccount
     * @return
     */
    @Override
    public List<AccountScoreResultVO> selectApiBindingAccountList(ApiAccount apiAccount) {
        return apiAccountMapper.selectApiBindingAccountList(apiAccount);
    }

    /**
     * 根据公司查询用户信息列表
     * @param apiAccount
     * @return
     */
    @Override
    public List<ApiAccount> selectAccountListByCompanyId(ApiAccount apiAccount){
        return apiAccountMapper.selectAccountListByCompanyId(apiAccount);
    }

    /**
     * 根据条件查询考勤统计
     * @param apiAccount
     * @return
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<AccountClockingResultVO> selectApiClockingAccountList(ApiAccount apiAccount){
        if(StringUtils.isBlank(apiAccount.getMonth())){
            apiAccount.setMonth(DateUtil.format(DateUtil.date(), "yyyy-MM"));
        }
        List<AccountClockingResultVO> resultVOList = apiAccountMapper.selectApiClockingAccountList(apiAccount);
        for (AccountClockingResultVO clockingResultVO : resultVOList){
            AccountClocking accountClocking = AccountClocking.builder()
                    .accountId(clockingResultVO.getAccountId())
                    .month(apiAccount.getMonth())
                    .build();
            clockingResultVO.setMonth(apiAccount.getMonth());
            // beLateCount:迟到次数  leaveEarlyCount:早退次数  lackCount:缺卡次数 clockingCount：出勤天数
            Map<String, Object> map = clockingService.selectApiAccountClockingStatisticsCount(accountClocking);
            clockingResultVO.setBeLateCount(Convert.toInt(map.get("beLateCount")));
            clockingResultVO.setLeaveEarlyCount(Convert.toInt(map.get("leaveEarlyCount")));
            clockingResultVO.setClockingCount(Convert.toInt(map.get("clockingCount")));
            // 缺卡次数
            // 0:相等 1：大于 2：小于
            if(DateUtils.monthCompare(apiAccount.getMonth()) == 0){
                accountClocking.setDate(DateUtil.today());
                clockingResultVO.setLackCount(Convert.toInt(clockingService.selectApiAccountClockingStatisticsCount(accountClocking).get("lackCount")));
            }
            if(DateUtils.monthCompare(apiAccount.getMonth()) == 1){
                clockingResultVO.setLackCount(0);
            }
            // 旷工次数
            Integer absenteeismDay = clockingService.absenteeismCount(clockingResultVO.getAccountId(), apiAccount.getMonth()).getAbsenteeismCount();
            clockingResultVO.setAbsenteeismDay(absenteeismDay);

            // 正常天数 = 正常天数 + 补卡天数
            Integer normalDay = clockingService.selectApiAccountNormalClockingDay(AccountClocking.builder()
                    .accountId(clockingResultVO.getAccountId())
                    .month(apiAccount.getMonth())
                    .build());
            clockingResultVO.setNormalDay(normalDay);

            // 异常天数
            Integer abnormalDay = clockingService.normalCount(clockingResultVO.getAccountId(),apiAccount.getMonth()) - normalDay;
            clockingResultVO.setAbnormalDay(abnormalDay < 0 ? 0 : abnormalDay);

            // 休息天数
            // 0:相等 1：大于 2：小于
            Integer restDay = 0;
            if(DateUtils.monthCompare(apiAccount.getMonth()) == 0){
                // 如果本月的话，查询本月剩余天数
                // 休息天数 = 本月已过天数 - 出勤天数 - 旷工天数
                restDay = DateUtils.alreadyDay() - cn.hutool.core.convert.Convert.toInt(map.get("clockingCount")) - absenteeismDay;
            }
            if(DateUtils.monthCompare(apiAccount.getMonth()) == 2){
                // 休息天数 = 月的总天数 - 出勤天数 - 旷工天数
                restDay = DateUtils.day(apiAccount.getMonth()) - cn.hutool.core.convert.Convert.toInt(map.get("clockingCount")) - absenteeismDay;
            }
            clockingResultVO.setRestDay(restDay < 0 ? 0 : restDay);

            // 请假次数
            Integer leaveCount = initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder()
                    .accountId(clockingResultVO.getAccountId())
                    .examineModuleId(1L)
                    .leaveMonth(apiAccount.getMonth())
                    .examineStatus(3)
                    .build());
            clockingResultVO.setLeaveCount(leaveCount);

            // 外勤次数
            accountClocking.setStatus(2);
            Integer signCount = clockingService.selectApiAccountClockingCount(accountClocking);
            clockingResultVO.setSignCount(signCount);

            // 补卡申请次数
            Integer reissueCount = initiateService.selectExamineInitiateApiCount(ExamineInitiate.builder()
                    .accountId(clockingResultVO.getAccountId())
                    .examineModuleId(5L)
                    .month(apiAccount.getMonth())
                    .examineStatus(3)
                    .build());
            clockingResultVO.setReissueCount(reissueCount);
        }
        return resultVOList;
    }
}
