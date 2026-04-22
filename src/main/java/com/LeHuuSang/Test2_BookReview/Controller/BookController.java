package com.LeHuuSang.Test2_BookReview.Controller;

import com.LeHuuSang.Test2_BookReview.Dto.Request.BookRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Request.RegisterRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ApiResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.BookResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.PageResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.UserResponse;
import com.LeHuuSang.Test2_BookReview.Service.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {

    BookService bookService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
   ApiResponse<BookResponse>createBook(@Valid @RequestBody BookRequest request)
    {
        return ApiResponse.<BookResponse>builder()
                .message("Create book success")
                .result(bookService.createBook(request))
                .build();

    }

    @GetMapping("get-books")
    public ApiResponse<PageResponse<BookResponse>> getBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ApiResponse.<PageResponse<BookResponse>>builder()
                .message("Fetch books success")
                .result(bookService.ListBooks(page, size))
                .build();
    }




    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookRequest request) {

        return ApiResponse.<BookResponse>builder()
                .message("Update book success")
                .result(bookService.updateBook(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);
        return ApiResponse.<String>builder()
                .message("Delete book success")
                .build();
    }



}
