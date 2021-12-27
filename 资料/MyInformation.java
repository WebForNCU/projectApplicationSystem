package com.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInformation {
    private String name;
    private int age;
    private String idNumber;
    private String email;
    private String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
}
