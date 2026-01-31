package com.springboot.university.domain.lecture.dto;

import java.time.LocalTime;
import java.util.List;

public record LectureDetailInfoDTO(
    Long id,
    String lectName,
    List<LectureDetailInfoDTO.ProfessorInfoDTO> professors,
    Integer lectureYear,
    Integer lectureTerm,
    String classroom,
    LocalTime startTime,
    LocalTime endTime,
    Integer enrollCnt,
    Integer maxEnrollCnt,
    String subjectId
) {
    public record ProfessorInfoDTO(
            Long id,
            String name
    ) {}
}
