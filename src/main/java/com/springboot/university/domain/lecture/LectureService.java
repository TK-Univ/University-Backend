package com.springboot.university.domain.lecture;

import com.springboot.university.domain.lecture.dto.LectureBriefInfoDTO;
import com.springboot.university.domain.lecture.dto.LectureDetailInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public List<LectureBriefInfoDTO> getLectureList() {
        return lectureRepository.findAllWithProfessors().stream()
                .map(l -> new LectureBriefInfoDTO(
                        l.getId(),
                        l.getLectName(),
                        l.getLectureProfessors().stream()
                                .map(lp -> new LectureBriefInfoDTO.ProfessorInfoDTO(
                                        lp.getProfessor().getId(),
                                        lp.getProfessor().getName()
                                ))
                                .toList()
                ))
                .toList();
    }

    public LectureDetailInfoDTO getLectureDetail(Long lectureId) {
        // 1. Fetch Join으로 모든 연관 데이터가 포함된 엔티티 조회
        Lecture lecture = lectureRepository.findDetailById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다. id=" + lectureId));

        // 2. 교수 정보 리스트 변환 (LectureProfessor -> ProfessorInfoDTO)
        List<LectureDetailInfoDTO.ProfessorInfoDTO> professorDTOs = lecture.getLectureProfessors().stream()
                .map(lp -> new LectureDetailInfoDTO.ProfessorInfoDTO(
                        lp.getProfessor().getId(),
                        lp.getProfessor().getName()
                ))
                .collect(Collectors.toList());

        // 3. 최종 DTO 매핑
        return new LectureDetailInfoDTO(
                lecture.getId(),
                lecture.getLectName(),
                professorDTOs, // 변환된 리스트 주입
                lecture.getLectureYear(),
                lecture.getLectureTerm(),
                lecture.getClassroom(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                lecture.getEnrollCnt(),
                lecture.getMaxEnrollCnt(),
                String.valueOf(lecture.getSubject().getId()) // Subject 엔티티에서 ID 추출
        );
    }
}
