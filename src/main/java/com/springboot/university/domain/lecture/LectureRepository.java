package com.springboot.university.domain.lecture;

import com.springboot.university.domain.lecture.dto.LectureBriefInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT DISTINCT l FROM Lecture l JOIN FETCH l.lectureProfessors")
    List<Lecture> findAllWithProfessors();
}
