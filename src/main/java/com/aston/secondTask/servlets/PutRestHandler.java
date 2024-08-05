package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PutRestHandler extends RestApiHandler {
    public PutRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) throws JsonProcessingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest req) throws IOException {
        int update_rows = 0;
        if (requestPath.matches("^/course/\\d+$")) {
            int course_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
            });
            return courseService.updateCourseName(course_id, map.get("name"));
        } else if (requestPath.matches("^/coordinator/\\d+$")) {
            int coordinator_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
            });
            return coordinatorService.updateCoordinatorName(coordinator_id, map.get("name"));
        } else if (requestPath.matches("^/student/\\d+$")) {
            int student_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
            });
            return studentService.updateCoordinator(student_id, Integer.parseInt(map.get("coordinatorId")));
        }

        return update_rows;
    }

    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}
