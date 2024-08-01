package com.aston.secondTask.servlets;

import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.infrastructure.configuration.SessionManagerJDBC;
import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import com.aston.secondTask.infrastructure.repository.CourseRepository;
import com.aston.secondTask.infrastructure.repository.StudentRepository;
import com.aston.secondTask.infrastructure.security.securityRepositories.UserRepository;
import com.aston.secondTask.infrastructure.security.securityService.Authentication;
import com.aston.secondTask.infrastructure.security.securityService.Registration;
import com.aston.secondTask.infrastructure.security.securityService.UserAuthenticationService;
import com.aston.secondTask.infrastructure.security.securityService.UserRegistrationService;
import com.aston.secondTask.infrastructure.security.securityService.dao.UserDAO;
import com.aston.secondTask.service.DAO.CoordinatorDAO;
import com.aston.secondTask.service.DAO.CourseDAO;
import com.aston.secondTask.service.DAO.StudentDAO;
import com.aston.secondTask.servlets.RestHandlers.*;

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
        UserDAO userDaoJDBC = new UserRepository(sessionManager);
        CourseDAO courseRepository = new CourseRepository(sessionManager);
        CoordinatorDAO coordinatorRepository = new CoordinatorRepository(sessionManager);
        StudentDAO studentRepository = new StudentRepository(sessionManager);
        Authentication authentication = new UserAuthenticationService(userDaoJDBC);
        Registration registration = new UserRegistrationService(userDaoJDBC);

        RestApiHandler getRestHandler = new GetRestHandler(coordinatorRepository, studentRepository, courseRepository);
        RestApiHandler postRestHandler = new PostRestHandler(coordinatorRepository, studentRepository, courseRepository);
        RestApiHandler putRestHandler = new PutRestHandler(coordinatorRepository, studentRepository, courseRepository);
        RestApiHandler deleteRestHandler = new DeleteRestHandler(coordinatorRepository, studentRepository, courseRepository);

            servletContext.setAttribute("authentication", authentication);
            servletContext.setAttribute("registration", registration);
            servletContext.setAttribute("courseRepository", courseRepository);
            servletContext.setAttribute("coordinatorRepository", coordinatorRepository);
            servletContext.setAttribute("studentRepository", studentRepository);
            servletContext.setAttribute("getRestHandler", getRestHandler);
            servletContext.setAttribute("postRestHandler", postRestHandler);
            servletContext.setAttribute("putRestHandler", putRestHandler);
            servletContext.setAttribute("deleteRestHandler", deleteRestHandler);
        }

}

