package com.wyc.systemmgr.service;

import com.wyc.base.service.IBaseService;
import com.wyc.exception.BaseException;

import java.util.List;
import java.util.Map;

public interface IDepartmentService extends IBaseService {
    List<Map> findTree(Map param) throws BaseException;
}