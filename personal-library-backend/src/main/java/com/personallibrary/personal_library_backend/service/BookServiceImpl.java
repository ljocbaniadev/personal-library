package com.personallibrary.personal_library_backend.service;

import com.personallibrary.personal_library_backend.dto.book.BookRequest;
import com.personallibrary.personal_library_backend.dto.book.BookResponse;
import com.personallibrary.personal_library_backend.entity.Book;
import com.personallibrary.personal_library_backend.entity.User;
import com.personallibrary.personal_library_backend.exception.BookNotFoundException;
import com.personallibrary.personal_library_backend.repository.BookRepository;
import com.personallibrary.personal_library_backend.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public List<BookResponse> getBooks() {
        User user = authenticatedUserService.getCurrentUser();
        return bookRepository.findByUserOrderByCreatedAtDescTitleAsc(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BookResponse getBook(Long id) {
        User user = authenticatedUserService.getCurrentUser();
        Book book = bookRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BookNotFoundException("Book not found."));
        return toResponse(book);
    }

    @Override
    public BookResponse createBook(BookRequest request) {
        User user = authenticatedUserService.getCurrentUser();
        Book book = Book.builder()
                .title(request.getTitle())
                .country(request.getCountry())
                .genre(request.getGenre())
                .protagonist(request.getProtagonist())
                .timePeriod(request.getTimePeriod())
                .status(request.getStatus())
                .rating(request.getRating())
                .bookType(request.getBookType())
                .tags(request.getTags())
                .chapter(request.getChapter())
                .comment(request.getComment())
                .user(user)
                .build();

        bookRepository.save(book);

        return toResponse(book);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        Book book = bookRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BookNotFoundException("Book not found."));

        book.setTitle(request.getTitle());
        book.setCountry(request.getCountry());
        book.setGenre(request.getGenre());
        book.setProtagonist(request.getProtagonist());
        book.setTimePeriod(request.getTimePeriod());
        book.setStatus(request.getStatus());
        book.setRating(request.getRating());
        book.setBookType(request.getBookType());
        book.setTags(request.getTags());
        book.setChapter(request.getChapter());
        book.setComment(request.getComment());

        bookRepository.save(book);

        return toResponse(book);
    }

    @Override
    public void deleteBook(Long id) {

        User user = authenticatedUserService.getCurrentUser();

        Book book = bookRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BookNotFoundException("Book not found."));

        bookRepository.delete(book);
    }

    private BookResponse toResponse(Book book) {

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .country(book.getCountry())
                .genre(book.getGenre())
                .protagonist(book.getProtagonist())
                .timePeriod(book.getTimePeriod())
                .status(book.getStatus())
                .rating(book.getRating())
                .bookType(book.getBookType())
                .tags(book.getTags())
                .chapter(book.getChapter())
                .comment(book.getComment())
                .build();
    }
}
