package com.springboot.university.common.init;

import com.springboot.university.domain.StudentDepartment.StudentDepartmentRepository;
import com.springboot.university.domain.department.Department;
import com.springboot.university.domain.department.DepartmentRepository;
import com.springboot.university.domain.department.MajorType;
import com.springboot.university.domain.staff.Staff;
import com.springboot.university.domain.staff.StaffAuthority;
import com.springboot.university.domain.staff.StaffRepository;
import com.springboot.university.domain.student.Student;
import com.springboot.university.domain.StudentDepartment.StudentDepartment;
import com.springboot.university.domain.student.StudentRepository;
import com.springboot.university.domain.student.StudentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Profile("local")
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentDepartmentRepository studentDepartmentRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. 학과 데이터 생성
        Department dept1 = departmentRepository.save(Department.builder()
                .deptName("컴퓨터공학과")
                .deptLeader("김교수")
                .build());

        Department dept2 = departmentRepository.save(Department.builder()
                .deptName("경영학과")
                .deptLeader("이교수")
                .build());

        // 2. 학생 데이터 생성
        Student student1 = Student.builder()
                .id(20240001L)
                .name("홍길동")
                .birth(LocalDate.of(2000, 1, 1))
                .sex("M")
                .grade(1)
                .status(StudentStatus.ENROLLED) // Enum 확인
                .email("aaa@naver.com")
                .password(passwordEncoder.encode("1234")) // 비밀번호 암호화 필수!
                .build();

        Student student2 = Student.builder()
                .id(20250001L)
                .name("성춘향")
                .birth(LocalDate.of(1999, 1, 1))
                .sex("F")
                .grade(2)
                .status(StudentStatus.ENROLLED) // Enum 확인
                .email("ddd@naver.com")
                .password(passwordEncoder.encode("2234")) // 비밀번호 암호화 필수!
                .build();

        studentRepository.save(student1);
        studentRepository.save(student2);

        // 3. 학생-학과 연결 (주전공)
        StudentDepartment link1 = StudentDepartment.builder()
                .student(student1)
                .department(dept1) // 컴공과 연결
                .majorType(MajorType.MAJOR)
                .build();

        StudentDepartment link2 = StudentDepartment.builder()
                .student(student2)
                .department(dept2) // 컴공과 연결
                .majorType(MajorType.MAJOR)
                .build();

        studentDepartmentRepository.save(link1);
        studentDepartmentRepository.save(link2);

        staffRepository.save(Staff.builder()
                .userId("kyuris")
                .staffName("김철수")
                .position("총장")
                .email("ggg@naver.com")
                .password(passwordEncoder.encode("1111"))
                .authority(StaffAuthority.ADMIN)
                .build());

        System.out.println("=============================");
        System.out.println(" 초기 목데이터(Mock Data) 삽입 완료 ");
        System.out.println("=============================");
    }
}
