package com.spring.security.service;

import com.spring.security.entity.FilePath;

public interface FilePathService {
    int addFilePath(FilePath filePath);

    String getPathByUserId(Integer userId);

    int deleteFilePath(Integer userId);
}
