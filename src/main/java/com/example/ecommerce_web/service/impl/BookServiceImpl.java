package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.mapper.BookMapperImpl;
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
import com.example.ecommerce_web.service.ClassifyService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
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
import java.awt.print.Book;
import java.util.*;
import java.util.stream.Collectors;

import java.util.Collections;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    AuthorService authorService;
    ClassifyRepository classifyRepository;
    AuthorRepository authorRepository;
    UserRepository userRepository;
    UserLocal userLocal;
    CategoryRepository categoryRepository;
    FeedbackRepository feedbackRepository;
    ClassifyService classifyService;
    BookMapper bookMapper;
    UserService userService;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository
            , AuthorService authorService, ClassifyRepository classifyRepository
            , AuthorRepository authorRepository, UserRepository userRepository, UserLocal userLocal,
                           CategoryRepository categoryRepository, FeedbackRepository feedbackRepository,
                           ClassifyService classifyService, BookMapper bookMapper, UserService userService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.classifyRepository = classifyRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.userLocal = userLocal;
        this.categoryRepository = categoryRepository;
        this.feedbackRepository = feedbackRepository;
        this.classifyService = classifyService;
        this.bookMapper = bookMapper;
        this.userService = userService;
    }

    @Override
    public Books getById(int id) {
        return this.bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book Not Found With ID: " + id)
        );
    }

    @Override
    public List<Books> findAll() {
        List<Books> listBookRecommend = this.bookRepository.findAll();
        ListValidator<Books> listBookValid = ListValidator.ofList(listBookRecommend);
        return listBookValid.ifNotEmpty();
    }


    @Override
    public Page<BookFeatureRespondDTO> getPage(String searchCode, String filter, String mode, int page, String authors)
    {
        int[] listFilter = getCategoriesFilter(filter);
        int[] listAuthors = getAuthorsFilter(authors);
        Pageable pageable = createPage(page, mode);
        Page<BookFeatureRespondDTO> listBookFeature = bookRepository.getPageBook(pageable, searchCode, listFilter, listAuthors);

        if (!listBookFeature.hasContent()) throw new ResourceNotFoundException("This Page Is Empty !!!");

        return listBookFeature;
    }


    @Override
    public Books add(BookRequestDTO bookRequestDTO) {

        int[] listCategoryId = bookRequestDTO.getListCategory();
        int authorId = bookRequestDTO.getAuthorId();
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);

        Author author = authorService.getById(authorId);

        List<Classify> classifyList = Arrays.stream(listCategoryId)
                                            .boxed()
                                            .map(id -> classifyService.createClassify(id))
                                            .collect(Collectors.toList());

        Books books = bookMapper.fromDTO(bookRequestDTO);

        books.setClassifies(classifyList);
        books.setAuthors(author);
        books.setUsers(users);
        books.setBookState(BookState.AVAILABLE);
        Books savedBook = this.bookRepository.save(books);
        classifyList.forEach(classify -> classifyService.updateClassifyWithBook(classify, savedBook));

        return savedBook;
    }


    @Override
    @Transactional
    public void delete(int bookId){
        Books books = getById(bookId);
        this.bookRepository.delete(books);
    }

    @Override
    public CartItem getCartItemByBook(int bookId) {
        String userName = userLocal.getLocalUserName();
        Users users = userService.findByUserName(userName);
        Books books = getById(bookId);
        return users.getCartItems()
                    .stream()
                    .filter(item -> item.getBooks().equals(books))
                    .findAny()
                    .orElseThrow(
                        () -> new ResourceNotFoundException("Don't have cart item exist with this book !"));
    }

    @Override
    public Books update(int bookId, ModifyBookRequestDTO request) {
        Books books = getById(bookId);
        Books mappedBook = bookMapper.toExistedBooks(request, books);
        return this.bookRepository.save(mappedBook);
    }



    @Override
    public List<Books> findTopPopular() {
        return findAll().stream()
                        .sorted(Comparator.comparingInt(Books::getQuantity))
                        .limit(8)
                        .collect(Collectors.toList());
    }

    @Override
    public List<Books> findTopOnSale(){
        return findAll().stream()
                        .sorted(Comparator.comparingDouble(Books::getBookPrice))
                        .limit(10)
                        .collect(Collectors.toList());
    }

    @Override
    public List<Books> findTopRecommend() {
        return findAll().stream()
                        .sorted(Comparator.comparingDouble(Books::getRatingPoint).reversed())
                        .limit(8)
                        .collect(Collectors.toList());
    }

    private Pageable createPage(int page, String sortBy){
        char field = sortBy.charAt(0);
        char mode = sortBy.charAt(1);

        String fieldSort = switch (field) {
            case 'p' -> "bookPrice";
            case 'r' -> "ratingPoint";
            case 'n' -> "bookName";
            case 'i' -> "bookId";
            default -> throw new ResourceNotFoundException("NOT FOUND FIELD SORT !!!");
        };

        Sort sort = switch (mode) {
            case 'a' -> Sort.by(Sort.Direction.ASC, fieldSort);
            case 'd' -> Sort.by(Sort.Direction.DESC, fieldSort);
            default -> throw new ResourceNotFoundException("NOT FOUND MODE SORT !!!");
        };

        return PageRequest.of(page, 20, sort);
    }

    private int[] getAuthorsFilter(String authors){

        if (authors.equals("0")){
            return this.authorRepository.findAll()
                                        .stream()
                                        .mapToInt(Author::getAuthorID)
                                        .toArray();
        }

        return Arrays.stream(authors.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();



    }

    private int[] getCategoriesFilter(String filter){

        if(filter.equals("0")) {
            return this.categoryRepository.findAll().stream()
                                                    .mapToInt(Category::getCategoryId)
                                                    .toArray();
        }

        return Arrays.stream(filter.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
    }

}
