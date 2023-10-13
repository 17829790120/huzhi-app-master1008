package com.wlwq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName VehicleLicenseInfo
 * @Description 行驶证识别返回值封装
 * @Date 2021/7/17 18:01
 * @Author Rick wlwq
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VehicleLicenseInfo implements Serializable {

    /** 车辆识别代号. */
    private String vehicleNumber;

    /** 地址. */
    private String address;

    /** 发证日期. */
    private String issueDate;

    /** 发证单位. */
    private String issueUnit;

    /** 车辆品牌. */
    private String vehicleBrand;

    /** 车辆类型. */
    private String vehicleType;

    /** 所有人. */
    private String owner;

    /** 使用性质. */
    private String useNature;

    /** 发动机号码. */
    private String engineNumber;

    /** 车牌号码. */
    private String vehicleLicenseNumber;

    /** 注册日期. */
    private String registerDate;

    /** 档案编号. */
    private String documentNumber;

    /** 核定载人数. */
    private String loadNumber;

    /** 总质量. */
    private String totalWeight;

    /** 整备质量. */
    private String curbWeight;

    /** 核定载质量. */
    private String loadWeight;

    /** 外廓尺寸. */
    private String gabarite;

    /** 准牵引总质量. */
    private String tractionMass;

    /** 备注. */
    private String remark;

    /** 检验记录. */
    private String inspectionRecord;

}
