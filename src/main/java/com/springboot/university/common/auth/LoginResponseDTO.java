package com.springboot.university.common.auth;

import com.springboot.university.domain.staff.StaffAuthority;

public record LoginResponseDTO (
        String id,
        String name,
        String auth,
        String token
) {

}
