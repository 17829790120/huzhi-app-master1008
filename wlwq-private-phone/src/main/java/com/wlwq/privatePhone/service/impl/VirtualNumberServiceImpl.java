package com.wlwq.privatePhone.service.impl;

import java.util.List;
import com.wlwq.common.utils.DateUtils;
import com.wlwq.privatePhone.domain.VirtualNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.privatePhone.mapper.VirtualNumberMapper;
import com.wlwq.privatePhone.service.IVirtualNumberService;
import com.wlwq.common.core.text.Convert;

/**
 * 虚拟号码Service业务层处理
 * 
 * @author Rick wlwq
 * @date 2021-04-01
 */
@Service
public class VirtualNumberServiceImpl implements IVirtualNumberService 
{
    @Autowired
    private VirtualNumberMapper virtualNumberMapper;

    /**
     * 查询虚拟号码
     * 
     * @param virtualNumberId 虚拟号码ID
     * @return 虚拟号码
     */
    @Override
    public VirtualNumber selectVirtualNumberById(Long virtualNumberId)
    {
        return virtualNumberMapper.selectVirtualNumberById(virtualNumberId);
    }

    /**
     * 查询虚拟号码列表
     * 
     * @param virtualNumber 虚拟号码
     * @return 虚拟号码
     */
    @Override
    public List<VirtualNumber> selectVirtualNumberList(VirtualNumber virtualNumber)
    {
        return virtualNumberMapper.selectVirtualNumberList(virtualNumber);
    }

    /**
     * 新增虚拟号码
     * 
     * @param virtualNumber 虚拟号码
     * @return 结果
     */
    @Override
    public int insertVirtualNumber(VirtualNumber virtualNumber)
    {
        virtualNumber.setCreateTime(DateUtils.getNowDate());
        return virtualNumberMapper.insertVirtualNumber(virtualNumber);
    }

    /**
     * 修改虚拟号码
     * 
     * @param virtualNumber 虚拟号码
     * @return 结果
     */
    @Override
    public int updateVirtualNumber(VirtualNumber virtualNumber)
    {
        virtualNumber.setUpdateTime(DateUtils.getNowDate());
        return virtualNumberMapper.updateVirtualNumber(virtualNumber);
    }

    /**
     * 删除虚拟号码对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVirtualNumberByIds(String ids)
    {
        return virtualNumberMapper.deleteVirtualNumberByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除虚拟号码信息
     * 
     * @param virtualNumberId 虚拟号码ID
     * @return 结果
     */
    @Override
    public int deleteVirtualNumberById(Long virtualNumberId)
    {
        return virtualNumberMapper.deleteVirtualNumberById(virtualNumberId);
    }

    /**
     * 获取一个可使用的隐私号码
     * @return
     */
    @Override
    public VirtualNumber selectCanUseVirtualNumber() {
        return virtualNumberMapper.selectCanUseVirtualNumber();
    }


    /**
     * 根据虚拟手机号修改虚拟号码
     */
    @Override
    public int updateVirtualNumberByVirtualNumber(String privateNum, int status) {
        return virtualNumberMapper.updateVirtualNumberByVirtualNumber(privateNum,status);
    }

    /**
     * 根据隐私号码获取隐私号码对象
     * @param privateNumber
     * @return
     */
    @Override
    public VirtualNumber selectVirtualNumberByPrivatePhone(String privateNumber) {
        return virtualNumberMapper.selectVirtualNumberByPrivatePhone(privateNumber);
    }

    /**
     * 根据绑定id查询
     * @param subId
     * @return
     */
    @Override
    public VirtualNumber selectVirtualNumberBySubId(String subId) {
        return virtualNumberMapper.selectVirtualNumberBySubId(subId);
    }
}
