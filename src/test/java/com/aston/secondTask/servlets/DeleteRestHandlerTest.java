package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestHandlerTest {
    @Mock
    private CoordinatorService coordinatorService;
    @Mock
    private StudentService studentService;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private DeleteRestHandler deleteRestHandler;

    @Test
    void testHandleCoordinatorDeleteRequest() {
        when(coordinatorService.deleteById(1)).thenReturn(1);


        int updatedRows = deleteRestHandler.handleRestRequest("/coordinator/1", null);

        assertEquals(1, updatedRows);
        verify(coordinatorService, times(1)).deleteById(1);

    }

    @Test
    void testHandleCourseDeleteRequest() {
        when(courseService.deleteCourse(2)).thenReturn(1);

        int updatedRows = deleteRestHandler.handleRestRequest("/course/2", null);

        assertEquals(1, updatedRows);
        verify(courseService, times(1)).deleteCourse(2);
    }

    @Test
    void testHandleStudentDeleteRequest() {
        when(studentService.deleteStudent(3)).thenReturn(3);

        int updated_rows = deleteRestHandler.handleRestRequest("/student/3", null);

        assertEquals(3, updated_rows);
        verify(studentService, times(1)).deleteStudent(3);

    }
}
