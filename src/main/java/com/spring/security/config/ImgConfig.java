package com.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ImgConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("配置文件----------------------");
        registry.addResourceHandler("/portraits/**").addResourceLocations("file:C:\\Users\\94997\\Desktop\\web大作业\\src\\main\\resources\\static\\portraits\\");
        registry.addResourceHandler("/files/**").addResourceLocations("file:C:\\Users\\94997\\Desktop\\web大作业\\src\\main\\resources\\static\\files\\");

    }
}
