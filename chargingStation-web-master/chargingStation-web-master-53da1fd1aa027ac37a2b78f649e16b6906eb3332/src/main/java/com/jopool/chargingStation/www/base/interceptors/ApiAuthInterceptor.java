package com.jopool.chargingStation.www.base.interceptors;

import com.jopool.chargingStation.www.base.helper.ApplicationConfigHelper;
import com.jopool.jweb.cache.Cache;
import com.jopool.jweb.controller.AppAuth;
import com.jopool.jweb.enums.Code;
import com.jopool.jweb.enums.ModeEnum;
import com.jopool.jweb.exceptions.JWebException;
import com.jopool.jweb.handler.AppAuthHandler;
import com.jopool.jweb.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gexin on 15/3/22.
 * 权限拦截器
 */
public class ApiAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ApiAuthInterceptor.class);
    @Resource
    private Cache cacheBean;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (ModeEnum.DEVELOP == ApplicationConfigHelper.getMode()) {
            return super.preHandle(request, response, o);
        }
        String userId = request.getParameter("passportId");
        String token = userId;
        if (StringUtils.isEmpty(userId)) {
            throw new JWebException(Code.ERROR_PARAM);
        }

        AppAuth.verify(request, token, new AppAuthHandler() {
            @Override
            public void onError(Code code) {
                throw new JWebException(code);
            }
        });
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
