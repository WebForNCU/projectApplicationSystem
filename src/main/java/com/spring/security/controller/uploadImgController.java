package com.spring.security.controller;

import com.spring.security.dao.ImageUrlDao;
import com.spring.security.entity.ImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
//@RestController
public class uploadImgController {
    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/uploadImg/";
    public String filePath;

    @Autowired
    private ImageUrlDao imageUrlDao;
    private ImageUrl imageUrl;

    @PostMapping("/headImg")
//    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile uploadFile) {
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
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);

            //获取存入数据库中的路径
            filePath = UPLOAD_PATH_PREFIX.substring(7) + format + File.separator;
            filePath = filePath.substring(0, filePath.length() - 1);
            filePath = filePath + newName;

            System.out.println(filePath);

            String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

            imageUrl = new ImageUrl(nickname, filePath);

            imageUrlDao.updateUrlByNickname(imageUrl);

            return "redirect:/toMyInformationPage";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败!";
        }
    }


}
