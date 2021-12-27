package com.spring.security.dao;

import com.spring.security.entity.MyInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MyInformationDao {
    //根据昵称查询个人信息
    MyInformation queryInformationByNickname(String nickname);

    //根据昵称修改个人信息
    Integer updateInformationByNickname(MyInformation myInformation);

    //查询昵称是否已录入个人信息库
    MyInformation queryNickname(String nickname);

    //录入昵称
    int registerNickname(String nickname);
}
