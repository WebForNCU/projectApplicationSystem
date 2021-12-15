package com.spring.security.service;

import com.spring.security.entity.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BatchService {

    List<Batch> queryBatchList();

    Batch queryBatchById(int id);

    int addBatch(Batch batch);

    int updateBatch(Batch batch);

    int deleteBatch(int id);
}
