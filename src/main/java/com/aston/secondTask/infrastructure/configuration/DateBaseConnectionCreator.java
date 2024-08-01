package com.aston.secondTask.infrastructure.configuration;

import java.sql.Connection;

public class DateBaseConnectionCreator {
   public static Connection getConnection() {
        return DatabaseConnector.getInstance().getConnection();
    }
}
