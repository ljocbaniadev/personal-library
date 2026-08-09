package com.personallibrary.personal_library_backend.dto.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String country;
    private String genre;
    private String protagonist;
    private String timePeriod;
    private String status;
    private String rating;
    private String bookType;
    private String tags;
    private Double chapter;
    private String comment;
}
