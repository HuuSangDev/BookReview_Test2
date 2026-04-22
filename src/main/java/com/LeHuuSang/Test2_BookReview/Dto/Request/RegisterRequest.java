package com.LeHuuSang.Test2_BookReview.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotNull
    String userName;
    @Email
    @NotNull
    String email;
    @NotNull
    @Size(min = 8, message = "password least 8")
    String password;

}
