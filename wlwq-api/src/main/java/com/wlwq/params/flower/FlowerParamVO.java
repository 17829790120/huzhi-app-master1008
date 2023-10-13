package com.wlwq.params.flower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author gaoce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowerParamVO implements Serializable {

    /**
     * 用户集合
     */
    @NotNull(message = "请传入要求要送的相关信息！")
    private List<AccountParamVO> flowerList;


    /**
     * 红花数量(个),最小值为1
     */
    @Min(1)
    @NotNull(message = "请传入红花数量(个)")
    private Integer num;

    /**
     * 奖励理由
     */
    @NotBlank(message = "请传入奖励理由！")
    private String remark;
}
