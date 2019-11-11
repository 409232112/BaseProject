package com.wyc.systemmgr.service;

import com.wyc.base.service.IBaseService;
import com.wyc.systemmgr.entity.User;

import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
public interface IUserService extends IBaseService {
    Map findUserByUsername(String username);
}
