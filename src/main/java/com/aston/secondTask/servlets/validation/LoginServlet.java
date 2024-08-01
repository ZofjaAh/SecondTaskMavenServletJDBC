package com.aston.secondTask.servlets.validation;

import com.aston.secondTask.infrastructure.security.securityService.Authentication;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet
@Slf4j
public class LoginServlet extends HttpServlet {
        private Authentication authenticationService;

        @Override
        public void init() {
            this.authenticationService = (Authentication) getServletContext().getAttribute("userAuthService");
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            request.getRequestDispatcher("/WEB-INF/view/login.html").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            try {
                if (authenticationService.isAuthenticated(email, password)) {
                    final HttpSession userSession = request.getSession();
                    userSession.setAttribute("email", email);
                    response.sendRedirect("/tasksmenu");
                } else {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/HTML");
                    response.getWriter().write("Неверный логин пароль");
                }
            } catch (SQLException e) {
                log.error("SQLException with authentication [{}]", e.getMessage());
                throw new RuntimeException(e);
            }

        }

    }

