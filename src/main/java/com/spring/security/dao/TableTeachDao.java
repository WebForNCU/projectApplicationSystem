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
    Integer addTableTeach(String table_teach_name,String table_name,String table_teacher_describe,String table_describe,Integer isfill,Integer audit,String src);
    TableTeach queryTableTeachById(Integer id);
    Integer updateTableTeachDate(Integer id,String table_teach_name,String table_name ,String table_teacher_describe,String table_describe,Integer isfill);
    Integer deleteTableTeach(String table_teach_name);
    String queryTableTeachTableName(String table_teach_name);
    String queryTableTeacherDescribe(String table_teach_name);
    String queryTableNameDescribe(String table_teach_name);
    TableTeach queryTableTeachByTableNameAndUserName(String table_name,String table_teach_name);
    //找到所有未审核的报表
    List<TableTeach> queryTableTeachListByusername(String table_teach_name);
    //找到所有已经审核的报表
    List<TableTeach> queryTableTeachListByusernameAudited(String table_teach_name);

    //审核功能
    Integer audit(Integer id);
    Integer auditAll(String str);
    //管理员查询所有未审核的表
    List<TableTeach> queryUnaudited(String table_name);
    //查询所有状态为已审核的报表
    List<TableTeach> queryAudited(String table_name);

    Integer queryTableNameDescribeById(String table_name,String table_teach_name);
    Integer updateIsfill(Integer isfill,Integer id);
    Integer updateAudit(Integer audit,Integer id);

    //更新附件路径
    Integer updateFilePath(Integer id,String src);

    //按批次名删除
    Integer deleteByBatch(String batch_name);

    String queryTableTeacherDescribeByUserNameAndTablename(String table_teach_name,String table_name);
}
