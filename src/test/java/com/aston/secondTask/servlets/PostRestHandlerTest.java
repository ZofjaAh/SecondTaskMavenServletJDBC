package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.DTOFixtures;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostRestHandlerTest {
    @Mock
    private CoordinatorService coordinatorService;
    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private PostRestHandler postRestHandler;


    @Test
    public void testCreateCourse() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int generatedId = 1;
        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"name\":\"Math\"}"));
        when(courseService.createCourse(any(CourseDTO.class))).thenReturn(generatedId);

        int result = postRestHandler.handleRestRequest("/course/", request);

        assertEquals(generatedId, result);
    }

    @Test
    public void testCreateStudent() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int coordinatorId = 1;
        int generatedId = 2;

        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"name\":\"Nikol BadVoice\",\"coordinatorId\":\"1\"}"));
        StudentDTO studentDTO = DTOFixtures.student_3_0();
        when(studentService.createStudentWithCoordinator(studentDTO, coordinatorId)).thenReturn(generatedId);

        int result = postRestHandler.handleRestRequest("/student/", request);

        assertEquals(generatedId, result);
    }

    @Test
    public void testCreateCoordinator() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int generatedId = 1;
        CoordinatorDTO coordinator = DTOFixtures.coordinator_1();
        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"name\":\"Stefan Dancer\"}"));
        when(coordinatorService.createCoordinator(coordinator)).thenReturn(generatedId);

        int result = postRestHandler.handleRestRequest("/coordinator/", request);

        assertEquals(generatedId, result);
    }

    @Test
    public void testCreateStudentCourse() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int studentId = 1;
        int courseId = 2;
        int updatedRows = 1;
        String requestPath = "/student_course/1";
        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"courseId\":\"2\"}"));
        when(studentService.addStudentCourse(studentId, courseId)).thenReturn(updatedRows);

        int result = postRestHandler.handleRestRequest(requestPath, request);

        assertEquals(updatedRows, result);
    }

}


