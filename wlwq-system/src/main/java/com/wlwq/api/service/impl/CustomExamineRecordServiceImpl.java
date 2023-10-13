package com.wlwq.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.wlwq.api.mapper.CustomTypeMapper;
import com.wlwq.api.resultVO.CustomExamineVO;
import com.wlwq.common.utils.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.CustomExamineRecordMapper;
import com.wlwq.api.domain.CustomExamineRecord;
import com.wlwq.api.service.ICustomExamineRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 客户成交Service业务层处理
 * 
 * @author wlwq
 * @date 2023-06-10
 */
@Service
public class CustomExamineRecordServiceImpl implements ICustomExamineRecordService {

    @Autowired
    private CustomExamineRecordMapper customExamineRecordMapper;
    @Autowired
    private CustomTypeMapper customTypeMapper;

    /**
     * 查询客户成交
     * 
     * @param customExamineRecordId 客户成交ID
     * @return 客户成交
     */
    @Override
    public CustomExamineRecord selectCustomExamineRecordById(String customExamineRecordId) {
        return customExamineRecordMapper.selectCustomExamineRecordById(customExamineRecordId);
    }

    /**
     * 查询客户成交列表
     * 
     * @param customExamineRecord 客户成交
     * @return 客户成交
     */
    @Override
    public List<CustomExamineRecord> selectCustomExamineRecordList(CustomExamineRecord customExamineRecord) {
        return customExamineRecordMapper.selectCustomExamineRecordList(customExamineRecord);
    }

    /**
     * 新增客户成交
     * 
     * @param customExamineRecord 客户成交
     * @return 结果
     */
    @Override
    public int insertCustomExamineRecord(CustomExamineRecord customExamineRecord) {
        customExamineRecord.setCreateTime(DateUtils.getNowDate());
        customExamineRecord.setCustomExamineStatus(NumberUtils.INTEGER_ZERO.toString());
        customExamineRecord.setExamineTime(new Date());
        return customExamineRecordMapper.insertCustomExamineRecord(customExamineRecord);
    }

    /**
     * 修改客户成交
     * 
     * @param customExamineRecord 客户成交
     * @return 结果
     */
    @Override
    public int updateCustomExamineRecord(CustomExamineRecord customExamineRecord) {
        return customExamineRecordMapper.updateCustomExamineRecord(customExamineRecord);
    }

    /**
     * 删除客户成交对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomExamineRecordByIds(String ids) {
        return customExamineRecordMapper.deleteCustomExamineRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户成交信息
     * 
     * @param customExamineRecordId 客户成交ID
     * @return 结果
     */
    @Override
    public int deleteCustomExamineRecordById(Long customExamineRecordId) {
        return customExamineRecordMapper.deleteCustomExamineRecordById(customExamineRecordId);
    }

    @Override
    public CustomExamineVO getCustomExamineVO(String customClaimId) {
        CustomExamineVO customExamineVO = customExamineRecordMapper.getCustomExamineVO(customClaimId);
        customExamineVO.setCustomLabels(customTypeMapper.findLabel(customExamineVO.getCustomLabel()));
        return customExamineVO;
    }


}
