package com.aston.secondTask.servlets;

import com.aston.secondTask.infrastructure.configuration.JDBCConfiguration;
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
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {

        private Authentication authentication;
        private Registration registration;
        private CoordinatorDAO coordinatorRepository;
        private StudentDAO studentRepository;
        private CourseDAO courseRepository;
        private RestApiHandler getRestHandler;
        private RestApiHandler postRestHandler;
        private RestApiHandler putRestHandler;
        private RestApiHandler deleteRestHandler;

        @Override
        public void contextInitialized(ServletContextEvent servletContextEvent) {

            final ServletContext servletContext =
                    servletContextEvent.getServletContext();

            DataSource dataSource = JDBCConfiguration.getHikariDataSource();
            SessionManager sessionManager = new SessionManagerJDBC(dataSource);
            UserDAO userDaoJDBC = new UserRepository(sessionManager);
            this.courseRepository = new CourseRepository(sessionManager);
            this.coordinatorRepository = new CoordinatorRepository(sessionManager);
            this.studentRepository = new StudentRepository(sessionManager);
            authentication = new UserAuthenticationService(userDaoJDBC);
            registration = new UserRegistrationService(userDaoJDBC);

            getRestHandler = new GetRestHandler(coordinatorRepository, studentRepository,courseRepository);
            postRestHandler = new PostRestHandler(coordinatorRepository, studentRepository,courseRepository);
            putRestHandler = new PutRestHandler(coordinatorRepository, studentRepository,courseRepository);
            deleteRestHandler = new DeleteRestHandler(coordinatorRepository, studentRepository,courseRepository);

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

        @Override
        public void contextDestroyed(ServletContextEvent sce) {

        }
    }

