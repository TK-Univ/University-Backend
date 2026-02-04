package com.springboot.university.common.auth;

import com.springboot.university.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springboot.university.common.response.ResponseCode.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO loginResponseDTO = authService.login(dto);

        return new BaseResponse<LoginResponseDTO>(SUCCESS, loginResponseDTO);
    }

    @PostMapping("/email-send")
    public BaseResponse<?> sendEmail(@RequestBody EmailDTO.SendRequest request) {
        if(emailService.sendVerificationCode(request.getEmail())) {
            return new BaseResponse<>(SUCCESS);
        }
        return new BaseResponse<>(NONE_EMAIL);
    }

    @PostMapping("/email-verify")
    public BaseResponse<?> verifyCode(@RequestBody EmailDTO.VerifyRequest request) {
        boolean isVerified = emailService.verifyCode(request.getEmail(), request.getCode());

        if (isVerified) {
            return new BaseResponse<>(SUCCESS);
        } else {
            return new BaseResponse<>(FAILURE);
        }
    }

    @PostMapping("/set-password")
    public BaseResponse<PasswordSetResponseDTO> setPassword(@RequestBody PasswordSetRequestDTO dto) {
        String id = authService.setPassword(dto.email(), dto.password());
        PasswordSetResponseDTO responseDto = new PasswordSetResponseDTO(id);
        return new BaseResponse<PasswordSetResponseDTO>(SUCCESS, responseDto);
    }
}
