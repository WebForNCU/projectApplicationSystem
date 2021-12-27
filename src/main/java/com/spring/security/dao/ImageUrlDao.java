package com.spring.security.dao;

import com.spring.security.entity.ImageUrl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageUrlDao {
    //查询昵称对应的头像路径
    public String queryUrlByNickname(String nickname);

    //修改昵称对应的头像路径
    public void updateUrlByNickname(ImageUrl imageUrl);

    //录入昵称
    public int insertNickname(String nickname);

    //查询昵称是否已录入
    public ImageUrl queryImageUrlByNickname(String nickname);
}
