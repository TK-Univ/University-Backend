package com.springboot.university.domain.lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT DISTINCT l FROM Lecture l JOIN FETCH l.lectureProfessors")
    List<Lecture> findAllWithProfessors();

    @Query("""
        SELECT l 
        FROM Lecture l 
        JOIN FETCH l.subject s 
        LEFT JOIN FETCH l.lectureProfessors lp 
        LEFT JOIN FETCH lp.professor p 
        WHERE l.id = :lectureId
    """)
    Optional<Lecture> findDetailById(@Param("lectureId") Long lectureId);
}
