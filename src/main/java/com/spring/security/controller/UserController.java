package com.spring.security.controller;

import com.spring.security.common.entity.JsonResult;
import com.spring.security.common.utils.ResultTool;
import com.spring.security.entity.SysUser;
import com.spring.security.entity.SysUserRoleRelation;
import com.spring.security.service.SysUserRoleRelationService;
import com.spring.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author: Hutengfei
 * @Description:
 * @Date Create in 2019/8/28 19:34
 */
@Controller
public class UserController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleRelationService sysUserRoleRelationService;

    @ResponseBody
    @GetMapping("/getUser")
    public JsonResult getUser() {
        List<SysUser> users = sysUserService.queryAllByLimit(1, 100);
        return ResultTool.success(users);
    }

    @ResponseBody
    @GetMapping("/test")
    public JsonResult test() {
        return ResultTool.success("hello world");
    }

    @GetMapping("/login")
    public String login()
    {
        return "myLogin";
    }

    //用户界面
    @GetMapping("/UserPage")
    public String UserPage()
    {return "UserPage";}
    //管理员界面
    @GetMapping("/AdminPage")
    public String AdminPage()
    {return "AdminPage";}

    //登录失败
    @GetMapping("/loginError")
    public String loginError()
    {
        return "loginError";
    }

    //注册页面
    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    //忘记密码页面
    @GetMapping("/forget")
    public String forget()
    {
        return "forget";
    }

    //配置处理重置密码的url
    @PostMapping("/reset")
    public String reset(HttpServletRequest request, Authentication authentication)
    {
        //根据用户名获取用户
        SysUser sysUser = sysUserService.selectByName(request.getParameter("username"));
        System.out.println("修改密码前--------------------------");
        System.out.println(sysUser.getPassword());
        //更新上一次登录时间、更新时间等字段
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());

        //获取用户需要重置的密码
        String password = request.getParameter("password");
        System.out.println(password);
        //给密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //更换密码
        sysUser.setPassword(encoder.encode(password));
        sysUserService.update(sysUser);
        System.out.println("修改密码后----------------------");
        System.out.println(sysUser.getPassword());
        //返回到登录页面
        return "myLogin";
    }

    //配置处理注册页面的url，通过注册完成向数据库中加入用户
    //完成注册返回到登录页面
    @PostMapping("/addUser")
    public String addUser(HttpServletRequest request)
    {
        //获取表单用户信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        //给密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //向数据库中添加一个用户
        SysUser user = new SysUser(username, username, encoder.encode(password), new Date(), true, true, true, true, new Date(), new Date());
        sysUserService.insert(user);

        Integer userId = user.getId();
        //默认为用户
        Integer roleId=2;
        if (role == "管理员")
            roleId=1;
        else if (role == "用户")
            roleId=2;
        //向用户角色表中插入数据
       sysUserRoleRelationService.insert(new SysUserRoleRelation(userId,roleId));
        System.out.println("注册成功返回登录");
        return "myLogin";
    }


}
