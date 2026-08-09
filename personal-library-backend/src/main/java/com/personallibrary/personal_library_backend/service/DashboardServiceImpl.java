package com.personallibrary.personal_library_backend.service;

import com.personallibrary.personal_library_backend.dto.book.BookResponse;
import com.personallibrary.personal_library_backend.dto.dashboard.DashboardResponse;
import com.personallibrary.personal_library_backend.entity.User;
import com.personallibrary.personal_library_backend.repository.BookRepository;
import com.personallibrary.personal_library_backend.repository.UserRepository;
import com.personallibrary.personal_library_backend.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final AuthenticatedUserService authenticatedUserService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardResponse getDashboard() {

        User user = authenticatedUserService.getCurrentUser();
        return DashboardResponse.builder()
                .totalBooks(
                        bookRepository.countByUser(user)
                )
                .inProgressBooks(
                        bookRepository.countByUserAndStatus(
                                user,"In-Progress")
                )
                .completedBooks(
                        bookRepository.countByUserAndStatus(
                                user,"Completed")
                )
                .favoriteBooks(
                        bookRepository.countByUserAndRating(
                                user,"Favorite"
                        )
                )
                .recentlyAddedBooks(
                        bookRepository.findTop5ByUserOrderByCreatedAtDesc(user)
                                .stream()
                                .map(book -> BookResponse.builder()
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
                                        .build())
                                .toList()
                )
                .build();

    }
}
