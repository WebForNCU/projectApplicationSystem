package com.spring.security.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(SysUser)实体类
 *
 * @author makejava
 * @since 2019-09-03 15:06:48
 */
@Entity(name = "sys_user")
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 915478504870211231L;
    @Id
    private Integer id;
    //账号
    private String account;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //上一次登录时间
    private Date lastLoginTime;
    //账号是否可用。默认为1（可用）
    private Boolean enabled;
    //是否过期。默认为1（没有过期）
    private Boolean accountNonExpired;
    //账号是否锁定。默认为1（没有锁定）
    private Boolean accountNonLocked;
    //证书（密码）是否过期。默认为1（没有过期）
    private Boolean credentialsNonExpired;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //创建人
    private Integer createUser;
    //修改人
    private Integer updateUser;
    //性别
    private String gender;
    //邮箱
    private String email;
    //电话
    private String phone;
    //头像url
    private String image;

    public SysUser() {
    }
    //构造初始化部分参数
    public SysUser(String account, String userName, String password, Date lastLoginTime, Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Date createTime, Date updateTime) {
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}