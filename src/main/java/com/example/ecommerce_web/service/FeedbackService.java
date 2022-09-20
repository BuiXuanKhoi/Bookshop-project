package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback giveFeedback(FeedbackRequestDTO feedbackRequestDTO, int bookId);
    List<Feedback> getListFeedback(int bookId);
}
