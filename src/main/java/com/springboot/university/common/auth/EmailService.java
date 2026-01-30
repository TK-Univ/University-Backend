package com.springboot.university.common.auth;

import com.springboot.university.domain.staff.Staff;
import com.springboot.university.domain.staff.StaffRepository;
import com.springboot.university.domain.student.Student;
import com.springboot.university.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public boolean sendVerificationCode(String email) {
        Optional<Staff> staffOptional = staffRepository.findByEmail(email);
        Optional<Student> studentOptional = studentRepository.findByEmail(email);

        if(!staffOptional.isPresent() && !studentOptional.isPresent()) {
            return false;
        }
        String code = createRandomCode();

        redisTemplate.opsForValue().set(email, code, Duration.ofMinutes(3));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(email);
        message.setSubject("[서비스명] 인증 코드가 도착했습니다.");
        message.setText("인증 코드: " + code + "\n\n3분 이내에 입력해주세요.");

        mailSender.send(message);

        return true;
    }

    public boolean verifyCode(String email, String inputCode) {
        String savedCode = redisTemplate.opsForValue().get(email);

        if (savedCode == null || !savedCode.equals(inputCode)) {
            return false;
        }

        redisTemplate.delete(email);
        return true;
    }

    private String createRandomCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            key.append(random.nextInt(10));
        }
        return key.toString();
    }
}
