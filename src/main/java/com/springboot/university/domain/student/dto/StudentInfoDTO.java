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

        String password,

        String contact,

        Long deptId

) {
    public Student toEntity(Department department) {
        return Student.builder()
                .id(this.id) // record는 필드명 자체가 getter 메서드입니다 (this.id()도 가능하지만 내부에서는 필드 직접 접근 가능)
                .name(this.name)
                .birth(this.birth)
                .sex(this.sex)
                .grade(this.grade)
                .status(this.status)
                .password(this.password)
                .contact(this.contact)
                .build();
    }
}
