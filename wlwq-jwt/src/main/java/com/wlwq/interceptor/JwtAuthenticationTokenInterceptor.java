package com.wlwq.interceptor;

import com.wlwq.annotation.PassToken;
import com.wlwq.service.TokenService;
import com.wlwq.vo.LoginAccount;
import com.wlwq.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * token过滤器 验证token有效性
 *
 * @author Renbowen
 */
@Component
@Configuration
@Slf4j
public class JwtAuthenticationTokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if (!(object instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查是否有PassToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
                // 检查用户当前是否携带token
                // 未携带token 直接返回
//                if (StringUtils.isBlank(tokenService.getToken(request))) {
//                    return true;
//                }
                // 有携带token
            } else {
                if (StringUtils.isNotNull(tokenService.getLoginUser(request))) {
                    try { // 刷新token有效期
                        LoginAccount loginAccount = tokenService.getLoginUser(request);
                        tokenService.verifyToken(loginAccount);
                    } catch (Exception exception) {
                        log.info("有PassToken注解的情况下接口携带了token，刷新token有效期异常！");
                    }
                    return true;
                }
            }
        }
        // 验证并刷新token有效期
        LoginAccount loginAccount = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginAccount)) {
            tokenService.verifyToken(loginAccount);
            return true;
        }
        return false;
    }
}
