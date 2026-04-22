package com.LeHuuSang.Test2_BookReview.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    List<T> data;
    int currentPage;
    int totalPages;
    long totalElements;
    int pageSize;
}