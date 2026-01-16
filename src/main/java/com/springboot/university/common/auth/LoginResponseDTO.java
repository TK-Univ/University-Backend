package com.springboot.university.common.auth;

import com.springboot.university.domain.staff.StaffAuthority;

public record LoginResponseDTO (
        String name,
        StaffAuthority auth,
        String token
) {

}
