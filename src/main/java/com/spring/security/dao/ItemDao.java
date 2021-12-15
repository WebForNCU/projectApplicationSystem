package com.spring.security.dao;

import com.spring.security.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ItemDao {
    List<Item> queryItemList();

    Item queryItemById(int id);

    int addItem(Item item);

    int updateItem(Item item);

    int deleteItem(Item item);
}
