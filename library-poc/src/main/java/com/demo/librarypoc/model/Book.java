package com.demo.librarypoc.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    @Column(name="BOOK_ID")
    private UUID bookId;

    @Column(name="ISBN")
    private String isbn;

    @Column(name="AUTHOR")
    private String author;

    @Column(name="TITLE")
    private String title;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name="BOOK_ID", referencedColumnName = "BOOK_ID")
    private List<Tag> tags;

}
