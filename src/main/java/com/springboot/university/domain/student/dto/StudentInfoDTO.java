package com.springboot.university.domain.student.dto;

import com.springboot.university.domain.department.Department;
import com.springboot.university.domain.student.Student;
import com.springboot.university.domain.student.StudentStatus;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record StudentInfoDTO(

        Long id,

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
