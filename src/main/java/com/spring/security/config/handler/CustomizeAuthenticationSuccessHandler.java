package com.spring.security.config.handler;

import com.alibaba.fastjson.JSON;
import com.spring.security.common.entity.JsonResult;
import com.spring.security.common.utils.ResultTool;
import com.spring.security.entity.SysUser;
import com.spring.security.entity.SysUserRoleRelation;
import com.spring.security.service.SysUserRoleRelationService;
import com.spring.security.service.SysUserService;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Hutengfei
 * @Description: 登录成功处理逻辑
 * @Date Create in 2019/9/3 15:52
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserRoleRelationService sysUserRoleRelationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //获取当前用户
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //更新用户表上次登录时间、更新人、更新时间等字段
        SysUser sysUser = sysUserService.selectByName(userDetails.getUsername());
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        sysUserService.update(sysUser);

        String username = userDetails.getUsername();

        httpServletRequest.getSession().setAttribute("username",username);

        //设置重定向页面的url,默认起始为登录页面
        String  redirectUrl = "login";
        SysUserRoleRelation sysUserRoleRelation = sysUserRoleRelationService.queryRoleIdByUserId(sysUser.getId());
        //查询sys_user_role_relation表来获取用户的角色
        int role_id = sysUserRoleRelation.getRoleId();
        //role_id=1 跳到管理员界面：/AdminPage
        if (role_id == 1)
            redirectUrl = "AdminPage"+"/"+username;
        //role_id=2 跳到用户界面： /UserPage
        else if (role_id == 2)
            redirectUrl = "UserPage"+"/"+username;

        httpServletResponse.sendRedirect(redirectUrl);

/*
        //返回json数据  !!!在这返回登录结果！！
        JsonResult result = ResultTool.success();
        System.out.println(result);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
*/
    }
}
