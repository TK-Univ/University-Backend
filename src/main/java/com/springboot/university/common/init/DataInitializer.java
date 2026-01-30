package com.springboot.university.common.init;

import com.springboot.university.domain.lecture.Lecture;
import com.springboot.university.domain.lecture_professor.LectureProfessor;
import com.springboot.university.domain.lecture_professor.LectureProfessorRepository;
import com.springboot.university.domain.professor.Professor;
import com.springboot.university.domain.student_department.StudentDepartmentRepository;
import com.springboot.university.domain.department.Department;
import com.springboot.university.domain.department.DepartmentRepository;
import com.springboot.university.domain.department.MajorType;
import com.springboot.university.domain.lecture.LectureRepository;
import com.springboot.university.domain.professor.ProfessorRepository;
import com.springboot.university.domain.staff.Staff;
import com.springboot.university.domain.staff.StaffAuthority;
import com.springboot.university.domain.staff.StaffRepository;
import com.springboot.university.domain.student.Student;
import com.springboot.university.domain.student_department.StudentDepartment;
import com.springboot.university.domain.student.StudentRepository;
import com.springboot.university.domain.student.StudentStatus;
import com.springboot.university.domain.subject.Subject;
import com.springboot.university.domain.subject.SubjectRepository;
import com.springboot.university.domain.subject.SubjectType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Profile("local")
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentDepartmentRepository studentDepartmentRepository;
    private final StaffRepository staffRepository;
    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final LectureRepository lectureRepository;
    private final LectureProfessorRepository lectureProfessorRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${init.data.email}")
    private String initEmail;

    @Override
    @Transactional
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
                .email(initEmail)
                .password(passwordEncoder.encode("1111"))
                .authority(StaffAuthority.ADMIN)
                .build());


        // 4. 과목 데이터 생성
        Subject subject1 = subjectRepository.save(Subject.builder()
                .subjectName("자바 프로그래밍")
                .type(SubjectType.MAJOR)
                .department(dept1)
                .credit(3)
                .build());

        // 5. 교수 데이터 생성
        Professor professor1 = professorRepository.save(Professor.builder()
                .name("박교수")
                .position("정교수")
                .department(dept1)
                .build());

        // 6. 강의(Lecture) 데이터 생성
        // 주의: Lecture 엔티티에 Builder가 없다면 생성자나 정적 팩토리 메서드를 사용해야 합니다.
        // 여기서는 필드 구성을 바탕으로 가상의 Builder를 사용합니다.
        Lecture lecture1 = Lecture.builder()
                .lectName("자바 기초 A반")
                .lectureYear(2026)
                .lectureTerm(1)
                .classroom("공학관 301호")
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(11, 0))
                .enrollCnt(0)
                .subject(subject1)
                .build();

        lectureRepository.save(lecture1);

        // 7. 강의-교수 매핑 데이터 생성 (LectureProfessor)
        LectureProfessor lectureProfessor = LectureProfessor.builder()
                .lecture(lecture1)
                .professor(professor1)
                .build();

        lectureProfessorRepository.save(lectureProfessor);
        System.out.println("=============================");
        System.out.println(" 초기 목데이터(Mock Data) 삽입 완료 ");
        System.out.println("=============================");
    }
}
