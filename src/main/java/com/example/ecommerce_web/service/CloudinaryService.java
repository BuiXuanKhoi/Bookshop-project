package com.example.ecommerce_web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

   String uploadImage(MultipartFile bookImage);
}
