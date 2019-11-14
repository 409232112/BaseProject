package com.wyc.logmgr.entity;

import java.sql.Timestamp;

/**
 * Created by wangyc on 2019/11/14.
 */
public class LoginLog {
    private  String id;
    private  String userId;
    private  String name;
    private  String username;
    private  String role;
    private  String department;
    private Timestamp loginTime;
    private  String loginIp;
    private  String userAgent;
    public  String  getId(){
        return  this.id;
    };
    public  void  setId(String id){
        this.id=id;
    }
    public  String  getUserId(){
        return  this.userId;
    };
    public  void  setUserId(String userId){
        this.userId=userId;
    }
    public  String  getName(){
        return  this.name;
    };
    public  void  setName(String name){
        this.name=name;
    }
    public  String  getUsername(){
        return  this.username;
    };
    public  void  setUsername(String username){
        this.username=username;
    }
    public  String  getRole(){
        return  this.role;
    };
    public  void  setRole(String role){
        this.role=role;
    }
    public  String  getDepartment(){
        return  this.department;
    };
    public  void  setDepartment(String department){
        this.department=department;
    }
    public  Timestamp  getLoginTime(){
        return  this.loginTime;
    };
    public  void  setLoginTime(Timestamp loginTime){
        this.loginTime=loginTime;
    }
    public  String  getLoginIp(){
        return  this.loginIp;
    };
    public  void  setLoginIp(String loginIp){
        this.loginIp=loginIp;
    }
    public  String  getUserAgent(){
        return  this.userAgent;
    };
    public  void  setUserAgent(String userAgent){
        this.userAgent=userAgent;
    }

}
