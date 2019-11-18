package com.wyc.logmgr.entity;


import java.sql.Timestamp;

/**
 * Created by wangyc on 2019/11/18.
 */
public class OperationLog {
    private  String id;
    private  String userId;
    private  String name;
    private  String username;
    private  String department;
    private  String url;
    private  String methodName;
    private  String params;
    private  String operationObject;
    private  String operationType;
    private  Timestamp operationTime;
    private  String returnValue;
    private  String ip;
    private  String runTime;

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

    public  String  getDepartment(){
        return  this.department;
    };
    public  void  setDepartment(String department){
        this.department=department;
    }

    public  String  getUrl(){
        return  this.url;
    };
    public  void  setUrl(String url){
        this.url=url;
    }

    public  String  getMethodName(){
        return  this.methodName;
    };
    public  void  setMethodName(String methodName){
        this.methodName=methodName;
    }

    public  String  getParams(){
        return  this.params;
    };
    public  void  setParams(String params){
        this.params=params;
    }

    public  String  getOperationObject(){
        return  this.operationObject;
    };
    public  void  setOperationObject(String operationObject){
        this.operationObject=operationObject;
    }

    public  String  getOperationType(){
        return  this.operationType;
    };
    public  void  setOperationType(String operationType){
        this.operationType=operationType;
    }

    public  Timestamp  getOperationTime(){
        return  this.operationTime;
    };
    public  void  setOperationTime(Timestamp operationTime){
        this.operationTime=operationTime;
    }

    public  String  getReturnValue(){
        return  this.returnValue;
    };
    public  void  setReturnValue(String returnValue){
        this.returnValue=returnValue;
    }

    public  String  getIp(){
        return  this.ip;
    };
    public  void  setIp(String ip){
        this.ip=ip;
    }

    public  String  getRunTime(){
        return  this.runTime;
    };
    public  void  setRunTime(String runTime){
        this.runTime=runTime;
    }
}
