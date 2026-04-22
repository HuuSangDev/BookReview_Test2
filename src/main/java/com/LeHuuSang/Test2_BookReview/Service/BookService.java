package com.LeHuuSang.Test2_BookReview.Service;

import com.LeHuuSang.Test2_BookReview.Dto.Request.BookRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.BookResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.PageResponse;
import com.LeHuuSang.Test2_BookReview.Entity.Author;
import com.LeHuuSang.Test2_BookReview.Entity.Book;
import com.LeHuuSang.Test2_BookReview.Exception.AppException;
import com.LeHuuSang.Test2_BookReview.Exception.ErrorCode;
import com.LeHuuSang.Test2_BookReview.Mapstruct.BookMapper;
import com.LeHuuSang.Test2_BookReview.Repository.AuthorRepository;
import com.LeHuuSang.Test2_BookReview.Repository.BookRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    BookMapper bookMapper;


    public PageResponse<BookResponse> ListBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookResponse> data = bookPage.getContent().stream()
                .map(book -> BookResponse.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .authorName(book.getAuthor().getName())
                        .build())
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .currentPage(page)
                .totalPages(bookPage.getTotalPages())
                .totalElements(bookPage.getTotalElements())
                .pageSize(size)
                .build();
    }
    // ADMIN tạo Book
    public BookResponse createBook(BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));


        Book book = Book.builder()
                .title(request.getTitle())
                .author(author)
                .build();
        book=bookRepository.save(book);
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthor().getName())
                .build();
    }

    // ADMIN cập nhật Book
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));

        bookMapper.updateBook(request, book);
        book.setAuthor(author);
        //da xoa di phan image cho book vi ko co trong yeu cau !
        return bookMapper.toBookResponse(bookRepository.save(book));
    }


    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }



}
