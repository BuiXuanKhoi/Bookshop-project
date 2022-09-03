package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.entities.Books;

import java.util.List;

public interface BookService {

    public List<BookFeatureRespondDTO> getListBooks();
}
