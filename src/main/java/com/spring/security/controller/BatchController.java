package com.spring.security.controller;
import com.spring.security.dao.BatchDao;
import com.spring.security.entity.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BatchController{

    @Autowired
    private BatchDao batchDao;
    private Batch batches;

    @GetMapping("/loginbatch")
    public String loginAddBatch(){
        return "batch/addlist";
    }


    @GetMapping("/queryBatchList")
    public String queryBatchList(Model model){
        List<Batch> batches = batchDao.queryBacthList();
        model.addAttribute("batches",batches);
        return "batch/list";
    }

    @GetMapping("/addbatch")
    public String addBatchList(String batch_name,
                                 Model model){
        Integer id;
        if(batchDao.queryBacthID()==null){
            id=1;
        }
        else{
            id=batchDao.queryBacthID()+1;
        }
        batchDao.addBatch(id,batch_name);
        return "redirect:/queryBatchList";
    }


    @GetMapping("/loginBatchUpdate")
    public String loginBatchUpdate(Model model){
        model.addAttribute("batches",batches);
        return "batch/updatelist";
    }
    @GetMapping("/updatelist/{id}")
    public String updateBatchList(@PathVariable Integer id, Model model){
        batches = batchDao.queryBacthById(id);
        return "redirect:/loginBatchUpdate";
    }


    @GetMapping("/updateByBatch")
    public String updateByBatch(String id,String batch_name, Model model){
        Integer Id=Integer.parseInt(id);
        batchDao.updateBatchDate(Id,batch_name);
        return "redirect:/queryBatchList";
    }

    @GetMapping("/deletelist/{id}")
    public String deletelist(@PathVariable Integer id, Model model){
        batchDao.deleteBatch(id);
        return "redirect:/queryBatchList";
    }

}
