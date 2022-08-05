package com.example.graphql.demo.spqrstarter;

import java.util.List;

/**
 * @author zhengqian
 * @date 2022.08.04
 */
public interface IBookService {
    Book getBookWithTitle(String title);

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Book book);

    boolean deleteBook(Book book);
}
