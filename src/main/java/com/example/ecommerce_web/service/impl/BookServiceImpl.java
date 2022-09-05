package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.BookState;
import com.example.ecommerce_web.model.dto.request.AddBookRequest;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.Author;
import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.Classify;
import com.example.ecommerce_web.model.entities.Users;
import com.example.ecommerce_web.repository.AuthorRepository;
import com.example.ecommerce_web.repository.BookRepository;
import com.example.ecommerce_web.repository.ClassifyRepository;
import com.example.ecommerce_web.repository.UserRepository;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    ModelMapper modelMapper;
    AuthorService authorService;
    ClassifyRepository classifyRepository;
    AuthorRepository authorRepository;
    UserRepository userRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper
            , AuthorService authorService, ClassifyRepository classifyRepository
            , AuthorRepository authorRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.classifyRepository = classifyRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BookFeatureRespondDTO> getListBooks() {
        return null;
    }

    @Override
    public ResponseEntity<?> addNewBook(AddBookRequest addBookRequest) {

        int listClassifiesId[] = addBookRequest.getListClassify();
        int authorId = addBookRequest.getAuthorId();
        int creatorId = addBookRequest.getCreator();

        List<Classify> classifyList = Arrays.stream(listClassifiesId)
                .boxed().map(id -> getClassification(id)).collect(Collectors.toList());

        Optional<Author> authorOptional = this.authorRepository.findById(authorId);

        authorOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found Author !!!")
        );

        Users creator = this.userRepository.findById(creatorId).get();

        Books books = modelMapper.map(addBookRequest, Books.class);
        books.setClassifies(classifyList);
        books.setAuthors(authorOptional.get());
        books.setBookState(BookState.AVAILABLE);
        books.setCreateDay(new Date());
        books.setUsers(creator);

        this.bookRepository.save(books);
        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "Add New Book Successfully !!!"));
    }

   public Classify getClassification(int classifyId){
       Optional<Classify> classifyOptional = this.classifyRepository.findById(classifyId);

       classifyOptional.orElseThrow(
               () -> new ResourceNotFoundException("Classify Not Exist !!!!")
       );
       return classifyOptional.get();
   }
}
