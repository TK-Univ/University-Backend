package com.springboot.university.domain.notification;

import com.springboot.university.common.response.BaseResponse;
import com.springboot.university.domain.enrollment.dto.EnrollmentRequestDTO;
import com.springboot.university.domain.notification.dto.NotificationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import static com.springboot.university.common.response.ResponseCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public BaseResponse<?> postNotification(@RequestBody EnrollmentRequestDTO dto) {
        return new BaseResponse<>(SUCCESS);
    }

    @GetMapping
    public BaseResponse<Page<NotificationResponseDTO>> getNotifications(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {

        return new BaseResponse<>(SUCCESS, notificationService.getNotifications(page, size));
    }

}
