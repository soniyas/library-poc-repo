package com.demo.librarypoc.repository;

import com.demo.librarypoc.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findAllByTitle(String title);

    List<Book> findAllByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    void deleteAllByIsbn(String isbn);

    @Query("SELECT book,tag FROM Book book JOIN Tag tag ON book.bookId = tag.book" +
            " WHERE (book.isbn = :isbn OR :isbn IS NULL)" +
            " OR (book.title = :title OR :title IS NULL) " +
            " OR (book.author = :author OR :author is NULL)" +
            "OR (tag.name IN :tags)")
    Optional<List<Book>> findbyBookInfo(@Param("isbn") String isbn
            , @Param("title") String title, @Param("author") String author
            , @Param("tags") List<String> tags
    );
}
