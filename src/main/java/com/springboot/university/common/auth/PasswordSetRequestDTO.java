package com.springboot.university.common.auth;

public record PasswordSetRequestDTO(
        String email,
        String password
) {
}
