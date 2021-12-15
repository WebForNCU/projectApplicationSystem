package com.spring.security.service;

import com.spring.security.entity.SysUserRoleRelation;

public interface SysUserRoleRelationService {
    /**
     * @description: 根据用户的id号来查询角色id号
     * @param UserId
     * @return: com.spring.security.entity.SysUserRoleRelation
     */
    SysUserRoleRelation queryRoleIdByUserId(Integer UserId);

    /**
     * @description:向表中插入数据
     * @param sysUserRoleRelation
     * @return: com.spring.security.entity.SysUserRoleRelation
     */
    void insert(SysUserRoleRelation sysUserRoleRelation);

}
