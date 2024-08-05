package DAOTests;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.infrastructure.configuration.DateBaseConnectionCreator;
import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import util.EntityFixtures;

import java.sql.*;
import java.util.List;


import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CoordinatorRepositoryTest extends DataBaseSQLContainer {


    @InjectMocks
    CoordinatorRepository coordinatorRepository;
    @Mock
    DateBaseConnectionCreator dateBaseConnectionCreator;

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

@BeforeAll
static void beforeAll(){
    Startables.deepStart(postgreSQLContainer);
}
    @BeforeEach
    void setup() {

        dateBaseConnectionCreator = Mockito.mock(DateBaseConnectionCreator.class);
        coordinatorRepository = new CoordinatorRepository(dateBaseConnectionCreator);
        super.overwriteURL(postgreSQLContainer);
        Assertions.assertTrue(postgreSQLContainer.isRunning());
        Assertions.assertTrue(postgreSQLContainer.isCreated());
        createTestTables();
    }
    @AfterEach
    void afterEach(){
        dropAllTestTables();
    }

    @Test
    void getAllCoordinators() throws SQLException {
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));

        List<CoordinatorEntity> result = coordinatorRepository.findAll();
        assertEquals(2, result.size());

    }

    @Test
    void getCoordinatorWithStudentsById() throws SQLException {
        int coordinatorId = 1;
        CoordinatorEntity coordinator = EntityFixtures.coordinatorWithStudents_1();
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        CoordinatorEntity result = coordinatorRepository.findCoordinatorWithStudentsByID(coordinatorId);
        assertEquals(coordinator.getId(), result.getId() );
        assertEquals(coordinator.getName(), result.getName() );
        assertEquals(coordinator.getStudents().size(), result.getStudents().size() );

    }
    @Test
    void createCoordinatorSuccessful() throws SQLException {
        int coordinatorId = 3;
        CoordinatorEntity coordinatorEntity = EntityFixtures.coordinator_2();
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = coordinatorRepository.createCoordinator(coordinatorEntity);
        assertEquals(coordinatorId, result);

    }
    @Test
    void deleteCoordinatorById() throws SQLException {
        int coordinatorId = 1;
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = coordinatorRepository.deleteById(coordinatorId);
        assertEquals(3, result);

    }
    @Test
    void updateCoordinatorName() throws SQLException {
        int coordinatorId = 1;
        String coordinatorName = "Stefan";
        Mockito.when(dateBaseConnectionCreator.getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));
        int result = coordinatorRepository.updateCoordinatorName(coordinatorId, coordinatorName);
        assertEquals(1, result);
    }

}
