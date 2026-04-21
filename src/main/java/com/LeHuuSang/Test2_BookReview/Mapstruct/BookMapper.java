package com.LeHuuSang.Test2_BookReview.Mapstruct;

import com.LeHuuSang.Test2_BookReview.Dto.Request.AuthorRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Request.BookRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.BookResponse;
import com.LeHuuSang.Test2_BookReview.Entity.Author;
import com.LeHuuSang.Test2_BookReview.Entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookResponse toBookResponse(Book book);

    void updateBook(BookRequest request, @MappingTarget Book book);
}
