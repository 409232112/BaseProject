package com.wyc.systemmgr.service.impl;

import com.wyc.base.utils.BeanUtil;
import com.wyc.base.utils.DataConvertUtil;
import com.wyc.base.utils.StringUtil;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.dao.DepartmentDao;
import com.wyc.systemmgr.entity.Department;
import com.wyc.systemmgr.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        String seq = String.valueOf(param.get("seq"));
        if(seq.equals("")){
            param.put("seq","1");
        }
        param.put("beanName",Department.class.getCanonicalName());
        if ("insert".equals(mode)){
            param.put("id", StringUtil.getUUID());
            Department department = (Department) BeanUtil.convertToBean(param);
            departmentDao.insert(department);
        }else if("update".equals(mode)){
            param.put("id",param.get("id").toString());
            Department department = (Department) BeanUtil.convertToBean(param);
            departmentDao.update(department);
        }
    }

    @Override
    public void delete(String id) throws BaseException{
        int count = departmentDao.hasChildren(id);
        if(count==0){
            departmentDao.delete(id);
        }else{
            throw new BaseException(-1, "当前部门有子部门，请先删除子部门！");
        }
    }


    @Override
    public List<Map> find(Map param){
        List<Map> datas = departmentDao.find(param);
        return DataConvertUtil.convertResultToTreeData(datas);
    }
}