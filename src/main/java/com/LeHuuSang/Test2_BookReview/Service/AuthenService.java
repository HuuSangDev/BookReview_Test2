package com.LeHuuSang.Test2_BookReview.Service;

import com.LeHuuSang.Test2_BookReview.Dto.Request.LoginRequest;
import com.LeHuuSang.Test2_BookReview.Dto.Response.LoginReponse;
import com.LeHuuSang.Test2_BookReview.Entity.User;
import com.LeHuuSang.Test2_BookReview.Exception.AppException;
import com.LeHuuSang.Test2_BookReview.Exception.ErrorCode;
import com.LeHuuSang.Test2_BookReview.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenService {


    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    //vao encrytion key generator
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    //Đăng Nhập
    public LoginReponse authenticated(LoginRequest request)
    {
        //tìm email ở db
        User user=userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));


        boolean authenticated= passwordEncoder.matches(request.getPassword(),user.getPassword());
        if (!authenticated)
        {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token= genereToken(user);
        return LoginReponse.builder()
                .token(token)
                .authenticated(true)
                .role(buildScope(user))
                .build();
    }
    private String genereToken(User user)
    {
        //tao header
        JWSHeader header= new JWSHeader(JWSAlgorithm.HS512);
        //tao claim
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("sangledev.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(5    , ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        //chuyen claim vao Payload
        Payload payload =new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject= new JWSObject(header,payload);

        //ky token va chuyen chuoi thanh JWT hoan chinh

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("can't create token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user)
    {
        StringJoiner stringJoiner=new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRole()))
        {
            user.getRole().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
