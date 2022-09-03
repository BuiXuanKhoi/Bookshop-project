package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.repository.InformationRepository;
import com.example.ecommerce_web.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl implements InformationService {

    InformationRepository informationRepository;

    @Autowired
    public InformationServiceImpl(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }
}
