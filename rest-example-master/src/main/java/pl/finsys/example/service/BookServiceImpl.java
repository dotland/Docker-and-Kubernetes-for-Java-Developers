package pl.finsys.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pl.finsys.example.domain.Book;
import pl.finsys.example.repository.BookRepository;
import pl.finsys.example.service.exception.BookAlreadyExistsException;
import pl.finsys.example.service.exception.BookNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(final BookRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Book saveBook(@NotNull @Valid final Book book) {
        LOGGER.debug("Creating {}", book);
        final Optional<Book> existing = repository.findById(book.getId());
        if (existing.isPresent()) {
            throw new BookAlreadyExistsException(
                    String.format("There already exists a book with id=%s", book.getId()));
        }
        return repository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getList() {
        LOGGER.debug("Retrieving the list of all users");
        return repository.findAll();
    }

    @Override
    public Book getBook(Long bookId) throws BookNotFoundException {
        return repository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id=" + bookId));
    }

    @Override
    @Transactional
    public void deleteBook(final Long bookId) {
        LOGGER.debug("deleting {}", bookId);
        repository.deleteById(bookId);
    }

}
