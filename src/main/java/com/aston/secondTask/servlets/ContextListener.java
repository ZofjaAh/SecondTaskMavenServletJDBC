package com.aston.secondTask.servlets;

import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import com.aston.secondTask.infrastructure.repository.CourseRepository;
import com.aston.secondTask.infrastructure.repository.StudentRepository;
import com.aston.secondTask.service.CoordinatorService;
import com.aston.secondTask.service.CourseService;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.service.StudentService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        final ServletContext servletContext =
                servletContextEvent.getServletContext();

        DateBaseConnectionCreator dateBaseConnectionCreator = new DateBaseConnectionCreator();
        CourseDAO courseRepository = new CourseRepository(dateBaseConnectionCreator);
        CoordinatorDAO coordinatorRepository = new CoordinatorRepository(dateBaseConnectionCreator);
        StudentDAO studentRepository = new StudentRepository(dateBaseConnectionCreator);
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

