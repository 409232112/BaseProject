package com.wyc.systemmgr.service;

import com.wyc.core.base.service.IBaseService;
import com.wyc.core.base.exception.BaseException;

import java.util.List;
import java.util.Map;

public interface IDepartmentService extends IBaseService {
    List<Map> findTree(Map param) throws BaseException;
}