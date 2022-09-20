package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Books;

public interface BookMapper extends RespondMapper<Books, BookRespondDTO>{

    Books toExistedBooks(ModifyBookRequestDTO modifyBookRequestDTO,Books books);
}
