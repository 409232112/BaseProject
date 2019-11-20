package com.wyc.core.shiro.interceptor;

import com.wyc.core.shiro.CurrentUserHelper;

import com.wyc.systemmgr.dao.ModelDao;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/19.
 */

@Service
public class RoleInterceptor implements HandlerInterceptor {
    @Autowired
    private ModelDao mdeolDao;

    /**
     * 通过RoleCheck注解控制角色权限
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    /**
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
    }*/

    /**
     * 通过查询数据库用户权限控制url
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnauthorizedException {

        String requestUrl = request.getRequestURI();

        List<Map> datas = mdeolDao.findForMenu(CurrentUserHelper.getId());
        List<String> urls = new ArrayList<>();
        for(int i =0 ;i<datas.size();i++){
            urls.add(String.valueOf("/"+datas.get(i).get("url")));
        }

        if (!urls.contains(requestUrl)){
              return false;
            //throw new UnauthorizedException();
        }
        return true;
    }
}
