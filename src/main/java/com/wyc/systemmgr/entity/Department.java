package com.wyc.systemmgr.entity;

import java.sql.Timestamp;

public class Department {

    private String id;
    private String name;
    private String code;
    private String level;
    private String parent_id;
    private String seq;
    private String link_user_name;
    private String link_phone;
    private String comments;
    private Timestamp created_time;
    private String created_user_id;
    private Timestamp update_time;
    private String update_user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getLink_user_name() {
        return link_user_name;
    }

    public void setLink_user_name(String link_user_name) {
        this.link_user_name = link_user_name;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }

    public String getCreated_user_id() {
        return created_user_id;
    }

    public void setCreated_user_id(String created_user_id) {
        this.created_user_id = created_user_id;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getUpdate_user_id() {
        return update_user_id;
    }

    public void setUpdate_user_id(String update_user_id) {
        this.update_user_id = update_user_id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", level='" + level + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", seq='" + seq + '\'' +
                ", link_user_name='" + link_user_name + '\'' +
                ", link_phone='" + link_phone + '\'' +
                ", comments='" + comments + '\'' +
                ", created_time=" + created_time +
                ", created_user_id='" + created_user_id + '\'' +
                ", update_time=" + update_time +
                ", update_user_id='" + update_user_id + '\'' +
                '}';
    }


}