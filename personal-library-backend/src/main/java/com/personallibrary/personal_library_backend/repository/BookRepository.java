package com.personallibrary.personal_library_backend.repository;

import com.personallibrary.personal_library_backend.entity.Book;
import com.personallibrary.personal_library_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUserOrderByCreatedAtDescTitleAsc(User user);
    Optional<Book> findByIdAndUser(Long id, User user);
    long countByUser(User user);
    long countByUserAndStatus(User user, String status);
    long countByUserAndRating(User user, String rating);
    List<Book> findTop5ByUserOrderByCreatedAtDesc(User user);

}
