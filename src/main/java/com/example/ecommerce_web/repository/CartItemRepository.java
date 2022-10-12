package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.CartItem;
import com.example.ecommerce_web.model.entities.Users;
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
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> getCartItemByBooksAndUsers(Users users, Books books);


    @Modifying
    @Query(value = "delete from cart_items where cart_item_id = :cartId", nativeQuery = true)
    void deleteByCartId(int cartId);

    boolean existsByBooksAndUsers(Books books, Users users);
}
