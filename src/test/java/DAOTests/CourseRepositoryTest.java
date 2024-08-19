package DAOTests;

import com.aston.secondTask.entities.CourseEntity;
import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.CourseRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.lifecycle.Startables;
import util.EntityFixtures;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class CourseRepositoryTest extends DataBaseSQLContainer {
    @InjectMocks
    CourseRepository courseRepository;
    @Mock
    DateBaseConnectionCreator dateBaseConnectionCreator;

    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgreSQLContainer);
    }


    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();

    }

    @BeforeEach
    void setup() {

        dateBaseConnectionCreator = Mockito.mock(DateBaseConnectionCreator.class);
        courseRepository = new CourseRepository(dateBaseConnectionCreator);
        super.overwriteURL(postgreSQLContainer);
        Assertions.assertTrue(postgreSQLContainer.isRunning());
        Assertions.assertTrue(postgreSQLContainer.isCreated());
        createTestTables();

    }

    @AfterEach
    void afterEach() {
        dropAllTestTables();
    }

    @Test
    void getAllCourses() throws SQLException {
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));

        Set<CourseEntity> result = courseRepository.findAll();
        assertEquals(2, result.size());

    }

    @Test
    void createCourseSuccessful() throws SQLException {
        int courseId = 3;
        CourseEntity courseEntity = EntityFixtures.course_1();
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = courseRepository.createCourse(courseEntity);
        assertEquals(courseId, result);

    }

    @Test
    void deleteCourseById() throws SQLException {
        int courseId = 1;
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = courseRepository.deleteCourse(courseId);
        assertEquals(3, result);

    }

    @Test
    void updateCourseName() throws SQLException {
        int courseId = 1;
        String courseName = "Dancing";
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = courseRepository.updateCourseName(courseId, courseName);
        assertEquals(1, result);
    }

}
