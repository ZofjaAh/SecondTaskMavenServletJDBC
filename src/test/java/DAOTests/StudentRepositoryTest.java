package DAOTests;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.entities.StudentEntity;
import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import com.aston.secondTask.infrastructure.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import util.EntityFixtures;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class StudentRepositoryTest extends DataBaseSQLContainer {


    @InjectMocks
    StudentRepository studentRepository;
    @Mock
    DateBaseConnectionCreator dateBaseConnectionCreator;

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgreSQLContainer);
    }

    @BeforeEach
    void setup() {
        dateBaseConnectionCreator = Mockito.mock(DateBaseConnectionCreator.class);
        studentRepository = new StudentRepository(dateBaseConnectionCreator);
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
    void getStudentWithCoursesById() throws SQLException {
        int studentId = 1;
        StudentEntity student = EntityFixtures.studentWithCourses_1();
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        StudentEntity result = studentRepository.findById(studentId);
        assertEquals(student.getId(), result.getId());
        assertEquals(student.getName(), result.getName());
        assertEquals(student.getCoordinator().getId(), result.getCoordinator().getId());
        assertEquals(student.getCourses().size(), result.getCourses().size());

    }

    @Test
    void createStudentWithCoordinatorSuccessful() throws SQLException {
        int studentId = 4;
        int coordinatorId = 2;
        StudentEntity studentEntity = EntityFixtures.student_4();
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = studentRepository.createStudentWithCoordinator(studentEntity, coordinatorId);
        assertEquals(studentId, result);

    }

    @Test
    void deleteStudentById() throws SQLException {
        int studentId = 1;
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = studentRepository.deleteStudent(studentId);
        assertEquals(3, result);

    }

    @Test
    void updateCoordinatorName() throws SQLException {
        int studentId = 1;
        int coordinatorId = 1;
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = studentRepository.updateCoordinator(studentId,coordinatorId);
        assertEquals(1, result);
    }
    @Test
    void addCourseForStudentSuccessful() throws SQLException {
        int studentId = 3;
        int courseId = 2;
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = studentRepository.addCourse(studentId,courseId);
        assertEquals(1, result);
    }

}
