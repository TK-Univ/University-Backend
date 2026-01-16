package com.springboot.university.common.jwt;

import com.springboot.university.domain.staff.StaffAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String createToken(String userId, StaffAuthority authority) {
        return "dummy-access-token-for-" + userId;
    }
}
