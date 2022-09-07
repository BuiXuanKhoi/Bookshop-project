package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.model.dto.request.DeleteBookDTO;
import com.example.ecommerce_web.model.dto.request.EditBookDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {

    public Page<BookFeatureRespondDTO> getPageBook(String searchCode, String filter, String mode, int page);

    public ResponseEntity<?> addNewBook(AddBookRequest addBookRequest);

    public ResponseEntity<?> editBook(EditBookDTO editBookDTO);

    public ResponseEntity<?> deleteBook(DeleteBookDTO deleteBookDTO);
}
