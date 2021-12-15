package com.spring.security.dao;

import com.spring.security.entity.Batch;

import java.util.List;


public interface BatchDao {

    List<Batch> queryBatchList();

    Batch queryBatchById(int id);

    int addBatch(Batch batch);

    int updateBatch(Batch batch);

    int deleteBatch(int id);
}
