package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface BookRepository extends JpaRepository<Books, Integer> {
}
