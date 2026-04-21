package com.LeHuuSang.Test2_BookReview.Controller;

import com.LeHuuSang.Test2_BookReview.Dto.Request.RegisterRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ApiResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.UserResponse;
import com.LeHuuSang.Test2_BookReview.Service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.control.MappingControl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse>createUser(@RequestBody @Valid RegisterRequest request)
    {
        return ApiResponse.<UserResponse>builder()
                .message("create new user success")
                .result(userService.registerUser(request))
                .build();
    }

}
