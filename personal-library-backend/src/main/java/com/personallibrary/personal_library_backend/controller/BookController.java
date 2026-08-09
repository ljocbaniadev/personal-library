package com.personallibrary.personal_library_backend.controller;

import com.personallibrary.personal_library_backend.dto.ApiResponse;
import com.personallibrary.personal_library_backend.dto.book.BookRequest;
import com.personallibrary.personal_library_backend.dto.book.BookResponse;
import com.personallibrary.personal_library_backend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ApiResponse<List<BookResponse>> getBooks() {

        return ApiResponse.<List<BookResponse>>builder()
                .success(true)
                .message("Books retrieved successfully.")
                .data(bookService.getBooks())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBook(@PathVariable Long id) {

        return ApiResponse.<BookResponse>builder()
                .success(true)
                .message("Book retrieved successfully.")
                .data(bookService.getBook(id))
                .build();
    }

    @PostMapping
    public ApiResponse<BookResponse> createBook(
            @RequestBody BookRequest request) {

        return ApiResponse.<BookResponse>builder()
                .success(true)
                .message("Book created successfully.")
                .data(bookService.createBook(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequest request) {

        return ApiResponse.<BookResponse>builder()
                .success(true)
                .message("Book updated successfully.")
                .data(bookService.updateBook(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteBook(
            @PathVariable Long id) {

        bookService.deleteBook(id);

        return ApiResponse.builder()
                .success(true)
                .message("Book deleted successfully.")
                .data(null)
                .build();
    }
}
