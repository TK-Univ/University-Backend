package com.springboot.university.domain.enrollment;

import com.springboot.university.domain.lecture.Lecture;
import com.springboot.university.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
     List<Enrollment> findByStudentId(Long studentId);

     boolean existsByStudentAndLecture(Student student, Lecture lecture);
}