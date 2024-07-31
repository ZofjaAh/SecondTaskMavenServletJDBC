package com.aston.secondTask.infrastructure.security.securityService;

import com.aston.secondTask.service.exeptions.AuthorisationException;

import java.sql.SQLException;
public interface Registration {
    int userRegistration(String username,String email, String password)
        throws SQLException, AuthorisationException;
}

