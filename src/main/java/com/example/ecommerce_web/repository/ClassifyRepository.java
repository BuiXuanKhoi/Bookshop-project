package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Classify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface ClassifyRepository extends JpaRepository<Classify, Integer> {
}
