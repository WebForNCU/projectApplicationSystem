package com.spring.security.service.impl;

import com.spring.security.dao.SysUserRoleRelationDao;
import com.spring.security.entity.SysUserRoleRelation;
import com.spring.security.service.SysUserRoleRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("sysUserRoleRelationService")
public class SysUserRoleRelationServiceImpl implements SysUserRoleRelationService {
    @Resource
    private SysUserRoleRelationDao sysUserRoleRelationDao;

    @Override
    public SysUserRoleRelation queryRoleIdByUserId(Integer userId) {
            return this.sysUserRoleRelationDao.queryRoleIdByUserId(userId);
        }

    @Override
    public void insert(SysUserRoleRelation sysUserRoleRelation) {
        sysUserRoleRelationDao.insert(sysUserRoleRelation);
    }


}
