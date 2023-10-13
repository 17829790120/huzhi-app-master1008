package com.wlwq.privatePhone.mapper;

import java.util.List;

import com.wlwq.privatePhone.domain.VirtualNumber;
import org.apache.ibatis.annotations.Param;

/**
 * 虚拟号码Mapper接口
 *
 * @author Rick wlwq
 * @date 2021-04-01
 */
public interface VirtualNumberMapper {
    /**
     * 查询虚拟号码
     *
     * @param virtualNumberId 虚拟号码ID
     * @return 虚拟号码
     */
    public VirtualNumber selectVirtualNumberById(Long virtualNumberId);

    /**
     * 查询虚拟号码列表
     *
     * @param virtualNumber 虚拟号码
     * @return 虚拟号码集合
     */
    public List<VirtualNumber> selectVirtualNumberList(VirtualNumber virtualNumber);

    /**
     * 新增虚拟号码
     *
     * @param virtualNumber 虚拟号码
     * @return 结果
     */
    public int insertVirtualNumber(VirtualNumber virtualNumber);

    /**
     * 修改虚拟号码
     *
     * @param virtualNumber 虚拟号码
     * @return 结果
     */
    public int updateVirtualNumber(VirtualNumber virtualNumber);

    /**
     * 删除虚拟号码
     *
     * @param virtualNumberId 虚拟号码ID
     * @return 结果
     */
    public int deleteVirtualNumberById(Long virtualNumberId);

    /**
     * 批量删除虚拟号码
     *
     * @param virtualNumberIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteVirtualNumberByIds(String[] virtualNumberIds);

    /**
     * 获取一个可使用的隐私号码
     *
     * @return
     */
    VirtualNumber selectCanUseVirtualNumber();

    /**
     * 根据虚拟手机号修改虚拟号码
     *
     * @param virtualNumber
     */
    int updateVirtualNumberByVirtualNumber(@Param("virtualNumber") String virtualNumber, @Param("status") Integer status);

    /**
     * 根据隐私号码获取隐私号码对象
     *
     * @param privateNumber
     * @return
     */
    VirtualNumber selectVirtualNumberByPrivatePhone(@Param("privateNumber") String privateNumber);

    /**
     * 根据绑定id查询
     *
     * @param subId
     * @return
     */
    VirtualNumber selectVirtualNumberBySubId(@Param("subId") String subId);
}
