package com.wlwq.api.resultVO.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseOrderLogisticsDetailResultVO
 * @Description 课程订单物流详情封装
 * @Date 2021/1/29 18:13
 * @Author Rick Jen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseOrderLogisticsDetailResultVO implements Serializable {

    /** 快递状态. */
    private String logisticsStatus;

    /** 快递公司. */
    private String logisticsCompany;

    /** 快递编号. */
    private String logisticsNumber;

    /** 快递公司电话. */
    private String logisticsPhone;

    /** 订单物流信息. */
    private List<Map<String, Object>> logisticsInformationList;

}
