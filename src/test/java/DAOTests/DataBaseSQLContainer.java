package DAOTests;

import com.aston.secondTask.service.exeptions.NotFoundException;
import com.aston.secondTask.service.exeptions.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Testcontainers
@Slf4j
public class DataBaseSQLContainer {
  private static final String DB_PROPERTIES_PATH = "D:\\Java Trainee Intensive\\SecondTaskMavenServletJDBC\\src\\test\\resources\\db.properties";


    private static final Properties dbProperties = new Properties();
    private static final String TEST_DB_SCRIPT = "TEST_DB.sql";
    @Container

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
                .withDatabaseName(getPropertySource("database_name"))
                .withUsername(getPropertySource("username"))
                .withPassword(getPropertySource("password"))
                .withInitScript(TEST_DB_SCRIPT);

    private static String getPropertySource (String source) {
        try{   dbProperties.load(new FileInputStream(DB_PROPERTIES_PATH));
            return   dbProperties.getProperty(source);
        }catch (IOException e) {
            log.error("Cannot load db.properties [{}}", e.getMessage());
            e.printStackTrace();
            throw new ProcessingException(e.getMessage());
        }}
    public void overwriteURL() {
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

    }

