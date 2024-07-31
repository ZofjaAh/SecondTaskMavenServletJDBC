package com.aston.secondTask.infrastructure.security.securityService.dao;

import com.aston.secondTask.infrastructure.security.securityRepositories.UserEntity;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO {
    Optional<UserEntity> findByEmail(String userEmail) throws SQLException;

    int insertUser(UserEntity user) throws SQLException;
}
