package com.aston.secondTask.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RestApiServletTest {

    @InjectMocks
    private RestApiServlet restApiServlet;

    @Mock
    private RestApiHandler getRestHandler;
    @Mock
    private RestApiHandler postRestHandler;
    @Mock
    private RestApiHandler putRestHandler;
    @Mock
    private RestApiHandler deleteRestHandler;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws Exception {
         MockitoAnnotations.openMocks(this);

        when(response.getWriter()).thenReturn(writer);

        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getAttribute("getRestHandler")).thenReturn(getRestHandler);
        when(servletContext.getAttribute("postRestHandler")).thenReturn(postRestHandler);
        when(servletContext.getAttribute("putRestHandler")).thenReturn(putRestHandler);
        when(servletContext.getAttribute("deleteRestHandler")).thenReturn(deleteRestHandler);

        restApiServlet = new RestApiServlet() {
            @Override
            public ServletContext getServletContext() {
                return servletContext;
            }
        };
        restApiServlet.init();
    }

    @Test
    public void testDoGet_Success() throws Exception {
        String pathInfo = "/somePath";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(getRestHandler.handleRestRequest(pathInfo)).thenReturn(Optional.of("Success"));

        restApiServlet.doGet(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(writer).write("Success");
    }

    @Test
    public void testDoGet_Coordinator_SQLException() throws Exception {
        String pathInfo = "/coordinator";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(getRestHandler.handleRestRequest(pathInfo)).thenThrow(new SQLException("Database error"));

        restApiServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(writer).write("Coordinator not found: Database error");
    }

    @Test
    public void testDoGet_Student_SQLException() throws Exception {
        String pathInfo = "/student";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(getRestHandler.handleRestRequest(pathInfo)).thenThrow(new SQLException("Database error"));

        restApiServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(writer).write("Student not found: Database error");
    }

    @Test
    public void testDoGet_Courses_SQLException() throws Exception {
        String pathInfo = "/courses";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(getRestHandler.handleRestRequest(pathInfo)).thenThrow(new SQLException("Database error"));

        restApiServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(writer).write("Courses not found: Database error");
    }

    @Test
    public void testDoGet_Coordinators_SQLException() throws Exception {
        String pathInfo = "/coordinators";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(getRestHandler.handleRestRequest(pathInfo)).thenThrow(new SQLException("Database error"));

        restApiServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(writer).write("Coordinators not found: Database error");
    }

    @Test
    public void testDoGet_JsonProcessingException() throws Exception {
        String pathInfo = "/somePath";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(getRestHandler.handleRestRequest(pathInfo)).thenThrow(new JsonProcessingException("JSON error") {
        });

        restApiServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(writer, never()).write(anyString());
    }

    @Test
    public void testDoPost_Coordinator_Success() throws Exception {
        String pathInfo = "/coordinator";
        int generatedId = 1;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenReturn(generatedId);

        restApiServlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("{ \"coordinator_id\" : \"1\" }");
    }

    @Test
    public void testDoPost_Course_Success() throws Exception {
        String pathInfo = "/course";
        int generatedId = 2;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenReturn(generatedId);

        restApiServlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("{ \"course_id\" : \"2\" }");
    }

    @Test
    public void testDoPost_Student_Success() throws Exception {
        String pathInfo = "/student";
        int generatedId = 1;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenReturn(generatedId);

        restApiServlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("{ \"student_id\" : \"1\" }");
    }

    @Test
    public void testDoPost_Student_Course_Success() throws Exception {
        String pathInfo = "/student_course";
        int generatedId = 2;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenReturn(generatedId);

        restApiServlet.doPost(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("{ \"student_course_id\" : \"2\" }");
    }

    @Test
    public void testDoPost_Course_SQLException() throws Exception {
        String pathInfo = "/course";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(writer).write("Course not created: Database error");
    }

    @Test
    public void testDoPost_Student_SQLException() throws Exception {
        String pathInfo = "/student";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(writer).write("Student not created: Database error");
    }

    @Test
    public void testDoPost_Coordinator_SQLException() throws Exception {
        String pathInfo = "/coordinator";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(writer).write("Coordinator not created: Database error");
    }

    @Test
    public void testDoPost_Student_Course_SQLException() throws Exception {
        String pathInfo = "/student_course";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(postRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(writer).write("Student course not created: Database error");
    }

    @Test
    public void testDoDelete_Successful() throws Exception {
        String pathInfo = "/somePath";
        int numberRows = 2;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(deleteRestHandler.handleRestRequest(pathInfo, request)).thenReturn(numberRows);

        restApiServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoDelete_Not_Successful() throws Exception {
        String pathInfo = "/coordinator";
        int numberRows = 0;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(deleteRestHandler.handleRestRequest(pathInfo, request)).thenReturn(numberRows);

        restApiServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Coordinator not deleted: Something wrong");
    }

    @Test
    public void testDoDelete_Course_SQLException() throws Exception {
        String pathInfo = "/course";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(deleteRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Course not deleted: Database error");
    }

    @Test
    public void testDoPut_Successful() throws Exception {
        String pathInfo = "/somePath";
        int updatedRows = 1;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(putRestHandler.handleRestRequest(pathInfo, request)).thenReturn(updatedRows);

        restApiServlet.doPut(request, response);

        verify(response).setContentType("application/json; charset=UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoPut_Not_Successful() throws Exception {
        String pathInfo = "/coordinator";
        int numberRows = 0;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(putRestHandler.handleRestRequest(pathInfo, request)).thenReturn(numberRows);

        restApiServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Coordinator not updated: Something wrong");
    }

    @Test
    public void testDoPut_Course_SQLException() throws Exception {
        String pathInfo = "/course";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(putRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Course not updated: Database error");
    }

    @Test
    public void testDoPut_Student_SQLException() throws Exception {
        String pathInfo = "/student";
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(putRestHandler.handleRestRequest(pathInfo, request)).thenThrow(new SQLException("Database error"));

        restApiServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Student not updated: Database error");
    }


}