package com.wyc.logmgr.service.impl;

import com.wyc.core.util.BeanUtil;
import com.wyc.core.util.DataConvertUtil;
import com.wyc.core.util.StringUtil;
import com.wyc.core.base.exception.BaseException;
import com.wyc.logmgr.dao.LoginLogDao;
import com.wyc.logmgr.entity.LoginLog;
import com.wyc.logmgr.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyc on 2019/10/31.
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService{

    @Autowired
    private LoginLogDao loginLogDao;

    public void insert(Map<String, Object> param) throws BaseException{
        param.put("beanName",LoginLog.class.getCanonicalName());
        LoginLog loginlog = (LoginLog) BeanUtil.convertToBean(param);
        loginlog.setId(StringUtil.getUUID());
        loginLogDao.insert(loginlog);
    }

    public List<Map> find(Map param) throws BaseException{
        List<Map> datas = loginLogDao.find(param);
        return datas;
    }

    public Map queryForBarChart(Map param) throws BaseException{
        List<Map> datas = loginLogDao.queryForBarChart(param);
        Map retMap = DataConvertUtil.convertResultToBarChartData(datas);
        return retMap;
    }
    public Map queryForPieChart(Map param) throws BaseException{
        List<Map> datas = loginLogDao.queryForPieChart(param);
        Map retMap = DataConvertUtil.convertResultToPieChartData(datas);
        return retMap;
    }



}
