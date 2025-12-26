package com.xenialsoft.api.custom.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class InitialPassword {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        String raw = "123qwe!@#";
        System.out.println("#### raw password: " + raw);
        System.out.println("#### encoded password: " + PASSWORD_ENCODER.encode(raw));
    }
}
