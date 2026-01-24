package com.springboot.university.domain.student;

import com.springboot.university.domain.StudentDepartment.StudentDepartment;
import com.springboot.university.domain.StudentDepartment.StudentDepartmentRepository;
import com.springboot.university.domain.department.Department;
import com.springboot.university.domain.department.DepartmentRepository;
import com.springboot.university.domain.department.MajorType;
import com.springboot.university.domain.student.dto.StudentBriefInfoDTO;
import com.springboot.university.domain.student.dto.StudentInfoDTO;
import com.springboot.university.domain.student.dto.StudentRegisterRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentDepartmentRepository studentDepartmentRepository;

    public List<StudentBriefInfoDTO> getStudentsList() {
        return studentRepository.getStudentsList();
    }

    public StudentInfoDTO getStudentInfo(Long studentId) {
        StudentInfoDTO studentInfoDTO = studentRepository.findStudentInfoById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다: " + studentId));

        return studentInfoDTO;
    }

    public Long register(StudentRegisterRequestDTO dto) {
        List<Department> departmentList = new ArrayList<>();
        for(String deptName : dto.deptName()) {
            Department department = departmentRepository.findByDeptName(deptName)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학과입니다: " + dto.deptName()));
            departmentList.add(department);
        }


        Long studentId = generateStudentId(dto);

        Student student = Student.builder()
                .id(studentId)
                .name(dto.name())
                .birth(dto.birth())
                .sex(dto.sex())
                .grade(dto.grade())
                .status(dto.status())
                .email(dto.email())
                .build();

        studentRepository.save(student);

        for(Department department : departmentList) {
            StudentDepartment mainMajor = StudentDepartment.builder()
                    .student(student)
                    .department(department)
                    .majorType(MajorType.MAJOR)
                    .build();
            studentDepartmentRepository.save(mainMajor);
        }

        return student.getId();

    }

    private Long generateStudentId(StudentRegisterRequestDTO dto) {
        int year = java.time.LocalDate.now().getYear();

        Long lastId = studentRepository.findMaxId()
                .orElse((long) year * 10000L);

        // 같은 해면 +1, 해가 바뀌면 그 해의 첫 번호
        long base = (long) year * 10000L;
        if (lastId < base) return base + 1;

        return lastId + 1;
    }
}
