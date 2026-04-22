package com.LeHuuSang.Test2_BookReview.Entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;

    //ở đây chúng ta chỉ làm 2 role , khách vãng lai và admin thôi

    //book 1-n review
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

}
