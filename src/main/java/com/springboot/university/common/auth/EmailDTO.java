package com.springboot.university.common.auth;

import lombok.Data;

public class EmailDTO {
    @Data
    public static class SendRequest {
        private String email;
    }

    @Data
    public static class VerifyRequest {
        private String email;
        private String code;
    }
}
