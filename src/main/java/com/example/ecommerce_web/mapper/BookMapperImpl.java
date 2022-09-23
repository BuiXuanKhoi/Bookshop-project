package com.example.ecommerce_web.mapper;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.entities.*;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.ClassifyService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookMapperImpl implements BookMapper {


    ModelMapper modelMapper;
    UserLocal userLocal;
    UserRepository userRepository;
    AuthorRepository authorRepository;
    ClassifyService classifyService;

    @Autowired
    public BookMapperImpl(ModelMapper modelMapper, UserLocal userLocal,
                          UserRepository userRepository, AuthorRepository authorRepository,
                          ClassifyService classifyService) {
        this.modelMapper = modelMapper;
        this.userLocal = userLocal;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.classifyService = classifyService;
    }

    @Override
    public BookRespondDTO toDTO(Books entity) {
        String authorName = entity.getAuthors().getAuthorName();

        List<Classify> classifyList = entity.getClassifies();

        List<String> categoryNameList = classifyList.parallelStream()
                                                    .map(Classify::getCategory)
                                                    .map(Category::getCategoryName)
                                                    .collect(Collectors.toList());

        BookRespondDTO bookRespondDTO = modelMapper.map(entity, BookRespondDTO.class);
        bookRespondDTO.setCategoryName(categoryNameList);
        Optional.ofNullable(authorName).ifPresent(bookRespondDTO::setAuthorName);
        return bookRespondDTO;
    }


    @Override
    public Books toExistedBooks(ModifyBookRequestDTO modifyBookRequestDTO,Books books) {
        String state = modifyBookRequestDTO.getState();
        BookState bookState = BookState.getState(state);
        modelMapper.map(modifyBookRequestDTO, books);
        books.setBookState(bookState);
        return books;
    }

    @Override
    public Books fromDTO(BookRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Books.class);
    }
}
