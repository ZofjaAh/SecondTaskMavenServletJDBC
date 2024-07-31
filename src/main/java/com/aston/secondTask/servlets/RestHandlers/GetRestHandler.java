package com.aston.secondTask.servlets.RestHandlers;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GetRestHandler extends RestApiHandler {
    public GetRestHandler(CoordinatorDAO coordinatorDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        super(coordinatorDAO, studentDAO, courseDAO);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) throws SQLException, JsonProcessingException {
        if (requestPath.matches("^/course/\\d+$")) {
            int courseId = getCurrentId(requestPath);
            CourseEntity course = (CourseEntity) courseDAO.findById(courseId).orElseThrow(SQLException::new);
            final String jsonCourse = objectMapper.writeValueAsString(course);
            return Optional.ofNullable(jsonCourse);

        } else if (requestPath.matches("^/student/\\d+$")) {
            int studentId = getCurrentId(requestPath);
            StudentEntity student = studentDAO.findById(studentId).orElseThrow(SQLException::new);
            final String jsonStudent = objectMapper.writeValueAsString(student);
            return Optional.ofNullable(jsonStudent);

        } else if (requestPath.matches("^/coordinator/\\d+$")) {
            int coordinatorId = getCurrentId(requestPath);
            CoordinatorEntity coordinator = coordinatorDAO.findById(coordinatorId).orElseThrow(SQLException::new);
            final String jsonCoordinator = objectMapper.writeValueAsString(coordinator);
            return Optional.ofNullable(jsonCoordinator);

        }
        else if (requestPath.matches("^/courses/$")) {
            final List<CourseEntity> allCourses = courseDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allCourses));

        } else if (requestPath.matches("^/students/$")) {
            final List<StudentEntity> allStudents = studentDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allStudents));
        }else if (requestPath.matches("^/coordinators/$")) {
            final List<CoordinatorEntity> allCoordinators = coordinatorDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allCoordinators));

        }

        return Optional.empty();
    }

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }
    
    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}
