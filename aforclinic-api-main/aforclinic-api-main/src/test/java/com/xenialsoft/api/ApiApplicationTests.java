package com.xenialsoft.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.xenialsoft.api.common.util.IdUtils;

@SpringBootTest
class ApiApplicationTests {

    @Test
    void contextLoads() {
    }

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Test
    public void testPassword() {
        // ID : MhuLyA
        String raw = "1";
        System.out.println("#### raw password: " + raw);
        System.out.println("#### encoded password: " + PASSWORD_ENCODER.encode(raw));
        System.out.println(IdUtils.generate());
    }

}
