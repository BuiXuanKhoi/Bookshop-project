package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{

    @Query(value = "" +
            "select new " +
            "com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO(" +
            "aut.authorID, aut.authorName) " +
            "from Author aut " +
            "where lower(aut.authorName) like %:search%")
    Page<AuthorRespondDTO> getPageAuthor(Pageable pageable, String search);


    @Query(value = "" +
            "select new com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO(" +
            "aut.authorID, aut.authorName) from Author aut " +
            "where aut.authorID not in (select bkd.authors.authorID from Books bkd) " +
            "and lower(aut.authorName) like %:search% ")
    Page<AuthorRespondDTO> getPageAuthorNotBooks(Pageable pageable, String search);
}
