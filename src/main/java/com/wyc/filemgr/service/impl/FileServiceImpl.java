package com.wyc.filemgr.service.impl;

import com.wyc.core.base.exception.BaseException;
import com.wyc.core.shiro.CurrentUserHelper;
import com.wyc.core.util.BeanUtil;
import com.wyc.core.util.FileUtil;
import com.wyc.core.util.StringUtil;
import com.wyc.filemgr.dao.FileDao;
import com.wyc.filemgr.entity.File;
import com.wyc.filemgr.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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
    public void save(Map<String, Object> param,MultipartFile uploadFile) throws BaseException {
        String fileName = uploadFile.getOriginalFilename();
        if(!fileName.equals("")){
            String suffixName;
            try{
                suffixName = fileName.substring(fileName.lastIndexOf("."));
            }catch (Exception e){
                suffixName = "";
            }

            String filePath="";
            if(String.valueOf(param.get("file_type")).equals("1")){
                FileUtil.touchDir(uploadFileDir+CurrentUserHelper.getId()+ java.io.File.separator);
                filePath = uploadFileDir+CurrentUserHelper.getId()+ java.io.File.separator+StringUtil.getUUID()+suffixName;
            }else if(String.valueOf(param.get("file_type")).equals("2")){
                FileUtil.touchDir(uploadFileDir+CurrentUserHelper.getDeptId()+ java.io.File.separator);
                filePath = uploadFileDir+CurrentUserHelper.getDeptId()+ java.io.File.separator+StringUtil.getUUID()+suffixName;

                param.put("department_id",CurrentUserHelper.getDeptId());

            }else{
                filePath = uploadFileDir+StringUtil.getUUID()+suffixName;
            }
            try {

                uploadFile.transferTo(new java.io.File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            param.put("file_path",filePath);
            param.put("file_size", FileUtil.getFileSize(filePath));

            if("update".equals(String.valueOf(param.get("mode")))){
                String oldFilePath = fileDao.getFilePath(String.valueOf(param.get("id")));
                FileUtil.deleteFile(oldFilePath);
            }
        }



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
    public void delete(String id) throws BaseException{
        String filePath = fileDao.getFilePath(id);
        FileUtil.deleteFile(filePath);
        fileDao.delete(id);

    }

    @Override
    public List<Map> find(Map params){
        String file_type = String.valueOf(params.get("file_type"));
        if("1".equals(file_type)){
            params.put("created_user_id",CurrentUserHelper.getId());
        }else if("2".equals(file_type)){
            params.put("department_id",CurrentUserHelper.getDeptId());
        }
        return fileDao.find(params);
    }

    @Override
    public List<Map> getFileType(){
        return fileDao.getFileType();
    }

    @Override
    public Map getFilePathAndName(String id){
        return  fileDao.getFilePathAndName(id);
    }
}