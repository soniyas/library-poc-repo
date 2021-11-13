package com.demo.librarypoc.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo implements Serializable {

    @NotNull(message = "ISBN cannot be null")
    String isbn;

    @NotNull(message = "author cannot be null")
    String author;

    @NotNull(message = "title cannot be null")
    String title;

    List<String> tags;

}
