package com.aston.secondTask.infrastructure.configuration;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor
public class DateBaseConnectionCreator {
    public  Connection getConnection() {

        return DatabaseConnector.getInstance().getConnection();
    }
    public void closeConnection() throws SQLException {
        DatabaseConnector.getInstance().closeConnection();
    }
}
