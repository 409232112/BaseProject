package com.wyc.systemmgr.dao;


import com.wyc.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * Created by wangyc on 2019/10/31.
 */

public interface ModelDao extends BaseDao {
    Integer hasChildren(String id);

    List<Map> findByUserId(String userId);

    void delUserModel(String userId);

    void insertUserModel(@Param("datas") List<Map> datas);
}
