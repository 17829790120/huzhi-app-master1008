package com.wlwq.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.wlwq.api.domain.CustomCustomInfo;
import com.wlwq.api.mapper.CustomCustomInfoMapper;
import com.wlwq.api.mapper.CustomLevelDaysMapper;
import com.wlwq.api.mapper.CustomUserClaimMapper;
import com.wlwq.api.resultVO.CustomFollowListVO;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CustomFollowMapper;
import com.wlwq.api.domain.CustomFollow;
import com.wlwq.api.service.ICustomFollowService;
import com.wlwq.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户跟进Service业务层处理
 * 
 * @author wlwq
 * @date 2023-06-02
 */
@Service
public class CustomFollowServiceImpl implements ICustomFollowService {

    @Autowired
    private CustomFollowMapper customFollowMapper;
    @Autowired
    private CustomUserClaimMapper customUserClaimMapper;
    @Autowired
    private CustomLevelDaysMapper customLevelDaysMapper;
    @Autowired
    private CustomCustomInfoMapper customCustomInfoMapper;
    /**
     * 查询客户跟进
     * 
     * @param customFollowId 客户跟进ID
     * @return 客户跟进
     */
    @Override
    public CustomFollow selectCustomFollowById(String customFollowId) {
        return customFollowMapper.selectCustomFollowById(customFollowId);
    }

    /**
     * 查询客户跟进列表
     * 
     * @param customFollow 客户跟进
     * @return 客户跟进
     */
    @Override
    public List<CustomFollow> selectCustomFollowList(CustomFollow customFollow) {
        return customFollowMapper.selectCustomFollowList(customFollow);
    }

    /**
     * 新增客户跟进
     * 
     * @param customFollow 客户跟进
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomFollow(CustomFollow customFollow) throws ParseException {
        customFollow.setCustomFollowId(IdUtil.getSnowflake(1,1).nextIdStr());
        customFollow.setCreateTime(DateUtils.getNowDate());
        int i = customFollowMapper.insertCustomFollow(customFollow);
        //修改最后最后跟进时间
        customUserClaimMapper.updateCustomFollowTime(customFollow.getCustomClaimId());
        return 1;
    }

    /**
     * 修改客户跟进
     * 
     * @param customFollow 客户跟进
     * @return 结果
     */
    @Override
    public int updateCustomFollow(CustomFollow customFollow) {
        customFollow.setUpdateTime(DateUtils.getNowDate());
        return customFollowMapper.updateCustomFollow(customFollow);
    }

    /**
     * 删除客户跟进对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomFollowByIds(String ids) {
        return customFollowMapper.deleteCustomFollowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户跟进信息
     * 
     * @param customFollowId 客户跟进ID
     * @return 结果
     */
    @Override
    public int deleteCustomFollowById(String customFollowId) {
        return customFollowMapper.deleteCustomFollowById(customFollowId);
    }

    @Override
    public List<CustomFollowListVO> findCustomFollowListVO(String companyId,String customName) {
        return customFollowMapper.findCustomFollows(companyId,customName);
    }
}
