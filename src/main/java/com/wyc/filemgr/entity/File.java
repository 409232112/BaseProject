package com.wyc.filemgr.entity;


import java.sql.Timestamp;



public class File {


    private  String id;

    //文件名
    private  String file_name;

    //服务器文件路径
    private  String file_path;

    //文件类型码表code
    private  String file_type_code;

    //文件类型，1是私人，2是部门，3是公共
    private  String file_type;

    //文件大小单位 MB
    private  String file_size;

    //文件描述
    private  String comments;

    //创建人
    private  String created_user_id;


    private  Timestamp created_time;

    //更新人
    private  String update_user_id;


    private  Timestamp update_time;

    //当type为2是，有值
    private  String department_id;


    public  String  getId(){
        return  this.id;
    };
    public  void  setId(String id){
        this.id=id;
    }

    public  String  getFile_name(){
        return  this.file_name;
    };
    public  void  setFile_name(String file_name){
        this.file_name=file_name;
    }

    public  String  getFile_path(){
        return  this.file_path;
    };
    public  void  setFile_path(String file_path){
        this.file_path=file_path;
    }

    public  String  getFile_type_code(){
        return  this.file_type_code;
    };
    public  void  setFile_type_code(String file_type_code){
        this.file_type_code=file_type_code;
    }

    public  String  getFile_type(){
        return  this.file_type;
    };
    public  void  setFile_type(String file_type){
        this.file_type=file_type;
    }

    public  String  getFile_size(){
        return  this.file_size;
    };
    public  void  setFile_size(String file_size){
        this.file_size=file_size;
    }

    public  String  getComments(){
        return  this.comments;
    };
    public  void  setComments(String comments){
        this.comments=comments;
    }

    public  String  getCreated_user_id(){
        return  this.created_user_id;
    };
    public  void  setCreated_user_id(String created_user_id){
        this.created_user_id=created_user_id;
    }

    public  Timestamp  getCreated_time(){
        return  this.created_time;
    };
    public  void  setCreated_time(Timestamp created_time){
        this.created_time=created_time;
    }

    public  String  getUpdate_user_id(){
        return  this.update_user_id;
    };
    public  void  setUpdate_user_id(String update_user_id){
        this.update_user_id=update_user_id;
    }

    public  Timestamp  getUpdate_time(){
        return  this.update_time;
    };
    public  void  setUpdate_time(Timestamp update_time){
        this.update_time=update_time;
    }

    public  String  getDepartment_id(){
        return  this.department_id;
    };
    public  void  setDepartment_id(String department_id){
        this.department_id=department_id;
    }
}