package com.springboot.university.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ResponseCode {

    // global
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    FAILURE(false, 1001, "요청에 실패하였습니다."),

    // 이메일 인증 서비스
    NONE_EMAIL(false, 2001, "등록된 사용자 이메일이 아닙니다."),

    // 학생
    NONE_STUDENT(false, 3001, "존재하지 않는 학생입니다."),

    // 강의
    NONE_LECTURE(false, 4001, "존재하지 않는 강의입니다."),

    // 수강신청
    ALREADY_ENROLLED(false, 5001, "이미 수강 신청한 강의입니다."),
    ENROLL_CNT_MAX(false, 5002, "정원이 다 찬 강의입니다.");

    private boolean isSuccess;
    private int code;
    private String message;

    @JsonValue
    public Map<String, Object> jsonValue() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("isSuccess", isSuccess);
        map.put("code", code);
        map.put("message", message);
        return map;
    }
}
