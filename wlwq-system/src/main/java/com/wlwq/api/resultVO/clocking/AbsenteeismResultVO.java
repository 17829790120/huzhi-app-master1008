package com.wlwq.api.resultVO.clocking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 旷工数量及日期
 * @author Administrator
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbsenteeismResultVO {

    /**
     * 旷工数量
     */
    private Integer absenteeismCount;

    /**
     * 旷工日期列表
     */
    private List<ClockingDayResultVO> clockingDayResultVOList;

}
