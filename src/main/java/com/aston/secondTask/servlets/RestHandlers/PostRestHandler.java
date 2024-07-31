package com.aston.secondTask.servlets.RestHandlers;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostRestHandler extends RestApiHandler{

    public PostRestHandler(CoordinatorDAO coordinatorDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        super(coordinatorDAO, studentDAO, courseDAO);
    }

    @Override
        public Optional<String> handleRestRequest(String requestPath) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int handleRestRequest(String requestPath, HttpServletRequest req) throws SQLException, IOException {
            int generated_id = 0;
            if (requestPath.matches("^/course/$")) {
                String bodyParams = req.getReader().lines().collect(Collectors.joining());
                CourseEntity course = objectMapper.readValue(bodyParams, CourseEntity.class);
                generated_id = courseDAO.createCourse(course);

            } else if (requestPath.matches("^/coordinator/$")) {
                String bodyParams = req.getReader().lines().collect(Collectors.joining());
                CoordinatorEntity coordinator = objectMapper.readValue(bodyParams, CoordinatorEntity.class);
                generated_id = coordinatorDAO.createCoordinator(coordinator);
            } else if (requestPath.matches("^/student/$")) {
                String bodyParams = req.getReader().lines().collect(Collectors.joining());
                StudentEntity student = objectMapper.readValue(bodyParams, StudentEntity.class);
                generated_id = studentDAO.createStudentWithCoordinatorAndCourse(student);
            }

            return generated_id;

        }
}
