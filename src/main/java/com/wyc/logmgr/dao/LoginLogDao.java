package com.wyc.logmgr.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/11/14.
 */
public interface LoginLogDao {
    void insert(Object o);

    List<Map> find(Map params);

    List<Map> queryForBarChart(Map params);

    List<Map> queryForPieChart(Map params);

}
