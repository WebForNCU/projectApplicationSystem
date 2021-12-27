package com.spring.security.controller;

import com.spring.security.dao.BatchDao;
import com.spring.security.dao.TableNameDao;
import com.spring.security.entity.Batch;
import com.spring.security.entity.TableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TableNameController {
    @Autowired
    private TableNameDao tableNameDao;
    private List<TableName>  tableNames;
    @Autowired
    private BatchDao batchDao;
    private String batchname;
    private String Batchname;
    private TableName tableName;
    private String Chinese;//为了解决中文消失情况
    //批次信息发布界面的表格名信息
    @RequestMapping("/tologintableName")
    public String tologintableName(Model model){
        model.addAttribute("tableNames", tableNames);
        model.addAttribute("Batchname", Batchname);
        return "tableName/tablename";
    }
    @RequestMapping("/logintableName/{batch_name}")
    public String logintableName(@PathVariable String batch_name, Model model){
        if(tableNames!=null)tableNames.clear();
        tableNames = tableNameDao.queryTableNameList(batch_name);
        Batchname = batch_name;
        return "redirect:/tologintableName";
    }
    //审核界面的表格名信息
    @RequestMapping("/tologintableName1")
    public String tologintableName1(Model model){
        model.addAttribute("tableNames", tableNames);
        model.addAttribute("Batchname", Batchname);
        return "audit/auditTableName";
    }
    @RequestMapping("/logintableName1/{batch_name}")
    public String logintableName1(@PathVariable String batch_name, Model model){
        if(tableNames!=null)tableNames.clear();
        tableNames = tableNameDao.queryTableNameList(batch_name);
        Batchname = batch_name;
        return "redirect:/tologintableName1";
    }
    //查询与导出界面的表格名信息
    @RequestMapping("/tologintableName2")
    public String tologintableName2(Model model){
        model.addAttribute("tableNames", tableNames);
        model.addAttribute("Batchname", Batchname);
        return "queryAndExport/tableNameList";
    }
    @RequestMapping("/logintableName2/{batch_name}")
    public String logintableName2(@PathVariable String batch_name, Model model){
        if(tableNames!=null)tableNames.clear();
        tableNames = tableNameDao.queryTableNameList(batch_name);
        Batchname = batch_name;
        return "redirect:/tologintableName2";
    }

    //这是为了处理中文消失问题
    @RequestMapping("/zwtologintableName")
    public String zwtologintableName(Model model){
        model.addAttribute("tableNames", tableNames);
        model.addAttribute("Batchname", Batchname);
        return "tableName/tablename";
    }
    @RequestMapping("/zwlogintableName")
    public String zwlogintableName(Model model){
        if(tableNames!=null)tableNames.clear();
        tableNames = tableNameDao.queryTableNameList(Chinese);
        Batchname = Chinese;
        return "redirect:/zwtologintableName";
    }



    @GetMapping("/tologinTableName")
    public String tologinAddBatch(Model model){
        model.addAttribute("batch_name",batchname);
        return "tableName/addTable";
    }
    @GetMapping("/loginTableName/{batch_name}")
    public String loginAddBatch(@PathVariable String batch_name){
        batchname=batch_name;
        return "redirect:/tologinTableName";
    }


    @RequestMapping(value="/addTableName",method= RequestMethod.POST)
    public String addTableName(HttpServletRequest request) {
        Integer len=Integer.valueOf(request.getParameter("len"));
        String table_describe="";
        for(int i=1; i<=len; i++){
            String news="news_"+i;
            table_describe+=request.getParameter(news);
            if(i!=len){
                table_describe+='#';
            }
        }
        table_describe+='#';
        Integer id;
        if(tableNameDao.queryTableNameID()==null){
            id=1;
        }else {
            id = tableNameDao.queryTableNameID() + 1;
        }
        String table_name = request.getParameter("table_name");
        String batch_name = request.getParameter("batch_name");
        tableNameDao.addTableName(id, table_name, batch_name, table_describe );
        Chinese = batch_name;
        batchDao.updateBatchDateExcel(batch_name,1);
        return "redirect:/queryBatchList";
    }


    @GetMapping("/toupdateTableName")
    public String toupdateTableName(Model model){
        model.addAttribute("tablename",tableName);
        return "tableName/updateTableName";
    }
    @GetMapping("/updateTableName/{id}")
    public String updateBatchList(@PathVariable Integer id){
        tableName = tableNameDao.queryTableNameById(id);
        return "redirect:/toupdateTableName";
    }


    @RequestMapping(value="/updateTableName",method= RequestMethod.POST)
    public String updateTableName(HttpServletRequest request) {
        Integer len=Integer.valueOf(request.getParameter("len"));
        String table_describe="";
        for(int i=1; i<=len; i++){
            String news="news_"+i;
            table_describe+=request.getParameter(news);
            if(i!=len){
                table_describe+='#';
            }
        }
        table_describe+='#';
        Integer id = Integer.valueOf(request.getParameter("table_id"));
        String table_name = request.getParameter("table_name");
        String batch_name = request.getParameter("batch_name");
        tableNameDao.updateTableNameDate(id, table_name, batch_name, table_describe);
        Chinese=batch_name;
        return "redirect:/zwlogintableName";
    }


    @GetMapping("/deleteTableName/{table_name}")
    public String deleteTableName(@PathVariable String table_name){
        String batch_name=tableNameDao.queryTableNameBatchName(table_name);
        tableNameDao.deleteTableName(table_name);
        Chinese=batch_name;
        return "redirect:/zwlogintableName";
    }
}
