package com.aston.secondTask.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * RestApiServlet handles REST API requests for various entities such as courses, coordinators, and students.
 */

@Slf4j
@WebServlet(urlPatterns = "/rest/*")
public class RestApiServlet extends HttpServlet {

    private static final String COORDINATOR_NOT_FOUND = "Coordinator not found: ";
    private static final String STUDENT_NOT_FOUND = "Student not found: ";
    private static final String COORDINATORS_NOT_FOUND = "Coordinators not found: ";
    private static final String COURSES_NOT_FOUND = "Courses not found: ";
    private static final String COORDINATOR_NOT_CREATED = "Coordinator not created: ";
    private static final String COURSE_NOT_CREATED = "Course not created: ";
    private static final String STUDENT_NOT_CREATED = "Student not created: ";
    private static final String STUDENT_COURSE_NOT_CREATED = "Student course not created: ";
    private static final String COORDINATOR_NOT_DELETED = "Coordinator not deleted: ";
    private static final String COURSE_NOT_DELETED = "Course not deleted: ";
    private static final String STUDENT_NOT_DELETED = "Student not deleted: ";
    private static final String STUDENT_COURSE_NOT_DELETED = "Student's course not deleted: ";
    private static final String COORDINATOR_NOT_UPDATED = "Coordinator not updated: ";
    private static final String COURSE_NOT_UPDATED = "Course not updated: ";
    private static final String STUDENT_NOT_UPDATED = "Student not updated: ";
    private static final String COURSE_CREATED_SUCCESS_JSON = "{ \"course_id\" : \"%d\" }";
    private static final String COORDINATOR_CREATED_SUCCESS_JSON = "{ \"coordinator_id\" : \"%d\" }";
    private static final String STUDENT_CREATED_SUCCESS_JSON = "{ \"student_id\" : \"%d\" }";
    private static final String STUDENT_COURSE_CREATED_SUCCESS_JSON = "{ \"student_course_id\" : \"%d\" }";
    private RestApiHandler getRestHandler;
    private RestApiHandler postRestHandler;
    private RestApiHandler putRestHandler;
    private RestApiHandler deleteRestHandler;

    /**
     * Initializes the servlet and retrieves the REST handlers from the servlet context.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {

        this.getRestHandler = (RestApiHandler) getServletContext().getAttribute("getRestHandler");
        this.postRestHandler = (RestApiHandler) getServletContext().getAttribute("postRestHandler");
        this.putRestHandler = (RestApiHandler) getServletContext().getAttribute("putRestHandler");
        this.deleteRestHandler = (RestApiHandler) getServletContext().getAttribute("deleteRestHandler");
    }

    /**
     * Handles GET requests and returns the appropriate response.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the GET request
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            String user_response = getRestHandler.handleRestRequest(pathInfo).orElseThrow(SQLException::new);
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = resp.getWriter();
            out.write(user_response);

        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            handleNotFoundError(resp, e, pathInfo);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles POST requests and returns the appropriate response.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the POST request
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        try {
            int generated_id = postRestHandler.handleRestRequest(pathInfo, req);

            handlePostSuccess(resp, pathInfo, generated_id);
        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());

            handlePostError(resp, e, pathInfo);
        }
    }

    /**
     * Handles DELETE requests and returns the appropriate response.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the DELETE request
     */

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        try {
            int deleted_rows = deleteRestHandler.handleRestRequest(pathInfo, req);
            if (deleted_rows != 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                throw new SQLException("Something wrong");
            }
        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            handleDeleteError(resp, e, pathInfo);
        }
    }

    /**
     * Handles PUT requests and returns the appropriate response.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error is detected when the servlet handles the PUT request
     */

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            int updated_rows = putRestHandler.handleRestRequest(pathInfo, req);
            if (updated_rows != 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                throw new SQLException("Something wrong");
            }
        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            handlePutError(resp, e, pathInfo);
        }
    }

    /**
     * Handles errors when a requested resource is not found.
     *
     * @param resp     the HttpServletResponse object that contains the response the servlet returns to the client
     * @param e        the SQLException that occurred
     * @param pathInfo the path of the request
     * @throws IOException if an input or output error is detected when the servlet handles the error
     */

    private static void handleNotFoundError(HttpServletResponse resp, SQLException e, String pathInfo) throws IOException {
        PrintWriter out = resp.getWriter();
        if (pathInfo.contains("coordinators")) {
            out.write(COORDINATORS_NOT_FOUND + e.getMessage());
        } else if (pathInfo.contains("courses")) {
            out.write(COURSES_NOT_FOUND + e.getMessage());
        } else if (pathInfo.contains("coordinator")) {
            out.write(COORDINATOR_NOT_FOUND + e.getMessage());
        } else if (pathInfo.contains("student")) {
            out.write(STUDENT_NOT_FOUND + e.getMessage());
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * Handles successful POST requests by writing the appropriate success message to the response.
     *
     * @param resp        the HttpServletResponse object that contains the response the servlet returns to the client
     * @param pathInfo    the path of the request
     * @param generatedId the ID generated for the newly created resource
     * @throws IOException if an input or output error is detected when the servlet handles the response
     */
    private static void handlePostSuccess(HttpServletResponse resp, String pathInfo, int generated_id) throws IOException {
        PrintWriter out = resp.getWriter();
        if (pathInfo.contains("coordinator")) {
            out.write(String.format(COORDINATOR_CREATED_SUCCESS_JSON, generated_id));
        } else if (pathInfo.contains("student_course")) {
            out.write(String.format(STUDENT_COURSE_CREATED_SUCCESS_JSON, generated_id));
        } else if (pathInfo.contains("course")) {
            out.write(String.format(COURSE_CREATED_SUCCESS_JSON, generated_id));
        } else if (pathInfo.contains("student")) {
            out.write(String.format(STUDENT_CREATED_SUCCESS_JSON, generated_id));
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    /**
     * Handles errors that occur during POST requests by writing the appropriate error message to the response.
     *
     * @param resp     the HttpServletResponse object that contains the response the servlet returns to the client
     * @param e        the SQLException that occurred
     * @param pathInfo the path of the request
     * @throws IOException if an input or output error is detected when the servlet handles the error
     */
    private static void handlePostError(HttpServletResponse resp, SQLException e, String pathInfo) throws IOException {
        PrintWriter out = resp.getWriter();
        if (pathInfo.contains("coordinator")) {
            out.write(COORDINATOR_NOT_CREATED + e.getMessage());
        } else if (pathInfo.contains("student_course")) {
            out.write(STUDENT_COURSE_NOT_CREATED + e.getMessage());
        } else if (pathInfo.contains("course")) {
            out.write(COURSE_NOT_CREATED + e.getMessage());
        } else if (pathInfo.contains("student")) {
            out.write(STUDENT_NOT_CREATED + e.getMessage());
        }
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles errors that occur during PUT requests.
     *
     * @param resp     the HttpServletResponse object that contains the response the servlet returns to the client
     * @param pathInfo the path of the request
     * @param e        the SQLException that occurred
     * @throws IOException if an input or output error is detected when the servlet handles the error
     */

    private static void handlePutError(HttpServletResponse resp, SQLException e, String pathInfo) throws IOException {
        PrintWriter out = resp.getWriter();
        if (pathInfo.contains("coordinator")) {
            out.write(COORDINATOR_NOT_UPDATED + e.getMessage());
        } else if (pathInfo.contains("course")) {
            out.write(COURSE_NOT_UPDATED + e.getMessage());
        } else if (pathInfo.contains("student")) {
            out.write(STUDENT_NOT_UPDATED + e.getMessage());
        }
    }

    /**
     * Handles errors that occur during DELETE requests.
     *
     * @param resp     the HttpServletResponse object that contains the response the servlet returns to the client
     * @param pathInfo the path of the request
     * @param e        the SQLException that occurred
     * @throws IOException if an input or output error is detected when the servlet handles the error
     */
    private static void handleDeleteError(HttpServletResponse resp, SQLException e, String pathInfo) throws IOException {
        PrintWriter out = resp.getWriter();
        if (pathInfo.contains("coordinator")) {
            out.write(COORDINATOR_NOT_DELETED + e.getMessage());
        } else if (pathInfo.contains("course")) {
            out.write(COURSE_NOT_DELETED + e.getMessage());
        } else if (pathInfo.contains("student")) {
            out.write(STUDENT_NOT_DELETED + e.getMessage());
        }
    }
}