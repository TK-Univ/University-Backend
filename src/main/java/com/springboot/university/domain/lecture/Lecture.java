package com.springboot.university.domain.lecture;

import com.springboot.university.domain.professor.Professor;
import com.springboot.university.domain.subject.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String lectName;

    @Column(nullable = false)
    private Integer lectureYear;

    @Column(nullable = false)
    private Integer lectureTerm;

    @Column(nullable = false, length = 20)
    private String classroom;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(columnDefinition = "int default 0")
    private Integer enrollCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<LectureProfessor> lectureProfessors = new ArrayList<>();
}
