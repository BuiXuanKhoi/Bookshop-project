package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;

public interface FeedbackService {

    FeedbackRespondDTO giveFeedback(FeedbackRequestDTO feedbackRequestDTO, int bookId);



}
