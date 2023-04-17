package com.azure.server.Controller;



import com.azure.server.Model.Book;
import com.azure.server.Services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BookController {

    @Autowired
    private BookService bookService;


    //Add New Book
    @PostMapping("/add-new-book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> addNewBook(@RequestBody Book bookDto) {
        return new ResponseEntity<>(bookService.addNewBook(bookDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/del-book/{id}")
    public ResponseEntity<?> deleteBookbyId(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.deleteBookById(id),HttpStatus.OK);
    }


    //Get Book By Id
    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    //Get All Books
    /**
     * @return
     */
    @GetMapping("/book-list")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //Get Number of books by Id
    @GetMapping("/number-of-books/{id}")
    public int getNumberOfBooksById(@PathVariable Long id) {
        return bookService.getNumberOfBooksById(id);
    }

    //Update a book
    @PutMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Book> updateBook(@PathVariable Long id,@RequestBody Book bookDto) {
        return new ResponseEntity<>(bookService.updateBook(id,bookDto),HttpStatus.OK);
    }

    //Sell a book
    @PutMapping("/sell-book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void sellBook(@PathVariable Long id) {
        bookService.sellBook(id);
    }

    //Sell List of Books
//    @PutMapping("/sell-books")
//    @ResponseStatus(HttpStatus.OK)
//    public void sellBooks(@Valid @RequestBody List<SellDto> sellDto) {
//        bookService.sellBooks(sellDto);
//    }

    //Get Book by Category and Keyword
//    @GetMapping("/books")
//    public List<BookDto> getBookByCategoryKeyWord(@RequestParam String keyword,
//                                                  @RequestParam Category category) {
//        return bookService.getBookByCategoryKeyWord(keyword, category);
//    }

    //Get Number of Books Sold Per Category/Keyword
//    @GetMapping("/number-of-books")
//    public int getNumberOfBooksSoldByCategoryAndKeyword(@RequestParam String keyword,
//                                                        @RequestParam Category category) {
//        return bookService.getNumberOfBooksSoldByCategoryAndKeyword(keyword, category);
//    }
    //Add books
//    @PutMapping("/add-book/{id}/{quantityToAdd}")
//    @ResponseStatus(HttpStatus.OK)
//    public void addBook(@PathVariable Long id,@PathVariable int quantityToAdd) {
//        bookService.addBook(id, quantityToAdd);
//    }

}