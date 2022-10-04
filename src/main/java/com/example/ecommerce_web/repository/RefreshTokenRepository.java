package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.RefreshToken;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "delete from refresh_token where refresh_token_code = :refreshToken", nativeQuery = true)
    void deleteByToken(String refreshToken);


    @Modifying
    @Query(value = "delete from refresh_token where user_id = :userId", nativeQuery = true)
    void deleteByUsers(int userId);

}
