package com.wyc.logmgr.service;

import com.wyc.base.service.IBaseService;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
public interface ILoginLogService{
    void insert(Map<String, Object> param) throws BaseException;

    List<Map> find(Map param) throws BaseException;

    Map queryForBarChart(Map param) throws BaseException;


}
