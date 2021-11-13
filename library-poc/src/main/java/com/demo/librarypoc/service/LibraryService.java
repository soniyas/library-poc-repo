package com.demo.librarypoc.service;


import com.demo.librarypoc.dto.BookInfo;
import com.demo.librarypoc.exception.BookNotFoundException;
import com.demo.librarypoc.model.Book;
import com.demo.librarypoc.model.Tag;
import com.demo.librarypoc.repository.BookRepository;
import com.demo.librarypoc.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private Logger LOGGER = LoggerFactory.getLogger(LibraryService.class);

    private final BookRepository bookRepository;

    private final TagRepository tagRepository;

    public LibraryService(BookRepository bookRepository, TagRepository tagRepository) {
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
    }

    /**
     * Enrolls a book into the Library
     *
     * @param bookInfo Book information
     * @return Book Information
     */
    public BookInfo enrollBook(BookInfo bookInfo) {
        List<Tag> tags = getTags(bookInfo);
        Book book = Book.builder().isbn(bookInfo.getIsbn())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .tags(tags)
                .build();
        bookRepository.save(book);
        return mapToBookInfo(book);

    }

    /**
     * @param bookInfos Imports the books to the Library
     * @return Books Information
     */
    public List<BookInfo> importBooks(List<BookInfo> bookInfos) {
        LOGGER.info("Importing books");
        return bookInfos.stream().map(bookInfo -> enrollBook(bookInfo)).collect(Collectors.toList());
    }

    /**
     * @param isbn Retrieves books using isbn
     * @return Books Information
     * @throws BookNotFoundException
     */
    public List<BookInfo> retrieveBook(String isbn) throws BookNotFoundException {
        LOGGER.info("Retrieving book by ISBN: {}", isbn);
        List<Book> books = bookRepository.findAllByIsbn(isbn);
        if (books.size() == 0) {
            throw new BookNotFoundException("No book found with the given isbn", "5002", HttpStatus.NOT_FOUND);
        }
        return books.stream().map(book -> mapToBookInfo(book)).collect(Collectors.toList());
    }

    /**
     * @param isbn Deletes all books by isbn
     */
    @Transactional
    public void deleteBook(String isbn) {
        bookRepository.deleteAllByIsbn(isbn);
    }

    /**
     * @param bookInfo Updates book by isbn
     * @return
     */
    public int updateBook(BookInfo bookInfo) {
        LOGGER.info("Updating book by ISBN: {}", bookInfo.getIsbn());
        List<Book> books = bookRepository.findAllByIsbn(bookInfo.getIsbn());
        AtomicInteger count = new AtomicInteger();
        books.stream().map(book -> {
            count.incrementAndGet();
            book.setAuthor(bookInfo.getAuthor());
            book.setTitle(bookInfo.getTitle());
            book.setTags(getTags(bookInfo));
            bookRepository.save(book);
            return count;
        });
        return count.get();
    }

    public List<BookInfo> listBooks(BookInfo bookInfo) {
        Optional<List<Book>> books = bookRepository.findbyBookInfo(bookInfo.getIsbn(),
                bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getTags());
        return books.get().stream().map(book -> mapToBookInfo(book)).collect(Collectors.toList());
    }

    private List<Tag> getTags(BookInfo bookInfo) {
        List<Tag> tags = new ArrayList<>();
        bookInfo.getTags().forEach(tag -> {
            tags.add(Tag.builder().name(tag).build());
        });
        return tags;
    }

    private BookInfo mapToBookInfo(Book book) {
        List<String> tags = book.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
        return BookInfo.builder()
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .tags(tags)
                .build();
    }

}