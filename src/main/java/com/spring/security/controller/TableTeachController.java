package com.spring.security.controller;

import com.spring.security.dao.TableNameDao;
import com.spring.security.dao.TableTeachDao;
import com.spring.security.entity.TableName;
import com.spring.security.entity.TableTeach;
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
    String table_describe;
    @RequestMapping("/tologintableTeach")
    public String tologintableTeach(Model model){
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Table_teach",Table_name);
        System.out.println(model);
        return "tableTeach/tableteach";
    }
    @RequestMapping("/logintableTeach/{table_name}")
    public String logintableTeach(@PathVariable String table_name){
        tableTeachs = tableTeachDao.queryTableTeachList(table_name);
        Table_name = table_name;
        return "redirect:/tologintableTeach";
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
        model.addAttribute("tablename",tablename);
        model.addAttribute("tabledescribe",table_describe);
        return "tableTeach/addTableTeach";
    }
    @GetMapping("/loginTableTeach/{table_name}")
    public String loginAddBatch(@PathVariable String table_name){
        tablename=table_name;
        table_describe = tableNameDao.queryTableNameDescribe(table_name);
        System.out.println(table_describe);
        return "redirect:/tologinTableTeach";
    }


    @RequestMapping(value="/addTableTeach",method= RequestMethod.POST)
    public String addTableTeach(HttpServletRequest request) {
        Integer len=Integer.valueOf(request.getParameter("len"));
        String table_teacher_describe="";
        for(int i=1; i<=len; i++){
            String news="news_"+i;
            table_teacher_describe+=request.getParameter(news);
            if(i!=len){
                table_teacher_describe+='#';
            }
        }
        table_teacher_describe+='#';
        Integer id;
        if(tableTeachDao.queryTableTeachID()==null){
            id=1;
        }else {
            id = tableTeachDao.queryTableTeachID() + 1;
        }
        String table_teach_name = request.getParameter("table_teach_name");
        String table_name = request.getParameter("table_name");
        String table_describe = request.getParameter("table_describe");
        tableTeachDao.addTableTeach(id,table_teach_name,table_name ,table_teacher_describe,table_describe,0,"#");
        Chinese = table_name;
        return "redirect:/zwlogintableTeach";
    }


    @GetMapping("/toupdateTableTeach")
    public String toupdateTableTeach(Model model){
        model.addAttribute("tableteach",tableTeach);
        System.out.println(model);
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
        tableTeachDao.updateTableTeachDate(id, table_teach_name,table_name ,table_teacher_describe,table_describe);
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
