package com.aston.secondTask.infrastructure.configuration;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for creating database connections.
 */
@NoArgsConstructor
public class DateBaseConnectionCreator {
    /**
     * Gets a new connection to the database.
     *
     * @return the database connection
     * @throws RuntimeException if a database access error occurs
     */
    public Connection getConnection() {
        try {
            return DatabaseConnector.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get a new connection", e);
        }


    }

}
