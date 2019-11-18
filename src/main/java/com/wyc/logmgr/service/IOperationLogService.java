package com.wyc.logmgr.service;

import com.wyc.exception.BaseException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
public interface IOperationLogService {
    void insert(Map<String, Object> param) throws BaseException;

    List<Map> find(Map param) throws BaseException;

}
