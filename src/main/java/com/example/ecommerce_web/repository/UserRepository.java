package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "select * from users where user_name = :userName", nativeQuery = true)
    Optional<Users> findUserByUserName(String userName);

}
