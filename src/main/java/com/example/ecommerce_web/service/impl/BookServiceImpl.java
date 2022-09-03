package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookFeatureRespondDTO> getListBooks() {
        return null;
    }
}
