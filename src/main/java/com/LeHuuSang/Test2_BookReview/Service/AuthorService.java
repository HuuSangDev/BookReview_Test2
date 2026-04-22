package com.LeHuuSang.Test2_BookReview.Service;

import com.LeHuuSang.Test2_BookReview.Dto.Request.AuthorRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.AuthorResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.PageResponse;
import com.LeHuuSang.Test2_BookReview.Entity.Author;
import com.LeHuuSang.Test2_BookReview.Exception.AppException;
import com.LeHuuSang.Test2_BookReview.Exception.ErrorCode;
import com.LeHuuSang.Test2_BookReview.Mapstruct.AuthorMapper;
import com.LeHuuSang.Test2_BookReview.Repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    public PageResponse<AuthorResponse> ListAuthor(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Author> authorPage = authorRepository.findAll(pageable);

        List<AuthorResponse> data = authorPage.getContent()
                .stream()
                .map(author -> AuthorResponse.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .booksCount(author.getBooks().size())
                        .build())
                .collect(Collectors.toList());  // ← bọc thành List rõ ràng

        return PageResponse.<AuthorResponse>builder()
                .data(data)
                .currentPage(page)
                .totalPages(authorPage.getTotalPages())
                .totalElements(authorPage.getTotalElements())
                .pageSize(size)
                .build();
    }


    public AuthorResponse createAuthor(AuthorRequest request) {
        if (authorRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.AUTHOR_EXISTED); // Cần thêm enum này
        }

        Author author = authorMapper.toAuthor(request);
        var save= authorRepository.save(author);
        return AuthorResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .build();
    }


    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        authorMapper.updateAuthor(request, author);
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }


    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new AppException(ErrorCode.AUTHOR_NOT_FOUND);
        }
        authorRepository.deleteById(id);
    }


}
