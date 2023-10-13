package com.wlwq.api.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.mapper.*;
import com.wlwq.api.resultVO.*;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.apiResult.PageParam;
import com.wlwq.common.utils.*;
import com.wlwq.system.mapper.SysDictDataMapper;
import com.wlwq.system.service.ISysDeptService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.service.ICustomCustomInfoService;
import com.wlwq.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户Service业务层处理
 *
 * @author wlwq
 * @date 2023-06-02
 */
@Service
public class CustomCustomInfoServiceImpl implements ICustomCustomInfoService {

    @Autowired
    private CustomCustomInfoMapper customCustomInfoMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private ApiAccountMapper apiAccountMapper;
    @Autowired
    private CustomFollowMapper customFollowMapper;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private CustomUserClaimMapper customUserClaimMapper;
    @Autowired
    private CustomLevelDaysMapper customLevelDaysMapper;
    @Autowired
    private CustomTypeMapper customTypeMapper;

    /**
     * 查询客户
     *
     * @param customId 客户ID
     * @return 客户
     */
    @Override
    public CustomCustomInfo selectCustomCustomInfoById(String customId) {
        CustomCustomInfo customCustomInfo = customCustomInfoMapper.selectCustomCustomInfoById(customId);
        List<SysDictTypeVO> sysDictTypeVO = translateNames();
        customCustomInfo.setCustomSource(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(customCustomInfo.getCustomSource()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("customSource"))).findAny().get().getDictLabel());
        customCustomInfo.setCustomSex(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(customCustomInfo.getCustomSex()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("sysUserSex"))).findAny().get().getDictLabel());
        customCustomInfo.setCustomLabelList(customLabel(customCustomInfo.getCustomLabel()));
        customCustomInfo.setCustomGrade(customLevelDaysMapper.selectCustomLevelDaysById(customCustomInfo.getCustomGrade()).getCustomLevel());
        customCustomInfo.setCustomDecisionsName(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(customCustomInfo.getCustomDecisions().toString()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("customDecisions"))).findAny().get().getDictLabel());
        return customCustomInfo;
    }

    /**
     * 查询客户列表
     *
     * @param customCustomInfo 客户
     * @return 客户
     */
    @Override
    public List<CustomCustomInfo> selectCustomCustomInfoList(CustomCustomInfo customCustomInfo) {
        List<CustomCustomInfo> customCustomInfos = customCustomInfoMapper.selectCustomCustomInfoList(customCustomInfo);
        List<SysDictTypeVO> sysDictTypeVO = translateNames();
        //翻译
        customCustomInfos.forEach(item -> {
            item.setCustomSource(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(item.getCustomSource()) && body.getDictType().equals("custom_source")).findAny().get().getDictLabel());
            item.setCustomSex(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(item.getCustomSex()) && body.getDictType().equals("sys_user_sex")).findAny().get().getDictLabel());
            item.setCustomLabelList(customLabel(item.getCustomLabel()));
            item.setCustomGrade(customLevelDaysMapper.selectCustomLevelDaysById(item.getCustomGrade()).getCustomLevel());
            item.setCustomDecisionsName(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(item.getCustomDecisions().toString()) && body.getDictType().equals("custom_decisions")).findAny().get().getDictLabel());
            item.setClaimStatusName(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(item.getCustomDecisions().toString()) && body.getDictType().equals("custom_decisions")).findAny().get().getDictLabel());
            if (ObjectUtil.isNotEmpty(item.getCustomResponsible())) {
                //根据用户id查询名称
                item.setCustomResponsibleName(apiAccountMapper.selectApiAccountById(item.getCustomResponsible().toString()).getNickName());
            }
        });
        return customCustomInfos;
    }

    private List<String> customLabel(String customLabel) {
        List<String> label = customTypeMapper.findLabel(customLabel);
        return label;
    }

    /**
     * 新增客户
     *
     * @param customCustomInfo 客户
     * @return 结果
     */
    @Override

    public int insertCustomCustomInfo(CustomCustomInfo customCustomInfo) throws ParseException {
        String s = IdUtil.getSnowflake(1, 1).nextIdStr();
        customCustomInfo.setCustomId(s);
        customCustomInfo.setCreateTime(DateUtils.getNowDate());
        customCustomInfo.setCustomAnnex("http://qiniu.sxhzxl.cn/head_1686884360530.png");
        customCustomInfoMapper.insertCustomCustomInfo(customCustomInfo);
        if (ObjectUtil.isNotEmpty(customCustomInfo.getCustomResponsible())) {
            claimCustom(customCustomInfo.getCustomResponsible(), customCustomInfo.getDeptId().toString(), customCustomInfo.getCompanyId().toString(), s);
        }
        return 1;

    }


    /**
     * 修改客户
     *
     * @param customCustomInfo 客户
     * @return 结果
     */
    @Override
    public int updateCustomCustomInfo(CustomCustomInfo customCustomInfo) {
        customCustomInfo.setUpdateTime(DateUtils.getNowDate());
        return customCustomInfoMapper.updateCustomCustomInfo(customCustomInfo);
    }

    /**
     * 删除客户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomCustomInfoByIds(String ids) {
        return customCustomInfoMapper.deleteCustomCustomInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户信息
     *
     * @param customId 客户ID
     * @return 结果
     */
    @Override
    public int deleteCustomCustomInfoById(String customId) {
        return customCustomInfoMapper.deleteCustomCustomInfoById(customId);
    }

    @Override
    public ApiResult getCustomInfoVO(String customId, String userId) {
        CustomInfoVO customInfoVO = customCustomInfoMapper.getCustomInfoVO(customId);
        if (ObjectUtil.isEmpty(customInfoVO)) {
            return ApiResult.fail("查询失败！");
        }
        //查询跟进记录
        List<CustomFollowVO> customFollowVO = customFollowMapper.findCustomFollowVO(customId, userId);
        if (ObjectUtil.isNotEmpty(customFollowVO) && customFollowVO.size() > 0) {
            customInfoVO.setCustomFollowVOs(customFollowVO);
        }
        //根据用户id查询名称
        if (StringUtils.isNotEmpty(customInfoVO.getCustomResponsible())) {
            customInfoVO.setCustomResponsibleName(apiAccountMapper.selectApiAccountById(customInfoVO.getCustomResponsible()).getNickName());
        }
        //翻译
        List<SysDictTypeVO> sysDictTypeVO = translateNames();
        customInfoVO.setCustomSource(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(customInfoVO.getCustomSource()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("customSource"))).findAny().get().getDictLabel());
        customInfoVO.setCustomSex(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(customInfoVO.getCustomSex()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("sysUserSex"))).findAny().get().getDictLabel());
        customInfoVO.setCustomLabelList(customLabel(customInfoVO.getCustomLabel()));
        customInfoVO.setCustomGrade(customLevelDaysMapper.selectCustomLevelDaysById(customInfoVO.getCustomGrade()).getCustomLevel());
        customInfoVO.setCustomDecisionsName(sysDictTypeVO.stream().filter(body -> body.getDictValue().equals(customInfoVO.getCustomDecisions().toString()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("customDecisions"))).findAny().get().getDictLabel());
        return ApiResult.ok(customInfoVO);
    }

    @Override
    public List<CustomVO> findCustomInfo(String customName, String customSource, Long companyId, String type) {
        //获取公司id
        List<CustomVO> customVO = customCustomInfoMapper.findCustomVO(customName, customSource, companyId);
        if (type.equals(NumberUtils.INTEGER_ONE.toString())) {
            //移除后台录入
            customVO.removeIf(s -> ObjectUtil.isEmpty(s.getStatus()));
        }
        if (type.equals(NumberUtils.INTEGER_TWO.toString())) {
            //移除最新释放
            customVO.removeIf(s -> ObjectUtil.isNotEmpty(s.getStatus()));
        }
        customVO.forEach(item -> {
            if (ObjectUtil.isNotEmpty(item.getStatus())) {
                item.setLabelName("释放");
            } else {
                item.setLabelName("后台");
            }
        });
        return customVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int claimCustom(Long userId, String deptId, String companyId, String customId) throws ParseException {
        //查询客户
        CustomCustomInfo customCustomInfo = customCustomInfoMapper.selectCustomCustomInfoById(customId);
        customCustomInfo.setDeptId(Long.parseLong(deptId));
        customCustomInfo.setCompanyId(Long.parseLong(companyId));
        customCustomInfo.setCustomResponsible(userId);
        customCustomInfo.setClaimStatus(1L);
        customCustomInfoMapper.updateCustomCustomInfo(customCustomInfo);
        //新增客户跟进状态
        CustomFollow customFollow = new CustomFollow();
        customFollow.setCustomFollowId(IdUtil.getSnowflake(1, 1).nextIdStr());
        customFollow.setCustomInfoId(customCustomInfo.getCustomId());
        customFollow.setCustomUserId(customCustomInfo.getCustomResponsible().toString());
        customFollow.setCustomContent("新建客户");
        customFollow.setCreateTime(new Date());
        customFollow.setCreateBy(userId.toString());
        customFollow.setDeptId(customCustomInfo.getDeptId());
        customFollow.setCompanyId(customCustomInfo.getCompanyId());
        customFollowMapper.insertCustomFollow(customFollow);
        //新增客户认领
        CustomUserClaim customUserClaim = new CustomUserClaim();
        customUserClaim.setCustomClaimId(IdUtil.getSnowflake(1, 1).nextIdStr());
        customUserClaim.setCustomInfoId(customCustomInfo.getCustomId());
        customUserClaim.setCustomUserId(customCustomInfo.getCustomResponsible().toString());
        customUserClaim.setCustomFollowLastTime(new Date());
        customUserClaim.setCreateTime(new Date());
        customUserClaim.setCreateBy(userId.toString());
        customUserClaim.setStatus(NumberUtils.INTEGER_ZERO.toString());
        customUserClaim.setClaimTime(new Date());
        customUserClaim.setDeptId(customCustomInfo.getDeptId());
        customUserClaim.setCompanyId(customCustomInfo.getCompanyId());
        customUserClaim.setIsFollow(NumberUtils.INTEGER_ZERO.toString());
        int customDays = customLevelDaysMapper.getCustomDays(customCustomInfo.getCustomGrade(), customCustomInfo.getCompanyId().toString());
        //当前日期加天
        customUserClaim.setCustomFollowTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LocalDate.now().plusDays(customDays) + " 23:59:59"));
        return customUserClaimMapper.insertCustomUserClaim(customUserClaim);
    }

    @Override
    public List<MyCustomVO> findMyCustomVO(CustomFindVO customFindVO) {
        List<MyCustomVO> myCustomVO;
        //type为1查询当天 不唯一查询几日内
        if (NumberUtils.INTEGER_ONE.toString().equals(customFindVO.getType())) {
            customFindVO.setDate(LocalDate.now().toString());
            myCustomVO = customCustomInfoMapper.findMyCustomVO(customFindVO);
        } else {
            //根据type增加时间查询几日内
            if (StringUtils.isNotEmpty(customFindVO.getType())) {
                customFindVO.setDate(LocalDate.now().plusDays(Long.valueOf(customFindVO.getType())).toString());
                myCustomVO = customCustomInfoMapper.findMyCustomVO(customFindVO);
            } else {
                myCustomVO = customCustomInfoMapper.findMyCustomVO(customFindVO);
            }
        }
        myCustomVO.forEach(item -> {
            if (item.getAddSource().equals(NumberUtils.INTEGER_ONE.toString())) {
                item.setLabelName("释放");
            } else {
                item.setLabelName("后台");
            }
        });
        List<SysDictTypeVO> sysDictTypeVOS = translateNames();
        myCustomVO.forEach(item -> {
            item.setCustomGrade(customLevelDaysMapper.selectCustomLevelDaysById(item.getCustomGrade()).getCustomLevel());
            item.setCustomLabelList(customLabel(item.getCustomLabel()));
            item.setStatus(sysDictTypeVOS.stream().filter(body -> body.getDictValue().equals(item.getStatus()) && body.getDictType().equals(HumpToGlideUtils.teseDemo("customFollowStatus"))).findAny().get().getDictLabel());
        });
        return myCustomVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int releaseCustom(String customId, String customClaimId) {
        //释放客户至公海
        customCustomInfoMapper.updateClaimStatus(0L, customId);
        //修改客户认领为战败
        customUserClaimMapper.updateCustomUser(1L, customClaimId);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int releaseCustoms(List<String> customIds, List<String> customClaimIds) {
        return 0;
    }

    @Override
    public CustomCountVO getCustomCountVO(String userId) {
        CustomCountVO customCountVO = new CustomCountVO();
        //查询本月-客户
        BigDecimal custom = customCustomInfoMapper.getCustom(userId, 1L, LastTimeUtil.getBeforeFirstMonthdate(), LastTimeUtil.getBeforeLastMonthdate());
        //查询本月-成交
        BigDecimal amount = customCustomInfoMapper.getAmount(userId, 1L, LastTimeUtil.getBeforeFirstMonthdate(), LastTimeUtil.getBeforeLastMonthdate());
        //查询累计
        BigDecimal custom1 = customCustomInfoMapper.getCustom(userId, 1L, null, null);
        BigDecimal amount1 = customCustomInfoMapper.getAmount(userId, 1L, null, null);
        customCountVO.setNewCustom(custom);
        customCountVO.setNewDeal(amount);
        customCountVO.setAllCustom(custom1);
        customCountVO.setAllDeal(amount1);
        return customCountVO;
    }

    @Override
    public List<CustomDictVO> findCustomDictVO(String dictType) {
        return customCustomInfoMapper.findCustomDictVO(dictType);
    }

    @Override
    public int updateCustomLabel(String customId, String customLabel) {
        return customCustomInfoMapper.updateCustomLabel(customId, customLabel);
    }

    @Override
    public CustomRankingVO getCustomRankingVO(PageParam pageParam, String userId, String companyId) {
        CustomRankingVO customRankingVO = new CustomRankingVO();
        //分页总榜
        PageHelper.startPage(pageParam.getPageNo(), pageParam.getPageSize());
        List<CustomRankingListVO> customRankingVOs = customCustomInfoMapper.findCustomRankingVO(companyId);
        Map<String, CustomRankingListVO> stringCustomRankingListVOMap = CollectionsUtil.toMap(customRankingVOs, CustomRankingListVO::getCustomUserId);
        //查找我的位置
        if (stringCustomRankingListVOMap.containsKey(userId)) {
            CustomRankingListVO customRankingListVO = stringCustomRankingListVOMap.get(userId);
            customRankingVO.setCustomUserId(customRankingListVO.getCustomUserId());
            customRankingVO.setDeptName(customRankingListVO.getDeptName());
            customRankingVO.setDealAmount(customRankingListVO.getDealAmount());
            customRankingVO.setUserName(customRankingListVO.getUserName());
            customRankingVO.setRankNo(customRankingListVO.getRankNo());
        }
        PageInfo<CustomRankingListVO> pageInfo = new PageInfo<>(customRankingVOs);
        customRankingVO.setCustomRankingList(pageInfo);
        return customRankingVO;
    }

    @Override
    public List<CustomThrowVO> findCustomThrowVO(Integer type) {
        return customCustomInfoMapper.findCustomThrowVO(type);
    }

    @Override
    public List<MyCustomVO> findMyCustom(Long userId, String customName) {
        List<MyCustomVO> myCustomVO = customCustomInfoMapper.findMyCustom(userId, customName);
        myCustomVO.forEach(item -> {
            if (item.getAddSource().equals(NumberUtils.INTEGER_ONE.toString())) {
                item.setLabelName("释放");
            } else {
                item.setLabelName("后台");
            }
        });
        return myCustomVO;
    }

    /**
     * 查询客户公共翻译批量
     */
    private List<SysDictTypeVO> translateNames() {
        /**
         * 翻译字典
         */
        List<String> ls = new ArrayList<>();
        ls.add(HumpToGlideUtils.teseDemo("customSource"));
        ls.add(HumpToGlideUtils.teseDemo("sysUserSex"));
        ls.add(HumpToGlideUtils.teseDemo("customDecisions"));
        ls.add(HumpToGlideUtils.teseDemo("customFollowStatus"));
        List<SysDictTypeVO> sysDictTypeVO = sysDictDataMapper.findSysDictTypeVO(ls);
        return sysDictTypeVO;
    }
}
