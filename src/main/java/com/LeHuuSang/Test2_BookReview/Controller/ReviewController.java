package com.LeHuuSang.Test2_BookReview.Controller;

import com.LeHuuSang.Test2_BookReview.Dto.Request.BookRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Request.ReviewRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ApiResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.BookResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ReviewResponse;
import com.LeHuuSang.Test2_BookReview.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    @PostMapping("/create")
    ApiResponse<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest request)
    {
        return ApiResponse.<ReviewResponse>builder()
                .message("Create book success")
                .result(reviewService.createReview(request))
                .build();

    }

    @GetMapping("get-reviews")
    public ApiResponse<List<ReviewResponse>> getReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResponse.<List<ReviewResponse>>builder()
                .message("Fetch review success")
                .result(reviewService.ListReview(page, size))
                .build();
    }



    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ReviewResponse> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid ReviewRequest request) {

        return ApiResponse.<ReviewResponse>builder()
                .message("Update review success")
                .result(reviewService.updateReview(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteBook(@PathVariable Long id) {

        reviewService.deleteReview(id);
        return ApiResponse.<String>builder()
                .message("Delete review success")
                .build();
    }
}
