package com.lucywu.springbootmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //映射路徑
        registry.addMapping("/**")
                //允許跨網域請求的來源
                .allowedOrigins("http://localhost:5173")
                //允許跨域攜帶cookie資訊，預設跨網域請求是不攜帶cookie資訊的。
                .allowCredentials(true)
                //允許使用那些請求方式
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //允許哪些Header
                .allowedHeaders("/*");
    }
}
