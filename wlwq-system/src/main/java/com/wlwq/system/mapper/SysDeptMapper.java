package com.wlwq.system.mapper;

import com.wlwq.common.core.domain.Ztree;
import com.wlwq.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 数据层
 *
 * @author wlwq
 */
public interface SysDeptMapper {
    /**
     * 查询部门人数
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int selectDeptCount(SysDept dept);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleDeptTree(Long roleId);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    public void updateDeptStatus(SysDept dept);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

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
    List<SysDept> treeDataByCompany(SysDept sysDept);
}
