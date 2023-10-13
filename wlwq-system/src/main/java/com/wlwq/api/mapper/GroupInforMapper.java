package com.wlwq.api.mapper;

import java.util.List;

import com.wlwq.api.domain.GroupInfor;

/**
 * 小组信息Mapper接口
 *
 * @author wwb
 * @date 2023-06-10
 */
public interface GroupInforMapper {
    /**
     * 查询小组信息
     *
     * @param groupInforId 小组信息ID
     * @return 小组信息
     */
    public GroupInfor selectGroupInforById(Long groupInforId);

    /**
     * 查询小组信息列表
     *
     * @param groupInfor 小组信息
     * @return 小组信息集合
     */
    public List<GroupInfor> selectGroupInforList(GroupInfor groupInfor);

    /**
     * 新增小组信息
     *
     * @param groupInfor 小组信息
     * @return 结果
     */
    public int insertGroupInfor(GroupInfor groupInfor);

    /**
     * 修改小组信息
     *
     * @param groupInfor 小组信息
     * @return 结果
     */
    public int updateGroupInfor(GroupInfor groupInfor);

    /**
     * 删除小组信息
     *
     * @param groupInforId 小组信息ID
     * @return 结果
     */
    public int deleteGroupInforById(Long groupInforId);

    /**
     * 批量删除小组信息
     *
     * @param groupInforIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupInforByIds(String[] groupInforIds);
}
