package com.LeHuuSang.Test2_BookReview.Service;

import com.LeHuuSang.Test2_BookReview.Dto.Request.RegisterRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.UserResponse;
import com.LeHuuSang.Test2_BookReview.Entity.User;
import com.LeHuuSang.Test2_BookReview.Enums.Role;
import com.LeHuuSang.Test2_BookReview.Exception.AppException;
import com.LeHuuSang.Test2_BookReview.Exception.ErrorCode;
import com.LeHuuSang.Test2_BookReview.Mapstruct.UserMapper;
import com.LeHuuSang.Test2_BookReview.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public UserResponse registerUser(RegisterRequest request)
    {
        if (userRepository.existsByEmail(request.getEmail()))
        {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user= User.builder()
                .email(request.getEmail())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))//o day phai ma hoa mk
                .build();

        HashSet<String>roles= new HashSet<>();
        roles.add(Role.USER.name());
        user.setRole(roles);

       var save= userRepository.save(user);

       return userMapper.toUserResponse(save);
    }
}
