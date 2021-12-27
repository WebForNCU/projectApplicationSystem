package com.spring.security.controller;
import com.spring.security.dao.BatchDao;
import com.spring.security.dao.TableNameDao;
import com.spring.security.dao.TableTeachDao;
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
    @Autowired
    private TableTeachDao tableTeachDao;
    @Autowired
    private TableNameDao tableNameDao;
    private Batch batches;

    @GetMapping("/loginbatch")
    public String loginAddBatch(){
        return "batch/addlist1";
    }

    //报表发布界面的批次信息
    @GetMapping("/queryBatchList")
    public String queryBatchList(Model model){
        List<Batch> batches = batchDao.queryBacthList();
        model.addAttribute("batches",batches);
        System.out.println(model);
        return "batch/list";
    }
    //审核界面的批次信息
    @GetMapping("/queryBatchList1")
    public String queryBatchList1(Model model){
        List<Batch> batches = batchDao.queryBacthList();
        model.addAttribute("batches",batches);
        System.out.println(model);
        return "audit/auditBatchList";
    }
    @GetMapping("/queryBatchList2")
    public String queryBatchList2(Model model){
        List<Batch> batches = batchDao.queryBacthList();
        model.addAttribute("batches",batches);
        System.out.println(model);
        return "queryAndExport/batchList";
    }
    //查询与导出界面的批次信息
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
        batchDao.updateBatchDateExcel(batch_name,0);
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
        String batch_name = batchDao.queryBacthById(id).getBatch_name();
        batchDao.deleteBatch(id);
        tableTeachDao.deleteByBatch(batch_name);
        //tableNameDao.deleteByBatch(batch_name);
        return "redirect:/queryBatchList";
    }

}
