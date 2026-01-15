package com.springboot.university.domain.department;

import com.springboot.university.common.BaseEntity;
import com.springboot.university.domain.student.StudentDepartment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String deptName;

    @Column(nullable = false, length = 20)
    private String deptLeader;

    // 양방향 매핑 (필요할 경우 사용)
    @OneToMany(mappedBy = "department")
    private List<StudentDepartment> students = new ArrayList<>();
}
