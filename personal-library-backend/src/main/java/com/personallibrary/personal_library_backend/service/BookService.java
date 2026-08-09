package com.personallibrary.personal_library_backend.service;

import com.personallibrary.personal_library_backend.dto.book.BookRequest;
import com.personallibrary.personal_library_backend.dto.book.BookResponse;

import java.util.List;

public interface BookService {
    List<BookResponse> getBooks();

    BookResponse getBook(Long id);

    BookResponse createBook(BookRequest request);

    BookResponse updateBook(Long id, BookRequest request);

    void deleteBook(Long id);
}
