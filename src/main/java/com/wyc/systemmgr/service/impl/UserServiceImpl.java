package com.wyc.systemmgr.service.impl;

import com.wyc.systemmgr.dao.UserDao;
import com.wyc.systemmgr.entity.User;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.service.IUserService;
import com.wyc.base.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        param.put("beanName",User.class.getCanonicalName());
        if ("insert".equals(mode)){
            param.remove("id");
            User user = (User) BeanUtil.convertToBean(param);
            userDao.insert(user);
        }else if("update".equals(mode)){
            param.put("id",Integer.valueOf(param.get("id").toString()));
            User user = (User) BeanUtil.convertToBean(param);
            userDao.update(user);
        }
    }

    @Override
    public void delete(int id){
        userDao.delete(id);
    }

    @Override
    public List<Map> find(Map params){

        return userDao.find(params);
    }

}
