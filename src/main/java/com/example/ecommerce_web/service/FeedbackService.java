package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.dto.respond.RateCountingRespondDTO;
import com.example.ecommerce_web.model.entities.Feedback;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedbackService {
    Feedback getById(int id);
    Feedback add(FeedbackRequestDTO feedbackRequestDTO, int bookId);
    List<Feedback> findAll(int bookId);
    Page<FeedbackRespondDTO> getPageByBook(int page, int size, char mode, float filter, int bookId);
    RateCountingRespondDTO countRatingPoint(int bookId);


}
