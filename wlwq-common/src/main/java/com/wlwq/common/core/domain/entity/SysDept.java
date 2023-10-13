package com.wlwq.common.core.domain.entity;

import javax.validation.constraints.*;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.wlwq.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 部门表 sys_dept
 *
 * @author wlwq
 */
//@Builder
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 城市等级 -1包括查询总公司与分公司
     */
    private Integer deptLevel;

    /**
     * 父部门名称
     */
    private String parentName;


    /**
     * 城市编码
     */
    private String countyCoding;

    private Integer tag;

    /**
     * 企业员工上班时间
     */
    private String onWork;

    /**
     * 企业员工下班时间
     */
    private String offWork;

    /**
     * 企业员工打卡范围(米)
     */
    private Long clockingRange;

    /**
     * 企业员工考勤打卡赠送积分
     */
    private Long clockingScore;

    /**
     * 企业员工外出签到赠送积分
     */
    private Long outwardCheckScore;

    /**
     * 打卡经度
     */
    private String lon;

    /**
     * 打卡维度
     */
    private String lat;

    /**
     * 地址
     */
    private String address;

    /**
     * 详细地址
     */
    private String addressDetail;

    /**
     * 补卡次数
     */
    private Integer reissueNum;

    /**
     * 周几，可以是多个
     */
    private String weeks;

    /**
     * 0:否 1：节假日休息
     */
    private Integer holidaysStatus;

    /**
     * 部门人数
     */
    public Integer deptPeopleCount;

    /**
     * 部门提交的数量(日精进)
     */
    private Integer subCount;

    /**
     * 公司图片
     */
    private String companyImg;

    public String getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public Integer getDeptPeopleCount() {
        return deptPeopleCount;
    }

    public void setDeptPeopleCount(Integer deptPeopleCount) {
        this.deptPeopleCount = deptPeopleCount;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public Integer getHolidaysStatus() {
        return holidaysStatus;
    }

    public void setHolidaysStatus(Integer holidaysStatus) {
        this.holidaysStatus = holidaysStatus;
    }

    public Integer getReissueNum() {
        return reissueNum;
    }

    public void setReissueNum(Integer reissueNum) {
        this.reissueNum = reissueNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOnWork() {
        return onWork;
    }

    public void setOnWork(String onWork) {
        this.onWork = onWork;
    }

    public String getOffWork() {
        return offWork;
    }

    public void setOffWork(String offWork) {
        this.offWork = offWork;
    }

    public Long getClockingRange() {
        return clockingRange;
    }

    public void setClockingRange(Long clockingRange) {
        this.clockingRange = clockingRange;
    }

    public Long getClockingScore() {
        return clockingScore;
    }

    public void setClockingScore(Long clockingScore) {
        this.clockingScore = clockingScore;
    }

    public Long getOutwardCheckScore() {
        return outwardCheckScore;
    }

    public void setOutwardCheckScore(Long outwardCheckScore) {
        this.outwardCheckScore = outwardCheckScore;
    }

    /**
     * 城市列表
     */
    private List<SysDept> cityList;

    public List<SysDept> getCityList() {
        return cityList;
    }

    public void setCityList(List<SysDept> cityList) {
        this.cityList = cityList;
    }

    public String getCountyCoding() {
        return countyCoding;
    }

    public void setCountyCoding(String countyCoding) {
        this.countyCoding = countyCoding;
    }

    public Integer getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Integer deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

//    @NotBlank(message = "显示顺序不能为空")
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deptId", getDeptId())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("deptName", getDeptName())
                .append("orderNum", getOrderNum())
                .append("leader", getLeader())
                .append("phone", getPhone())
                .append("email", getEmail())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("tag", getTag())
                .append("deptLevel", getDeptLevel())
                .append("onWork", getOnWork())
                .append("offWork", getOffWork())
                .append("clockingRange", getClockingRange())
                .append("clockingScore", getClockingScore())
                .append("outwardCheckScore", getOutwardCheckScore())
                .append("lon", getLon())
                .append("lat", getLat())
                .append("address", getAddress())
                .append("addressDetail", getAddressDetail())
                .append("reissueNum", getReissueNum())
                .append("weeks", getWeeks())
                .append("holidaysStatus", getHolidaysStatus())
                .append("deptPeopleCount", getDeptPeopleCount())
                .append("subCount", getSubCount())
                .append("companyImg", getCompanyImg())
                .toString();
    }
}
