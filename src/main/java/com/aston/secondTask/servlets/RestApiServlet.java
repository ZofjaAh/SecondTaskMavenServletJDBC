package com.aston.secondTask.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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

    @Override
    public void init() throws ServletException {

        this.getRestHandler = (RestApiHandler) getServletContext().getAttribute("getRestHandler");
        this.postRestHandler = (RestApiHandler) getServletContext().getAttribute("postRestHandler");
        this.putRestHandler = (RestApiHandler) getServletContext().getAttribute("putRestHandler");
        this.deleteRestHandler = (RestApiHandler) getServletContext().getAttribute("deleteRestHandler");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
            String user_response = getRestHandler.handleRestRequest(pathInfo).orElseThrow(SQLException::new);
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.write(user_response);

        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            PrintWriter out = resp.getWriter();
            if (pathInfo.contains("coordinators")) {
                resp.getWriter().write(COORDINATORS_NOT_FOUND + e.getMessage());
            } else if (pathInfo.contains("courses")) {
                resp.getWriter().write(COURSES_NOT_FOUND + e.getMessage());
            }else  if (pathInfo.contains("coordinator")) {
                resp.getWriter().write(COORDINATOR_NOT_FOUND + e.getMessage());
            } else if (pathInfo.contains("student")) {
                resp.getWriter().write(STUDENT_NOT_FOUND + e.getMessage());
            }
            resp.setStatus(404);

        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            resp.setStatus(500);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        try {
            int generated_id = postRestHandler.handleRestRequest(pathInfo, req);

            if (pathInfo.contains("coordinator")) {
                resp.getWriter().write(String.format(COORDINATOR_CREATED_SUCCESS_JSON, generated_id));
            } else if (pathInfo.contains("student_course")) {
                resp.getWriter().write(String.format(STUDENT_COURSE_CREATED_SUCCESS_JSON, generated_id));
            }else if (pathInfo.contains("course")) {
                resp.getWriter().write(String.format(COURSE_CREATED_SUCCESS_JSON, generated_id));
            }  else if (pathInfo.contains("student")) {
                resp.getWriter().write(String.format(STUDENT_CREATED_SUCCESS_JSON, generated_id));
            }
            resp.setStatus(201);
        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());

            if (pathInfo.contains("coordinator")) {
                resp.getWriter().write(COORDINATOR_NOT_CREATED + e.getMessage());
            } else if (pathInfo.contains("student_course")) {
                resp.getWriter().write(STUDENT_COURSE_NOT_CREATED + e.getMessage());
            }else if (pathInfo.contains("course")) {
                resp.getWriter().write(COURSE_NOT_CREATED + e.getMessage());
            } else if (pathInfo.contains("student")) {
                resp.getWriter().write(STUDENT_NOT_CREATED + e.getMessage());
            }
            resp.setStatus(400);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        try {
            int deleted_rows = deleteRestHandler.handleRestRequest(pathInfo, req);
            if (deleted_rows != 0) {
                resp.setStatus(200);
            } else {
                throw new SQLException("Something wrong");
            }
        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            resp.setStatus(400);
            if (pathInfo.contains("coordinator")) {
                resp.getWriter().write(COORDINATOR_NOT_DELETED + e.getMessage());
            } else if (pathInfo.contains("course")) {
                resp.getWriter().write(COURSE_NOT_DELETED + e.getMessage());
            } else if (pathInfo.contains("student")) {
                resp.getWriter().write(STUDENT_NOT_DELETED + e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Servlet catch request method: [{}] with URI: [{}]", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        try {
           int updated_rows = putRestHandler.handleRestRequest(pathInfo, req);
            if (updated_rows != 0) {
                resp.setStatus(200);
            } else {
                throw new SQLException("Something wrong");
            }
        } catch (SQLException e) {
            log.error("SQLException [{}] with request method: [{}] with URI: [{}] ",
                    e.getMessage(), req.getMethod(), req.getRequestURI());
            resp.setStatus(400);
            if (pathInfo.contains("coordinator")) {
                resp.getWriter().write(COORDINATOR_NOT_UPDATED + e.getMessage());
            } else if (pathInfo.contains("course")) {
                resp.getWriter().write(COURSE_NOT_UPDATED + e.getMessage());
            } else if (pathInfo.contains("student")) {
                resp.getWriter().write(STUDENT_NOT_UPDATED + e.getMessage());
            }
        }
    }
}