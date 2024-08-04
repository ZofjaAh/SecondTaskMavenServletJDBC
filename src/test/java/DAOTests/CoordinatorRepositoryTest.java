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
import org.testcontainers.lifecycle.Startables;

import java.sql.*;
import java.util.List;


import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class CoordinatorRepositoryTest extends DataBaseSQLContainer{
@InjectMocks
    CoordinatorRepository coordinatorRepository;
@Mock
    DateBaseConnectionCreator dateBaseConnectionCreator;
    @BeforeAll
    static void beforeAll()
    {
        Startables.deepStart(postgreSQLContainer);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

@BeforeEach
 void setup(){
   dateBaseConnectionCreator = Mockito.mock(DateBaseConnectionCreator.class);
    coordinatorRepository = new CoordinatorRepository(dateBaseConnectionCreator);
    super.overwriteURL();
    Assertions.assertTrue(postgreSQLContainer.isRunning());
    Assertions.assertTrue(postgreSQLContainer.isCreated());

}

@Test

    void getAllCoordinators() throws SQLException {
       Mockito.when(dateBaseConnectionCreator.getConnection())
               .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                       postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()));

       List<CoordinatorEntity> result = coordinatorRepository.findAll();
       assertEquals(2, result.size());

   }
}
