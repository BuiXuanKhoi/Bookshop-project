package com.example.ecommerce_web.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinaryConfig(){
        final String API_KEY = "225229435733562";
        final String API_SECRET_KEY = "mSGGj_5hEZhdaZcywQBg3DVTD94";
        final String CLOUD_NAME = "university-of-sciene";

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET_KEY
        ));

    }

}
