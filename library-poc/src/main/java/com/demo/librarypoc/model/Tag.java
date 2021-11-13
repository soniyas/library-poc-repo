package com.demo.librarypoc.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    @Column(name="TAG_ID")
    private UUID tagId;

    @Column(name="NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="BOOK_ID", referencedColumnName = "BOOK_ID")
    private Book book;

}
