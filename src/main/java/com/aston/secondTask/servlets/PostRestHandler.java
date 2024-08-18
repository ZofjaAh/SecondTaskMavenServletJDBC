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

public class PostRestHandler extends RestApiHandler {

    public PostRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

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

    private int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }
}

