package com.springboot.university.domain.lecture;

import com.springboot.university.common.response.BaseResponse;
import com.springboot.university.domain.lecture.dto.LectureBriefInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springboot.university.common.response.ResponseCode.SUCCESS;

@RestController
@RequestMapping("/api/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/list")
    public BaseResponse<List<LectureBriefInfoDTO>> getStudentInfoList() {
        List<LectureBriefInfoDTO> dto = lectureService.getLectureList();
        return new BaseResponse<>(SUCCESS, dto);
    }
}
