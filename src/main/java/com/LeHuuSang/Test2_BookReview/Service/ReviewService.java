package com.LeHuuSang.Test2_BookReview.Service;


import com.LeHuuSang.Test2_BookReview.Dto.Request.ReviewRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.BookResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ReviewResponse;
import com.LeHuuSang.Test2_BookReview.Entity.Book;
import com.LeHuuSang.Test2_BookReview.Entity.Review;
import com.LeHuuSang.Test2_BookReview.Exception.AppException;
import com.LeHuuSang.Test2_BookReview.Exception.ErrorCode;
import com.LeHuuSang.Test2_BookReview.Repository.BookRepository;
import com.LeHuuSang.Test2_BookReview.Repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewService {
    ReviewRepository reviewRepository;
    BookRepository bookRepository;



    private ReviewResponse mapperBuild(Review review)
    {
        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .title(review.getBook().getTitle())
                .author(review.getBook().getAuthor().getName())
                .build();
    }


    public List<ReviewResponse> ListReview(int page, int size) {
        Page<Review> reviewPage = reviewRepository.findAll(PageRequest.of(page - 1, size));
        return reviewPage.getContent().stream()
                .map(this::mapperBuild)
                .toList();
    }

    public ReviewResponse createReview(ReviewRequest request)
    {

        Book book= bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        Review review= Review.builder()
                .content(request.getContent())
                .book(book)
                .build();

        return mapperBuild(reviewRepository.save(review));

    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        review.setContent(request.getContent());
        review.setBook(book); // Update lại sách nếu Admin muốn đổi

        return mapperBuild(reviewRepository.save(review));
    }


    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        reviewRepository.delete(review);
    }


}
