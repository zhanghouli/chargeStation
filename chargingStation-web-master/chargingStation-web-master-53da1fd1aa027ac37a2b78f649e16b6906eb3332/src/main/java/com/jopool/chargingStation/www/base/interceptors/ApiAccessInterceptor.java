package com.jopool.chargingStation.www.base.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gexin on 15/3/22.
 *  跨域拦截器
 */
public class ApiAccessInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ApiAccessInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //支持跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Headers", "Accept-Ranges");
        response.setHeader("Access-Control-Allow-Headers", "Content-Encoding");
        response.setHeader("Access-Control-Allow-Headers", "Content-Length");
        response.setHeader("Access-Control-Allow-Headers", "Content-Range");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Expose-Headers");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Allow", "GET");
        return super.preHandle(request, response, o);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        super.postHandle(httpServletRequest, httpServletResponse, o, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        super.afterCompletion(httpServletRequest, httpServletResponse, o, e);
    }
}
