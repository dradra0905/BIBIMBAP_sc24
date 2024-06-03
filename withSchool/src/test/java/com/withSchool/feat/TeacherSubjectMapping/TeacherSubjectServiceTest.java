package com.withSchool.feat.TeacherSubjectMapping;

import com.withSchool.dto.mapping.ReqTeacherSubjectMappingDTO;
import com.withSchool.dto.mapping.ResTeacherSubjectDefaultDTO;
import com.withSchool.entity.classes.ClassInformation;
import com.withSchool.entity.school.SchoolInformation;
import com.withSchool.entity.subject.Subject;
import com.withSchool.entity.user.User;
import com.withSchool.repository.classes.ClassRepository;
import com.withSchool.repository.school.SchoolInformationRepository;
import com.withSchool.repository.subject.SubjectRepository;
import com.withSchool.repository.user.UserRepository;
import com.withSchool.service.mapping.TeacherSubjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TeacherSubjectServiceTest {
    SchoolInformation schoolInformation;
    Subject subject;
    ClassInformation classInformation;
    User student;
    User teacher;

    @Autowired
    private SchoolInformationRepository schoolInformationRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherSubjectService teacherSubjectService;

    @BeforeEach
    public void init() {
        schoolInformation = schoolInformationRepository.save(SchoolInformation.builder().schulNm("아주고등학교").build());
        subject = subjectRepository.save(Subject.builder().subjectName("수학").grade("3").semester("1").year("2024").schoolInformation(schoolInformation).build());
        classInformation = classRepository.save(ClassInformation.builder().grade(1).year(2024).inClass(8).schoolInformation(schoolInformation).build());
        student = userRepository.save(User.builder().id("student").name("student").accountType(0).schoolInformation(schoolInformation).classInformation(classInformation).build());
        teacher = userRepository.save(User.builder().id("teacher").name("teacher").accountType(1).schoolInformation(schoolInformation).classInformation(classInformation).build());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(student, student.getPassword()));
    }

    @Test
    @DisplayName("교사 수업 매핑")
    public void testTeacherSubjectMapping(){
        // given
        Long subjectId = subject.getSubjectId();
        Long teacherId = teacher.getUserId();
        ReqTeacherSubjectMappingDTO reqTeacherSubjectMappingDTO = ReqTeacherSubjectMappingDTO.builder()
                .subjectId(subjectId)
                .teacherId(teacherId)
                .build();

        // when
        ResTeacherSubjectDefaultDTO resTeacherSubjectDefaultDTO = teacherSubjectService.save(reqTeacherSubjectMappingDTO);

        // then
        Assertions.assertEquals("teacher", resTeacherSubjectDefaultDTO.getTeacher().getName());
    }

}
