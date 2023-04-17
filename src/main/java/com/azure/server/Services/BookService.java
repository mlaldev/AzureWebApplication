package com.azure.server.Services;


import com.azure.server.Model.Book;


import java.util.List;

public interface BookService {
    
    Book addNewBook(Book bookDto);

    void addBook(Long id, int quantityToAdd);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    int getNumberOfBooksById(Long id);

    Book updateBook(Long id, Book bookDto);

    void sellBook(Long id);

    void sellBooks(List<Book> sellDtos);

    List<Book> getBookByCategoryKeyWord(String keyword);

    int getNumberOfBooksSoldByCategoryAndKeyword(String keyword);

    String deleteBookById(Long id);
}
