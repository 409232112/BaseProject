package com.wyc.systemmgr.service.impl;

import com.wyc.base.utils.StringUtil;
import com.wyc.systemmgr.dao.UserDao;
import com.wyc.systemmgr.entity.User;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.service.IUserService;
import com.wyc.base.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
    public void save(Map<String, Object> param) throws BaseException{
        String mode = String.valueOf(param.get("mode"));
        param.put("beanName",User.class.getCanonicalName());
        if ("insert".equals(mode)){
            param.put("id", StringUtil.getUUID());
            //默认密码88888888
            String password = DigestUtils.md5DigestAsHex("88888888".getBytes());
            param.put("password",password);
            User user = (User) BeanUtil.convertToBean(param);
            userDao.insert(user);
        }else if("update".equals(mode)){
            User user = (User) BeanUtil.convertToBean(param);
            try{
                userDao.update(user);
            }catch (DuplicateKeyException e){
                throw new BaseException(-1, "该登陆名已经被占用！", e);
            }

        }
    }

    @Override
    public void delete(String id){
        userDao.delete(id);
    }

    @Override
    public List<Map> find(Map params){

        return userDao.find(params);
    }

    @Override
    public Map findUserByUsername(String username){
        Map user = userDao.findUserByUsername(username);
        return user;
    }

}
