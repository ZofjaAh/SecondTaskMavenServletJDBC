package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DTOFixtures;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRestHandlerTest {
    @Mock
    private CoordinatorService coordinatorService;
    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private GetRestHandler getRestHandler;

    @Test
    void testHandleCoordinatorGetRequest() throws JsonProcessingException {
        CoordinatorDTO coordinator = DTOFixtures.coordinator_1_0();
        when(coordinatorService.findById(1)).thenReturn(coordinator);
        Optional<String> result = getRestHandler.handleRestRequest("/coordinator/1");

        Assertions.assertTrue(result.isPresent());

    }
    @Test
    void testHandleStudentGetRequest() throws JsonProcessingException {
        StudentDTO studentDTO = DTOFixtures.student_1_1();
        when(studentService.findStudentWithCoursesByID(1)).thenReturn(studentDTO);
        Optional<String> result = getRestHandler.handleRestRequest("/student/1");
        Assertions.assertTrue(result.isPresent());

    }
    @Test
    void testHandleCoursesGetRequest() throws JsonProcessingException {
        Set<CourseDTO> courses = Set.of(DTOFixtures.course_1_0(),DTOFixtures.course_2_0());
        when(courseService.findAll()).thenReturn(courses);
        Optional<String> result = getRestHandler.handleRestRequest("/courses/");

        Assertions.assertTrue(result.isPresent());

    }
    @Test
    void testHandleCoordinatorsGetRequest() throws JsonProcessingException {
        Set<CoordinatorDTO> coordinators = Set.of(DTOFixtures.coordinator_1_0(), DTOFixtures.coordinator_2_0());
        when(coordinatorService.findAll()).thenReturn(coordinators);
        Optional<String> result = getRestHandler.handleRestRequest("/coordinators/");

        Assertions.assertTrue(result.isPresent());

    }

//    @Test
//    void testHandleCourseGetRequest() {
//        when(courseService.getCourse(2)).thenReturn(1);
//
//        getRestHandler.handleRestRequest("/course/2", null);
//
//        assertEquals(1, updatedRows);
//        verify(courseService, times(1)).getCourse(2);
//    }
//
//    @Test
//    void testHandleStudentGetRequest() {
//        when(studentService.getStudent(3)).thenReturn(3);
//
//        getRestHandler.handleRestRequest("/student/3", null);
//
//        assertEquals(3, updated_rows);
//        verify(studentService, times(1)).getStudent(3);
//
//    }
}
