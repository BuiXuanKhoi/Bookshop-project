package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Feedback;

public interface FeedbackMapper extends RespondMapper<Feedback, FeedbackRespondDTO>
        , RequestMapper<FeedbackRequestDTO, Feedback> {
}
