package com.LeHuuSang.Test2_BookReview.Mapstruct;

import com.LeHuuSang.Test2_BookReview.Dto.Request.AuthorRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.AuthorResponse;
import com.LeHuuSang.Test2_BookReview.Entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toAuthor(AuthorRequest request);

    AuthorResponse toAuthorResponse(Author author);

    void updateAuthor(AuthorRequest request, @MappingTarget Author author);

}
