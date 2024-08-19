package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PutRestHandlerTest {
    @Mock
    private CoordinatorService coordinatorService;
    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private PutRestHandler putRestHandler;


    @Test
    public void testUpdateStudentCoordinator() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int studentId = 2;
        int coordinatorId = 3;
        int updatedRows = 1;
        String requestPath = "/student/2";
        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"coordinatorId\":\"3\"}"));
        when(studentService.updateCoordinator(studentId, coordinatorId)).thenReturn(updatedRows);

        int result = putRestHandler.handleRestRequest(requestPath, request);

        assertEquals(updatedRows, result);
    }

    @Test
    public void testUpdateCourseName() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int courseId = 2;
        String courseName = "Voicing";
        int updatedRows = 1;
        String requestPath = "/course/2";
        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"name\":\"Voicing\"}"));
        when(courseService.updateCourseName(courseId, courseName)).thenReturn(updatedRows);

        int result = putRestHandler.handleRestRequest(requestPath, request);

        assertEquals(updatedRows, result);
    }

    @Test
    public void testUpdateCoordinatorName() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = mock(BufferedReader.class);
        int coordinatorId = 1;
        String coordinatorName = "Stefan Dancer";
        int updatedRows = 1;
        String requestPath = "/coordinator/1";
        when(request.getReader()).thenReturn(reader);
        when(reader.lines()).thenReturn(Stream.of("{\"name\":\"Stefan Dancer\"}"));
        when(coordinatorService.updateCoordinatorName(coordinatorId, coordinatorName)).thenReturn(updatedRows);

        int result = putRestHandler.handleRestRequest(requestPath, request);

        assertEquals(updatedRows, result);
    }


}


