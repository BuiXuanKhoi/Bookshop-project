package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Classify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {


    @Query(value = "select distinct NEW com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO(" +
            "bkk.bookId, bkk.bookName, bkk.bookPrice, bkk.ratingPoint, bkk.imageLink, au.authorName) " +
            "from Books bkk " +
            "join bkk.classifies cls " +
            "join bkk.authors au " +
            "where " +
            "bkk.bookName like %:searchCode% " +
            "and " +
            "bkk.bookState = 'AVAILABLE' " +
            "and " +
            "cls.category.categoryId in :filter " +
            "and " +
            "au.authorID in :author")
    Page<BookFeatureRespondDTO> getPageBook(Pageable pageable, String searchCode, int[] filter, int[] author);
}


