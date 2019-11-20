package com.wyc.core.base.entity;

import java.util.List;

public class Pagination {
    private long total;
    private List rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }


    public Pagination(long total,List rows){
        this.total = total;
        this.rows = rows;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder("Pagination{");
        sb.append(", total=").append(this.total);
        sb.append(", rows=").append(this.rows);
        sb.append('}');
        return sb.toString();
    }


}