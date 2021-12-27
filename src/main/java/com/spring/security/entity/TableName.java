package com.spring.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableName{
    private int id;
    private String table_name;
    private String batch_name;
    private String table_describe;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return table_name;
    }
    public void setTableName(String table_name) {
        this.table_name = table_name;
    }

    public String getBatchName() {
        return batch_name;
    }
    public void setBatchName(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getTable_describe() {
        return table_describe;
    }
    public void setTable_describe(String table_describe) {
        this.table_describe = table_describe;
    }
}
