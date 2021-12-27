package com.spring.security.controller;

import com.spring.security.dao.TableNameDao;
import com.spring.security.dao.TableTeachDao;
import com.spring.security.entity.TableTeach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class Getexcel{
    @Autowired
    private TableTeachDao tableTeachDao;
    @Autowired
    private TableNameDao tableNameDao;
    private List<TableTeach>  tableTeachs;
    com.spring.security.entity.ExcleImpl  excleImpl=new com.spring.security.entity.ExcleImpl();
  //获取url链接上的参数
  @RequestMapping("/Getexcel/{table_teach_name}")
  public String Getexcel(@PathVariable String table_teach_name,HttpServletResponse response){

      response.setContentType("application/binary;charset=UTF-8");
      String name = table_teach_name;
      try{
          ServletOutputStream out=response.getOutputStream();
          try {
              //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.xls)
              response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
          } catch (UnsupportedEncodingException e1) {
              e1.printStackTrace();
          }
          String Table_describe=tableTeachDao.queryTableNameDescribe(table_teach_name);
          String Teach_table_describe=tableTeachDao.queryTableTeacherDescribe(table_teach_name);
          List<String> titles = new ArrayList<>();
          List<String> teacher =new ArrayList<>();
          String table_describe="";
          for(int i=0;i<Table_describe.length();i++){
            //  System.out.println(Table_describe.substring(i,i+1).equals("#"));
              if(!(Table_describe.substring(i,i+1).equals("#"))){
                  table_describe+=Table_describe.substring(i,i+1);
              }else{
                  titles.add(table_describe);
                  table_describe="";
              }
          }
          String teach_table_describe="";
          for(int i=0;i<Teach_table_describe.length();i++){

              if(!(Teach_table_describe.substring(i,i+1).equals("#"))){
                  teach_table_describe+=Teach_table_describe.substring(i,i+1);
              }else{
                  teacher.add(teach_table_describe);
                  teach_table_describe="";
              }
          }
          excleImpl.export(titles,teacher,out);
          return "success";
      } catch(Exception e){
          e.printStackTrace();
          return "导出信息失败";
      }
  }
  //按批次导出信息
    @RequestMapping("/Getexcelall/{batch_name}")
    public String Getexcelall(@PathVariable String batch_name,HttpServletResponse response){

        response.setContentType("application/binary;charset=UTF-8");
        String name = batch_name;
        try{
            ServletOutputStream out=response.getOutputStream();
            try {
                //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.xls)
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            System.out.println(tableNameDao.queryTableNameTableName(batch_name));
            String table_name = tableNameDao.queryTableNameTableName(batch_name);
            tableTeachs = tableTeachDao.queryAudited(table_name);//select * from tableteach where table_name= #{table_name}
            excleImpl.export1(tableTeachs,out);
            return "success";
        } catch(Exception e){
            e.printStackTrace();
            return "导出信息失败";
        }
    }
}
