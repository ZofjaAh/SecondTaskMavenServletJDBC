package com.aston.secondTask.infrastructure.configuration;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor
public class DateBaseConnectionCreator {
    public  Connection getConnection() {
        try {
            return DatabaseConnector.getInstance().getConnection();
        }catch (SQLException e){
            throw new RuntimeException("Failed to get a new connection", e);
        }


    }

}
