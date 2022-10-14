package com.example.ecommerce_web.service;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorServiceTest {

    AuthorRepository authorRepository;
    AuthorServiceImpl authorService;

    @BeforeEach
    public void initTest(){
        authorRepository = mock(AuthorRepository.class);
        authorService = new AuthorServiceImpl(authorRepository);
    }


    @Test
    public void addAuthor_shouldReturnNewAuthor(){
        Author author = mock(Author.class);
        Author savedAuthor = mock(Author.class);
        when(authorRepository.save(author)).thenReturn(savedAuthor);
        assertThat(authorService.add(author), is(savedAuthor));
    }

    @Test
    public void whenGetById_shouldThrowResourceNotFoundException_ifIdNotExist(){
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> authorService.getById(0));
        assertThat(exception.getMessage(), is("Not Found Author with ID: 0"));
    }

    @Test
    public void whenGetById_shouldReturnAuthor_ifIdExisted(){
        Author author = mock(Author.class);
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        assertThat(authorService.getById(1), is(author));
    }

    @Test
    public void whenFindAll_thenThrowResourceNotFoundException_ifListNull(){
        List<Author> authors = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> authorService.findAll());
        assertThat(exception.getMessage(), is("List is Empty !!!"));
    }

    @Test
    public void whenFindAll_thenReturnList_ifListNotNull(){
        List<Author> authors = List.of(
                Author.builder()
                      .authorName("Khoi")
                      .authorID(1)
                      .books(null).build(),
                Author.builder()
                      .authorName("Tuan")
                      .authorID(2)
                      .books(null).build()
        );

        when(authorRepository.findAll()).thenReturn(authors);
        assertThat(authorService.findAll(), is(authors));
    }

    @Test
    public void whenGetPage_thenThrowResourceNotFoundException_ifPageEmpty(){
        final int PAGE_SIZE = 20;
        String search = "khoi";
        int page = 0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<AuthorRespondDTO> pageAuthorRespond = Page.empty(pageable);
        when(authorRepository.getPageAuthorNotBooks(pageable, search)).thenReturn(pageAuthorRespond);
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> authorService.getPage(page, search));

        assertThat(exception.getMessage(), is("Page is empty !!!"));
    }
}
