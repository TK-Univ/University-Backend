package com.springboot.university.common.auth;

import com.springboot.university.common.jwt.JwtUtil;
import com.springboot.university.domain.staff.Staff;
import com.springboot.university.domain.staff.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        System.out.println(dto.toString());
        Staff staff = staffRepository.findByUserId(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디이거나, 잘못된 비밀번호입니다."));

        if (!passwordEncoder.matches(dto.password(), staff.getPassword())) {
            throw new IllegalArgumentException("가입되지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }

        // 인증 성공 시, 토큰(Access Token) 생성 및 반환
        // 토큰에는 보통 "PK(id)"나 "아이디(userId)", "권한(Role)" 정보를 담습니다.
        String token = jwtUtil.createToken(staff.getUserId(), staff.getAuthority());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(staff.getStaffName(), staff.getAuthority(), token);

        return loginResponseDTO;
    }
}
