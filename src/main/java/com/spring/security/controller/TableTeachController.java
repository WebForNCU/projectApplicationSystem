package com.spring.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.dao.TableNameDao;
import com.spring.security.dao.TableTeachDao;
import com.spring.security.entity.TableTeach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class TableTeachController {
    @Autowired
    private TableNameDao tableNameDao;
    @Autowired
    private TableTeachDao tableTeachDao;
    private List<TableTeach>  tableTeachs;
    private String tablename;
    private String Table_name;
    private TableTeach tableTeach;
    private String Chinese;
    private String Table_teach_name;
    String table_describe;
    @RequestMapping("/tologintableTeach")
    public String tologintableTeach(Model model,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Table_teach",Table_name);
        return "tableTeach/tableteach";
    }
    @RequestMapping("/logintableTeach/{table_name}")
    public String logintableTeach(@PathVariable String table_name){
        tableTeachs = tableTeachDao.queryTableTeachList(table_name);
        Table_name = table_name;

        return "redirect:/tologintableTeach";
    }

    //审核界面信息 审核界面只显示状态为未审核的报表信息
    @RequestMapping("/tologinUnaudited")
    public String tologintableTeach1(Model model,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Table_teach",Table_name);
        return "audit/auditTableTeach";
    }
    @RequestMapping("/loginUnaudited/{table_name}")
    public String logintableTeach1(@PathVariable String table_name){
        tableTeachs = tableTeachDao.queryUnaudited(table_name);
        Table_name = table_name;

        return "redirect:/tologinUnaudited";
    }

    //查询与导出界面 只能查询和导出状态为已审核的报表信息
    @RequestMapping("/tologinAudited")
    public String tologinAudited(Model model,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Table_teach",Table_name);
        return "queryAndExport/tableTeachList";
    }
    @RequestMapping("/loginAudited/{table_name}")
    public String loginAudited(@PathVariable String table_name){
        tableTeachs = tableTeachDao.queryAudited(table_name);
        Table_name = table_name;
        return "redirect:/tologinAudited";
    }

    //这是为了处理中文消失问题
    @RequestMapping("/zwtologintableTeach")
    public String zwtologintableName(Model model){
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Table_teach", Table_name);
        return "tableTeach/tableteach";
    }
    @RequestMapping("/zwlogintableTeach")
    public String zwlogintableName(Model model){
        if(tableTeachs!=null)tableTeachs.clear();
        tableTeachs = tableTeachDao.queryTableTeachList(Chinese);
        Table_name = Chinese;
        return "redirect:/zwtologintableTeach";
    }


    @GetMapping("/tologinTableTeach")
    public String tologinAddBatch(Model model){
        model.addAttribute("tableteach",tableTeach);
        return "tableTeach/aduitTableTeach";
    }
    @GetMapping("/aduitTableTeach/{id}/{table_name}")
    public String loginAddBatch(@PathVariable Integer id,@PathVariable String table_name){
        tableTeachDao.audit(id);
        tableTeachs = tableTeachDao.queryTableTeachList(table_name);
        Chinese = table_name;
        return "redirect:/loginUnaudited/{table_name}";
    }
    @RequestMapping("/auditAll/{table_name}")
    public String auditAll(@PathVariable String table_name,@RequestParam(value="str", required=true) String str){

        tableTeachDao.auditAll(str);
        tableTeachs = tableTeachDao.queryTableTeachList(table_name);
        Chinese = table_name;
        return "redirect:/loginUnaudited/{table_name}";
    }

    @RequestMapping(value="/aduitTableTeach",method= RequestMethod.POST)
    public String aduitTableTeach(HttpServletRequest request) {
        Integer len=Integer.valueOf(request.getParameter("len"));
        String  table_teacher_describe="";
        for(int i=1; i<=len; i++){
            String news="news_"+i;
            table_teacher_describe+=request.getParameter(news);
            if(i!=len){
                table_teacher_describe+='#';
            }
        }
        table_teacher_describe+='#';
        Integer id = Integer.valueOf(request.getParameter("table_id"));
        String table_name = request.getParameter("table_name");
        String table_teach_name = request.getParameter("table_teach_name");
        String table_describe = request.getParameter("table_describe");
        tableTeachDao.updateTableTeachDate(id, table_teach_name,table_name ,table_teacher_describe,table_describe, 1);
        tableTeachDao.updateAudit(1,id);
        Chinese = table_name;
        return "redirect:/zwlogintableTeach";
    }

    @GetMapping("/toupdateTableTeach")
    public String toupdateTableTeach(Model model){
        model.addAttribute("tableteach",tableTeach);
        return "tableTeach/updateTableTeach";
    }
    @GetMapping("/updateTableTeach/{id}")
    public String updateBatchList(@PathVariable Integer id) {
        tableTeach = tableTeachDao.queryTableTeachById(id);
        return "redirect:/toupdateTableTeach";
    }
    @RequestMapping(value="/updateTableTeach",method= RequestMethod.POST)
    public String updateTableTeach(HttpServletRequest request) {
        Integer len=Integer.valueOf(request.getParameter("len"));
        String  table_teacher_describe="";
        for(int i=1; i<=len; i++){
            String news="news_"+i;
            table_teacher_describe+=request.getParameter(news);
            if(i!=len){
                table_teacher_describe+='#';
            }
        }
        table_teacher_describe+='#';
        Integer id = Integer.valueOf(request.getParameter("table_id"));
        String table_name = request.getParameter("table_name");
        String table_teach_name = request.getParameter("table_teach_name");
        String table_describe = request.getParameter("table_describe");
        tableTeachDao.updateTableTeachDate(id, table_teach_name,table_name ,table_teacher_describe,table_describe, 1 );
        Chinese = table_name;
        return "redirect:/zwlogintableTeach";
    }


    @GetMapping("/deleteTableTeach/{table_teach_name}")
    public String deleteTableTeach(@PathVariable String table_teach_name, Model model){
        System.out.println("zzzzzzzzz");
        String table_name=tableTeachDao.queryTableTeachTableName(table_teach_name);
        System.out.println(table_name);
        tableTeachDao.deleteTableTeach(table_teach_name);
        Chinese = table_name;
        return "redirect:/zwlogintableTeach";
    }
}
