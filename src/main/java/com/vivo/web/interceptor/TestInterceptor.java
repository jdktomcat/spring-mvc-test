package com.vivo.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：测试拦截器
 *
 * @author 汤旗
 * @date 2019-08-21 17:11
 */
@Component(value = "testInterceptor")
public class TestInterceptor extends HandlerInterceptorAdapter {
    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("测试拦截器");
        return true;
    }
}
