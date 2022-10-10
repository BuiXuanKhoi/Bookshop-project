package com.example.ecommerce_web.repository;


import com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO;
import com.example.ecommerce_web.model.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "" +
            "select new com.example.ecommerce_web.model.dto.respond.CategoryRespondDTO(" +
            "ctg.categoryId, ctg.categoryName, ctg.categoryDescription) " +
            "from Category ctg " +
            "where lower(ctg.categoryName)  like %:search%")
    Page<CategoryRespondDTO> getPage(Pageable pageable, String search);
}
