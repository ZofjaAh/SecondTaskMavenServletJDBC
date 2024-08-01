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
        UserEntity userEntity = null;
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
           PreparedStatement statement =  connection.prepareStatement(GET_USER_BY_EMAIL)) {
            statement.setString(1, email);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    userEntity = new UserEntity();
                    userEntity.setUserId(Integer.parseInt(result.getString("userEntity_id")));
                    userEntity.setUserName(result.getString("userEntity_name"));
                    userEntity.setEmail(result.getString("email"));
                    userEntity.setPassword(result.getString("password"));
                }
            }
        } catch (SQLException ex) {
            log.error("SQLException with searching by email: [{}] - [{}]", email, ex.getMessage());
            sessionManager.rollbackSession();
            throw ex;
        }

        return Optional.ofNullable(userEntity);
    }

    @Override
    public int insertUser(UserEntity userEntity) throws SQLException {
        sessionManager.beginSession();

        try (Connection connection = sessionManager.getCurrentSession();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userEntity.getUserName());
            statement.setString(2, userEntity.getPassword());
            statement.setString(3, userEntity.getEmail());

            statement.executeUpdate();
            try (ResultSet result = statement.getGeneratedKeys()) {
                result.next();
                int id = result.getInt(1);
                sessionManager.commitSession();
                return id;
            }

        } catch (SQLException ex) {
            log.error("SQLException with user creation [{}]", ex.getMessage());
            sessionManager.rollbackSession();
            throw ex;
        }
    }
}
