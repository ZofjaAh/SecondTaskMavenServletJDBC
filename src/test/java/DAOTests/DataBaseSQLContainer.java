package DAOTests;

import com.aston.secondTask.service.exeptions.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Testcontainers
public class DataBaseSQLContainer {
    static final String DB_PROPERTIES_PATH = "src/test/resources/db.properties";


    static final Properties dbProperties = new Properties();
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName(getPropertySource("database_name"))
            .withUsername(getPropertySource("username"))
            .withPassword(getPropertySource("password"));

    static String getPropertySource(String source) {
        try {
            dbProperties.load(new FileInputStream(DB_PROPERTIES_PATH));
            return dbProperties.getProperty(source);
        } catch (IOException e) {
            log.error("Cannot load db.properties [{}}", e.getMessage());
            throw new ProcessingException(e.getMessage());
        }
    }

    public void overwriteURL(PostgreSQLContainer<?> postgreSQLContainer) {
        try {
            dbProperties.load(new FileInputStream(DB_PROPERTIES_PATH));
            String regExString = "[0-9]{4,}";
            Pattern pattern = Pattern.compile(regExString);
            Matcher mat = pattern.matcher(postgreSQLContainer.getJdbcUrl());

            String dbUrl = dbProperties.getProperty("url");
            if (mat.find() && !dbUrl.contains(mat.group())) {
                dbUrl = dbUrl.replaceAll(regExString, mat.group());
                dbProperties.setProperty("url", dbUrl);
                dbProperties.store(new FileOutputStream(DB_PROPERTIES_PATH), null);
            }

        } catch (IOException e) {
            log.error("Cannot load or save db.properties.");
            log.error(e.getMessage(), e);
        }

    }

    public static void createTestTables() {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE coordinator (coordinator_id SERIAL NOT NULL, name VARCHAR(32) NOT NULL,
                    PRIMARY KEY (coordinator_id), UNIQUE(name));
                    """);
            statement.execute("""
                    CREATE TABLE course (course_id SERIAL NOT NULL, name VARCHAR(32) NOT NULL, PRIMARY KEY (course_id),
                    UNIQUE (name));
                    """);
            statement.execute("""
                    CREATE TABLE student (student_id SERIAL NOT NULL, name VARCHAR(32) NOT NULL, coordinator_id INT,
                    PRIMARY KEY (student_id), UNIQUE(name), CONSTRAINT fk_student_coordinator
                    FOREIGN KEY (coordinator_id) REFERENCES coordinator (coordinator_id));
                    """);
            statement.execute("""
                    CREATE TABLE student_course (id SERIAL NOT NULL, student_id INT NOT NULL, course_id INT NOT NULL,
                    PRIMARY KEY (id), CONSTRAINT fk_student_course_student FOREIGN KEY (student_id)
                    REFERENCES student(student_id), CONSTRAINT fk_student_course_course FOREIGN KEY (course_id)
                     REFERENCES course(course_id));
                    """);
            statement.execute("""
                    INSERT INTO coordinator (coordinator_id, name) VALUES (1, 'Shopen'), (2, 'Mocart');
                    """);
            statement.execute("""
                    SELECT SETVAL('coordinator_coordinator_id_seq', 2);
                    """);
            statement.execute("""
                    INSERT INTO course (course_id, name) VALUES (1, 'Play guitar'), (2, 'Play pianoforte');
                    """);
            statement.execute("""
                    SELECT SETVAL('course_course_id_seq', 2);
                    """);
            statement.execute("""
                    INSERT INTO student (student_id, name, coordinator_id) VALUES (1, 'Ivan Goodhear', 2),
                    (2, 'Nikol BadVoice', 1), (3, 'Ihor Nothear', 1);
                    """);
            statement.execute("""
                    SELECT SETVAL('student_student_id_seq', 3);
                    """);
            statement.execute("""
                    INSERT INTO student_course (id, student_id, course_id) VALUES (1, 1, 2), (2, 1, 1), (3, 2, 1);
                    """);
            statement.execute("""
                    SELECT SETVAL('student_course_id_seq', 3);
                    """);
        } catch (SQLException e) {
            log.error("Something wrong with loading test tables [{}] [{}]", e.getMessage(), e.getSQLState());

        }
    }

    public static void dropAllTestTables() {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS student_course CASCADE; ");
            statement.execute("DROP TABLE IF EXISTS course CASCADE;");
            statement.execute("DROP TABLE IF EXISTS student CASCADE;");
            statement.execute("DROP TABLE IF EXISTS coordinator CASCADE;");
        } catch (SQLException e) {
           log.error("SQLException with drop all tables [{}]", e.getMessage());
           throw new ProcessingException(e.getMessage());
        }


    }
}

