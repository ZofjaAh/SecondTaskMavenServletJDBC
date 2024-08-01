package com.aston.secondTask.servlets;

import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.infrastructure.configuration.SessionManagerJDBC;
import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import com.aston.secondTask.infrastructure.repository.CourseRepository;
import com.aston.secondTask.infrastructure.repository.StudentRepository;
import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.service.StudentService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        final ServletContext servletContext =
                servletContextEvent.getServletContext();
        SessionManager sessionManager = new SessionManagerJDBC();
        CourseDAO courseRepository = new CourseRepository(sessionManager);
        CoordinatorDAO coordinatorRepository = new CoordinatorRepository(sessionManager);
        StudentDAO studentRepository = new StudentRepository(sessionManager);
        CoordinatorService coordinatorService = new CoordinatorService(coordinatorRepository);
        StudentService studentService = new StudentService(studentRepository);
        CourseService courseService = new CourseService(courseRepository);

        RestApiHandler getRestHandler = new GetRestHandler(coordinatorService, studentService, courseService);
        RestApiHandler postRestHandler = new PostRestHandler(coordinatorService, studentService, courseService);
        RestApiHandler putRestHandler = new PutRestHandler(coordinatorService, studentService, courseService);
        RestApiHandler deleteRestHandler = new DeleteRestHandler(coordinatorService, studentService, courseService);

        servletContext.setAttribute("courseRepository", courseRepository);
        servletContext.setAttribute("coordinatorRepository", coordinatorRepository);
        servletContext.setAttribute("studentRepository", studentRepository);
        servletContext.setAttribute("courseService", courseService);
        servletContext.setAttribute("coordinatorService", coordinatorService);
        servletContext.setAttribute("studentService", studentService);
        servletContext.setAttribute("getRestHandler", getRestHandler);
        servletContext.setAttribute("postRestHandler", postRestHandler);
        servletContext.setAttribute("putRestHandler", putRestHandler);
        servletContext.setAttribute("deleteRestHandler", deleteRestHandler);
    }

}

