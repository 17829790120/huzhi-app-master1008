package com.wlwq.information.service;

import java.util.List;
import com.wlwq.information.domain.InformationBrowseRecord;

/**
 * 资讯浏览记录Service接口
 * 
 * @author Rick wlwq
 * @date 2021-04-20
 */
public interface IInformationBrowseRecordService 
{
    /**
     * 查询资讯浏览记录
     * 
     * @param informationBrowseRecordId 资讯浏览记录ID
     * @return 资讯浏览记录
     */
    public InformationBrowseRecord selectInformationBrowseRecordById(Long informationBrowseRecordId);

    /**
     * 查询资讯浏览记录列表
     * 
     * @param informationBrowseRecord 资讯浏览记录
     * @return 资讯浏览记录集合
     */
    public List<InformationBrowseRecord> selectInformationBrowseRecordList(InformationBrowseRecord informationBrowseRecord);

    /**
     * 新增资讯浏览记录
     * 
     * @param informationBrowseRecord 资讯浏览记录
     * @return 结果
     */
    public int insertInformationBrowseRecord(InformationBrowseRecord informationBrowseRecord);

    /**
     * 修改资讯浏览记录
     * 
     * @param informationBrowseRecord 资讯浏览记录
     * @return 结果
     */
    public int updateInformationBrowseRecord(InformationBrowseRecord informationBrowseRecord);

    /**
     * 批量删除资讯浏览记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInformationBrowseRecordByIds(String ids);

    /**
     * 删除资讯浏览记录信息
     * 
     * @param informationBrowseRecordId 资讯浏览记录ID
     * @return 结果
     */
    public int deleteInformationBrowseRecordById(Long informationBrowseRecordId);
    /**
     * 查询资讯浏览记录列表
     *
     * @param informationBrowseRecord 资讯浏览记录
     * @return 资讯浏览记录集合
     */
    List<InformationBrowseRecord> selectInformationBrowseRecordNewsCenterList(InformationBrowseRecord informationBrowseRecord);
}
