package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    Page<BookFeatureRespondDTO> getPageBook(String searchCode, String filter, String mode, int page, String authors);
    Books getById(int bookId);
    Books add(BookRequestDTO bookRequestDTO);
    Books update(int bookId, ModifyBookRequestDTO modifyBookRequestDTO);
    List<Books> findAllBooks();
    List<Books> findTopPopular();
    List<Books> findTopRecommend();
    List<Books> findTopOnSale();
    void delete(int bookId);
    CartItem getCartItemByBook(int bookId);
}
