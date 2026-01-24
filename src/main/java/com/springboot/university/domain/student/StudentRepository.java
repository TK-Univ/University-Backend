package com.springboot.university.domain.student;

import com.springboot.university.domain.student.dto.StudentBriefInfoDTO;
import com.springboot.university.domain.student.dto.StudentInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
        SELECT new com.springboot.university.domain.student.dto.StudentBriefInfoDTO(
            s.id, 
            s.name, 
            d.deptName
        ) 
        FROM Student s 
        JOIN s.departments sd 
        JOIN sd.department d
    """)
    List<StudentBriefInfoDTO> getStudentsList();

    @Query("""
        SELECT new com.springboot.university.domain.student.dto.StudentInfoDTO(
            s.id, 
            s.name, 
            s.birth, 
            s.sex, 
            s.grade, 
            s.status, 
            s.email, 
            d.deptName
        ) 
        FROM Student s
        JOIN s.departments sd
        JOIN sd.department d
        WHERE s.id = :studentId AND sd.majorType = 'MAJOR'
    """)
    Optional<StudentInfoDTO> findStudentInfoById(@Param("studentId") Long studentId);

    Optional<Student> findById(Long studentId);

    Optional<Student> findByEmail(String email);

    @Query("select max(s.id) from Student s")
    Optional<Long> findMaxId();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student s SET s.password = :password WHERE s.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);

}
