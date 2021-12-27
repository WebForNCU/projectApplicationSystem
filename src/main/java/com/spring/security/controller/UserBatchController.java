package com.spring.security.controller;

import com.spring.security.dao.BatchDao;
import com.spring.security.entity.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserBatchController {
    @Autowired
    private BatchDao batchDao;
    private Batch batches;


    //用户只能查询所有的批次信息
    @GetMapping("/queryUserBatchList")
    public String queryBatchList(Model model){
        List<Batch> batches = batchDao.queryBacthList();
        model.addAttribute("batches",batches);
        return "UserBatch/list";
    }

}
