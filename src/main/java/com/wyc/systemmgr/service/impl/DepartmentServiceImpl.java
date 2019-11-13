package com.wyc.systemmgr.service.impl;

import com.wyc.base.utils.BeanUtil;
import com.wyc.base.utils.DataConvertUtil;
import com.wyc.base.utils.StringUtil;
import com.wyc.exception.BaseException;
import com.wyc.shiro.CurrentUserHelper;
import com.wyc.systemmgr.dao.DepartmentDao;
import com.wyc.systemmgr.dao.ModelDao;
import com.wyc.systemmgr.entity.Department;
import com.wyc.systemmgr.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private ModelDao modelDao;

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        String seq = String.valueOf(param.get("seq"));
        if(seq.equals("")){
            param.put("seq","1");
        }
        param.put("beanName",Department.class.getCanonicalName());
        Department department = (Department) BeanUtil.convertToBean(param);
        if ("insert".equals(mode)){
            department.setId(StringUtil.getUUID());
            department.setCreated_user_id(CurrentUserHelper.getId());
            departmentDao.insert(department);
        }else if("update".equals(mode)){
            department.setUpdate_user_id(CurrentUserHelper.getId());
            departmentDao.update(department);
        }
    }

    @Override
    @Transactional
    public void delete(String id) throws BaseException{
        int count = departmentDao.hasChildren(id);
        if(count==0){
            modelDao.delRoleModel(id);
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

    @Override
    public List<Map> findTree(Map param){
        System.out.println(param);
        List<Map> datas = departmentDao.find(param);

        if(param.isEmpty()){
            return DataConvertUtil.convertResultToTreeData(datas);
        }else{
            List<Map> retDatas = new ArrayList();
            for(int i=0; i<datas.size();i++){
                String children_id = String.valueOf(datas.get(i).get("id"));
                List<Map> deptTree = departmentDao.getDeptParentList(children_id);
                retDatas.addAll(deptTree);
            }
            retDatas = DataConvertUtil.removeRepeatMapByKey(retDatas,"id");
            System.out.println(retDatas);
            System.out.println(DataConvertUtil.convertResultToTreeData(retDatas));
            return DataConvertUtil.convertResultToTreeData(retDatas);
        }



    }
}