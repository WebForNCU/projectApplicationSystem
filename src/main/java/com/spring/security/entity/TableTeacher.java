package com.spring.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTeacher{
    private int id;
    private String teach_name;
    private String table_name;
    private String teacher_describe;
    private int audit;
    private String src;
}
