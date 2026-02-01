package com.springboot.university.domain.lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT DISTINCT l FROM Lecture l JOIN FETCH l.lectureProfessors")
    List<Lecture> findAllWithProfessors();

    @Query("""
        SELECT l 
        FROM Lecture l 
        JOIN FETCH l.subject s 
        LEFT JOIN FETCH l.lectureProfessors lp 
        LEFT JOIN FETCH lp.professor p 
        WHERE l.id = :lectureId
    """)
    Optional<Lecture> findDetailById(@Param("lectureId") Long lectureId);

    @Query("SELECT l.enrollCnt FROM Lecture l WHERE l.id = :id")
    Optional<Integer> findEnrollCntById(@Param("id") Long id);

    /**
     * 2. 수강 인원 1 증가 (수강 신청)
     * 조건: 현재 인원이 최대 인원보다 작을 때만 증가 (Concurrency safe)
     * 반환값: 업데이트된 행의 개수 (1이면 성공, 0이면 정원 초과거나 없는 강의)
     */
    @Modifying(clearAutomatically = true) // 쿼리 실행 후 영속성 컨텍스트 초기화
    @Query("UPDATE Lecture l SET l.enrollCnt = l.enrollCnt + 1 WHERE l.id = :id AND l.enrollCnt < l.maxEnrollCnt")
    int increaseEnrollment(@Param("id") Long id);

    /**
     * 3. 수강 인원 1 감소 (수강 취소)
     * 조건: 현재 인원이 0보다 클 때만 감소
     * 반환값: 업데이트된 행의 개수 (1이면 성공, 0이면 인원이 0이거나 없는 강의)
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Lecture l SET l.enrollCnt = l.enrollCnt - 1 WHERE l.id = :id AND l.enrollCnt > 0")
    int decreaseEnrollment(@Param("id") Long id);
}
