package com.aston.secondTask.infrastructure.configuration;

import com.aston.secondTask.service.exeptions.DatabaseConnectorException;
import com.aston.secondTask.service.exeptions.SessionManagerException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
@Slf4j
public class SessionManagerJDBC implements SessionManager {

        private static final int TIMEOUT_IN_SECONDS = 10;

        private Connection connection;


        @Override
        public Connection getCurrentSession() {
            checkConnection();
            return connection;
        }

        @Override
        public void beginSession() {
                connection = DateBaseConnectionCreator.getConnection();
        }

        @Override
        public void commitSession() {
            checkConnection();
            try {
                connection.commit();
            } catch (SQLException e) {
                log.error("SQLException with checking connection [{}]", e.getMessage());
                throw new SessionManagerException(e);
            }
        }

        @Override
        public void rollbackSession() {
            checkConnection();
            try {
                connection.rollback();
            } catch (SQLException e) {
                log.error("SQLException with rollback session [{}]", e.getMessage());
                throw new SessionManagerException(e);
            }
        }

        @Override
        public void close() {
            checkConnection();
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("SQLException with close connection [{}]", e.getMessage());
                throw new SessionManagerException(e);
            }
        }

        private void checkConnection() {
            try {
                if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
                    log.error("Connection is invalid");
                    throw new SessionManagerException("Connection is invalid");
                }
            } catch (SQLException ex) {
                log.error("SQLException with checking connection [{}]", ex.getMessage());
                throw new SessionManagerException(ex);
            }
        }
    }


