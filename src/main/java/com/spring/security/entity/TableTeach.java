package com.spring.security.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTeach{
    private int id;
    private String table_teach_name;
    private String table_name;
    private String table_teacher_describe;
    private String table_describe;
    private Integer audit;
    private String src;
    private Integer isfill;
    private String file_path;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableTeachName() {
        return this.table_teach_name;
    }
    public void setTableTeachName(String table_teach_name) {
        this.table_teach_name = table_teach_name;
    }

    public String getTableName() {
        return this.table_name;
    }
    public void setTableName(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_teacher_describe() {
        return this.table_teacher_describe;
    }
    public void setTable_teach_describe(String table_teacher_describe) {
        this.table_teacher_describe = table_teacher_describe;
    }

    public String getTabledescribe() {
        return this.table_describe;
    }
    public void setTabledescribe(String table_teach_describe) {
        this.table_describe = table_teach_describe;
    }

    public Integer getAudit() {
        return this.audit;
    }
    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getSrc() {
        return this.src;
    }
    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getIsfill() {
        return this.isfill;
    }
    public void setIsfill(Integer isfill) {
        this.isfill = isfill;
    }
}
