package com.spring.security.controller;

import com.spring.security.dao.ImageUrlDao;
import com.spring.security.dao.MyInformationDao;
import com.spring.security.entity.ImageUrl;
import com.spring.security.entity.MyInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@Controller
public class MyInformationController {
    @Autowired
    private MyInformationDao myInformationDao;
    private MyInformation myInformation;

    @Autowired
    private ImageUrlDao imageUrlDao;
    private ImageUrl imageUrl;

    @GetMapping("/toMyInformationPage")
    public String jumpToMyInformationPage(Model model) {
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();

        if (myInformationDao.queryNickname(nickname) == null) {//插入该昵称
            myInformationDao.registerNickname(nickname);
        }

        if (imageUrlDao.queryImageUrlByNickname(nickname) == null) {
            imageUrlDao.insertNickname(nickname);
        }

        myInformation = myInformationDao.queryInformationByNickname(nickname);
//        System.out.println(myInformation);
//        System.out.println(myInformation);
//        System.out.println(myInformation.getIdNumber());
        model.addAttribute("mine", myInformation);

        imageUrl = imageUrlDao.queryImageUrlByNickname(nickname);

        System.out.println(imageUrl);

        String headImgUrl = imageUrlDao.queryUrlByNickname(nickname);

        if (headImgUrl == null) imageUrl = new ImageUrl(nickname, "uploadImg/2021/12/18/jinx.png");

//        System.out.println(headImgUrl);
//        System.out.println(imageUrl.getImgUrl());

        model.addAttribute("headImage", imageUrl);

        return "myInformation";
    }

    @RequestMapping("/toUpdateMyInformation")
    public String toUpdMyInformation(MyInformation myInformation) {
//        System.out.println(myInformation);
//        System.out.println("why");
        myInformationDao.updateInformationByNickname(myInformation);
        return "redirect:/toMyInformationPage";
    }
}
