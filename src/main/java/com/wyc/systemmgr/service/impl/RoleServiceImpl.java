package com.wyc.systemmgr.service.impl;

import com.wyc.core.util.BeanUtil;
import com.wyc.core.util.StringUtil;
import com.wyc.core.base.exception.BaseException;

import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.systemmgr.dao.ModelDao;
import com.wyc.systemmgr.dao.RoleDao;
import com.wyc.systemmgr.entity.Role;
import com.wyc.systemmgr.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModelDao modelDao;

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        param.put("beanName",Role.class.getCanonicalName());
        Role role = (Role) BeanUtil.convertToBean(param);
        if ("insert".equals(mode)){
            role.setId(StringUtil.getUUID());
            role.setCreated_user_id(CurrentUserHelper.getId());
            roleDao.insert(role);
        }else if("update".equals(mode)){
            role.setUpdate_user_id(CurrentUserHelper.getId());
            roleDao.update(role);
        }
    }

    @Override
    @Transactional
    public void delete(String id){
        modelDao.delRoleModel(id);
        roleDao.delete(id);
    }

    @Override
    public List<Map> find(Map params){
        return roleDao.find(params);
    }

}
