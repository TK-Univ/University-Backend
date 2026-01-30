package com.springboot.university.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ResponseCode {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    FAILURE(false, 1001, "요청에 실패하였습니다."),
    ROLE_REQUIRE(false, 1001, "역할이 지정되지 않았습니다. 운영진에게 문의주세요."),
    AWS_S3_UPLOAD_ISSUE(false, 1002, "파일 업로드 중 문제가 발생했습니다."),
    EXPIRED_ACCESS_TOKEN(false, 1003, "이미 만료된 Access 토큰입니다."),
    UNSUPPORTED_TOKEN_TYPE(false, 1004, "지원되지 않는 토큰 형식입니다."),
    MALFORMED_TOKEN_TYPE(false, 1005, "인증 토큰이 올바르게 구성되지 않았습니다."),
    INVALID_SIGNATURE_JWT(false, 1006, "인증 시그니처가 올바르지 않습니다"),
    INVALID_TOKEN_TYPE(false, 1007, "잘못된 토큰입니다."),
    BAD_REQUEST(false, 1008, "잘못된 요청입니다."),
    INVALID_INPUT_ENUM(false, 1009, "입력값이 올바르지 않습니다. 허용된 값 중 하나를 사용해야 합니다."),

    // 이메일 인증 서비스
    INVALID_EMAIL(false, 2000, "등록된 사용자 이메일이 아닙니다.");

    private boolean isSuccess;
    private int code;
    private String message;
}
