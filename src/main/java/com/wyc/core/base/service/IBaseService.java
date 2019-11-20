package com.wyc.core.base.service;

import com.wyc.core.base.exception.BaseException;

import java.util.List;
import java.util.Map;

public interface IBaseService {
    void save(Map<String, Object> param) throws BaseException;

    void delete(String id) throws BaseException;

    List<Map> find(Map param) throws BaseException;
}