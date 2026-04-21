package com.LeHuuSang.Test2_BookReview.Exception;

public enum ErrorCode {
    USER_EXISTED(101,"user existed"),
    ID_USER_NOT_FOUND(102,"id not found"),
    EMAIL_EXISTED(103,"email existed"),
    USER_NOT_FOUND(104,"user not found"),
    UNAUTHENTICATED(105,"unauthenticated"),
    AUTHOR_EXISTED(106,"author existed"),
    AUTHOR_NOT_FOUND(107,"author not found"),
    BOOK_NOT_FOUND(108,"book not found"),


    ;

    ErrorCode(int code, String message)
    {
        this.code=code;
        this.message=message;
    }


    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
