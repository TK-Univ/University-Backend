package com.springboot.university.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String writerName;
//    private LocalDateTime createdAt;
}
