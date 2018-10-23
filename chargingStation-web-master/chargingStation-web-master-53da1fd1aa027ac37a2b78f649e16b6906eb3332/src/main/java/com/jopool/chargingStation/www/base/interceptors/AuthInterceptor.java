package com.jopool.chargingStation.www.base.interceptors;

import com.jopool.chargingStation.www.constants.Constants;
import com.jopool.chargingStation.www.vo.SessionUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by synn on 2017/8/28.
 */
public class AuthInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionUser user = (SessionUser) request.getSession().getAttribute(Constants.SESSION_KEY_LOGIN_USER);
        if (null == user) {
            response.sendRedirect(request.getContextPath() + "/login.htm");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
