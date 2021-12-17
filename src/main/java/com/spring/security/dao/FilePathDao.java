package com.spring.security.dao;

import com.spring.security.entity.FilePath;

public interface FilePathDao {
    int addFilePath(FilePath filePath);

    String getPathByUserId(Integer userId);

    int deleteFilePath(Integer userId);

}
