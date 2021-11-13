package com.demo.librarypoc.controller;

import com.demo.librarypoc.dto.BookInfo;
import com.demo.librarypoc.exception.BookNotFoundException;
import com.demo.librarypoc.service.LibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/library/book")
@Api(tags = "Library - Application")
public class LibraryController {

    private final Logger log = LoggerFactory.getLogger(LibraryController.class);

    private LibraryService libraryService;

    @Autowired
    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/{isbn}")
    @ApiOperation(value = "Retrieves the book using isbn.")
    public ResponseEntity<List<BookInfo>> getBook(@PathVariable("isbn") String isbn)
            throws BookNotFoundException {
        return new ResponseEntity<>(libraryService.retrieveBook(isbn), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Adds a book into the Library.")
    public ResponseEntity<BookInfo> enrollBook(@Valid @RequestBody BookInfo bookInfo) {
        return new ResponseEntity<>(libraryService.enrollBook(bookInfo), HttpStatus.CREATED);
    }

    @PatchMapping
    @ApiOperation(value = "Updates a book in the Library.")
    public ResponseEntity<Integer> updateBook(@RequestBody BookInfo bookInfo) {
        return new ResponseEntity<>(libraryService.updateBook(bookInfo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{isbn}")
    @ApiOperation(value = "Deletes a book from the Library.")
    public ResponseEntity deleteBook(@PathVariable String isbn) {
        libraryService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<BookInfo>> listBooks(@RequestBody BookInfo bookInfo) {
        return new ResponseEntity<>(libraryService.listBooks(bookInfo), HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<List<BookInfo>> importBooks(@RequestBody List<BookInfo> bookInfos) {
        return new ResponseEntity<>(libraryService.importBooks(bookInfos), HttpStatus.OK);
    }
}
