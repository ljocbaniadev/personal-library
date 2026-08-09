package com.personallibrary.personal_library_backend.dto.dashboard;

import com.personallibrary.personal_library_backend.dto.book.BookResponse;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private long totalBooks;

    private long inProgressBooks;

    private long completedBooks;

    private long favoriteBooks;

    private List<BookResponse> recentlyAddedBooks;
}
