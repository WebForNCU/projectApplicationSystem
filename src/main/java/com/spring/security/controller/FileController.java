package com.spring.security.controller;


import com.spring.security.entity.FilePath;


import com.spring.security.service.FilePathService;
import com.spring.security.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;

@Controller
public class FileController {
    @Autowired
    FilePathService filePathService;
    @Autowired
    TeacherService teacherService;


    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("upload") MultipartFile file, Principal principal){
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println(suffixName);
            if(!suffixName.equals(".zip")){
                return "非法上传";
            }
            // 设置文件存储路径
            String filePath = "D://demo1//src//main//resources//static//files//";

            String path = filePath + fileName;

            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            String uploadPath = "http://localhost:8081/files/"+fileName;
            Integer userId = teacherService.getTeacherIdByName(principal.getName());

            filePathService.addFilePath(new FilePath(userId,path));

            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
    @ResponseBody
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        String filePath = filePathService.getPathByUserId(teacherService.getTeacherIdByName(principal.getName()));
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);// 设置文件名
        System.out.println(filePath);
        System.out.println(fileName);
        if (fileName != null) {
            //设置文件路径
            String realPath = "D://demo1//src//main//resources//static//files//";
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                try {
                    response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                response.addHeader("Content-Length",String.valueOf(file.length()));
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载成功";
    }

    @ResponseBody
    @RequestMapping("/deleteFilePath")
    public String deleteFilePath(Principal principal){
        String realPath = filePathService.getPathByUserId(teacherService.getTeacherIdByName(principal.getName()));
        filePathService.deleteFilePath(teacherService.getTeacherIdByName(principal.getName()));
        File file = new File(realPath);
        file.delete();
        return "取消上传成功";
    }
}
