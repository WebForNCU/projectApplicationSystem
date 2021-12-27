package com.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ImgConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("uploadImg/**").addResourceLocations("file:C:\\Users\\94997\\Desktop\\web大作业\\src\\main\\resources\\static\\portraits\\");
        registry.addResourceHandler("updateUserTableTeach/**").addResourceLocations("file:C:\\Users\\94997\\Desktop\\web大作业\\src\\main\\resources\\static\\files\\");
    }
}
