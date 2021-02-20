package com.example.gachonapp.MvcConfiguration;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

//@Configuration
//public class MvcConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/templates/startbootstrap-freelancer-gh-pages/")
//                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
//
//    }
//}
