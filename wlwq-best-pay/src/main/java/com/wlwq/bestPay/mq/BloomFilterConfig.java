package com.wlwq.bestPay.mq;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BloomFilterConfig
 * @Description 布隆过滤拦截器
 * @Date 2021/10/19 16:24
 * @Author
 */
@Configuration
public class BloomFilterConfig {

    @Bean
    public BitMapBloomFilter bitMapBloomFilter() {
        return new BitMapBloomFilter(512);
    }

}
