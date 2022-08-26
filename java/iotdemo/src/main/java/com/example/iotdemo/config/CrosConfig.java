package com.example.iotdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig {
    @Bean//@bean注解的作用：将对象交给springIOC容器进行管理
    public WebMvcConfigurer CorsConfiguration(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")//允许跨域请求的“来源”（即，请求的url）
                        .allowCredentials(false)//是否允许跨域请求携带cookie(当allowedOrigins的值为【*】时，这里只能为false)
                        .allowedMethods("POST","GET","DELETE","PUT","OPTIONS")//允许跨域请求的类型
                        //.exposedHeaders("")//允许response暴露的响应头（改项目中没有需要暴露的响应头，所以不设置）
                        .allowedHeaders("*");
            }
        };
    }
}
