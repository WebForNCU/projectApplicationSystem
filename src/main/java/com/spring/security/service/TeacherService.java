package com.spring.security.service;

import com.spring.security.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TeacherService {
    List<Teacher> queryTeacherList();

    Teacher queryTeacherById(int id);

    Teacher queryTeacherByName(String name);

    int addTeacher(Teacher teacher);

    int updateTeacher(Teacher teacher);

    int deleteTeacher(Teacher teacher);

    int getTeacherIdByName(String name);
}
