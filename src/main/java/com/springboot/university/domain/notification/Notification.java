package com.springboot.university.domain.notification;

import com.springboot.university.common.BaseEntity;
import com.springboot.university.domain.staff.Staff;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToOne
    @JoinColumn(name="userId")
    private Staff writer;
}
