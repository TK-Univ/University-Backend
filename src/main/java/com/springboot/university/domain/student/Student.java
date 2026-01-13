package com.springboot.university.domain.student;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, length = 10)
    private String sex;

    @Column(nullable = false)
    private Integer grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus status;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 40)
    private String contact;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentDepartment> departments = new ArrayList<>();
}