package com.springboot.university.domain.enrollment;

import com.springboot.university.common.exception.CustomException;
import com.springboot.university.common.response.ResponseCode;
import com.springboot.university.domain.enrollment.dto.EnrollmentInfoDTO;
import com.springboot.university.domain.enrollment.dto.EnrollmentRequestDTO;
import com.springboot.university.domain.lecture.Lecture;
import com.springboot.university.domain.lecture.LectureRepository;
import com.springboot.university.domain.student.Student;
import com.springboot.university.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    public Long enroll(EnrollmentRequestDTO dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new CustomException(ResponseCode.NONE_STUDENT));

        Lecture lecture = lectureRepository.findById(dto.lectureId())
                .orElseThrow(() -> new CustomException(ResponseCode.NONE_LECTURE));

        if (enrollmentRepository.existsByStudentAndLecture(student, lecture)) {
            throw new CustomException(ResponseCode.ALREADY_ENROLLED);
        }

        int affectedRows = lectureRepository.increaseEnrollment(dto.lectureId());

        if (affectedRows == 0) {
            Integer enrollCnt = lectureRepository.findEnrollCntById(dto.lectureId())
                    .orElseThrow(() -> new CustomException(ResponseCode.NONE_LECTURE));
            Integer maxEnrollCnt = lectureRepository.findMaxEnrollCntById(dto.lectureId())
                    .orElseThrow(() -> new CustomException(ResponseCode.NONE_LECTURE));
            if(enrollCnt == maxEnrollCnt) throw new CustomException(ResponseCode.ENROLL_CNT_MAX);
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .lecture(lecture)
                .status(EnrollmentStatus.COMPLETED)
                .grade(null)
                .build();

        enrollmentRepository.save(enrollment);

        return enrollment.getId();
    }

    @Transactional(readOnly = true)
    public List<EnrollmentInfoDTO> getMyEnrollments(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ResponseCode.NONE_STUDENT));

        return student.getEnrollments().stream()
                .map(e -> {
                    Lecture l = e.getLecture();

                    // 강의에 연결된 모든 교수님의 이름을 리스트로 수집
                    List<String> professorNames = l.getLectureProfessors().stream()
                            .map(lp -> lp.getProfessor().getName())
                            .collect(Collectors.toList());

                    return new EnrollmentInfoDTO(
                            e.getId(),
                            l.getId(),
                            l.getLectName(),
                            professorNames,
                            l.getClassroom(),
                            l.getStartTime(),
                            l.getEndTime(),
                            e.getGrade()
                    );
                })
                .collect(Collectors.toList());
    }
}