package com.aston.secondTask.servlets.RestHandlers;

import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteRestHandler extends RestApiHandler {

    public DeleteRestHandler(CoordinatorDAO coordinatorDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        super(coordinatorDAO, studentDAO, courseDAO);
    }

    @Override
    public Optional<String> handleRestRequest(String requestPath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int handleRestRequest(String requestPath, HttpServletRequest req) throws SQLException {
        int updated_rows = 0;
        if (requestPath.matches("^/coordinator/\\d+$")) {
            int coordinatorId = getCurrentId(requestPath);
            updated_rows = coordinatorDAO.deleteById(coordinatorId);

        } else if (requestPath.matches("^/course/\\d+$"))
        {
            int courseId = getCurrentId(requestPath);
            updated_rows = courseDAO.deleteCourse(courseId);
        } else if (requestPath.matches("^/student/\\d+$"))
        {
            int studentId = getCurrentId(requestPath);
            updated_rows = studentDAO.deleteStudent(studentId);
        }


        return updated_rows;
    }

    private static int getCurrentId(String requestPath) {
        String[] parts = requestPath.split("/");
        return Integer.parseInt(parts[2]);

    }

}
