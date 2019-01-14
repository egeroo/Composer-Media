package com.egeroo.composer.core.config;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Value("${file-storage-dir}")
    private String location;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//          .addResourceHandler("/files/**")
//          .addResourceLocations("file:///"+this.location+"/"); 
    }
}