package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.model.BookState;
import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.*;
import com.example.ecommerce_web.repository.*;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    ModelMapper modelMapper;
    AuthorService authorService;
    ClassifyRepository classifyRepository;
    AuthorRepository authorRepository;
    UserRepository userRepository;
    UserLocal userLocal;
    CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper
            , AuthorService authorService, ClassifyRepository classifyRepository
            , AuthorRepository authorRepository, UserRepository userRepository, UserLocal userLocal, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.classifyRepository = classifyRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.userLocal = userLocal;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Page<BookFeatureRespondDTO> getPageBook(String searchCode, String filter, String mode, int page) {
        int[] listFilter;
        Pageable pageable = createPage(page, mode);

        if(filter.equals("0"))
        {
            listFilter = this.categoryRepository.findAll().stream()
                    .mapToInt(Category::getCategoryId).toArray();
        }else
        {
            listFilter = Arrays.stream(filter.split(","))
                    .mapToInt(Integer::parseInt).toArray();
        }

        Page<BookFeatureRespondDTO> listBookFeature = this.bookRepository.getPageBook(pageable, searchCode, listFilter);

        if (listBookFeature.hasContent()){
            return listBookFeature;
        }

        throw new ResourceNotFoundException("This Page Is Empty !!!");
    }


    @Override
    @Transactional
    public ResponseEntity<?> addNewBook(BookRequestDTO bookRequestDTO) {

        int listClassifiesId[] = bookRequestDTO.getListCategory();
        int authorId = bookRequestDTO.getAuthorId();
        String userName = userLocal.getLocalUserName();

        List<Classify> classifyList = Arrays.stream(listClassifiesId)
                .boxed().map(id -> getClassification(id)).collect(Collectors.toList());

        Optional<Author> authorOptional = this.authorRepository.findById(authorId);
        Optional<Users> usersOptional = this.userRepository.findUserByUserName(userName);


        usersOptional.orElseThrow(
                () -> new ResourceNotFoundException("Creator Not Found !!!")
        );

        authorOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found Author !!!")
        );


        Books books = modelMapper.map(bookRequestDTO, Books.class);
        books.setClassifies(classifyList);
        books.setAuthors(authorOptional.get());
        books.setBookState(BookState.AVAILABLE);
        books.setCreateDay(new Date());
        books.setUsers(usersOptional.get());

        Books savedBook = this.bookRepository.save(books);

        classifyList.forEach(classify -> {
            classify.setBooks(savedBook);
            this.classifyRepository.save(classify);
        });

        return ResponseEntity.ok(new MessageRespond(HttpStatus.CREATED.value(), "Add New Book Successfully !!!"));
    }

    @Override
    public BookRespondDTO getBookDetail(int bookId) {
        Optional<Books> booksOptional = this.bookRepository.findById(bookId);

        booksOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found Book With ID: " + bookId)
        );

        Books books = booksOptional.get();
        String authorName = books.getAuthors().getAuthorName();

        List<Classify> classifyList = books.getClassifies();

        List<String> categoryNameList = classifyList.stream()
                                                    .map(Classify::getCategory)
                                                    .map(Category::getCategoryName)
                                                    .collect(Collectors.toList());

        BookRespondDTO bookRespondDTO = modelMapper.map(books, BookRespondDTO.class);
        bookRespondDTO.setAuthorName(authorName);
        bookRespondDTO.setCategoryName(categoryNameList);
        return bookRespondDTO;
    }



    public Classify getClassification(int categoryId){
       Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);

       categoryOptional.orElseThrow(
               () -> new ResourceNotFoundException("Category Not Exist !!!!")
       );

       Classify classify = new Classify(0,null, categoryOptional.get());
       return this.classifyRepository.save(classify) ;
   }

    private Pageable createPage(int page, String sortBy){
        Sort sort;
        char field = sortBy.charAt(0);
        char mode = sortBy.charAt(1);
        String fieldSort;

        switch (field){
            case 'p':
                fieldSort = "bookPrice";
                break;
            case 'r':
                fieldSort = "ratingPoint";
                break;
            case 'n' :
                fieldSort = "bookName";
                break;
            case 'i':
                fieldSort = "bookId";
                break;
            default:
                throw new ResourceNotFoundException("NOT FOUND FIELD SORT !!!");
        }

        switch (mode){
            case 'a':
                sort = Sort.by( Sort.Direction.ASC,fieldSort);
                break;
            case 'd':
                sort = Sort.by( Sort.Direction.DESC,fieldSort);
                break;
            default:
                throw new ResourceNotFoundException("NOT FOUND MODE SORT !!!");
        }

        return PageRequest.of(page, 20, sort);
    }

    @Override
    public ResponseEntity<?> editBook(int bookId, ModifyBookRequestDTO modifyBookRequestDTO){

        Optional<Books> booksOptional = this.bookRepository.findById(bookId);

        booksOptional.orElseThrow(
                () -> new ResourceNotFoundException("Book Not Found With ID: " + bookId)
        );

        Books books = booksOptional.get();

        modelMapper.map(modifyBookRequestDTO, books);

        String state = modifyBookRequestDTO.getState();
        BookState bookState = BookState.getState(state);
        books.setBookState(bookState);

        this.bookRepository.save(books);
        return  ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Update Book successfully !!!"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBook(int bookId){

        Optional<Books> booksOptional = this.bookRepository.findById(bookId);

        booksOptional.orElseThrow(
                () -> new ResourceNotFoundException("Not Found Books With ID: " + bookId)
        );

        Books books = booksOptional.get();

        this.bookRepository.delete(books);

        return ResponseEntity.ok(new MessageRespond(HttpStatus.OK.value(), "Delete Book successfully !!!"));
    }

}
