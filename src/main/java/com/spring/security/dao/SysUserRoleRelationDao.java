package com.spring.security.dao;

import com.spring.security.entity.SysUserRoleRelation;

public interface SysUserRoleRelationDao {
    /**
     * @description: 根据用户的id号来查用户角色信息表
     * @param UserId
     * @return: com.spring.security.entity.SysUserRoleRelation
     */
    SysUserRoleRelation queryRoleIdByUserId(Integer UserId);

    /**
     * @description: 向表中插入数据
     * @param sysUserRoleRelation
     * @return: com.spring.security.entity.SysUserRoleRelation
     */
    void insert(SysUserRoleRelation sysUserRoleRelation);
}
