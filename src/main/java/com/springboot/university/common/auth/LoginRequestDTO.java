package com.springboot.university.common.auth;

public record LoginRequestDTO(
        String userId,
        String password
){

}
