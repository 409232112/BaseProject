package com.wyc.filemgr.dao;

import com.wyc.core.base.dao.BaseDao;

import java.util.List;
import java.util.Map;

public interface FileDao extends BaseDao {
    List<Map> getFileType();

    String getFilePath(String id);
}