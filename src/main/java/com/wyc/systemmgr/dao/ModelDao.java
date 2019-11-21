package com.wyc.systemmgr.dao;


import com.wyc.core.base.dao.BaseDao;
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

    List<Map> findByRoleId(String roleId);

    void delRoleModel(String roleId);

    void insertRoleModel(@Param("datas") List<Map> datas);

    List<Map> findByDepartmentId(String departmentId);

    void delDepartmentModel(String departmentId);

    void insertDepartmentModel(@Param("datas") List<Map> datas);

    List<Map> findForMenu(String userId);

    List<String> findAllUrl();
}
