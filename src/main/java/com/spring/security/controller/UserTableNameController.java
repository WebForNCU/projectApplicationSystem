package com.spring.security.controller;

import com.spring.security.dao.TableNameDao;
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
public class UserTableNameController {
    @Autowired
    private TableNameDao tableNameDao;
    private List<TableName> tableNames;
    private String Batchname;
    private TableName tableName;
    private String Chinese;//为了解决中文消失情况
    @RequestMapping("/tologinUsertableName")
    public String tologinUsertableName(Model model){
        model.addAttribute("tableNames", tableNames);
        model.addAttribute("Batchname", Batchname);
        return "UserTableName/tablename";
    }
    @RequestMapping("/loginUsertableName/{batch_name}")
    public String loginUsertableName(@PathVariable String batch_name, Model model){
        if(tableNames!=null)tableNames.clear();
        tableNames = tableNameDao.queryTableNameList(batch_name);
        Batchname = batch_name;
        return "redirect:/tologinUsertableName";
    }


}
