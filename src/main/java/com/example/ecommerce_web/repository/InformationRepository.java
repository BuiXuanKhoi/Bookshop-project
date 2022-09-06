package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Information;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {

    @Query(value = "select * from information where user_id = :userId", nativeQuery = true)
    Information getInformationByUsers(int userId);



}
