package com.springboot.university.common.auth;

import com.springboot.university.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springboot.university.common.response.ResponseCode.SUCCESS;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO loginResponseDTO = authService.login(dto);

        return new BaseResponse<LoginResponseDTO>(SUCCESS, loginResponseDTO);
    }
}
