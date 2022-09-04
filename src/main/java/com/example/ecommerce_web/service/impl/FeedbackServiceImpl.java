package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.repository.FeedbackRepository;
import com.example.ecommerce_web.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository){
        this.feedbackRepository = feedbackRepository;
    }
}
