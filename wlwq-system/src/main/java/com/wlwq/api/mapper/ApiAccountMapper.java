package com.wlwq.api.mapper;

import com.wlwq.api.domain.ApiAccount;
import com.wlwq.api.resultVO.clocking.AccountClockingResultVO;
import com.wlwq.api.resultVO.score.AccountScoreResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 用户账户Mapper接口
 *
 * @author Renbowen
 * @date 2020-09-25
 */
public interface ApiAccountMapper {
    /**
     * 查询用户账户
     *
     * @param accountId 用户账户ID
     * @return 用户账户
     */
    public ApiAccount selectApiAccountById(String accountId);

    /**
     * 查询用户账户列表
     *
     * @param apiAccount 用户账户
     * @return 用户账户集合
     */
    public List<ApiAccount> selectApiAccountList(ApiAccount apiAccount);

    /**
     * 新增用户账户
     *
     * @param apiAccount 用户账户
     * @return 结果
     */
    public int insertApiAccount(ApiAccount apiAccount);

    /**
     * 修改用户账户
     *
     * @param apiAccount 用户账户
     * @return 结果
     */
    public int updateApiAccount(ApiAccount apiAccount);

    /**
     * 删除用户账户
     *
     * @param accountId 用户账户ID
     * @return 结果
     */
    public int deleteApiAccountById(String accountId);

    /**
     * 批量删除用户账户
     *
     * @param accountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteApiAccountByIds(String[] accountIds);

    /**
     * 根据openId获取用户信息
     *
     * @param openid
     * @return
     */
    ApiAccount selectApiAccountByWxOpenId(@Param("openid") String openid);

    /**
     * 我邀请的一级人数
     *
     * @param accountId
     * @return
     */
    Integer countMyInventNum(@Param("accountId") String accountId,@Param("conditionDate") String conditionDate);

    /**
     * 我邀请的二级人数
     *
     * @param accountId
     * @return
     */
    Integer countMyTwoInventNum(@Param("accountId") String accountId,@Param("conditionDate") String conditionDate);

    /**
     * 我的一级邀请好友列表
     *
     * @param accountId
     * @param conditionDate
     * @return
     */
    List<ApiAccount> selectApiAccountOneList(@Param("accountId") String accountId, @Param("conditionDate") String conditionDate);

    /**
     * 我的二级邀请好友列表
     *
     * @param accountId
     * @param conditionDate
     * @return
     */
    List<ApiAccount> selectApiAccountTwoList(@Param("accountId") String accountId, @Param("conditionDate") String conditionDate);



    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号
     * @return
     */
    public ApiAccount selectApiAccountByPhone(@Param("phone") String phone);

    /**
     * 查询用户账户
     *
     * @param apiAccount 用户账户
     * @return 用户账户集合
     */
    public ApiAccount selectApiAccount(ApiAccount apiAccount);


    /**
     * 微信解绑
     *
     * @param accountId 用户账户ID
     * @return 结果
     */
    public int wxUntie(@Param("accountId") String accountId);

    /**
     * 根据微信小程序openId获取用户信息
     *
     * @param openid
     * @return
     */
    ApiAccount selectApiAccountByWxAppletOpenId(@Param("openid") String openid);


    /**
     * 根据部门和岗位获取某一个用户信息
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return
     */
    ApiAccount selectApiAccountLimitByPostIdAndDeptId(@Param("deptId") Long deptId, @Param("postId") Long postId);
    /**
     * 按照公司与部门查询人员信息
     *
     * @param deptId 部门
     * @param companyId 公司
     * @return 结果
     */
    List<HashMap<String,Object>> selectAccountMap(@Param("deptId")Long deptId,@Param("companyId") Long companyId,@Param("keyword") String keyword);

    /**
     * 根据部门和岗位获取用户列表信息列表
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return
     */
    List<ApiAccount> selectApiAccountListByPostIdAndDeptId(@Param("deptId") Long deptId, @Param("postId") Long postId);
    /**
     * 按部门查询
     *
     * @return
     */
    List<ApiAccount> selectListByDept(ApiAccount apiAccount);

    /**
     * 查询用户数量
     * @param apiAccount
     * @return
     */
    public int selectApiAccountCount(ApiAccount apiAccount);

    /**
     * 积分排行榜
     * @param apiAccount
     * @return
     */
    public List<AccountScoreResultVO> selectApiScoreAccountList(ApiAccount apiAccount);

    /**
     *  查询某个用户某个时间段的积分
     * @param apiAccount
     * @return
     */
    public AccountScoreResultVO selectApiScoreAccount(ApiAccount apiAccount);
    /**
     * 按照公司与小组查询人员信息
     * @param groupInforId
     * @param companyId
     * @return
     */
    List<HashMap<String, Object>> selectAccountMapByGroup(@Param("groupInforId")Long groupInforId,@Param("companyId") Long companyId);
    /**
     * 查询用户是否管理岗位
     */
    String getUserIsLeader(@Param("postIds") String postIds,@Param("type") String type);

    /**
     * 查询体验用户过期时间
     */
    String findExpireAccount(String phone);

    /**
     * 查询绑定的人员列表
     * @param apiAccount
     * @return
     */
    public List<AccountScoreResultVO> selectApiBindingAccountList(ApiAccount apiAccount);

    /**
     * 根据公司查询用户信息列表
     * @param apiAccount
     * @return
     */
    public List<ApiAccount> selectAccountListByCompanyId(ApiAccount apiAccount);

    /**
     * 根据条件查询考勤统计
     * @param apiAccount
     * @return
     */
    public List<AccountClockingResultVO> selectApiClockingAccountList(ApiAccount apiAccount);

}
