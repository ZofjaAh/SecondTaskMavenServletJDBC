package com.aston.secondTask.infrastructure.configuration;

import com.aston.secondTask.service.exeptions.DatabaseConnectorException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static com.aston.secondTask.infrastructure.repository.queries.SQLInitQuery.*;

@Slf4j
@Getter
public class DatabaseConnector {

    private static volatile DatabaseConnector instance;
    private final Properties dbProperties = new Properties();
    private static final String DB_PATH = Thread.currentThread().getContextClassLoader()
            .getResource("db.properties").getPath();


    public static DatabaseConnector getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnector.class) {
                if (instance == null) {
                    instance = new DatabaseConnector();
                }
            }
        }
        return instance;
    }

    private DatabaseConnector() {
        try {
            setProperties();
            loadDatabaseDriver();
            if (isDataBaseExists()) {
                log.info("The database has already been created");
            } else {
                createDatabase();
            }

        } catch (IOException | ClassNotFoundException e) {
            log.error("Exception with connection to database [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }
    }


    private void setProperties() throws IOException{
            dbProperties.load(new FileInputStream(DB_PATH));

    }


    private void loadDatabaseDriver() throws ClassNotFoundException {
            Class.forName(dbProperties.getProperty("driver"));
            log.info("Loading driver success.");

    }

    public Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(dbProperties.getProperty("url"),
                dbProperties.getProperty("username"),
                dbProperties.getProperty("password"));
    }

    private boolean isDataBaseExists() {
        try (Connection connection = getConnection()) {
            String databaseName = connection.getCatalog();
            log.info("databaseName: [{}]", databaseName);
            log.info("databaseName with DbProperties: [{}]", dbProperties.getProperty("database_name"));

            return databaseName.equalsIgnoreCase(dbProperties.getProperty("database_name"));
        } catch (Exception e) {
            log.error("Exception with database existence check : [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }
    }

    private void createDatabase() {
        try (Connection connection = getConnection();
             Statement s = connection.createStatement()) {
            s.executeUpdate(String.valueOf(CREATE_DATABASE));
            s.executeUpdate(String.valueOf(CREATE_COORDINATOR_TABLE));
            s.executeUpdate(String.valueOf(CREATE_COURSE_TABLE));
            s.executeUpdate(String.valueOf(CREATE_STUDENT_TABLE));
            s.executeUpdate(String.valueOf(CREATE_STUDENT_COURSE_TABLE));
            log.info("Database is created.");
        } catch (SQLException e) {
            log.error("SQLException with creation database [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }

    }


}
