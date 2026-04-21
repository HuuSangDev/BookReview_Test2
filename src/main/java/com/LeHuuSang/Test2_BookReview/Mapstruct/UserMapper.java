package com.LeHuuSang.Test2_BookReview.Mapstruct;

import com.LeHuuSang.Test2_BookReview.Dto.Response.UserResponse;
import com.LeHuuSang.Test2_BookReview.Entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    //map tu userCreateDTO vao user

    //map tu user vao UserDTO
    UserResponse toUserResponse(User user);


}
