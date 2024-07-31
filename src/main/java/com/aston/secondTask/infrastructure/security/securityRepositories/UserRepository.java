package com.aston.secondTask.infrastructure.security.securityRepositories;

import com.aston.secondTask.infrastructure.configuration.SessionManager;
import com.aston.secondTask.infrastructure.security.securityService.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class UserRepository implements UserDAO {
    private final static String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = (?)";
    private final static String INSERT_USER =
            "INSERT into maven_servlet_jdbc_user (username, password, email) VALUES ((?), (?),(?))";

    private final SessionManager sessionManager;
    @Override
    public Optional<UserEntity> findByEmail(String email) throws SQLException {
        UserEntity dbUser = null;
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    dbUser = new UserEntity();
                    dbUser.setUserId(Integer.parseInt(result.getString("user_id")));
                    dbUser.setUserName(result.getString("user_name"));
                    dbUser.setEmail(result.getString("email"));
                    dbUser.setPassword(result.getString("password"));
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            sessionManager.rollbackSession();
            throw ex;
        }

        return Optional.ofNullable(dbUser);
    }

    @Override
    public int insertUser(UserEntity user) throws SQLException {
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                result.next();
                int id = result.getInt(1);
                sessionManager.commitSession();
                return id;
            }

        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            sessionManager.rollbackSession();
            throw ex;
        }
    }
}
