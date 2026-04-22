package com.LeHuuSang.Test2_BookReview.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorResponse {
    Long id;
    String name;
    int booksCount;
}
