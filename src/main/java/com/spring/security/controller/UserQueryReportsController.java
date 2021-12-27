package com.spring.security.controller;

import com.spring.security.dao.TableNameDao;
import com.spring.security.dao.TableTeachDao;
import com.spring.security.entity.TableTeach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserQueryReportsController {
    @Autowired
    TableTeachDao tableTeachDao;
    @Autowired
    TableNameDao tableNameDao;
    public List<TableTeach> tableTeachesAudited;
    public  TableTeach tableTeach;
    public String tableDescribe;
    public  String tableTeachDescribe;
    public List<String> headList;
    public List<String> myReports;
    //该页面查找的均是已审核完毕的
    @RequestMapping("/toqueryTableNameAudited")
    public String toQueryTableNameAudited(Model model)
    {
        model.addAttribute("tableTeachesAudited",tableTeachesAudited);
        return "UserQueryReports";
    }
    //获得所有已审核的报表
    @RequestMapping("/queryTableNameAudited/{username}")
    public String queryTableNameAudited(@PathVariable String username)
    {
        tableTeachesAudited = tableTeachDao.queryTableTeachListByusernameAudited(username);
        return "redirect:/toqueryTableNameAudited";
    }

    //查看填报信息
    @RequestMapping("/queryTableInfo/{tablename}/{username}")
    public String queryTableInfo(@PathVariable String username, @PathVariable String tablename)
    {
        tableDescribe = tableNameDao.queryTableNameDescribe(tablename);
        //获取填报表格标题
        headList = Arrays.asList(tableDescribe.split("#"));

        tableTeach = tableTeachDao.queryTableTeachByTableNameAndUserName(tablename,username);
        //获得所填内容
        tableTeachDescribe = tableTeachDao.queryTableTeacherDescribeByUserNameAndTablename(username, tablename);
        myReports = Arrays.asList(tableTeachDescribe.split("#"));
        return "redirect:/toqueryTableInfo";
    }
    @RequestMapping("/toqueryTableInfo")
    public String toqueryTableInfo(Model model)
    {
        model.addAttribute("myReports",myReports);
        model.addAttribute("headList",headList);
        model.addAttribute("tableTeach",tableTeach);
        return "queryTableInfo";
    }
}
