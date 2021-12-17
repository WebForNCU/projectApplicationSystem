package com.spring.security.controller;

import com.spring.security.entity.Teacher;
import com.spring.security.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FillingController {

    @Autowired
    TeacherService teacherService;

    @RequestMapping("/queryMyFilling")
    public String queryMyFilling(){
        return "query";
    }

    @RequestMapping("/display")
    public String display(Model model,Principal principal){
        System.out.println(principal.getName());
        Teacher teacher = teacherService.queryTeacherByName(principal.getName());
        String[] infos = teacher.getFill_info().split(",");
        model.addAttribute("infos",infos);
        model.addAttribute("teacher",teacher);
        return "display";
    }
    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id")int id, Model model, Principal principal){
        Teacher teacher = teacherService.queryTeacherById(id);
        String[] infos = teacher.getFill_info().split(",");
        String[] labels = {"序号","填报人","教材名称","主编","主编排序","出版社","出版时间","教材入选情况","入选时间"};
        model.addAttribute("infos",infos);
        model.addAttribute("labels",labels);
        return "update";
    }
    /*@PostMapping("/updateForm")
    public String updateFilling(HttpServletRequest request){
        String[] infos = request.getParameterValues("infos");
        teacherService.addTeacher()
    }


     */
}
