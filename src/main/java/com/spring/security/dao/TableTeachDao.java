package com.spring.security.dao;

import com.spring.security.entity.TableTeach;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface TableTeachDao{
    List<TableTeach> queryTableTeachList(String table_name);
    Integer queryTableTeachID();
    Integer addTableTeach(Integer id,String table_teach_name,String table_name,String table_teacher_describe,String table_describe,Integer audit,String src);
    TableTeach queryTableTeachById(Integer id);
    Integer updateTableTeachDate(Integer id,String table_teach_name,String table_name ,String table_teacher_describe,String table_describe);
    Integer deleteTableTeach(String table_teach_name);
    String queryTableTeachTableName(String table_teach_name);
    String queryTableTeacherDescribe(String table_teach_name);
    String queryTableNameDescribe(String table_teach_name);
}
