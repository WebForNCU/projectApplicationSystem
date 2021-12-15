package com.spring.security.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sys_user_role_relation")
public class SysUserRoleRelation {

    @Id
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public SysUserRoleRelation() {
    }

    public SysUserRoleRelation(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
