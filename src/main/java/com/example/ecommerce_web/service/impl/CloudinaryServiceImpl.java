package com.example.ecommerce_web.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ecommerce_web.config.CloudinaryConfig;
import com.example.ecommerce_web.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;


    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile bookImage) throws IOException {
        File uploadedFile = convertToFile(bookImage);
        Map uploadedResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
        boolean isDeleted = uploadedFile.delete();

        return uploadedResult.get("url").toString();
    }


    public File convertToFile(MultipartFile bookImage) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(bookImage.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(bookImage.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }
}
