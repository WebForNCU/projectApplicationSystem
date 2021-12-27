package com.spring.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch{
    private int id;
    private String batch_name;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchName() {
        return batch_name;
    }
    public void setBatchName(String batch_name) {
        this.batch_name = batch_name;
    }
}
