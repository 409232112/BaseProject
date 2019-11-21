package com.wyc.filemgr.service;


import com.wyc.core.base.exception.BaseException;
import com.wyc.core.base.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface IFileService extends IBaseService {
    List<Map> getFileType();
}