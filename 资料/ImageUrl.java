package com.spring.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUrl {
    private String nickname = SecurityContextHolder.getContext().getAuthentication().getName();;
    private String imgUrl;
}
