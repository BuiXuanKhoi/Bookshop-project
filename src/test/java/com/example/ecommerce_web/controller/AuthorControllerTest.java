package com.example.ecommerce_web.controller;

import com.example.ecommerce_web.mapper.AuthorMapper;
import com.example.ecommerce_web.model.dto.respond.AuthorRespondDTO;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class AuthorControllerTest {
    AuthorService authorService;
    AuthorMapper authorMapper;
    SimpleGrantedAuthority admin;
    SimpleGrantedAuthority customer;

    @BeforeEach
    private void init(){
        authorService = mock(AuthorService.class);
        authorMapper = mock(AuthorMapper.class);
        admin = new SimpleGrantedAuthority("ADMIN");
        customer = new SimpleGrantedAuthority("CUSTOMER");
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenAddNewAuthor_thenReturnStatmentToInformTheAuthorHasBeenAdded () throws Exception {
        Author newAuthor = mock(Author.class);
        AuthorRespondDTO authorRespondDTO = mock(AuthorRespondDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Author author = Author.builder()
                            .authorName("Hunter S. Thompson")
                            .build();

        when(authorService.add(author)).thenReturn(newAuthor);
        when(authorMapper.toDTO(newAuthor)).thenReturn(authorRespondDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/authors")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(author))
                                    .with(user("lfsdfdlfsd").authorities(admin))
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void whenAddNewAuthor_throwsBadRequestError_ifTheAuthorNameIsEmpty () throws Exception {
        Author newAuthor = mock(Author.class);
        AuthorRespondDTO authorRespondDTO = mock(AuthorRespondDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Author author = Author.builder()
                .authorName("")
                .build();

        when(authorService.add(author)).thenReturn(newAuthor);
        when(authorMapper.toDTO(newAuthor)).thenReturn(authorRespondDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author))
                .with(user("lfsdfdlfsd").authorities(admin))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'validateMessage':{'authorName':'author name is required'}}"))
                .andDo(print());
    }

    @Test
    public void whenAddNewAuthor_throwForbiddenError_ifItIsNotAdmin () throws Exception {
        Author newAuthor = mock(Author.class);
        AuthorRespondDTO authorRespondDTO = mock(AuthorRespondDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Author author = Author.builder()
                .authorName("Jeff Kinney ")
                .build();

        when(authorService.add(author)).thenReturn(newAuthor);
        when(authorMapper.toDTO(newAuthor)).thenReturn(authorRespondDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author))
                .with(user("tuan2").authorities(customer))
        )
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    public void whenSendRequestToGetAuthorList_thenReturnListOfAuthor () throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/authors")
        )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
