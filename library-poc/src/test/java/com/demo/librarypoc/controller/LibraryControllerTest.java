package com.demo.librarypoc.controller;

import com.demo.librarypoc.dto.BookInfo;
import com.demo.librarypoc.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    private static final String URL_PATH = "/library/book";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void enrollBook() throws Exception {

        when(libraryService.enrollBook(any(BookInfo.class))).thenReturn(getBookInfo());
        mockMvc.perform(post(URL_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getBookInfo())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", is("Title1")))
                .andExpect(jsonPath("$.isbn", is("12345")))
                .andExpect(jsonPath("$.author", is("MT")));
    }

    @Test
    public void retrieveBook() throws Exception {

        when(libraryService.retrieveBook(anyString())).thenReturn(Arrays.asList(getBookInfo()));
        mockMvc.perform(get(URL_PATH+"/1234").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateBook() throws Exception {

        when(libraryService.retrieveBook(anyString())).thenReturn(Arrays.asList(getBookInfo()));
        mockMvc.perform(patch(URL_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getBookInfo())))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteBook() throws Exception {

        when(libraryService.retrieveBook(anyString())).thenReturn(Arrays.asList(getBookInfo()));
        mockMvc.perform(delete(URL_PATH + "/12345").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void listBook() throws Exception {

        when(libraryService.listBooks(any(BookInfo.class))).thenReturn(Arrays.asList(getBookInfo()));
        mockMvc.perform(post(URL_PATH + "/search").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getBookInfo())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].title", is("Title1")))
                .andExpect(jsonPath("$[0].isbn", is("12345")))
                .andExpect(jsonPath("$[0].author", is("MT")));
    }

    @Test
    public void importBooks() throws Exception {

        when(libraryService.importBooks(any(List.class))).thenReturn(Arrays.asList(getBookInfo()));
        mockMvc.perform(post(URL_PATH + "/import").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(getBookInfo()))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].title", is("Title1")))
                .andExpect(jsonPath("$[0].isbn", is("12345")))
                .andExpect(jsonPath("$[0].author", is("MT")))
                .andExpect(jsonPath("$[0].tags[0]", is("tag1")))
                .andExpect(jsonPath("$[0].tags[1]", is("tag2")));
    }

    private BookInfo getBookInfo() {

        return BookInfo.builder().isbn("12345").author("MT").title("Title1")
                .tags(Arrays.asList("tag1","tag2")).build();
    }

}
