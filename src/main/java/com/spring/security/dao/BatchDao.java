package com.spring.security.dao;

import com.spring.security.entity.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BatchDao{
    List<Batch> queryBacthList();
    Integer queryBacthID();
    Batch queryBacthById(Integer id);
    Integer addBatch(Integer id,String batch_name);
    Integer updateBatchDate(Integer id,String batch_name);
    Integer deleteBatch(Integer id);
    //int updateBatchName(Batch batch);
    //int deleteBatch(Batch batch);
}
