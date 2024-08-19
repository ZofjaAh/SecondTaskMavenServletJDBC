package com.aston.secondTask.service;

import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DTOFixtures;
import util.EntityFixtures;

import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentDAO studentDAO;

    @Test
    void shouldCreateStudentCorrectly() {
        StudentDTO studentDTO = DTOFixtures.student_1();
        StudentEntity studentEntity = EntityFixtures.student_1();
        int studentId = 1;
        int coordinatorId = 1;
        when(studentDAO.createStudentWithCoordinator(studentEntity, coordinatorId)).thenReturn(studentId);
        int result = studentService.createStudentWithCoordinator(studentDTO, coordinatorId);
        Assertions.assertEquals(studentId, result);
    }

    @Test
    void shouldDeleteStudentSuccessful() {
        int studentId = 1;
        int numberChangedRows = 2;
        when(studentDAO.deleteStudent(studentId)).thenReturn(numberChangedRows);
        int result = studentService.deleteStudent(studentId);
        Assertions.assertEquals(numberChangedRows, result);
    }

    @Test
    void shouldUpdateCoordinatorSuccessful() {
        int studentId = 1;
        int coordinatorId = 3;
        int numberChangedRows = 1;
        when(studentDAO.updateCoordinator(studentId, coordinatorId)).thenReturn(numberChangedRows);
        int result = studentService.updateCoordinator(studentId, coordinatorId);
        Assertions.assertEquals(numberChangedRows, result);
    }

    @Test
    void shouldSearchStudentWithCoursesByIdSuccessful() {
        StudentEntity studentEntity = EntityFixtures.student_1_1()
                .withCourses(Set.of(EntityFixtures.course_1_0(), EntityFixtures.course_2_0()));
        StudentDTO studentDTO = DTOFixtures.student_1_0()
                .withCourses(Set.of(DTOFixtures.course_1_3(), DTOFixtures.course_2_3()));
        int studentId = 1;
        when(studentDAO.findById(1)).thenReturn(studentEntity);
        StudentDTO result = studentService.findStudentWithCoursesByID(studentId);
        Assertions.assertEquals(studentDTO.getName(), result.getName());
        Assertions.assertEquals(studentDTO.getCoordinatorId(), result.getCoordinatorId());
        Assertions.assertEquals(studentDTO.getCourses().size(), result.getCourses().size());
    }

    @Test
    void shouldAddCourseForStudentSuccessful() {
        int studentId = 2;
        int courseId = 2;
        int numberChangedRows = 1;
        when(studentDAO.addCourse(studentId, courseId)).thenReturn(numberChangedRows);
        int result = studentService.addStudentCourse(studentId, courseId);
        Assertions.assertEquals(numberChangedRows, result);
    }


}