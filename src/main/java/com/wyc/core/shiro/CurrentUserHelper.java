package com.wyc.core.shiro;


import org.apache.shiro.SecurityUtils;

import java.util.Map;

public class CurrentUserHelper{

    public static String getId(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("id");
    }

    public static String getName(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("name");
    }

    public static String getUsername(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("username");
    }

    public static String getRoleId(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("role_id");
    }
    public static String getRole(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("role");
    }
    public static String getDeptId(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("department_id");
    }
    public static String getDept(){
        return  ((Map<String,String>)SecurityUtils.getSubject().getPrincipal()).get("department");
    }
}