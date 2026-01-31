package com.springboot.university.domain.lecture.dto;

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
