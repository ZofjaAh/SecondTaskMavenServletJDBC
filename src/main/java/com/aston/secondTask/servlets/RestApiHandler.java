package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Abstract class RestApiHandler provides a template for handling REST API requests.
 * It includes common services and an ObjectMapper for JSON processing.
 */
public abstract class RestApiHandler {
    ObjectMapper objectMapper = new ObjectMapper();
    CoordinatorService coordinatorService;
    StudentService studentService;
    CourseService courseService;


    protected RestApiHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        this.coordinatorService = coordinatorService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    abstract Optional<String> handleRestRequest(String requestPath)
            throws SQLException, JsonProcessingException;


    abstract int handleRestRequest(String requestPath, HttpServletRequest request)
            throws SQLException, IOException;

}

