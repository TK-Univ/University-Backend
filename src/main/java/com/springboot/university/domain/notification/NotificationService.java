package com.springboot.university.domain.notification;

import com.springboot.university.domain.notification.dto.NotificationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Page<NotificationResponseDTO> getNotifications(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 페이지 번호와 크기 설정
        Page<Notification> notifications = notificationRepository.findAll(pageable); // 페이징된 결과 반환

        return notifications.map(notification -> NotificationResponseDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .writerName(notification.getWriter().getStaffName())
                .build());
    }
}
