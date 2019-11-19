package com.wyc.shiro.config;

import com.wyc.shiro.interceptor.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by wangyc on 2019/11/19.
 */
@Configuration
public class DefineAdapter implements WebMvcConfigurer {
    @Autowired
    public RoleInterceptor roleInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor).addPathPatterns("/**");
    }
}