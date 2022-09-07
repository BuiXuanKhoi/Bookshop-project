package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.model.dto.request.EditBookDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {

    Page<BookFeatureRespondDTO> getPageBook(String searchCode, String filter, String mode, int page);

    ResponseEntity<?> addNewBook(AddBookRequest addBookRequest);

    BookRespondDTO getBookDetail(int bookId);




    public ResponseEntity<?> addNewBook(AddBookRequest addBookRequest);

    public ResponseEntity<?> editBook(EditBookDTO editBookDTO);
}
