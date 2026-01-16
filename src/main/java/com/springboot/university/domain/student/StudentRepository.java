package com.springboot.university.domain.student;

import com.springboot.university.domain.student.dto.StudentBriefInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("select max(s.id) from Student s")
    Optional<Long> findMaxId();

}
