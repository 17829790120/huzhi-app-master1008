package com.wlwq.common.apiResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : PageParam  //类名
 * @Description : 分页参数接收  //描述
 * @Author : Renbowen  //作者
 * @Date: 2019-12-10 15:27  //时间
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageParam {

    @Builder.Default
    private Integer pageNo = 1;

    @Builder.Default
    private Integer pageSize = 8;

}
