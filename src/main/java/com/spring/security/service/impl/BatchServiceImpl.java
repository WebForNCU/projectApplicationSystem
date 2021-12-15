package com.spring.security.service.impl;

import com.spring.security.dao.BatchDao;
import com.spring.security.entity.Batch;
import com.spring.security.service.BatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("BatchService")
public class BatchServiceImpl implements BatchService {

    @Resource
    private BatchDao batchDao;

    @Override
    public List<Batch> queryBatchList() {
        return batchDao.queryBatchList();
    }

    @Override
    public Batch queryBatchById(int id) {
        return batchDao.queryBatchById(id);
    }

    @Override
    public int addBatch(Batch batch) {
        batchDao.addBatch(batch);
        return 1;
    }

    @Override
    public int updateBatch(Batch batch) {
        batchDao.updateBatch(batch);
        return 1;
    }

    @Override
    public int deleteBatch(int id) {
        batchDao.deleteBatch(id);
        return 1;
    }
}
