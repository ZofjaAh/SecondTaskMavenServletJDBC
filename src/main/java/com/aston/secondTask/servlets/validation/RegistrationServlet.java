package com.aston.secondTask.servlets.validation;

import com.aston.secondTask.infrastructure.security.securityService.Registration;
import com.aston.secondTask.service.exeptions.AuthorisationException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final String USER_REGISTRATION_SUCCESS = "Пользователь успешно зарегистрирован";
    private static final String EMAIL_ALREADY_REGISTERED = "Пользователь с таким email уже существует";
    private static final String REGISTRATION_NOT_ABLE = "К сожалению, регистрация пользователя не удалась";

    public static final String EMAIL_REGEX_MACHER =
           "/^[\\\\w._-]+[+]?[\\\\w._-]+@[\\\\w.-]+\\\\.[a-zA-Z]{2,6}$/";
    private Registration registrationService;

    @Override
    public void init() throws ServletException {
        this.registrationService = (Registration) getServletContext()
                .getAttribute("registrationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/registration.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/HTML");

        String username = req.getParameter("username");
        String email = req.getParameter("email").toLowerCase();
        String password = req.getParameter("password");
        boolean isValidateEmail = validateEmail(email);
        boolean isPasswordNotNull = Objects.nonNull(password);
        boolean isUsernameNotNull = Objects.nonNull(username);
        if (isValidateEmail && isUsernameNotNull && isPasswordNotNull) {
            try {
                registrationService.userRegistration(username, email, password);
                resp.getWriter().write(USER_REGISTRATION_SUCCESS);
            } catch (SQLException sqlException) {
                log.error("SQLException with registration new User [{}]", sqlException.getMessage());
                resp.getWriter().write(REGISTRATION_NOT_ABLE);
            }  catch (AuthorisationException authorisationException) {
                resp.getWriter().write(EMAIL_ALREADY_REGISTERED);
            }
        } else {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("validEmail", isValidateEmail);
            jsonObj.put("validPassword", isPasswordNotNull);
            jsonObj.put("validUserName", isUsernameNotNull);
            try (PrintWriter writer = resp.getWriter()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.info("Registration response: " + jsonObj);
                jsonObj.writeJSONString(writer);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private boolean validateEmail(String email) {
        log.info("User's EMAIL input: {}", email);
        return email.matches(EMAIL_REGEX_MACHER);

    }
}
