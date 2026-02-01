package com.springboot.university.domain.enrollment;

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));

        Lecture lecture = lectureRepository.findById(dto.lectureId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        if (enrollmentRepository.existsByStudentAndLecture(student, lecture)) {
            throw new IllegalArgumentException("이미 수강 신청한 강의입니다.");
        }

        int affectedRows = lectureRepository.increaseEnrollment(dto.lectureId());

        if (affectedRows == 0) {
            // 0이 반환되었다면? -> 정원이 꽉 찼거나 강의 ID가 잘못됨
            // 정확한 원인 파악을 위해 조회 한 번 더 하거나, 바로 예외 던짐
            throw new IllegalStateException("수강 정원이 초과되었거나 존재하지 않는 강의입니다.");
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));

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