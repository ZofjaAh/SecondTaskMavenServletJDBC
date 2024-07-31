package com.aston.secondTask.servlets.RestHandlers;

import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public abstract class RestApiHandler {
     ObjectMapper objectMapper = new ObjectMapper();
     CoordinatorDAO coordinatorDAO;
     StudentDAO studentDAO;
    CourseDAO courseDAO;

    protected RestApiHandler(CoordinatorDAO coordinatorDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        this.coordinatorDAO = coordinatorDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }

    abstract Optional<String> handleRestRequest(String requestPath)
                throws SQLException, JsonProcessingException;

        abstract int handleRestRequest(String requestPath, HttpServletRequest request)
                throws SQLException, IOException;

    }

