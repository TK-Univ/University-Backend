package com.springboot.university.domain.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s.name FROM Student s")
    List<String> findAllNames();

    @Query("select max(s.id) from Student s")
    Optional<Long> findMaxId();

}
