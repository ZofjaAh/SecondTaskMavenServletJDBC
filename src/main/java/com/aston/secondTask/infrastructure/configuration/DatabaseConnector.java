package com.aston.secondTask.infrastructure.configuration;

import com.aston.secondTask.service.exeptions.DatabaseConnectorException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

import static com.aston.secondTask.infrastructure.repository.queries.SQLInitQuery.*;

@Slf4j
@Getter
public class DatabaseConnector {

    private static volatile DatabaseConnector instance;
    private Connection connection;
    private final Properties dbProperties = new Properties();


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
            getConnection(dbProperties.getProperty("url"),
                    dbProperties.getProperty("username"),
                    dbProperties.getProperty("password"));

            if (isDataBaseExists()) {
                log.info("The database has already been created");
            } else {
                try (Statement s = connection.createStatement();) {
                    s.executeUpdate(String.valueOf(CREATE_DATABASE));
                    s.executeUpdate(dbProperties.getProperty("username") + "=# \\c"
                                    + dbProperties.getProperty("database_name") + ";");
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

        } catch (SQLException e) {
            log.error("SQLException with connection to database [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }
    }


    private void setProperties() {
        try {
            String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                    .getResource("db.properties")).getPath();
            dbProperties.load(new FileInputStream(path));
        } catch (IOException e) {
            log.error("IOException with loading database properties [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }

    }

    private void loadDatabaseDriver() {
        try {
            Class.forName(dbProperties.getProperty("driver"));
            log.info("Loading driver success.");
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException with registration database driver [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }
    }


    private Connection getConnection(String url, String userName, String password) throws SQLException {
        connection = DriverManager.getConnection(url, userName, password);
        return connection;
    }

    private boolean isDataBaseExists() {
        try (Statement s = connection.createStatement();) {
            ResultSet rs = connection.getMetaData().getCatalogs();

            while (rs.next()) {
                String databaseName = rs.getString("TABLE_CAT");
                if (databaseName.equalsIgnoreCase(dbProperties.getProperty("database_name"))) {
                    s.executeUpdate(dbProperties.getProperty("username") + "=# \\c" + dbProperties.getProperty("database_name") + ";");
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Exception with database existence check : [{}]", e.getMessage());
            throw new DatabaseConnectorException(e);
        }

        return false;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            instance = null;
        }
    }

}
