package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ConstraintViolateException;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.entities.*;
import com.example.ecommerce_web.repository.*;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.ClassifyService;
import com.example.ecommerce_web.service.UserService;
import com.example.ecommerce_web.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;



@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final UserLocal userLocal;
    private final CategoryRepository categoryRepository;
    private final ClassifyService classifyService;
    private final BookMapper bookMapper;
    private final UserService userService;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository
            , AuthorService authorService
            , AuthorRepository authorRepository, UserLocal userLocal,
                           CategoryRepository categoryRepository,
                           ClassifyService classifyService, BookMapper bookMapper, UserService userService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.authorRepository = authorRepository;
        this.userLocal = userLocal;
        this.categoryRepository = categoryRepository;
        this.classifyService = classifyService;
        this.bookMapper = bookMapper;
        this.userService = userService;
    }

    @Override
    public Books getById(int id) {
        return this.bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book Not Found With ID: " + id));
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
        searchCode = searchCode.toLowerCase();
        Page<BookFeatureRespondDTO> listBookFeature = bookRepository.getPageBook(pageable, searchCode, listFilter, listAuthors);

        if (!listBookFeature.hasContent()) throw new ResourceNotFoundException("This Page Is Empty !!!");

        return listBookFeature;
    }

    @Override
    public Page<BookFeatureRespondDTO> getPageManageBook(String searchCode, int page) {
        final int PAGE_SIZE = 20;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<BookFeatureRespondDTO> pageManageBook = bookRepository.getPageManageBook(pageable, searchCode);
        if (!pageManageBook.hasContent()) throw new ResourceNotFoundException("This Page Is Empty !!!");
        return pageManageBook;
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
                                            .map(classifyService::createClassify)
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
    public Books checkout(int bookId, int minusQuantity) {
        Books books = getById(bookId);
        int oldQuantity = books.getQuantity();

        if(oldQuantity == minusQuantity){
            books.setBookState(BookState.OUT_OF_STOCK);
        }
        else if (oldQuantity < minusQuantity){
            throw new ConstraintViolateException("Cannot add to order due to out of stock books !!!");
        }

        books.setQuantity(oldQuantity - minusQuantity);
        return this.bookRepository.save(books);
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
        final int PAGE_SIZE = 20;

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

        return PageRequest.of(page, PAGE_SIZE, sort);
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
