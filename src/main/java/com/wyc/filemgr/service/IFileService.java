package com.wyc.filemgr.service;


import com.wyc.core.base.exception.BaseException;
import com.wyc.core.base.service.IBaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IFileService {
    void save(Map<String, Object> param,MultipartFile uploadFile) throws BaseException;

    void delete(String id) throws BaseException;

    List<Map> find(Map param) throws BaseException;

    List<Map> getFileType();

    Map getFilePathAndName(String id);
}