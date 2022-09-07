package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BookService {

    Page<BookFeatureRespondDTO> getPageBook(String searchCode, String filter, String mode, int page);


    BookRespondDTO getBookDetail(int bookId);




    public ResponseEntity<?> addNewBook(BookRequestDTO bookRequestDTO);

    public ResponseEntity<?> editBook(int bookId, ModifyBookRequestDTO modifyBookRequestDTO);

    public ResponseEntity<?> deleteBook(int bookId);
}
