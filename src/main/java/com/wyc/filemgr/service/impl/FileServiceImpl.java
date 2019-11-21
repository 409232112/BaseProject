package com.wyc.filemgr.service.impl;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.utils.BeanUtil;
import com.wyc.core.utils.CommonUtility;
import com.wyc.core.utils.StringUtil;
import com.wyc.filemgr.dao.FileDao;
import com.wyc.filemgr.entity.File;
import com.wyc.filemgr.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileDao fileDao;

    private static String uploadFileDir;

    @Value("${filesDir.uploadFileDir}")
    public void setUploadFileDir(String uploadFileDir) {
        this.uploadFileDir = uploadFileDir;
    }

    @Override
    public void save(Map<String, Object> param) throws BaseException {
        String mode = String.valueOf(param.get("mode"));
        param.put("beanName",File.class.getCanonicalName());
        File file = (File) BeanUtil.convertToBean(param);
        if ("insert".equals(mode)){
            file.setId(StringUtil.getUUID());
            file.setCreated_user_id(CurrentUserHelper.getId());
            fileDao.insert(file);
        }else if("update".equals(mode)){
            file.setUpdate_user_id(CurrentUserHelper.getId());
            fileDao.update(file);
        }
    }

    @Override
    public void delete(String id){
        fileDao.delete(id);
    }

    @Override
    public List<Map> find(Map params){
        return fileDao.find(params);
    }

    @Override
    public List<Map> getFileType(){
        return fileDao.getFileType();
    }
}