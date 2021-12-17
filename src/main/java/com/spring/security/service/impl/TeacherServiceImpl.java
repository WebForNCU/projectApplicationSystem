package com.spring.security.service.impl;

import com.spring.security.dao.TeacherDao;
import com.spring.security.entity.Teacher;
import com.spring.security.service.TeacherService;
import org.hibernate.annotations.Source;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("TeacherService")
public class TeacherServiceImpl implements TeacherService {
    @Resource
    TeacherDao teacherDao;

    @Override
    public Teacher queryTeacherByName(String name) {
        return teacherDao.queryTeacherByName(name);
    }

    @Override
    public List<Teacher> queryTeacherList() {
        return null;
    }

    @Override
    public Teacher queryTeacherById(int id) {
        return null;
    }

    @Override
    public int addTeacher(Teacher teacher) {
        return 0;
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return 0;
    }

    @Override
    public int deleteTeacher(Teacher teacher) {
        return 0;
    }

    @Override
    public int getTeacherIdByName(String name) {
        return teacherDao.getTeacherIdByName(name);
    }
}
