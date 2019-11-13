package com.wyc.systemmgr.service.impl;

import com.wyc.base.utils.BeanUtil;
import com.wyc.base.utils.StringUtil;
import com.wyc.exception.BaseException;

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
        if ("insert".equals(mode)){
            param.put("id", StringUtil.getUUID());
            Role role = (Role) BeanUtil.convertToBean(param);
            roleDao.insert(role);
        }else if("update".equals(mode)){
            Role role = (Role) BeanUtil.convertToBean(param);
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
