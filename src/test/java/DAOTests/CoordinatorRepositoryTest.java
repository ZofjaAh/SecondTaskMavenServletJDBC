package DAOTests;

import com.aston.secondTask.entities.CoordinatorEntity;
import com.aston.secondTask.infrastructure.configuration.DatabaseConnector;
import com.aston.secondTask.infrastructure.repository.CoordinatorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.lenient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinatorRepositoryTest extends DataBaseSQLContainer{

    CoordinatorRepository coordinatorRepository;
    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }


@BeforeEach
void mockConnection() throws SQLException{
        lenient().when(DatabaseConnector.getInstance().getConnection())
                .thenReturn(DriverManager.getConnection(postgreSQLContainer.getJdbcUrl()
                        ,postgreSQLContainer.getUsername(),postgreSQLContainer.getPassword()));
      coordinatorRepository = new CoordinatorRepository();
    }

    @AfterAll
    public static void closeConnection() {
        try {
            DatabaseConnector.getInstance().closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



   @Test
    void getAllCoordinators()  {
       List<CoordinatorEntity> result = coordinatorRepository.findAll();
       assertEquals(3, result.size());

   }
}
