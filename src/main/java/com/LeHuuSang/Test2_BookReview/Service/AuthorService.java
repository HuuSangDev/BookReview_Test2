package com.LeHuuSang.Test2_BookReview.Service;

import com.LeHuuSang.Test2_BookReview.Dto.Request.AuthorRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.AuthorResponse;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    public List<AuthorResponse>ListAuthor(int page, int size)
    {
        Page<Author> authorPage = authorRepository.findAll(PageRequest.of(page - 1, size));

        return authorPage.getContent().stream()
                .map(author -> AuthorResponse.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .booksCount(author.getBooks() != null ? author.getBooks().size() : 0)
                        .build())
                .toList();
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
