package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Handles REST API POST requests for various entities such as courses, coordinators, and students.
 */
public class PostRestHandler extends RestApiHandler {
    /**
     * Constructs a new PostRestHandler with the specified services.
     *
     * @param coordinatorService the service for handling coordinator-related operations
     * @param studentService the service for handling student-related operations
     * @param courseService the service for handling course-related operations
     */
    public PostRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }
    /**
     * Handles a REST request and returns an optional string response.
     *
     * @param requestPath the path of the request
     * @return an optional string response
     * @throws UnsupportedOperationException always thrown as this method is not supported
     */

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a REST request and returns the generated ID.
     *
     * @param requestPath the path of the request
     * @param req the HTTP servlet request
     * @return the generated ID
     * @throws IOException if an I/O error occurs while reading the request
     */
    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest req) throws IOException {
        int generated_id = 0;
        if (requestPath.matches("^/course/?$")) {
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            CourseDTO course = objectMapper.readValue(bodyParams, CourseDTO.class);
            generated_id = courseService.createCourse(course);
        } else if (requestPath.matches("^/coordinator/?$")) {
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            CoordinatorDTO coordinator = objectMapper.readValue(bodyParams, CoordinatorDTO.class);
            generated_id = coordinatorService.createCoordinator(coordinator);
        } else if (requestPath.matches("^/student/?$")) {
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            StudentDTO student = objectMapper.readValue(bodyParams, StudentDTO.class);
            generated_id = studentService.createStudentWithCoordinator(student, student.getCoordinatorId());
        } else if (requestPath.matches("^/student_course/?\\d+$")) {
            int studentId = getCurrentId(requestPath);
            String bodyParams = req.getReader().lines().collect(Collectors.joining());
            Map<String, String> map = objectMapper.readValue(bodyParams, new TypeReference<Map<String, String>>() {
            });
            generated_id = studentService.addStudentCourse(studentId, Integer.parseInt(map.get("courseId")));
        }

        return generated_id;
    }
    /**
     * Extracts the current ID from the request path.
     *
     * @param requestPath the path of the request
     * @return the extracted ID
     */
    private int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}

