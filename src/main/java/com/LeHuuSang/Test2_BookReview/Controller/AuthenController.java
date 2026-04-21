package com.LeHuuSang.Test2_BookReview.Controller;

import com.LeHuuSang.Test2_BookReview.Dto.Request.LoginRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.ApiResponse;
import com.LeHuuSang.Test2_BookReview.Dto.Response.LoginReponse;
import com.LeHuuSang.Test2_BookReview.Service.AuthenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenController {

    AuthenService authenService;


    @PostMapping("/log-in")
    ApiResponse<LoginReponse> login(@RequestBody LoginRequest request)
    {
        var result=authenService.authenticated(request);
        return ApiResponse.<LoginReponse>builder()
                .message("log in success")
                .result(result)
                .build();
    }
}
