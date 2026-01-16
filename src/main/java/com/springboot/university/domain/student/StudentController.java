package com.springboot.university.domain.student;

import com.springboot.university.common.response.BaseResponse;
import com.springboot.university.domain.student.dto.StudentBriefInfoDTO;
import com.springboot.university.domain.student.dto.StudentRegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.university.common.response.ResponseCode.SUCCESS;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    @PostMapping("/register")
    public BaseResponse<?> registerStudent(@RequestBody StudentRegisterRequestDTO dto) {
        Long studentId = studentService.register(dto);
        log.info("신규 학생 등록 완료 - ID: {}", studentId);

        return new BaseResponse<>(SUCCESS);
    }

    @GetMapping("/list")
    public BaseResponse<List<StudentBriefInfoDTO>> getStudentInfoList() {
        List<StudentBriefInfoDTO> dto = studentService.getStudentsList();
        return new BaseResponse<>(SUCCESS, dto);
    }
}
