package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Basic;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CartItemRepository extends BasicRepository<CartItem> {

    @Query(value = "select * from cart_items where user_id = :userId and book_id = :bookId", nativeQuery = true)
    Optional<CartItem> getCartItemByBooksAndUsers(int userId, int bookId);
}
