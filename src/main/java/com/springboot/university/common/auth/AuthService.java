package com.springboot.university.common.auth;

import com.springboot.university.common.jwt.JwtUtil;
import com.springboot.university.domain.staff.Staff;
import com.springboot.university.domain.staff.StaffRepository;
import com.springboot.university.domain.student.Student;
import com.springboot.university.domain.student.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;
    private final String STUDENT_AUTH = "STUDENT";

    public LoginResponseDTO login(LoginRequestDTO dto) {
        Optional<Staff> staffOptional = staffRepository.findByUserId(dto.userId());

        if (staffOptional.isPresent()) {
            return loginStaff(dto, staffOptional.get());
        }

        Optional<Student> studentOptional = studentRepository.findById(Long.valueOf(dto.userId()));

        if (studentOptional.isPresent()) {
            return loginStudent(dto, studentOptional.get());
        }

        throw new IllegalArgumentException("가입되지 않은 아이디이거나, 잘못된 비밀번호입니다.");

    }

    public LoginResponseDTO loginStaff(LoginRequestDTO dto, Staff staff) {
        if (!passwordEncoder.matches(dto.password(), staff.getPassword())) {
            throw new IllegalArgumentException("가입되지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }

        System.out.println("직원 로그인 - " + dto.toString());

        String token = jwtUtil.createToken(staff.getUserId(), staff.getAuthority().toString());
        return new LoginResponseDTO(staff.getUserId(), staff.getStaffName(), staff.getAuthority().toString(), token);

    }

    public LoginResponseDTO loginStudent(LoginRequestDTO dto, Student student) {

        if (!passwordEncoder.matches(dto.password(), student.getPassword())) {
            throw new IllegalArgumentException("가입되지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }


        System.out.println("학생 로그인 - " + dto.toString());

        String token = jwtUtil.createToken(String.valueOf(student.getId()), STUDENT_AUTH);
        return new LoginResponseDTO(student.getId().toString(), student.getName(), STUDENT_AUTH, token);

    }

    public String setPassword(String email, String password) {
        Optional<String> staffId =
                staffRepository.findByEmail(email)
                        .map(staff -> {
                            staffRepository.updatePassword(staff.getEmail(), passwordEncoder.encode(password));
                            return staff.getUserId();
                        });

        if (staffId.isPresent()) {
            return staffId.get();
        }

        Optional<String> studentId =
                studentRepository.findByEmail(email)
                        .map(student -> {
                            studentRepository.updatePassword(student.getEmail(), passwordEncoder.encode(password));
                            return student.getId().toString();
                        });

        return studentId.orElse(null);
    }
}
