package com.springboot.university.domain.enrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final StringRedisTemplate redisTemplate;

    // 대기열 Key 생성 (강의마다 별도의 대기열)
    private String getQueueKey(Long lectureId) {
        return "enrollment:queue:" + lectureId;
    }

    // 1. 대기열 등록 (Enqueue)
    // Key: 강의ID, Value: 학생ID, Score: 현재시간(먼저 온 사람이 낮은 점수)
    public void addQueue(Long lectureId, Long studentId) {
        String key = getQueueKey(lectureId);
        long score = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(key, studentId.toString(), score);
        System.out.println("대기열 등록: 학생 " + studentId + " -> 강의 " + lectureId);
    }

    // 2. 내 순번 확인 (Polling)
    public Long getOrder(Long lectureId, Long studentId) {
        String key = getQueueKey(lectureId);
        // 내 랭킹(0부터 시작하므로 +1)을 반환. 없으면 null
        Long rank = redisTemplate.opsForZSet().rank(key, studentId.toString());
        return (rank != null) ? rank + 1 : -1; // -1이면 대기열에 없음(이미 처리됨 or 미등록)
    }

    // 3. 차례가 된 유저들 꺼내기 (Pop)
    // count만큼 앞에서부터 꺼내서 반환하고 대기열에서 삭제
    public Set<String> popMin(Long lectureId, long count) {
        String key = getQueueKey(lectureId);
        // 상위 count명 조회
        Set<String> targets = redisTemplate.opsForZSet().range(key, 0, count - 1);

        if (targets != null && !targets.isEmpty()) {
            // 대기열에서 삭제 (이 처리가 있어야 중복 처리 안 됨)
            redisTemplate.opsForZSet().remove(key, targets.toArray());
        }
        return targets;
    }

    // 4. 처리 결과 기록 (성공/실패 여부 저장)
    public void saveResult(Long lectureId, Long studentId, String status) {
        String key = "enrollment:result:" + lectureId;
        String hashKey = studentId.toString();
        redisTemplate.opsForHash().put(key, studentId.toString(), status);
        // 결과 데이터는 10분 뒤 자동 만료되도록 설정하는 것이 좋습니다 (메모리 관리)
        redisTemplate.expire(key, Duration.ofMinutes(10));
        System.out.println("💾 [Redis 저장] Key: " + key + " | HashKey: " + hashKey + " | Value: " + status);
    }

    // 5. 처리 결과 조회 (프론트엔드용)
    public String getResult(Long lectureId, Long studentId) {
        String key = "enrollment:result:" + lectureId;
        String hashKey = studentId.toString();
        Object result = redisTemplate.opsForHash().get(key, studentId.toString());
        System.out.println("🔍 [Redis 조회] Key: " + key + " | HashKey: " + hashKey + " | 찾은결과: " + result);
        return (result != null) ? result.toString() : "PROCESSING"; // 없으면 아직 처리 중
    }
}
