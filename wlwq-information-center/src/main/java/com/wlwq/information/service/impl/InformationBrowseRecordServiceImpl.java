package com.wlwq.information.service.impl;

import java.util.List;

import com.wlwq.common.annotation.DataScope;
import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.information.mapper.InformationBrowseRecordMapper;
import com.wlwq.information.domain.InformationBrowseRecord;
import com.wlwq.information.service.IInformationBrowseRecordService;
import com.wlwq.common.core.text.Convert;

/**
 * 资讯浏览记录Service业务层处理
 *
 * @author Rick wlwq
 * @date 2021-04-20
 */
@Service
public class InformationBrowseRecordServiceImpl implements IInformationBrowseRecordService {
    @Autowired
    private InformationBrowseRecordMapper informationBrowseRecordMapper;

    /**
     * 查询资讯浏览记录
     *
     * @param informationBrowseRecordId 资讯浏览记录ID
     * @return 资讯浏览记录
     */
    @Override
    public InformationBrowseRecord selectInformationBrowseRecordById(Long informationBrowseRecordId) {
        return informationBrowseRecordMapper.selectInformationBrowseRecordById(informationBrowseRecordId);
    }

    /**
     * 查询资讯浏览记录列表
     *
     * @param informationBrowseRecord 资讯浏览记录
     * @return 资讯浏览记录
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<InformationBrowseRecord> selectInformationBrowseRecordList(InformationBrowseRecord informationBrowseRecord) {
        return informationBrowseRecordMapper.selectInformationBrowseRecordList(informationBrowseRecord);
    }

    /**
     * 新增资讯浏览记录
     *
     * @param informationBrowseRecord 资讯浏览记录
     * @return 结果
     */
    @Override
    public int insertInformationBrowseRecord(InformationBrowseRecord informationBrowseRecord) {
        informationBrowseRecord.setCreateTime(DateUtils.getNowDate());
        return informationBrowseRecordMapper.insertInformationBrowseRecord(informationBrowseRecord);
    }

    /**
     * 修改资讯浏览记录
     *
     * @param informationBrowseRecord 资讯浏览记录
     * @return 结果
     */
    @Override
    public int updateInformationBrowseRecord(InformationBrowseRecord informationBrowseRecord) {
        return informationBrowseRecordMapper.updateInformationBrowseRecord(informationBrowseRecord);
    }

    /**
     * 删除资讯浏览记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteInformationBrowseRecordByIds(String ids) {
        return informationBrowseRecordMapper.deleteInformationBrowseRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资讯浏览记录信息
     *
     * @param informationBrowseRecordId 资讯浏览记录ID
     * @return 结果
     */
    @Override
    public int deleteInformationBrowseRecordById(Long informationBrowseRecordId) {
        return informationBrowseRecordMapper.deleteInformationBrowseRecordById(informationBrowseRecordId);
    }

    /**
     * 查询资讯浏览记录列表
     *
     * @param informationBrowseRecord 资讯浏览记录
     * @return 资讯浏览记录集合
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<InformationBrowseRecord> selectInformationBrowseRecordNewsCenterList(InformationBrowseRecord informationBrowseRecord) {
        return informationBrowseRecordMapper.selectInformationBrowseRecordNewsCenterList(informationBrowseRecord);
    }
}
