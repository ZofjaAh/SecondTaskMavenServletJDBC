package com.aston.secondTask.infrastructure.security.securityService;

import com.aston.secondTask.infrastructure.security.securityRepositories.UserEntity;
import com.aston.secondTask.infrastructure.security.securityService.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class UserAuthenticationService implements Authentication {
    private UserDAO userDAO;

    @Override
    public boolean isAuthenticated(String userEmail, String inputPassword) throws SQLException {
           final Optional<UserEntity> optionalUser = userDAO.findByEmail(userEmail);
            if (optionalUser.isPresent()) {
                final UserEntity user = optionalUser.get();
                return user.getPassword().equals(DigestUtils.md5Hex(inputPassword));
            } else
                return false;
          }
}
