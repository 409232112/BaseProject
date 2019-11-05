package com.wyc.systemmgr.dao;


import com.wyc.base.dao.BaseDao;

import java.util.List;
import java.util.Map;


/**
 * Created by wangyc on 2019/10/31.
 */

public interface DepartmentDao extends BaseDao {
    Integer hasChildren(String id);

    List<Map> getDeptParentList(String id);

}
