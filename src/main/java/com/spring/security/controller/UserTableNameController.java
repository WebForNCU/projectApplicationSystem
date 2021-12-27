package com.spring.security.controller;

import com.spring.security.dao.TableNameDao;
import com.spring.security.dao.TableTeachDao;
import com.spring.security.entity.TableName;
import com.spring.security.entity.TableTeach;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserTableNameController {
    public final static String UPLOAD_PATH_PREFIX = "static/files/";
    public String src;
    @Autowired
    private TableTeachDao tableTeachDao;
    @Autowired
    private TableNameDao tableNameDao;
    private List<TableTeach>  tableTeachs;//未填报
    private List<TableName>  tableNames;
    private String Username;
    private String batchname;
    private String Chinese;
    private String Table_name;
    TableTeach tableTeach;

    private  List<TableTeach> tableTeachesAudited;//已审核


    @RequestMapping("/tologinUsertableName")
    public String tologinUsertableName(Model model){
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Username",Username);
        model.addAttribute("batchname",batchname);
        //System.out.println(model);
        return "UserTableName/tablename";
    }
    @RequestMapping("/loginUsertableName/{username}/{batch_name}")
    public String loginUsertableName(@PathVariable String username, @PathVariable String batch_name){
        batchname = batch_name;
        tableNames = tableNameDao.queryTableNameList(batchname);
        System.out.println(tableNames.get(0));
        //System.out.println(tableNames.get(1));

        for(int i=0; i<tableNames.size(); i++){
            String table_name = tableNames.get(i).getTable_name();
            String table_describe = tableNames.get(i).getTable_describe();
            if(tableTeachDao.queryTableNameDescribeById(table_name,username)==null){
                tableTeachDao.addTableTeach(tableTeachDao.queryTableTeachID()+1,username,table_name,"",table_describe,0,0, "");
            }
        }
        tableTeachs = tableTeachDao.queryTableTeachListByusername(username);
        Username = username;
        batchname = batch_name;
        return "redirect:/tologinUsertableName";
    }


//    @RequestMapping("/toqueryTableNameAudited")
//    public String toQueryTableNameAudited(Model model)
//    {
//        model.addAttribute("tableTeachesAudited",tableTeachesAudited);
//        return "User/myReports";
//    }
    //获得所有已审核的报表
//    @RequestMapping("/queryTableNameAudited/{username}")
//    public String queryTableNameAudited(@PathVariable String username,Model model)
//    {
//        tableTeachesAudited = tableTeachDao.queryTableTeachListByusernameAudited(username);
//        return "redirect:/toqueryTableNameAudited";
//    }


    //这是为了处理中文消失问题
    @RequestMapping("/zwtologinUsertableTeach")
    public String zwtologintableName(Model model){
        model.addAttribute("tableTeachs", tableTeachs);
        model.addAttribute("Username",Username);
        model.addAttribute("batchname",batchname);
        //System.out.println(model);
        return "UserTableName/tablename";
    }
    @RequestMapping("/zwloginUsertableTeach")
    public String zwlogintableName(Model model){
        if(tableTeachs!=null)tableTeachs.clear();
        //找到所有未审核的报表
        tableTeachs = tableTeachDao.queryTableTeachListByusername(Chinese);
        Username = Chinese;
        return "redirect:/zwtologinUsertableTeach";
    }



    @GetMapping("/toupdateUserTableTeach")
    public String toupdateTableTeach(Model model){
        model.addAttribute("tableteach",tableTeach);
        return "UserTableName/updateUserTableTeach";
    }
    @GetMapping("/updateTableTeach/{id}/{batchName}")
    public String updateBatchList(@PathVariable Integer id,@PathVariable String batchName) {
        tableTeach = tableTeachDao.queryTableTeachById(id);
        batchname = batchName;
        return "redirect:/toupdateUserTableTeach";
    }
    @RequestMapping(value="/updateUserTableTeach",method= RequestMethod.POST)
    public String updateTableTeach(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) {
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
        tableTeachDao.updateTableTeachDate(id, table_teach_name,table_name ,table_teacher_describe,table_describe, 0);
        tableTeachDao.updateIsfill(1,id);

        //处理附件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + format);
        if(!file.isDirectory()){
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = uploadFile.getOriginalFilename();
        System.out.println(oldName);
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);

            //获取存入数据库中的路径
            src = UPLOAD_PATH_PREFIX.substring(7) + format + File.separator;
            src = src.substring(0, src.length() - 1);
            //获取文件新的url
            src = src + oldName;
            System.out.println("file_path");
            System.out.println(src);
            //存储文件路径
            tableTeachDao.updateFilePath(id, src);
        }catch (Exception e)
        {
            e.printStackTrace();
            return "附件上传失败！";
        }

        Chinese = table_teach_name;
        return "redirect:/zwloginUsertableTeach";
    }
}
