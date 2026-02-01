package com.springboot.university.domain.enrollment.dto;

import com.springboot.university.domain.enrollment.EnrollmentStatus;

import java.time.LocalTime;
import java.util.List;

public record EnrollmentInfoDTO(
        Long enrollmentId,
        Long lectureId,
        String lectName,
        List<String> professorNames,
        String classroom,
        LocalTime startTime,
        LocalTime endTime,
        String grade
) {
}
