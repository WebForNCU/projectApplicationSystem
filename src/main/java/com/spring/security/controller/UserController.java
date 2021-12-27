package com.spring.security.controller;

import com.spring.security.common.entity.JsonResult;
import com.spring.security.common.utils.ResultTool;
import com.spring.security.entity.SysUser;
import com.spring.security.entity.SysUserRoleRelation;
import com.spring.security.service.SysUserRoleRelationService;
import com.spring.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private String username;
    private String role;
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


    @GetMapping("/UserPage")
    public String toUserPage(Model model)
    {
        model.addAttribute("username",this.username);
        return "UserPage";
    }
    //用户界面
    @GetMapping("/UserPage/{username}")
    public String UserPage(@PathVariable String username)
    {
        this.username = username;
        return "redirect:/UserPage";
    }

    @GetMapping("/AdminPage")
    public String toAdminPage(Model model)
    {
        model.addAttribute("username",this.username);
        return "AdminPage";
    }
    //管理员界面
    @GetMapping("/AdminPage/{username}")
    public String AdminPage(@PathVariable String username)
    {
        this.username = username;
        return "redirect:/AdminPage";
    }

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
        System.out.println(role);
        //给密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //向数据库中添加一个用户
        SysUser user = new SysUser(username, username, encoder.encode(password), new Date(), true, true, true, true, new Date(), new Date());
        sysUserService.insert(user);

        Integer userId = user.getId();
        //默认为用户
        Integer roleId=(role.equals("admin"))?1:2;
        //向用户角色表中插入数据
       sysUserRoleRelationService.insert(new SysUserRoleRelation(userId,roleId));
        System.out.println("注册成功返回登录");
        return "myLogin";
    }
    //完善个人资料页面
    @GetMapping("/myProfile")
    public String myProfile(Model model) {
        //model.addAttribute("username", username);
        //查询该用户的角色：管理员还是用户
        SysUser sysUser = sysUserService.selectByName(username);
        SysUserRoleRelation userRoleRelation = sysUserRoleRelationService.queryRoleIdByUserId(sysUser.getId());
        Integer roleId = userRoleRelation.getRoleId();
        if (roleId==1)
            role="管理员";
        else
            role="用户";

        model.addAttribute("user",sysUser);
        model.addAttribute("role",role);
        return  "profile";
    }

    //完善用户信息
    @PostMapping("/improveUser")
    public String improveUser(HttpServletRequest request,Model model)
    {
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        SysUser sysUser = sysUserService.selectByName(username);
        System.out.println(sysUser.toString());
        //更新用户
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        //完善用户信息  没有更新头像！！
        sysUser.setGender(gender);
        sysUser.setEmail(email);
        sysUser.setPhone(phone);
        sysUser.setImage(sysUser.getImage());
        sysUserService.update(sysUser);

        model.addAttribute("role",role);
        model.addAttribute("user",sysUser);
        //跳转页面可以显示用户的基本信息
        return "profile";
    }


    //管理员个人资料
    //完善个人资料页面
    @GetMapping("/AdminProfile")
    public String AdminProfile(Model model) {
        //model.addAttribute("username", username);
        //查询该用户的角色：管理员还是用户
        SysUser sysUser = sysUserService.selectByName(username);
        SysUserRoleRelation userRoleRelation = sysUserRoleRelationService.queryRoleIdByUserId(sysUser.getId());
        Integer roleId = userRoleRelation.getRoleId();
        if (roleId==1)
            role="管理员";
        else
            role="用户";
        model.addAttribute("user",sysUser);
        model.addAttribute("role",role);
        return  "AdminProfile";
    }

    //完善用户信息
    @PostMapping("/improveAdmin")
    public String improveAdmin(HttpServletRequest request,Model model)
    {
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        SysUser sysUser = sysUserService.selectByName(username);
        System.out.println(sysUser.toString());
        //更新用户
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        //完善用户信息
        sysUser.setGender(gender);
        sysUser.setEmail(email);
        sysUser.setPhone(phone);
        sysUserService.update(sysUser);
        model.addAttribute("role",role);
        model.addAttribute("user",sysUser);
        //跳转页面可以显示用户的基本信息
        return "AdminProfile";
    }

    //查询用户所有信息
    @GetMapping("/queryAllUsers")
    public String queryAllUsers(Model model)
    {
        List<SysUser> users = sysUserService.queryAllUsers();
        model.addAttribute("users",users);
        return "queryAllUsers";
    }

    //管理员功能：删除用户
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id)
    {
        sysUserService.deleteById(id);
        sysUserRoleRelationService.delete(id);
        return "redirect:/queryAllUsers";
    }

    //管理员功能：更新用户信息
    @PostMapping("/updateUser")
    public String updateUser(HttpServletRequest request)
    {
        String username = request.getParameter("username");
        System.out.println(username);
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        SysUser sysUser = sysUserService.selectByName(username);
        //System.out.println(sysUser.toString());
        //更新用户
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        //完善用户信息
        sysUser.setGender(gender);
        sysUser.setEmail(email);
        sysUser.setPhone(phone);
        sysUserService.update(sysUser);
        return "redirect:/queryAllUsers";
    }
}
