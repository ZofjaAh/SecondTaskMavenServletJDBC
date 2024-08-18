package com.aston.secondTask.servlets;

import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import com.aston.secondTask.infrastructure.repository.CourseRepository;
import com.aston.secondTask.infrastructure.repository.StudentRepository;
import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ContextListenerTest {
    @Test
    public void testContextInitialized() {
        ServletContext servletContext = mock(ServletContext.class);
        ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);

        ContextListener contextListener = new ContextListener();
        contextListener.contextInitialized(servletContextEvent);

        verify(servletContext, times(1)).setAttribute(eq("courseRepository"),
                any(CourseRepository.class));
        verify(servletContext, times(1)).setAttribute(eq("coordinatorRepository"),
                any(CoordinatorRepository.class));
verify(servletContext, times(1)).setAttribute(eq("studentRepository"),
                any(StudentRepository.class));
verify(servletContext, times(1)).setAttribute(eq("courseService"),
                any(CourseService.class));
verify(servletContext, times(1)).setAttribute(eq("coordinatorService"),
                any(CoordinatorService.class));
verify(servletContext, times(1)).setAttribute(eq("studentService"),
                any(StudentService.class));
verify(servletContext, times(1)).setAttribute(eq("getRestHandler"),
                any(GetRestHandler.class));
verify(servletContext, times(1)).setAttribute(eq("postRestHandler"),
                any(PostRestHandler.class));
verify(servletContext, times(1)).setAttribute(eq("putRestHandler"),
                any(PutRestHandler.class));
verify(servletContext, times(1)).setAttribute(eq("deleteRestHandler"),
                any(DeleteRestHandler.class));


    }
}
