package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import com.aston.secondTask.servlets.DTO.CoordinatorDTO;
import com.aston.secondTask.servlets.DTO.CourseDTO;
import com.aston.secondTask.servlets.DTO.StudentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.Set;

/**
 * Handles GET REST API requests for students, coordinators, and courses.
 */
public class GetRestHandler extends RestApiHandler {
    public GetRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }

    /**
     * Handles a REST request based on the request path.
     *
     * @param requestPath the path of the REST request
     * @return an Optional containing the JSON response, or an empty Optional if the path is not recognized
     * @throws JsonProcessingException if an error occurs while processing JSON
     */

    @Override
    public Optional<String> handleRestRequest(String requestPath) throws JsonProcessingException {
        if (requestPath.matches("^/student/?\\d+$")) {
            int studentId = getCurrentId(requestPath);
            StudentDTO student = studentService.findStudentWithCoursesByID(studentId);
            final String jsonStudent = objectMapper.writeValueAsString(student);
            return Optional.ofNullable(jsonStudent);

        } else if (requestPath.matches("^/coordinator/?\\d+$")) {
            int coordinatorId = getCurrentId(requestPath);
            CoordinatorDTO coordinator = coordinatorService.findById(coordinatorId);
            final String jsonCoordinator = objectMapper.writeValueAsString(coordinator);
            return Optional.ofNullable(jsonCoordinator);

        } else if (requestPath.matches("^/courses/?$")) {
            final Set<CourseDTO> allCourses = courseService.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allCourses));

        } else if (requestPath.matches("^/coordinators/?$")) {
            final Set<CoordinatorDTO> allCoordinators = coordinatorService.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allCoordinators));

        }

        return Optional.empty();
    }

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

    /**
     * Extracts the ID from the request path.
     *
     * @param requestPath the path of the REST request
     * @return the extracted ID
     */

    private int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}
