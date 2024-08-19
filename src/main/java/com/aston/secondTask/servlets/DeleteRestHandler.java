package com.aston.secondTask.servlets;

import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Handles DELETE REST API requests for updating various entities such as courses, coordinators, and students.
 */
public class DeleteRestHandler extends RestApiHandler {

    public DeleteRestHandler(CoordinatorService coordinatorService, StudentService studentService, CourseService courseService) {
        super(coordinatorService, studentService, courseService);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a REST delete request with the specified request path and HTTP request.
     *
     * @param requestPath the path of the REST request
     * @param req         the HTTP request
     * @return the number of rows updated (deleted)
     */
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

    /**
     * Extracts the ID from the request path.
     *
     * @param requestPath the path of the REST request
     * @return the extracted ID
     */
    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }

}
