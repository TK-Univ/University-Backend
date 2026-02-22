package com.springboot.university.domain.enrollment;

import com.springboot.university.domain.enrollment.dto.EnrollmentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class EnrollmentWorker {
    private final QueueService queueService;
    private final EnrollmentService enrollmentService; // 기존의 비관적 락 로직이 있는 서비스
    private final StringRedisTemplate redisTemplate;

    // 1초마다 실행 (서버 사정에 따라 조절 가능)
    @Scheduled(fixedDelay = 1000)
    public void processQueue() {
        Set<String> queueKeys = redisTemplate.keys("enrollment:queue:*");

        if (queueKeys == null || queueKeys.isEmpty()) {
            return; // 대기열이 아무것도 없으면 패스
        }

        // 2. 존재하는 모든 대기열을 순회하며 처리
        for (String key : queueKeys) {
            // 키 이름에서 lectureId를 추출합니다. ("enrollment:queue:5" -> 5)
            String[] parts = key.split(":");
            Long lectureId = Long.valueOf(parts[2]);

            long processSize = 40; // 한 번에 처리할 인원 수

            // 3. 해당 강의의 대기열에서 꺼내기
            Set<String> studentIds = queueService.popMin(lectureId, processSize);

            if (studentIds == null || studentIds.isEmpty()) continue;

            // 4. 수강신청 로직 실행
            for (String studentIdStr : studentIds) {
                Long studentId = null;
                try {
                    studentId = Long.valueOf(studentIdStr);
                    EnrollmentRequestDTO dto = new EnrollmentRequestDTO(studentId, lectureId);

                    enrollmentService.enroll(dto);

                    // 성공 기록
                    queueService.saveResult(lectureId, studentId, "SUCCESS");
                    System.out.println("✅ 수강신청 성공: 학생 " + studentId + " (강의 " + lectureId + ")");

                } catch (Exception e) {
                    // 실패 기록 (예외 발생 시에도 반드시 기록해야 프론트가 응답을 받음)
                    if (studentId != null) {
                        queueService.saveResult(lectureId, studentId, "FAIL:" + e.getMessage());
                        System.out.println("❌ 수강신청 실패: 학생 " + studentId + " - " + e.getMessage());
                    }
                }
            }
        }
    }
}
