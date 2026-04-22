package com.LeHuuSang.Test2_BookReview.Exception;

import com.LeHuuSang.Test2_BookReview.Dto.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {



    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse>handlingException (AppException appException)
    {
        ErrorCode errorCode=appException.getErrorCode();
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());

        return ResponseEntity.badRequest().body(apiResponse);

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidationException(MethodArgumentNotValidException exception)
    {

        String errorMessage = exception.getFieldError().getDefaultMessage();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(400);
        apiResponse.setMessage(errorMessage);

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
