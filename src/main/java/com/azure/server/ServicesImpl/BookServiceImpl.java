package com.azure.server.ServicesImpl;



import com.azure.server.CustomExceptions.BadRequestException;
import com.azure.server.CustomExceptions.BookNotFoundException;
import com.azure.server.CustomExceptions.DuplicateResourceException;
import com.azure.server.CustomExceptions.UserNotFoundException;
import com.azure.server.Model.Book;
import com.azure.server.Model.Role;
import com.azure.server.Model.User;
import com.azure.server.Repository.BookRepository;
import com.azure.server.Services.BookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;
//    @Autowired
//    private final ModelMapper modelMapper;

//    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
//        this.bookRepository = bookRepository;
//        this.modelMapper = modelMapper;
//    }

    /**
     * List all the books
     *
     * @return List<BookDto>
     */
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return (books);
    }
    /**
     * Register new book with new identifier into database
     * Save the book In Book Domain
     * Maintain the count in BookCount Domain
     *
     * @param bookDto
     */
    @Override
    @Transactional
    public Book addNewBook(Book bookDto) {
        //Check if bookDto is previously present
        Book book = bookRepository.findByTitle(bookDto.getTitle());
        if(book!=null){
            throw new DuplicateResourceException("Book with same id present. " +
                    "Either use update methods to update the book counts or use addBook(Long id, int quantityToAdd) methods");
        }else {
            LOGGER.info("No Duplicates found.");
            //Map bookDto to book

            //Set the status to available
            LOGGER.info("The data are mapped and ready to save.");

            //Save to book
            return bookRepository.save(bookDto);
        }
    }

    /**
     * Get book by id
     *
     * @param id
     * @return bookdto
     */
    @Override
    public Book getBookById(Long id) {
        //Get the book from repo
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id:" + id + " is not found."));

        return book;
    }


    /**
     * update a book
     *
     * @param id
     * @param bookDto
     */
    @Override
    @Transactional
    public Book updateBook(Long id, Book bookDto) {
        if (bookDto.getId() != null) {
            if (!bookDto.getId().equals(id)) {
                throw new BadRequestException("Id cannot be updated.");
            }
        }
        Book existingBook = bookRepository.findByTitle(bookDto.getTitle());
        if (existingBook == null) {
            System.out.println("Book does not exist with this title.");
            throw new BookNotFoundException("Book does not exist with title:" + bookDto.getTitle());
        }
            existingBook.setTitle(bookDto.getTitle());
            existingBook.setAuthor(bookDto.getAuthor());
            existingBook.setPrice(bookDto.getPrice());
            existingBook.setCategory(bookDto.getCategory());
            existingBook.setPublisher(bookDto.getPublisher());
            existingBook.setImageUrl(bookDto.getImageUrl());

            return bookRepository.save(existingBook);

    }

    @Override
    public String deleteBookById(Long id) {
        try {
            Optional<Book> existingBook =
                    Optional.ofNullable(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book does not exist")));
            bookRepository.deleteById(id);
        }catch (BadRequestException e){
            return e.getMessage();
        }
        return "Book Deleted Successfully!!";
    }











    /**
     * This method adds the quantity of book if the book with given id is already registered.
     *
     * @param id
     * @param quantityToAdd
     */
    @Override
    public void addBook(Long id, int quantityToAdd) {
        //Get the book by id
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id:" + id + " is not registered. Use addNewBook to register."));
        LOGGER.info("The book with id " + id + " is registered");


        bookRepository.save(book);
    }




    @Override
    public int getNumberOfBooksById(Long id) {
        return 0;
    }

    /**
     * Number of books on particular identifier
     *
     * @param id
     * @return
     */
//    @Override
//    public int getNumberOfBooksById(Long id) {
//        Optional<Book> book = bookRepository.findById(id);
//
//        //If book is present get Total Count else return 0
//        return book.isPresent() ? book.get().getTotalCount() : 0;
//    }


    @Override
    public void sellBook(Long id) {

    }

    @Override
    public void sellBooks(List<Book> sellDtos) {

    }

    @Override
    public List<Book> getBookByCategoryKeyWord(String keyword) {
        return null;
    }

    @Override
    public int getNumberOfBooksSoldByCategoryAndKeyword(String keyword) {
        return 0;
    }


    /**
     * Sell book of given id
     *
     * @param id
     */
//    @Override
//    @Transactional
//    public void sellBook(Long id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " is not found."));
//        //Selling one book decreases the amount of book in the store and increases the amount of book sold.
//        int totalCount = book.getTotalCount() - 1;
//        if (totalCount < 0) {
//            throw new BadRequestException("TotalCount cannot be negative. Not enough book in store to sell.");
//        }
//        int sold = book.getSold() + 1;
//        LOGGER.info("Setting total amount less by 1 and setting sold to increase by 1.");
//        book.setTotalCount(totalCount);
//        book.setSold(sold);
//        bookRepository.save(book);
//    }

    /**
     * @param sellDtos
     */
//    @Override
//    @Transactional
//    public void sellBooks(List<SellDto> sellDtos) {
//        sellDtos.forEach(sellDto -> {
//            Book book = bookRepository.findById(sellDto.getBookId())
//                    .orElseThrow(() -> new BookNotFoundException("Book with id: " + sellDto.getBookId() + " is not found."));
//            //Selling book decreases the amount of book in the store and increases the amount of book sold.
//            int totalCount = book.getTotalCount() - sellDto.getQuantity();
//            if (totalCount < 0) {
//                throw new BadRequestException("TotalCount cannot be negative. Not enough book in store to sell.");
//            }
//            int sold = book.getSold() + sellDto.getQuantity();
//            LOGGER.info("Total amount is decreased and sold amount increased.");
//            book.setTotalCount(totalCount);
//            book.setSold(sold);
//            bookRepository.save(book);
//        });
//    }


    /**
     * Get the list of books according to category and keyword
     * Keyword is assumed to be any words in id, title and author field of the book
     *
     * @param category
     * @param keyword
     * @return
     */
//    @Override
//    public List<BookDto> getBookByCategoryKeyWord(String keyword,
//                                                  Category category) {
//
//        //if the status is Available, gives list of books which are available
//        LOGGER.info("Fetch all the books by category and keyword.");
//        List<Book> book = bookRepository.findAllBookByCategoryAndKeyword(keyword.toLowerCase(), category.getValue());
//        return mapBookListToBooDtoList(book);
//    }

    /**
     * Getting the number of books by category and keyword
     *
     * @param category
     * @param keyword
     * @return
     */
//    @Override
//    public int getNumberOfBooksSoldByCategoryAndKeyword(String keyword) {
//        LOGGER.info("Total number of books which are sold");
//        return (int) bookRepository.countNumberOfBooksSold(keyword.toLowerCase());
//    }

    //Convert List of books to List of bookDto
//    private List<Book> mapBookListToBooDtoList(List<Book> books) {
//        return books.stream()
//                .map(book -> modelMapper.map(book, Book.class))
//                .collect(Collectors.toList());
//    }
}