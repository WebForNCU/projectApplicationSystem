package com.spring.security.controller;

import com.spring.security.entity.Batch;
import com.spring.security.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FillingController {

    @Autowired
    BatchService batchService;

    @RequestMapping("/filling")
    public String filling(){
        return "filling";
    }

    @RequestMapping("/queryBatchList")
    public String queryBatchList(Model model){
        List<Batch> batchList = batchService.queryBatchList();
        model.addAttribute("batchList",batchList);
        return "query";
    }
}
