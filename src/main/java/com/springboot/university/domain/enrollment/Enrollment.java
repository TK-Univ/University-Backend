package com.springboot.university.domain.enrollment;

import com.springboot.university.common.BaseEntity;
import com.springboot.university.domain.lecture.Lecture;
import com.springboot.university.domain.student.Student;
import jakarta.persistence.*;
        import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "enrollment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_student_lecture",
                        columnNames = {"student_id", "lecture_id"}
                )
        }
) // 학생이 같은 강의를 중복 신청하지 못하도록 유니크 제약조건 추가
public class Enrollment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column
    private String grade;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}