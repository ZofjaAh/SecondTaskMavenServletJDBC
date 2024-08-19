package com.aston.secondTask.infrastructure.configuration;

import com.aston.secondTask.service.exeptions.DatabaseConnectorException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static com.aston.secondTask.infrastructure.repository.queries.SQLInitQuery.*;

/**
 * Singleton class for managing database connections.
 */
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

    /**
     * Sets the database properties from the properties file.
     *
     * @throws IOException if an error occurs while loading properties
     */

    private void setProperties() throws IOException {
        dbProperties.load(new FileInputStream(DB_PATH));

    }

    /**
     * Loads the database driver.
     *
     * @throws ClassNotFoundException if the driver class is not found
     */

    private void loadDatabaseDriver() throws ClassNotFoundException {
        Class.forName(dbProperties.getProperty("driver"));
        log.info("Loading driver success.");

    }

    /**
     * Gets a connection to the database.
     *
     * @return the database connection
     * @throws SQLException if a database access error occurs
     */

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbProperties.getProperty("url"),
                dbProperties.getProperty("username"),
                dbProperties.getProperty("password"));
    }

    /**
     * Checks if the database already exists.
     *
     * @return true if the database exists, false otherwise
     */
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

    /**
     * Creates the database and necessary tables.
     */
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
