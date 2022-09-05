package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {

    public List<BookFeatureRespondDTO> getListBooks();

    public ResponseEntity<?> addNewBook(AddBookRequest addBookRequest);
}
