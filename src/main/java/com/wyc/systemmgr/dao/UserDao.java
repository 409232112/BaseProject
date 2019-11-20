package com.wyc.systemmgr.dao;


import com.wyc.core.base.dao.BaseDao;

import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

public interface UserDao extends BaseDao {
    Map findUserByUsername(String username);

    void changePassword(Map data);
}
