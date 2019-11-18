package com.wyc.logmgr.service.impl;

import com.wyc.base.utils.BeanUtil;

import com.wyc.base.utils.StringUtil;
import com.wyc.exception.BaseException;

import com.wyc.logmgr.dao.OperationLogDao;

import com.wyc.logmgr.entity.OperationLog;
import com.wyc.logmgr.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
@Service
public class OperationLogServiceImpl implements IOperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    public void insert(Map<String, Object> param) throws BaseException{
        param.put("beanName", OperationLog.class.getCanonicalName());
        OperationLog operationLog = (OperationLog) BeanUtil.convertToBean(param);
        operationLog.setId(StringUtil.getUUID());
        operationLogDao.insert(operationLog);
    }

    public List<Map> find(Map param) throws BaseException{
        List<Map> datas = operationLogDao.find(param);
        return datas;
    }

}
