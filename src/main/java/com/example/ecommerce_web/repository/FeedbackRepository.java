package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Feedback;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {

    @Query(value = "select new com.example.ecommerce_web.model.dto.respond.FeedbackRespondDTO(" +
            "u.userName, f.createDay, f.comment, f.ratingPoint, f.title ) " +
            "from Feedback f " +
            "join f.users u " +
            "where f.ratingPoint >= :min " +
            "and f.ratingPoint < :max " +
            "and f.books.bookId = :bookId", nativeQuery = false)
    Page<FeedbackRespondDTO> getPageFeedback(Pageable pageable, float min, float max, int bookId);

    boolean existsByUsersAndBooks(Users users, Books books);


    Integer countByRatingPointIsBetweenAndBooks(float min, float max, Books books);
}
