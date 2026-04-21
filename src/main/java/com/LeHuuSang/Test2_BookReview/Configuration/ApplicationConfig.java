package com.LeHuuSang.Test2_BookReview.Configuration;

import com.LeHuuSang.Test2_BookReview.Entity.User;
import com.LeHuuSang.Test2_BookReview.Enums.Role;
import com.LeHuuSang.Test2_BookReview.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class ApplicationConfig {

    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository)
    {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty())
            {
                var roles=new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user= User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(roles)
                        .build();

                userRepository.save(user);
                log.error("admin has been create with default password: admin ");

            }


        };

    }
}
