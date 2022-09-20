package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.request.FeedbackRequestDTO;
import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeedbackMapperImpl implements FeedbackMapper{

    ModelMapper modelMapper;

    @Autowired
    public FeedbackMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FeedbackRespondDTO toDTO(Feedback entity) {
        Users users = entity.getUsers();
        String userName = users.getUserName();
        Date createDay = entity.getCreateDay();
        String comment = entity.getComment();
        float ratingPoint = entity.getRatingPoint();


        return FeedbackRespondDTO.builder()
                                 .userName(userName)
                                 .comment(comment)
                                 .ratingPoint(ratingPoint)
                                 .CreateDay(createDay)
                                 .build();
    }

    @Override
    public Feedback fromDTO(FeedbackRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Feedback.class);
    }
}
