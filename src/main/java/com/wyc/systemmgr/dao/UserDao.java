package com.wyc.systemmgr.dao;


import com.wyc.base.dao.BaseDao;
import com.wyc.systemmgr.entity.User;

import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */

public interface UserDao extends BaseDao {
    Map findUserByUsername(String username);

    void changePassword(Map data);
}
