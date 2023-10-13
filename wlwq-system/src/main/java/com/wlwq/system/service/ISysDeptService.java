package com.wlwq.system.service;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.core.domain.entity.SysDept;
import com.wlwq.common.core.domain.entity.SysRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务层
 *
 * @author wlwq
 */
public interface ISysDeptService {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 查询部门管理树
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDeptTree(SysDept dept);

    /**
     * 查询部门管理树（排除下级）
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Ztree> roleDeptTreeData(SysRole role);

    /**
     * 查询部门人数
     *
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(Long parentId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDept dept);

    /**
     * Api查询城市列表
     *
     * @param dept
     * @return
     */
    public List<SysDept> selectApiDeptList(SysDept dept);

    /**
     * Api查询某个城市
     *
     * @param dept
     * @return
     */
    public SysDept selectApiNearbyCity(SysDept dept);

    /***
     * 查询部门及部门下人数统计
     * @param companyId
     * @return
     */
    List<HashMap<String, Object>> selectDeptMap(Long companyId);

    /***
     * 查询部门及部门下的人
     * @param companyId
     * @return
     */
    List<HashMap<String, Object>> selectDeptAndAccountMap(Long companyId);

    /**
     * 根据dept_id获取到分公司信息
     *
     * @param deptId
     * @return
     */
    public SysDept selectDeptByDeptId(Long deptId);

    /**
     * 根据公司查询考勤信息
     * @param dept
     * @return
     */
    public Map<String,Object> selectClock(SysDept dept);

    /**
     * 查询本公司的人
     * @param sysDept
     * @return
     */
    List<Ztree> treeDataByCompany(SysDept sysDept);
}
