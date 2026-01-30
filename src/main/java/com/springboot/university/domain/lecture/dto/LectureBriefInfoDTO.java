package com.springboot.university.domain.lecture.dto;


import com.springboot.university.domain.lecture_professor.LectureProfessor;

import java.util.List;

public record LectureBriefInfoDTO(
        Long id,
        String lectName,
        List<ProfessorInfoDTO> professors
) {
    public record ProfessorInfoDTO(
            Long id,
            String name
    ) {}
}
