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
    List<Batch> batches;
    String Username;
    @GetMapping("/toqueryUserBatchList")
    public String toqueryUserBatchList(Model model){
        model.addAttribute("batches",batches);
        model.addAttribute("Username",Username);
        return "UserBatch/list";
    }
    //用户只能查询所有的批次信息
    @GetMapping("/queryUserBatchList/{username}")
    public String queryUserBatchList(@PathVariable String username, Model model){
        batches = batchDao.queryBacthList();
        Username = username;
        return "redirect:/toqueryUserBatchList";
    }

}
