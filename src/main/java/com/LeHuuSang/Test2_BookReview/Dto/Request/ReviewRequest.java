package com.LeHuuSang.Test2_BookReview.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    @NotNull(message = "* Please select book")
    Long bookId;
    //loi do @data khi sinh ra get,set BookId -> no se thanh bookId

    @NotNull(message = "* Please enter review")
    String content;

}
