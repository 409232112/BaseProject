package com.wyc.systemmgr.service.impl;

import com.wyc.base.utils.BeanUtil;
import com.wyc.base.utils.DataConvertUtil;
import com.wyc.base.utils.StringUtil;
import com.wyc.exception.BaseException;
import com.wyc.systemmgr.dao.ModelDao;
import com.wyc.systemmgr.entity.Model;
import com.wyc.systemmgr.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ModelServiceImpl implements IModelService {
    @Autowired
    private ModelDao mdeolDao;

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        String seq = String.valueOf(param.get("seq"));
        if(seq.equals("")){
            param.put("seq","1");
        }
        param.put("beanName",Model.class.getCanonicalName());
        if ("insert".equals(mode)){
            param.put("id", StringUtil.getUUID());
            Model model = (Model) BeanUtil.convertToBean(param);
            mdeolDao.insert(model);
        }else if("update".equals(mode)){
            param.put("id",param.get("id").toString());
            Model model = (Model) BeanUtil.convertToBean(param);
            mdeolDao.update(model);
        }
    }

    @Override
    public void delete(String id) throws BaseException{
        int count = mdeolDao.hasChildren(id);
        if(count==0){
            mdeolDao.delete(id);
        }else{
            throw new BaseException(-1, "当前模块有子模块，请先删除子模块！");
        }
    }

    @Override
    public List<Map> find(Map params){
        return mdeolDao.find(params);
    }

    @Override
    public List<Map> findForMenu(){
        List<Map> datas = mdeolDao.find(null);
        return DataConvertUtil.convertResultToTreeData(datas);
    }
    @Override
    public List<Map> findByUserId(String userId){
        List<Map> datas = mdeolDao.findByUserId(userId);
        return DataConvertUtil.convertResultToTreeData(datas);
    }

    @Transactional
    @Override
    public void saveUserModel(List<Map> datas){
        mdeolDao.delUserModel(String.valueOf(datas.get(0).get("userId")));
        mdeolDao.insertUserModel(datas);
    }
}