package com.LeHuuSang.Test2_BookReview.Service;

import com.LeHuuSang.Test2_BookReview.Dto.Request.BookRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.BookResponse;
import com.LeHuuSang.Test2_BookReview.Entity.Author;
import com.LeHuuSang.Test2_BookReview.Entity.Book;
import com.LeHuuSang.Test2_BookReview.Exception.AppException;
import com.LeHuuSang.Test2_BookReview.Exception.ErrorCode;
import com.LeHuuSang.Test2_BookReview.Mapstruct.BookMapper;
import com.LeHuuSang.Test2_BookReview.Repository.AuthorRepository;
import com.LeHuuSang.Test2_BookReview.Repository.BookRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    BookMapper bookMapper;
    Cloudinary cloudinary;

    @NonFinal
    @Value("${upload.path}") // Cấu hình trong `application.properties`
    protected String uploadPath;

    public List<BookResponse> ListBooks(int page, int size) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page - 1, size));
        return bookPage.getContent().stream()
                .map(book -> BookResponse.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .authorName(book.getAuthor().getName())
                        .build()
                )
                .toList();
    }

    // ADMIN tạo Book
    public BookResponse createBook(BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));


        var imgUrl=uploadToCouldinary(request.getImageUrl());
        Book book = Book.builder()
                .title(request.getTitle())
                .author(author)
                .imageUrl(imgUrl)
                .build();
        book=bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }

    // ADMIN cập nhật Book
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        bookMapper.updateBook(request, book);
        book.setAuthor(author);

        return bookMapper.toBookResponse(bookRepository.save(book));
    }


    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }


    private String uploadToCouldinary(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);


        }

    }
}
