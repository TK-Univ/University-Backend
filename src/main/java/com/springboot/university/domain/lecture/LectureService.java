package com.springboot.university.domain.lecture;

import com.springboot.university.domain.lecture.dto.LectureBriefInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
