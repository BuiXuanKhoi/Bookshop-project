package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.repository.ClassifyRepository;
import com.example.ecommerce_web.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassifyServiceImpl implements ClassifyService {
    ClassifyRepository classifyRepository;

    @Autowired
    public ClassifyServiceImpl(ClassifyRepository classifyRepository){
        this.classifyRepository = classifyRepository;
    }
}
