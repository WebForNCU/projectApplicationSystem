package com.spring.security.dao;

import com.spring.security.entity.Batch;
import com.spring.security.entity.TableName;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TableNameDao{
      List<TableName> queryTableNameList(String batch_name);
      Integer queryTableNameID();
      Integer addTableName(Integer id,String table_name,String batch_name,String table_describe);
      TableName queryTableNameById(Integer id);
      Integer updateTableNameDate(Integer id,String table_name,String batch_name,String table_describe);
      Integer deleteTableName(String table_name);
      String queryTableNameBatchName(String table_name);
      String queryTableNameDescribe(String table_name);
//    int queryBacthID();
//    Batch queryBacthById(int id);
//    int addBatch(int id,String batch_name);
//    int updateBatchDate(int id,String batch_name);
//    int deleteBatch(int id);
}
