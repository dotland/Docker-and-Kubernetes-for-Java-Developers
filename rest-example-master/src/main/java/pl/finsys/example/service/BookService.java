package pl.finsys.example.service;

import pl.finsys.example.domain.Book;
import pl.finsys.example.service.exception.BookNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BookService {

    Book saveBook(@NotNull @Valid final Book book);

    List<Book> getList();

    Book getBook(Long bookId) throws BookNotFoundException;

    void deleteBook(final Long bookId);
}
