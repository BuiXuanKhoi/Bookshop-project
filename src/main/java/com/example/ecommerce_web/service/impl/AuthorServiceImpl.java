package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }



    @Override
    public ResponseEntity<?> addAuthor(Author author){
        author = new Author(author.getAuthorName());

        this.authorRepository.save(author);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "New author has been added !!!"));
    }

    @Override
    public List<Author> getListAuthor() {
        List<Author> listAuthor = this.authorRepository.findAll();

        if(listAuthor.isEmpty()){
            throw new ResourceNotFoundException("Don't have any author available !!!");
        }

        return listAuthor;
    }


}
