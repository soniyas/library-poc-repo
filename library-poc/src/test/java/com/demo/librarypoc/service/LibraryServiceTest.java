package com.demo.librarypoc.service;

import com.demo.librarypoc.dto.BookInfo;
import com.demo.librarypoc.exception.BookNotFoundException;
import com.demo.librarypoc.model.Book;
import com.demo.librarypoc.model.Tag;
import com.demo.librarypoc.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    private LibraryService libraryService;

    @Mock
    private BookRepository bookRepository;

    private BookInfo bookInfo;

    private Book mockedBook;


    @BeforeEach
    public void init(){
        libraryService = new LibraryService(bookRepository);
        bookInfo = BookInfo.builder().isbn("ISBN1").title("Alchemist")
                .author("Paulo Coelho").tags(Arrays.asList("tag1")).build();
        mockedBook = Book.builder()
                .isbn("ISBN1").title("Alchemist")
                .author("Paulo Coelho").tags(Collections.singletonList(Tag.builder().name("tag1").build()))
                .build();
    }

    @Test
    void enrollBook() {

        when(bookRepository.save(any(Book.class))).thenReturn(mockedBook);
        BookInfo enrolledBookInfo = libraryService.enrollBook(bookInfo);
        Assertions.assertEquals(mockedBook.getAuthor(), enrolledBookInfo.getAuthor());
        Assertions.assertEquals(mockedBook.getTitle(), enrolledBookInfo.getTitle());
        Assertions.assertEquals(mockedBook.getIsbn(), enrolledBookInfo.getIsbn());
        Assertions.assertEquals(mockedBook.getTags().get(0).getName(), enrolledBookInfo.getTags().get(0));

    }

    @Test
    void importBooks() {
        
        BookInfo bookInfo2 = BookInfo.builder().isbn("ISBN2")
                .title("Randamoozham").author("MT").tags(Arrays.asList("tag2")).build();
        Book mockedBook2 = Book.builder()
                .title("Alchemist").author("Paulo Coelho")
                .tags(Collections.singletonList(Tag.builder().name("tag1").build()))
                .build();
        when(bookRepository.save(any(Book.class))).thenReturn(mockedBook).thenReturn(mockedBook2);

        List<BookInfo> bookInfos = libraryService.importBooks(Arrays.asList(bookInfo, bookInfo2));
        Assertions.assertEquals(2, bookInfos.size());
    }

    @Test
    void retrieveBook_failure() {
        
        Assertions.assertThrows(BookNotFoundException.class, () -> {
          libraryService.retrieveBook("1234");
        });
    }

    @Test
    void retrieveBook() throws BookNotFoundException {

        when(bookRepository.findAllByIsbn(anyString())).thenReturn(Optional.of(Arrays.asList(mockedBook)));
        List<BookInfo> bookInfos = libraryService.retrieveBook("1234");
        Assertions.assertEquals(1, bookInfos.size());

    }

    @Test
    void updateBook() {

        when(bookRepository.findAllByIsbn(anyString())).thenReturn(Optional.of(Arrays.asList(mockedBook)));
        int count = libraryService.updateBook(bookInfo);
        Assertions.assertEquals(1, count);
    }

    @Test
    void listBooks() {

        Book mockedBook2 = Book.builder()
                .isbn("ISBN1").title("Alchemist")
                .author("Paulo Coelho").tags(Collections.singletonList(Tag.builder().name("tag1").build()))
                .build();
        when(bookRepository.findbyBookInfo(anyString(),anyString(), anyString(),any(List.class)))
                .thenReturn(Optional.of(Arrays.asList(mockedBook, mockedBook2)));
        List<BookInfo> bookInfos = libraryService.listBooks(bookInfo);
        Assertions.assertEquals(2, bookInfos.size());
    }
}