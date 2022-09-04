package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {
}