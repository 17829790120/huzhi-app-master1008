package com.wlwq.api.service.impl;

import java.util.List;

import com.wlwq.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wlwq.api.mapper.GroupInforMapper;
import com.wlwq.api.domain.GroupInfor;
import com.wlwq.api.service.IGroupInforService;
import com.wlwq.common.core.text.Convert;

/**
 * 小组信息Service业务层处理
 *
 * @author wwb
 * @date 2023-06-10
 */
@Service
public class GroupInforServiceImpl implements IGroupInforService {

    @Autowired
    private GroupInforMapper groupInforMapper;

    /**
     * 查询小组信息
     *
     * @param groupInforId 小组信息ID
     * @return 小组信息
     */
    @Override
    public GroupInfor selectGroupInforById(Long groupInforId) {
        return groupInforMapper.selectGroupInforById(groupInforId);
    }

    /**
     * 查询小组信息列表
     *
     * @param groupInfor 小组信息
     * @return 小组信息
     */
    @Override
    public List<GroupInfor> selectGroupInforList(GroupInfor groupInfor) {
        return groupInforMapper.selectGroupInforList(groupInfor);
    }

    /**
     * 新增小组信息
     *
     * @param groupInfor 小组信息
     * @return 结果
     */
    @Override
    public int insertGroupInfor(GroupInfor groupInfor) {
        groupInfor.setCreateTime(DateUtils.getNowDate());
        return groupInforMapper.insertGroupInfor(groupInfor);
    }

    /**
     * 修改小组信息
     *
     * @param groupInfor 小组信息
     * @return 结果
     */
    @Override
    public int updateGroupInfor(GroupInfor groupInfor) {
        groupInfor.setUpdateTime(DateUtils.getNowDate());
        return groupInforMapper.updateGroupInfor(groupInfor);
    }

    /**
     * 删除小组信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroupInforByIds(String ids) {
        return groupInforMapper.deleteGroupInforByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除小组信息信息
     *
     * @param groupInforId 小组信息ID
     * @return 结果
     */
    @Override
    public int deleteGroupInforById(Long groupInforId) {
        return groupInforMapper.deleteGroupInforById(groupInforId);
    }
}
