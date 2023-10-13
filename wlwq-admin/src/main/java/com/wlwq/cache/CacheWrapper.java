package com.wlwq.cache;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 *  Create By Renbowen
 *  @Date: 2020/9/27 0:26
 *  @Description: Cache wrapper.
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class CacheWrapper<V> implements Serializable {

    /**
     * Cache data
     */
    private V data;

    /**
     * Expired time.
     */
    private Date expireAt;

    /**
     * Create time.
     */
    private Date createAt;
}
