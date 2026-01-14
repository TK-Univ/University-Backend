package com.springboot.university.domain.student.dto;

import com.springboot.university.domain.student.StudentStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record StudentRegisterRequestDTO (

    String name,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birth,

    String sex,

    Integer grade,

    StudentStatus status,

    String contact,

    String deptName
) {

}
