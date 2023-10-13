package com.wlwq.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Description mvc配置 使token验证拦截器生效
 * @Date 2020/9/29 15:27
 * Create By Renbowen
 */
@Configuration
public class JWTWebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationTokenInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //TODO 添加拦截器，配置拦截地址
        registry.addInterceptor(interceptor).addPathPatterns("/api/**");
    }
}

