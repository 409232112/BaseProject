package com.wyc.shiro.interceptor;

import com.wyc.shiro.CurrentUserHelper;
import com.wyc.shiro.annotation.RoleCheck;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

/**
 * Created by wangyc on 2019/11/19.
 */

@Service
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RoleCheck roleCheck = method.getAnnotation(RoleCheck.class);
            if (roleCheck != null) {
                String[] roles = roleCheck.roles();//获取方法中设置的权限信息
                String userType = CurrentUserHelper.getRole();
                for (String role : roles) {
                    if (role.equals(userType)) {//判断用户是否有权限访问
                        return true;
                    }
                }
                throw new UnauthorizedException();
            }
            return true;
        }
        return true;
    }
}
