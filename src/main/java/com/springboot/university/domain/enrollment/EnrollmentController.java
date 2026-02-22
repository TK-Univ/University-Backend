package com.springboot.university.domain.enrollment;

import com.springboot.university.common.response.BaseResponse;
import com.springboot.university.domain.enrollment.dto.EnrollmentInfoDTO;
import com.springboot.university.domain.enrollment.dto.EnrollmentRequestDTO;
import com.springboot.university.domain.student.dto.StudentRegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.university.common.response.ResponseCode.SUCCESS;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final QueueService queueService;

    @PostMapping("/register")
    public BaseResponse<?> registerEnrollment(@RequestBody EnrollmentRequestDTO dto) {
//        Long enrollmentId = enrollmentService.enroll(dto);
//        return new BaseResponse<>(SUCCESS, enrollmentId);
        queueService.addQueue(dto.lectureId(), dto.studentId());
        return new BaseResponse<>(SUCCESS);
    }

    @GetMapping("/order")
    public BaseResponse<Long> getOrder(@RequestParam Long lectureId, @RequestParam Long studentId) {
        Long order = queueService.getOrder(lectureId, studentId);
        return new BaseResponse<>(SUCCESS, order);
    }

    @GetMapping("/result")
    public BaseResponse<String> getResult(@RequestParam Long lectureId, @RequestParam Long studentId) {
        String result = queueService.getResult(lectureId, studentId);
        return new BaseResponse<>(SUCCESS, result);
    }

    @GetMapping("/list/{studentId}")
    public BaseResponse<List<EnrollmentInfoDTO>> getMyEnrollmentList(@PathVariable("studentId") Long studentId) {
        List<EnrollmentInfoDTO> list = enrollmentService.getMyEnrollments(studentId);
        return new BaseResponse<>(SUCCESS, list);
    }
}
