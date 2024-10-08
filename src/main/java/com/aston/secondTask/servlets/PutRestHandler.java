package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles PUT REST API requests for updating various entities such as courses, coordinators, and students.
 */
public class PutRestHandler extends RestApiHandler {
    public PutRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) throws JsonProcessingException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a REST request and returns the number of updated rows.
     *
     * @param requestPath the path of the request
     * @param req         the HttpServletRequest object that contains the request the client made to the servlet
     * @return the number of updated rows
     * @throws IOException if an input or output error is detected when the servlet handles the request
     */

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest req) throws IOException {
        int update_rows = 0;
        if (requestPath.matches("^/course/?\\d+$")) {
            int course_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<>() {
            });
            return courseService.updateCourseName(course_id, map.get("name"));
        } else if (requestPath.matches("^/coordinator/?\\d+$")) {
            int coordinator_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<>() {
            });
            return coordinatorService.updateCoordinatorName(coordinator_id, map.get("name"));
        } else if (requestPath.matches("^/student/?\\d+$")) {
            int student_id = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<>() {
            });
            return studentService.updateCoordinator(student_id, Integer.parseInt(map.get("coordinatorId")));
        }

        return update_rows;
    }

    /**
     * Extracts the current ID from the request path.
     *
     * @param requestPath the path of the request
     * @return the extracted ID
     */

    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}
