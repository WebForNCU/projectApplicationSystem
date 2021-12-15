package com.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    private int id;
    private String batch_name;
    private Timestamp build_date;
    private String item_name;
}
