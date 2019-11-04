package com.wyc.base.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {
    void insert(Object o);

    void update(Object o);

    void delete(int id);

    List<Map> find(Map params);
}