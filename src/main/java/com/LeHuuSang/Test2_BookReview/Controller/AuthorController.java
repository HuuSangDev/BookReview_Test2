package com.LeHuuSang.Test2_BookReview.Controller;

import com.LeHuuSang.Test2_BookReview.Dto.Request.AuthorRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ApiResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.AuthorResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.PageResponse;
import com.LeHuuSang.Test2_BookReview.Service.AuthorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorController {

    AuthorService authorService;

    @GetMapping("get-authors")
    public ApiResponse<PageResponse<AuthorResponse>> getAuthors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ApiResponse.<PageResponse<AuthorResponse>>builder()
                .message("Fetch authors success")
                .result(authorService.ListAuthor(page, size))
                .build();
    }


    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest request) {

        return ApiResponse.<AuthorResponse>builder()
                .message("Create new author success")
                .result(authorService.createAuthor(request))
                .build();
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @RequestBody @Valid AuthorRequest request) {

        return ApiResponse.<AuthorResponse>builder()
                .message("Update author success")
                .result(authorService.updateAuthor(id, request))
                .build();
    }

    // 4. Xóa Tác giả (Chỉ ADMIN)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);
        return ApiResponse.<String>builder()
                .message("Delete author success")
                .build();
    }

}
