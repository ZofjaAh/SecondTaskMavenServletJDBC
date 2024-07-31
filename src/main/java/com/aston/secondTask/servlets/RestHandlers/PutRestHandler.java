package com.aston.secondTask.servlets.RestHandlers;

import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PutRestHandler extends RestApiHandler{
    public PutRestHandler(CoordinatorDAO coordinatorDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        super(coordinatorDAO, studentDAO, courseDAO);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) throws SQLException, JsonProcessingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest req) throws SQLException, IOException {
      int update_rows = 0;
        if (requestPath.matches("^/course/\\d+$")) {
            int course_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
                Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
                });
                return courseDAO.updateCourseName(course_id, map.get("name"));
            }
        else  if (requestPath.matches("^/coordinator/\\d+$")) {
            int coordinator_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
            });
            return coordinatorDAO.updateCoordinatorName(coordinator_id, map.get("name"));
        }
        else  if (requestPath.matches("^/student/\\d+$")) {
            int student_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
            });
            return studentDAO.updateCoordinatorName(student_id, map.get("name"));
        }

       return update_rows;
    }
    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}
