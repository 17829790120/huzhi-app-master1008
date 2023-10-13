package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.api.domain.CustomExamineRecord;
import com.wlwq.api.mapper.CustomExamineRecordMapper;
import com.wlwq.api.resultVO.CustomUserVO;
import com.wlwq.common.utils.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CustomUserClaimMapper;
import com.wlwq.api.domain.CustomUserClaim;
import com.wlwq.api.service.ICustomUserClaimService;
import com.wlwq.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户认领Service业务层处理
 * 
 * @author wlwq
 * @date 2023-06-02
 */
@Service
public class CustomUserClaimServiceImpl implements ICustomUserClaimService {

    @Autowired
    private CustomUserClaimMapper customUserClaimMapper;
    @Autowired
    private CustomExamineRecordMapper customExamineRecordMapper;
    /**
     * 查询客户认领
     * 
     * @param customClaimId 客户认领ID
     * @return 客户认领
     */
    @Override
    public CustomUserClaim selectCustomUserClaimById(Long customClaimId) {
        return customUserClaimMapper.selectCustomUserClaimById(customClaimId);
    }

    /**
     * 查询客户认领列表
     * 
     * @param customUserClaim 客户认领
     * @return 客户认领
     */
    @Override
    public List<CustomUserClaim> selectCustomUserClaimList(CustomUserClaim customUserClaim) {
        return customUserClaimMapper.selectCustomUserClaimList(customUserClaim);
    }

    /**
     * 新增客户认领
     * 
     * @param customUserClaim 客户认领
     * @return 结果
     */
    @Override
    public int insertCustomUserClaim(CustomUserClaim customUserClaim) {
        customUserClaim.setCreateTime(DateUtils.getNowDate());
        return customUserClaimMapper.insertCustomUserClaim(customUserClaim);
    }

    /**
     * 修改客户认领
     * 
     * @param customUserClaim 客户认领
     * @return 结果
     */
    @Override
    public int updateCustomUserClaim(CustomUserClaim customUserClaim) {
        customUserClaim.setUpdateTime(DateUtils.getNowDate());
        return customUserClaimMapper.updateCustomUserClaim(customUserClaim);
    }

    /**
     * 删除客户认领对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomUserClaimByIds(String ids) {
        return customUserClaimMapper.deleteCustomUserClaimByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户认领信息
     * 
     * @param customClaimId 客户认领ID
     * @return 结果
     */
    @Override
    public int deleteCustomUserClaimById(Long customClaimId) {
        return customUserClaimMapper.deleteCustomUserClaimById(customClaimId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCustomUserClaimStatus(String customInfoId,String customExamineRecordId) {
        customUserClaimMapper.updateCustomUserClaimStatus(customInfoId);
        CustomExamineRecord customExamineRecord = customExamineRecordMapper.selectCustomExamineRecordById(customExamineRecordId);
        customExamineRecord.setCustomExamineStatus(NumberUtils.INTEGER_ONE.toString());
        customExamineRecordMapper.updateCustomExamineRecord(customExamineRecord);
        return 1;
    }

    @Override
    public List<CustomUserVO> findCustomUserVO(String companyId, String customName) {
        return customUserClaimMapper.findCustomUserVO(companyId,customName);
    }
}
