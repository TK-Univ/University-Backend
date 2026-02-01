package com.springboot.university.domain.enrollment;

import com.springboot.university.common.response.BaseResponse;
import com.springboot.university.domain.enrollment.dto.EnrollmentInfoDTO;
import com.springboot.university.domain.enrollment.dto.EnrollmentRequestDTO;
import com.springboot.university.domain.student.dto.StudentRegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.university.common.response.ResponseCode.SUCCESS;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/register")
    public BaseResponse<Long> registerEnrollment(@RequestBody EnrollmentRequestDTO dto) {
        Long enrollmentId = enrollmentService.enroll(dto);
        return new BaseResponse<>(SUCCESS, enrollmentId);
    }

    @GetMapping("/list/{studentId}")
    public BaseResponse<List<EnrollmentInfoDTO>> getMyEnrollmentList(@PathVariable("studentId") Long studentId) {
        List<EnrollmentInfoDTO> list = enrollmentService.getMyEnrollments(studentId);
        return new BaseResponse<>(SUCCESS, list);
    }
}
