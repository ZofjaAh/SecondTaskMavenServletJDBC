package com.aston.secondTask.infrastructure.security.securityService;

import com.aston.secondTask.infrastructure.security.securityRepositories.UserEntity;
import com.aston.secondTask.infrastructure.security.securityService.dao.UserDAO;
import com.aston.secondTask.service.exeptions.AuthorisationException;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.Optional;
@AllArgsConstructor
public class UserRegistrationService implements Registration{
     private final static String EMAIL_ALREADY_REGISTERED_MSG = "Пользователь с таким email уже зарегистрирован !";
    private UserDAO userDAO;

    @Override
    public int userRegistration(String username, String email, String password)
            throws SQLException, AuthorisationException {

        final Optional<UserEntity> foundUserByEmail = userDAO.findByEmail(email);
        if (foundUserByEmail.isPresent()) {
            throw new AuthorisationException(EMAIL_ALREADY_REGISTERED_MSG);
        }

        UserEntity user = new UserEntity();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(DigestUtils.md5Hex(password));

        return userDAO.insertUser(user);
    }
}
