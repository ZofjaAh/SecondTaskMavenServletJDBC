package com.aston.secondTask.infrastructure.security.securityService;

import java.sql.SQLException;

public interface Authentication {
    boolean isAuthenticated(String username, String inputPassword) throws SQLException;
}

