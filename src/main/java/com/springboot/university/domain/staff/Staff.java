package com.springboot.university.domain.staff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;

    @Column(nullable = false, length = 20)
    private String staffName;

    @Column(length = 20)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffAuthority authority;

    @Column(nullable = false, length = 200)
    private String password;

    public boolean isAdmin() {
        return this.authority == StaffAuthority.ADMIN;
    }
}
