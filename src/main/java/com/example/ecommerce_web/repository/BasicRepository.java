package com.example.ecommerce_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface BasicRepository<T> extends JpaRepository<T, Integer> {
}
