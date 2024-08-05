package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteRestHandler extends RestApiHandler {

    public DeleteRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest req) {
        int updated_rows = 0;
        if (requestPath.matches("^/coordinator/\\d+$")) {
            int coordinatorId = getCurrentId(requestPath);
            updated_rows = coordinatorService.deleteById(coordinatorId);

        } else if (requestPath.matches("^/course/\\d+$")) {
            int courseId = getCurrentId(requestPath);
            updated_rows = courseService.deleteCourse(courseId);
        } else if (requestPath.matches("^/student/\\d+$")) {
            int studentId = getCurrentId(requestPath);
            updated_rows = studentService.deleteStudent(studentId);
        }


        return updated_rows;
    }

    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }

}
