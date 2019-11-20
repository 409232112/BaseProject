package com.wyc.systemmgr.service;

import com.wyc.core.base.service.IBaseService;

import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
public interface IUserService extends IBaseService {
    Map findUserByUsername(String username);
    void changePassword(Map data);
}
