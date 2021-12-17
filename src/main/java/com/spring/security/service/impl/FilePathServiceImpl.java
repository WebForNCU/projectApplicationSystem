package com.spring.security.service.impl;

import com.spring.security.dao.FilePathDao;
import com.spring.security.entity.FilePath;
import com.spring.security.entity.Teacher;
import com.spring.security.service.FilePathService;
import com.spring.security.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("FilePathService")
public class FilePathServiceImpl implements FilePathService {

    @Resource
    FilePathDao filePathDao;
    @Override
    public int addFilePath(FilePath filePath) {
        return filePathDao.addFilePath(filePath);
    }

    @Override
    public String getPathByUserId(Integer userId) {
        return filePathDao.getPathByUserId(userId);
    }

    @Override
    public int deleteFilePath(Integer userId) {
        return filePathDao.deleteFilePath(userId);
    }
}
