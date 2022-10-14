package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author add(Author author){
        return this.authorRepository.save(author);
    }

    @Override
    public Author getById(int id) {
        return this.authorRepository.findById(id)
                                    .orElseThrow(
                             () -> new ResourceNotFoundException("Not Found Author with ID: " + id));
    }

    @Override
    public List<Author> findAll() {
        List<Author> listAuthor = this.authorRepository.findAll();
        return ListValidator.ofList(listAuthor).ifNotEmpty();
    }

    @Override
    public Page<AuthorRespondDTO> getPage(int page, String search) {
        final int PAGE_SIZE = 10;
        search = search.toLowerCase();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<AuthorRespondDTO> pageAuthorNotBook =  this.authorRepository.getPageAuthorNotBooks(pageable, search);
        if (!pageAuthorNotBook.hasContent()) throw new ResourceNotFoundException("Page is empty !!!");
        return pageAuthorNotBook;
    }


}
