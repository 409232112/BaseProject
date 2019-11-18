package com.wyc.logmgr.enums;

/**
 * Created by wangyc on 2019/11/18.
 */
public enum  OperationType {
    UNKNOWN("unknown"),
    DELETE("delete"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert"),
    SAVE("save");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationType(String s) {
        this.value = s;
    }
}
